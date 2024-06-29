package likelion.ideateam3.happy_board.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import likelion.ideateam3.happy_board.domain.member.MemberPrincipal;
import likelion.ideateam3.happy_board.domain.member.RoleType;
import io.jsonwebtoken.Claims;
import likelion.ideateam3.happy_board.service.member.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider implements AuthenticationProvider {
    private final MemberDetailsService memberDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createToken(String email, RoleType role) {
        Claims claims = Jwts.claims().setSubject("ACCESS_TOKEN");
        claims.put("email", email);
        claims.put("role", role);

        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                // TODO. 만료 시간 30분으로 해둠. 필요 시 추후 변경
                .setExpiration(new Date(now.getTime() + 30*60*1000L))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("email");
        } catch (ExpiredJwtException e) {
            log.error("JWT가 만료되었습니다.");
        } catch (Exception e) {
            log.error("토큰이 유효하지 않습니다.");
        }
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberPrincipal memberPrincipal = (MemberPrincipal) memberDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        return new UsernamePasswordAuthenticationToken(memberPrincipal, "", memberPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
