package org.fooddeliverysystem.authservice.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.authservice.model.Provider;
import org.fooddeliverysystem.authservice.model.RefreshToken;
import org.fooddeliverysystem.authservice.model.User;
import org.fooddeliverysystem.authservice.repositories.RefreshTokenRepository;
import org.fooddeliverysystem.authservice.repositories.UserRepository;
import org.fooddeliverysystem.authservice.security.JwtService;
import org.fooddeliverysystem.authservice.service.CookieService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    @Value("${app.auth.success-redirect}")
    private String frontendRedirectURL;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Identifying provider
        String registrationId = "unknown";
        if (authentication instanceof OAuth2AuthenticationToken token) {
            registrationId = token.getAuthorizedClientRegistrationId();
        }

        logger.debug("OAuth2 user attributes: {}", oAuth2User.getAttributes());

        User user;
        switch (registrationId) {
            case "google" -> {
                String googleId = oAuth2User.getAttributes()
                        .getOrDefault("sub", "")
                        .toString();
                String email = oAuth2User.getAttributes()
                        .getOrDefault("email", "")
                        .toString();
                String name = oAuth2User.getAttributes()
                        .getOrDefault("name", "")
                        .toString();
                String image = oAuth2User.getAttributes()
                        .getOrDefault("picture", "")
                        .toString();
                user = saveOrUpdateOAuth2User(googleId, email, name, image, Provider.GOOGLE);
            }
            case "github" -> {
                String githubId = String.valueOf(
                        oAuth2User.getAttributes().getOrDefault("id", "")
                );
                String email = (String) oAuth2User.getAttributes().get("email");
                String name = (String) oAuth2User.getAttributes().get("login");
                if (email == null) {
                    email = name + "@github.com";
                }
                String avatarUrl = oAuth2User.getAttributes()
                        .getOrDefault("avatar_url", "")
                        .toString();
                user = saveOrUpdateOAuth2User(githubId, email, name, avatarUrl, Provider.GITHUB);
            }
            default -> throw new RuntimeException("Unsupported provider: " + registrationId);
        }

        String jti = UUID.randomUUID().toString();

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .revoked(false)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, jti);

        cookieService.attachRefreshCookie(
                response,
                refreshToken,
                (int) jwtService.getRefreshTtlSeconds()
        );

        response.sendRedirect(frontendRedirectURL);
    }

    private User saveOrUpdateOAuth2User(String providerId, String email, String name, String image, Provider provider) {

        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .providerId(providerId)
                                .email(email)
                                .name(name)
                                .provider(provider)
                                .image(image)
                                .enabled(true)
                                .password("")
                                .build()
                ));
    }

}