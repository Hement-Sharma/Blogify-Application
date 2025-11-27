package com.codeWithhHemant.blog.security;

import com.codeWithhHemant.blog.paylods.JwtErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    ApplicationContext context;


   public final ObjectMapper objectMapper = new ObjectMapper(); //to convert java object to json data

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       String authHeader = request.getHeader("Authorization");
       String token = null;
       String userName = null;

       if(authHeader != null && authHeader.startsWith("Bearer ")){

           token = authHeader.substring(7);

           try {
               userName = jwtTokenHelper.extractUserName(token);

       } catch (ExpiredJwtException e) {   //if Token expired
               JwtErrorResponse errorResponse = new JwtErrorResponse(
                       401,
                       "Unauthorized",
                       "JWT Token expired",
                       request.getRequestURI()
               );

               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.setContentType("application/json");

               objectMapper.writeValue(response.getWriter(), errorResponse); //converting java object to json(because content type is json) and writing json data using writer
           return;
        } catch (MalformedJwtException e) { // if Token broken / corrupted
               JwtErrorResponse errorResponse = new JwtErrorResponse(
                       400,
                       "Bad Request",
                       "Invalid JWT token",
                       request.getRequestURI()
               );

               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               response.setContentType("application/json");

               objectMapper.writeValue(response.getWriter(), errorResponse);
          return;
        } catch (SignatureException e) {  //if Token signature changed (someone changed data in token)
               JwtErrorResponse errorResponse = new JwtErrorResponse(
                       401,
                       "Unautorized",
                       "Invalid JWT signature",
                       request.getRequestURI()
               );

               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.setContentType("application/json");

               objectMapper.writeValue(response.getWriter(), errorResponse);
          return;
        } catch (Exception e) { //if other exception
               JwtErrorResponse errorResponse = new JwtErrorResponse(
                       400,
                       "Bad Request",
                       "Invalid Token",
                       request.getRequestURI()
               );
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               response.setContentType("application/json");

               objectMapper.writeValue(response.getWriter(), errorResponse);

           }

       }

       if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
           UserDetails userDetails = context.getBean(UserDetailsService.class).loadUserByUsername(userName);
           if(jwtTokenHelper.validateToken(token,userDetails)){
               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
          //continueing the filter chain. now  if because of any reason the authetication object not set in the security context that may be reaseons:- AuthHeader is not sended by user or token is not sending or may be tokenValidation Fail  then spring security sends default message with 401(unauthorize)  when it will excute the nextFilter which requres autheticatioin object but i have used AuthenticationEntryPoint if now the spring security not send the default message but it will call that and response will be given by EntryPoint. Or if user is sending token then if token has some problems that related exceptions we have handled above like malformed,invlidsign,
       filterChain.doFilter(request,response);
    }
}


