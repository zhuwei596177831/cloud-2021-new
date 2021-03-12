package cpm.example.springcloudstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author 朱伟伟
 * @date 2021-03-12 10:41:35
 * @description
 */
@SpringBootApplication
public class SpringCloudStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    @Bean
    public Consumer<Person> log() {
        return person -> {
            System.out.println("received person:" + person);
        };
    }

    @Bean
    public Function<String, String> uppercase() {
        return String::toUpperCase;
    }

}
