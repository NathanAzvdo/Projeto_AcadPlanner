package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService{


    private final String secret = System.getenv("JWT_SECRET");

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("AcadPlanner")
                    .withSubject(user.getEmail())
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
            return "";
        }
    }

}
