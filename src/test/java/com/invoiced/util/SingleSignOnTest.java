package com.invoiced.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.invoiced.exception.SingleSignOnException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SingleSignOnTest {

    @Test
    public void testGenerateToken() {
        String secret = "8baa4dbc338a54bbf7696eef3ee4aa2daadd61bba85fcfe8df96c7cfa227c43";
        SingleSignOn sso = new SingleSignOn(secret);
        String token = null;
        try {
            token = sso.generateToken(1234, 3600);
        } catch (SingleSignOnException e) {
            e.printStackTrace();
            fail();
        }

        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            fail();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail();
        }

        JWTVerifier verifier = JWT.require(algorithm).withIssuer("Invoiced Java").build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals("1234", jwt.getSubject());
    }
}
