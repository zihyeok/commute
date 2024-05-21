package hos.acorns.work.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "BOS",description = "BOS 전체 API",version = "${springdoc.version}"))
@AllArgsConstructor
@Configuration
public class SwaggerConfig {
    //Test한거
    /*@Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc을 사용한 Swagger UI 테스트")
                .version("1.0.0");
    }*/

    @Bean
    public GroupedOpenApi BosOpenApi() {
        String[] paths = {
                "/schedule/**",
                "/board/**",
                "/user/**",
                "/reply/**",
                "/attendance/**",
                "/dayOff/**"
        };
        return GroupedOpenApi.builder()
                .group("BOS")
                .pathsToMatch(paths)
                .packagesToScan(
                        "hos.acorns.work.domain.schedule.controller",
                        "hos.acorns.work.domain.board.controller",
                        "hos.acorns.work.domain.user.controller",
                        "hos.acorns.work.domain.reply.controller",
                        "hos.acorns.work.domain.attendance.controller",
                        "hos.acorns.work.domain.dayOff.controller"
                )
                .build();
    }

    /*@Bean
    public GroupedOpenApi AgentGroupOpenApi() {
        String paths[] = {"/schedule/**"};
        return GroupedOpenApi.builder()
                .group("schedule")
                .pathsToMatch(paths)
                .packagesToScan(
                        "hos.acorns.work.domain.schedule.controller"
                )
                .build();
    }*/


}
