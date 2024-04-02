package np.com.bigbracktes.jobhive.config.securities;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import np.com.bigbracktes.jobhive.auth.CustomUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.refreshToken.secretKey}")
    private String refreshTokenSecretKey;

    @Value("${jwt.accessToken.secretKey}")
    private String accessTokenSecretKey;

    @Value("${jwt.refreshToken.expiresIn}")
    private Long refreshTokenExpireInMs;

    @Value("${jwt.accessToken.expiresIn}")
    private Long accessTokenExpireInMs;

    public String generateAccessToken(Authentication authentication) {
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpireInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUser().getId()))
                .setIssuedAt(expiryDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.accessTokenSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateRefreshToken(Authentication authentication) {
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpireInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUser().getId()))
                .setIssuedAt(expiryDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, refreshTokenSecretKey)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateAccessToken(String accessToken)
            throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedOperationException, IllegalArgumentException {
        Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(accessToken);
        return true;
    }


    public boolean validateRefreshToken(String refreshToken)
            throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedOperationException, IllegalArgumentException {
        Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken);
        return true;
    }
}
