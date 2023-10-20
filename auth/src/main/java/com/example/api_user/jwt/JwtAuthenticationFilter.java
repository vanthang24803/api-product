package com.example.api_user.jwt;

import com.example.api_user.security.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)&&bearerToken.startsWith(("Bearer "))){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
          String jwt = getJwtFromRequest(request);

          if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
              String username = jwtTokenProvider.getUserNameFromJWT(jwt);
              UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
              if(userDetails != null) {
                  UsernamePasswordAuthenticationToken
                          authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }
          }
      }
      catch (Exception e){
          log.error(e.getMessage());
      }
        filterChain.doFilter(request, response);
    }

}
