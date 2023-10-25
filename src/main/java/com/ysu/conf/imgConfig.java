package com.ysu.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yun
 * @version 1.0
 * @description: 为图片储存路径映射虚拟路径，解决浏览器无法直接访问本地图片的问题
 * @date 2023/9/4
 */
@Configuration
public class imgConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:E:/uploads/");
    }
}
