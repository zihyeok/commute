package hos.acorns.work.global.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import hos.acorns.work.global.security.JwtAuthenticationFilter;
import hos.acorns.work.global.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final JwtProvider jwtProvider;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable() // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
			.cors().and() // WebConfig 에서 이미 설정했으므로 기본 cors 설정
			.csrf().disable() // CSRF 보호 비활성
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
	        // 위에 까지가 기본설정. 아래서부터는 security 적용
//	        .and()
//	        .authorizeRequests() // 조건별로 요청 허용/제한 설정
//	        .requestMatchers("/user/register", "/user/login").permitAll(); // 회원가입과 로그인은 모두 승인
//	        추후 spring security로 권한 나눌시 사용 예정
//	        // /admin으로 시작하는 요청은 ADMIN 권한이 있는 유저에게만 허용
//	        .requestMatchers("/admin/**").hasRole("ADMIN")
//	        // /user 로 시작하는 요청은 USER 권한이 있는 유저에게만 허용
//	        .requestMatchers("/user/**").hasRole("USER")
//	        .anyRequest().denyAll()
//	        .and()
//	        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 적용
//	        .exceptionHandling() // 에러 핸들링
//	        추후 권한부분 적용시 사용
//	        .accessDeniedHandler(new AccessDeniedHandler() {
//	            @Override
//	            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//	                // 권한 문제가 발생했을 때 이 부분을 호출한다.
//	                response.setStatus(403);
//	                response.setCharacterEncoding("utf-8");
//	                response.setContentType("text/html; charset=UTF-8");
//	                response.getWriter().write("권한이 없는 사용자입니다.");
//	            }
//	        })
//	        .authenticationEntryPoint(new AuthenticationEntryPoint() {
//	            @Override
//	            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//	                // 인증문제가 발생했을 때 이 부분을 호출한다.
//	                response.setStatus(401);
//	                response.setCharacterEncoding("utf-8");
//	                response.setContentType("text/html; charset=UTF-8");
//	                response.getWriter().write("인증되지 않은 사용자입니다.");
//		            }
//		        });
		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
