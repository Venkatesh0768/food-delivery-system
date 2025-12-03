package org.fooddeliverysystem.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.authservice.helper.UserPrincipal;
import org.fooddeliverysystem.authservice.model.Role;
import org.fooddeliverysystem.authservice.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (!jwtService.isAccessToken(token)) {
                chain.doFilter(req, res);
                return;
            }

            try {

                Jws<Claims> jws = jwtService.parseToken(token);
                Claims claims = jws.getPayload();

                UUID userId = UUID.fromString(claims.getSubject());

                userRepository.findById(userId).ifPresent(user -> {

                    if (!user.isEnabled()) return;

                    List<SimpleGrantedAuthority> authorities =
                            user.getRoles() == null
                                    ? List.of()
                                    : user.getRoles().stream()
                                    .map(Role::getName)
                                    .map(SimpleGrantedAuthority::new)
                                    .toList();

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    new UserPrincipal(user),
                                    null,
                                    authorities
                            );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                });

            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if(request.getRequestURI().equals("/api/v1/auth/me")){
            return false;
        }

        return request.getRequestURI().startsWith("/api/v1/auth");
    }


}
