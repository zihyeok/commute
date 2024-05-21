package hos.acorns.work.global.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String salt;

    private Key secretKey;

    private final JpaUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(Long idx, int roles) {
        Claims claims = Jwts.claims();
        		claims.put("userIdx", idx);
        		claims.put("role", roles);
		//현재시간
		long curTime = System.currentTimeMillis();
        return Jwts.builder()
        		// Header 설정
        		.setHeaderParam("typ", "JWT")
        		.setHeaderParam("alg", "HS256")
        		// Payload 설정
                .setClaims(claims)
                .setExpiration(new Date(curTime + 36000000)) // 만료시간 : 10시간, 추후 시간 줄이고 refresh token 부여 예정
                // 시그니쳐 설정 : 복호활 할 때 사용. header의 인코딩 값 + payload 인코딩값 + 비밀키 = 시그니처
                // signWith api는 해싱알고리즘과 비밀키가 필요
                .signWith(secretKey, SignatureAlgorithm.HS256)
                // 생성
                .compact();
    }

//    // 권한정보 획득
//    // Spring Security 인증과정에서 권한확인을 위한 기능.. 추후 사용 예정
//    public Authentication getAuthentication(String token) {
//    	System.out.println("dd여기sssssssssssssss"+token);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.getUserIdx(token)));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    // 토큰에 담겨있는 UserIdx 가져오기
    public String getUserIdx(String token) {
        return String.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userIdx"));
    }
    
    // 토큰에 담겨있는 position 가져오기
    public String getPosition(String token) {
        return String.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role"));
    }

    // Authorization Header를 통해 인증
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
//        try {
//            // Bearer 검증
//            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
//            	return false;
//            } else {
//                token = token.split(" ")[1].trim();
//            }
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
//            // 만료되었을시 false 반환
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        // 만료되었을시 false 반환
        return !claims.getBody().getExpiration().before(new Date());
    }
}