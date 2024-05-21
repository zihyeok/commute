package hos.acorns.work.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    // 비동기 실행을 위한 설정을 제공하는 AsyncConfig 클래스
    @Override
    public Executor getAsyncExecutor() {
        // ThreadPoolTaskExecutor 인스턴스 생성
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("async-thread-"); // 쓰레드 이름 접두사 설정
        executor.setCorePoolSize(10); // 쓰레드 풀의 기본 크기 설정
        executor.setMaxPoolSize(50); // 쓰레드 풀의 최대 크기 설정
        executor.setQueueCapacity(100); // 작업 큐의 용량 설정
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 거부 정책 설정
        executor.initialize(); // 쓰레드 풀 초기화

        return executor; // 구성된 Executor 반환
    }
}
