package org.quanta.eas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("org.quanta.eas.mapper")
@SpringBootApplication
@EnableSwagger2
public class EASApplication {

    public static void main(String[] args) {
        SpringApplication.run(EASApplication.class, args);
    }

}
