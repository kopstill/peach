package com.kopever.peach.service.eleme.client;

import com.kopever.peach.domain.HttpMessage;
import com.kopever.peach.domain.HttpResponse;
import com.kopever.peach.domain.user.vo.UserVO;
import feign.Logger;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "peach-service-user",
        path = "/peach-service-user",
        fallbackFactory = UserClientFallbackFactory.class,
        configuration = UserClientConfiguration.class)
public interface UserClient {

    @GetMapping("/{username}")
    HttpResponse<UserVO> getUserByUsername(@PathVariable("username") String username);

}

@Slf4j
@Component
class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return username -> {
            log.info("UserClientFallbackFactory.create.Throwable", throwable);
            return new HttpResponse<>(HttpMessage.EXCEPTION);
        };
    }

}

@Configuration
class UserClientConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
