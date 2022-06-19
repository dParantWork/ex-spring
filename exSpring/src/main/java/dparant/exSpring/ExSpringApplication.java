package dparant.exSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ExSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExSpringApplication.class, args);
    }

}
