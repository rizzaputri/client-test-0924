package com.enigma.maju_mundur_eshop.security;

import com.enigma.maju_mundur_eshop.dto.response.JWTClaims;
import com.enigma.maju_mundur_eshop.entity.UserAccount;
import com.enigma.maju_mundur_eshop.service.JWTService;
import com.enigma.maju_mundur_eshop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    final String AUTH_HEADER = "Authorization";
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // 1. Mendapatkan JWT dari Header Request
            String bearerToken = request.getHeader(AUTH_HEADER);

            // 2. Pengecekan apakah Bearer Token tidak null dan valid
            if (bearerToken != null && jwtService.validateToken(bearerToken)) {
                // 3. Jika valid, JWT diklaim, dimana nanti ada User Account Id dan roles
                JWTClaims decodeJWT = jwtService.getClaimsByToken(bearerToken);

                // 4. Mencari User Account berdasarkan User Account Id
                UserAccount userAccountBySub = userService.getByUserId(decodeJWT.getUserAccountId());

                // 5. Mendaftarkan User Account ke Authentication agar dikelola oleh Spring Security
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userAccountBySub.getUsername(),
                        null,
                        userAccountBySub.getAuthorities());

                // 6. Menyimpan info tambahan di Security Context
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}" + e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
