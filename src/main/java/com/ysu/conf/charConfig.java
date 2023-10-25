package com.ysu.conf;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yun
 * @version 1.0
 * @description: 处理请求中的特殊符号
 * @date 2023/9/5
 */
@Configuration
public class charConfig {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            //允许的特殊字符
            connector.setProperty("relaxedQueryChars", "|{}[]");
        });
        return factory;
    }
}
