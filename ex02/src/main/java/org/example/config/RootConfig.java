package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration: 설정
@Configuration
//@ComponentScan: 해당 패키지에서 @Component를 찾아서 등록해주겠다
@ComponentScan(basePackages = {"org.example"})
public class RootConfig {
}
