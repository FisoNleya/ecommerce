package com.manica.productscatalogue.auth;



import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.ValidUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Value("${user.service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if ((request.getServletPath().contains("/api/v1/auth")
              ||  request.getServletPath().contains("/api/v1/variant/all")
              ||  request.getServletPath().contains("/api/v1/variant/related/")
              ||  request.getServletPath().contains("/api/v1/rating/all/variant")
              ||  request.getServletPath().contains("/api/v1/variant/item")
              ||  request.getServletPath().contains("/api/v1/variant/other/item/color")
              ||  request.getServletPath().contains("/api/v1/brand/all")
              ||  request.getServletPath().contains("/api/v1/category/all")
              ||  request.getServletPath().contains("/api/v1/order") && authHeader == null)

        ) {
            filterChain.doFilter(request, response);
            return;
        }


        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            ////////////////

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authHeader);

            HttpEntity<ValidateTokenRequest> entity = new HttpEntity<>(new ValidateTokenRequest(jwt), headers);
            ResponseEntity<ValidUser> res = restTemplate.exchange(userServiceUrl +
                    "/api/v1/users/valid", HttpMethod.POST, entity, ValidUser.class);

            ValidUser validUser = res.getBody();

            if (Objects.isNull(validUser)) {

                throw new AccessDeniedException("User not authorized");

            }

            log.info("Got the valid user :" + validUser.email());
            UserDetails userDetails = AuthenticationUser.builder()
                    .email(validUser.email())
                    .role(validUser.role())
                    .firstname(validUser.firstname())
                    .lastname(validUser.lastname())
                    .build();


            //////////////
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);


        }
        filterChain.doFilter(request, response);
    }
}
