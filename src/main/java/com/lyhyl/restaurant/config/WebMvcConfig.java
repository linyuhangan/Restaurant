package com.lyhyl.restaurant.config;

import com.lyhyl.restaurant.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 一旦使用继承了WebMvcConfigurationSupport或者WebMvcConfigurerAdapter的自定义配置类，
     * 即使没有重写addResourceHandlers方法，默认配置都会被不被采用。如果要采用原本默认的配置，
     * 则需要webmvc配置类中重写如下方法addResourceHandlers
     * https://blog.csdn.net/weixin_43752854/article/details/103710413
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/**")
                .addResourceLocations("resources/", "static/", "public/",
                        "META-INF/resources/")
                .addResourceLocations("classpath:resources/", "classpath:static/",
                        "classpath:public/", "classpath:META-INF/resources/")
                .addResourceLocations("file:///tmp/webapps/");
    }


    /**
     * 扩展MVC的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("消息转换器");

        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用jackson将Java对象转换成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的转换器追加到mvc框架的消息转换器集合中
        converters.add(0,messageConverter);

    }
}
