package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ulan on 5/12/2023
 */
@RestController
@RequestMapping(value = "/api/v1/demo")
@Tag(name = "Demo", description = "Demo Controller APIs")
public class DemoController {
    @SecurityRequirements
    @GetMapping
    public String sayHello() {
        return "Hello, Strong Team";
    }


    @PostMapping
    public String answerToAuthor(@RequestBody String hello) {
        return "Hello, " + hello;
    }

}
