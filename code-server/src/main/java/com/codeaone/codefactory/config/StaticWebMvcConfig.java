package com.codeaone.codefactory.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticWebMvcConfig implements WebMvcConfigurer {
	// 解决spring-boot缓存静态资源问题
	@Value("${static.file.localpath}")
	private String staticFile;

	/*
	 * 精确设定缓存资源。使用配置文件可以粗略设置缓存时间。 指定png、jpg、jpeg、gif结尾的文件，缓存时间为24小时。
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**/*.svg", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/*.js",
				"/**/*.css", "/**/*.mp4")
		.addResourceLocations("classpath:/static/", "file:" + staticFile)
		.setCacheControl(CacheControl.maxAge(24, TimeUnit.HOURS).cachePublic());
	}
}
