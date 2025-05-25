package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class TokenService{


    private final String secret = System.getenv("JWT_SECRET");

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("AcadPlanner")
                    .withSubject(String.valueOf(user.getId()))
                    .withClaim("email", user.getEmail())
                    .withClaim("curso", user.getCurso().getId())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
            return token;
        }catch(JWTCreationException e){
            throw new RuntimeException("Erro durante a geração do token: "+ e);
        }
    }

    private Instant getExpirationTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("AcadPlanner")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException e){
            return null;
        }
    }

    public Long getUsuarioIdFromRequest(HttpServletRequest request) {
        var token = recoverToken(request);
        var subject = validateToken(token);
        if (subject == null || subject.isBlank()) {
            throw new SecurityException("Token inválido ou ausente");
        }
        return Long.valueOf(subject);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.replace("Bearer ", "").trim();
    }

    public Long getCursoFromRequest(HttpServletRequest request) {
        var token = recoverToken(request);
        var subject = validateToken(token);
        if (subject == null || subject.isBlank()) {
            throw new SecurityException("Token inválido ou ausente");
        }
        return JWT.decode(token).getClaim("curso").asLong();
    }


}
