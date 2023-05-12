package kz.ulank.strongteamnewsportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by Ulan on 5/12/2023
 */
@SpringBootApplication
@EnableFeignClients
public class StrongTeamNewsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrongTeamNewsPortalApplication.class, args);
    }

}
