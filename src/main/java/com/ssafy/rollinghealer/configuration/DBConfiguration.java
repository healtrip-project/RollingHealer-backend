package com.ssafy.rollinghealer.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = { "com.ssafy.rollinghealer.**.mapper" })
public class DBConfiguration {

}
