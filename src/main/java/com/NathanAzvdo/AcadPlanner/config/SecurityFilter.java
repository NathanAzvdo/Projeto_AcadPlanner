package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.FilterException;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private TokenService tokenService;
    private UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            logger.debug("Iniciando filtro de autenticação para: " + request.getRequestURI());

            var token = this.recoverToken(request);
            if (token == null) {
                logger.debug("Token ausente, seguindo sem autenticação");
                filterChain.doFilter(request, response);
                return;
            }

            logger.debug("Token recebido: " + token);

            var subject = Optional.ofNullable(tokenService.validateToken(token))
                    .orElseThrow(() -> new SecurityException("Token inválido ou expirado"));

            logger.debug("Token válido, subject extraído: " + subject);

            if (subject.isBlank()) {
                throw new SecurityException("Token com ID vazio");
            }

            User user = userRepository.findById(Long.valueOf(subject))
                    .orElseThrow(() -> new SecurityException("Usuário não encontrado!"));

            logger.debug("Usuário autenticado: " + user.getEmail());

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Erro durante o filtro de autenticação para rota " + request.getRequestURI(), e);
            throw new FilterException("Erro na autenticação, entre em contato com o suporte");
        }
    }



    private String recoverToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization == null) return null;
        return authorization.replace("Bearer", "").trim();
    }
}
