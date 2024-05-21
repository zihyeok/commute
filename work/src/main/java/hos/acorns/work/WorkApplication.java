package hos.acorns.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableTransactionManagement
public class WorkApplication {
	/* @EnableTransactionManagement: 트랜잭션 관리를 활성화하는 어노테이션입니다. 데이터베이스와의 상호작용 시
	트랜잭션을 관리할 수 있도록 합니다.
	SecurityAutoConfiguration: 클래스를 제외시켜 해당 보안 구성을 사용하지 않도록 설정합니다. */
	public static void main(String[] args) {
		SpringApplication.run(WorkApplication.class, args);
	}

}
