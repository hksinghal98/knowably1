package com.stackroute.datapopulator.zuulapi;

import com.stackroute.datapopulator.zuulapi.pre.SimpleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulApiApplication {
	private static final Logger logger = LoggerFactory.getLogger(ZuulApiApplication.class);

	public static void main(String[] args)
	{
		logger.info("Api Gateway Started");
		SpringApplication.run(ZuulApiApplication.class, args);
	}

	@Bean
	public SimpleFilter simpleFilter(){
		return new SimpleFilter();
	}
}
