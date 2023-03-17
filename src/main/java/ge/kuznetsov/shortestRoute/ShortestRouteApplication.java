package ge.kuznetsov.shortestRoute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ShortestRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortestRouteApplication.class, args);

        try {
            File resource = new ClassPathResource("resources/data/file.xlsx").getFile();

        } catch (IOException e) {
            e.getStackTrace();
        }


        System.out.println("Has been ran successfully so far.");

    }

}
