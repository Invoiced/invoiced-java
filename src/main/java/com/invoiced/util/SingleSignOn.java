package com.invoiced.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.invoiced.exception.SingleSignOnException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public final class SingleSignOn {

    private final String ssoKey;

    public SingleSignOn(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    public String generateToken(int customerId, int ttlSeconds) throws SingleSignOnException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.ssoKey);

            long expiresAtMs = (long) (System.currentTimeMillis() + ttlSeconds * 1000.0);
            Date expiresAt = new Date(expiresAtMs);

            return JWT.create()
                    .withIssuer("Invoiced Java")
                    .withIssuedAt(new Date())
                    .withSubject(Integer.toString(customerId))
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            throw new SingleSignOnException(e);
        } catch (UnsupportedEncodingException e) {
            throw new SingleSignOnException(e);
        }
    }
}
