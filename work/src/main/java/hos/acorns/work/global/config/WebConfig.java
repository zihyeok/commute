package hos.acorns.work.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins("*") /* 모든 클라이언트 요청 허용 */
				.allowedMethods("*")
				.allowedHeaders("*") /* cors요청에 허용되는 헤더 지정 */
				.exposedHeaders("*"); /* 해당 header에 접근 허용 */
//				.exposedHeaders("authorization"); /* 해당 header에 접근 허용 */

	}


}
