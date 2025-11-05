package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            logger.debug("Iniciando filtro de autenticação para: " + request.getRequestURI());

            String token = this.recoverToken(request);
            if (token == null) {
                logger.debug("Token ausente, seguindo sem autenticação");
                filterChain.doFilter(request, response);
                return;
            }

            logger.debug("Token recebido (primeiros 20 chars): " + token.substring(0, Math.min(20, token.length())));

            String subject = tokenService.validateToken(token);
            if (subject == null || subject.isBlank()) {
                logger.warn("Token inválido ou expirado para rota: " + request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            logger.debug("Token válido, subject extraído: " + subject);

            Long userId;
            try {
                userId = Long.valueOf(subject);
            } catch (NumberFormatException e) {
                logger.error("Subject do token não é um número válido: " + subject);
                filterChain.doFilter(request, response);
                return;
            }

            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                logger.warn("Usuário não encontrado para ID: " + userId);
                filterChain.doFilter(request, response);
                return;
            }

            logger.debug("Usuário autenticado com sucesso: " + user.getEmail());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("Erro inesperado no filtro de autenticação para rota: " + request.getRequestURI(), e);
            // Não lança exception, apenas loga e continua sem autenticação
            filterChain.doFilter(request, response);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || authorization.isBlank()) {
            return null;
        }
        if (!authorization.startsWith("Bearer ")) {
            logger.warn("Header Authorization não começa com 'Bearer '");
            return null;
        }
        return authorization.replace("Bearer ", "").trim();
    }
}