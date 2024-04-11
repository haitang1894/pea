package com.pea;

import com.pea.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableCaching
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class PeaApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PeaApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PeaApplication.class, args);
        String hostIp = IpUtil.getHostIp();
        String port = context.getEnvironment().getProperty("server.port");
        log.info("Swagger访问地址：http://{}:{}/doc.html", hostIp, port);
    }

}
