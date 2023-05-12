package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ulan on 5/12/2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/demo")
@Tag(name = "Demo", description = "Demo Controller APIs")
public class DemoController {

    @SecurityRequirements
    @Operation(
            summary = "Say Hello to Strong Team",
            description = "It's for testing Spring Security."
    )
    @GetMapping
    public String sayHello() {
        return "Hello, Strong Team";
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(
            summary = "Say Hello to Author",
            description = "It's for testing Spring Security."
    )
    @PostMapping
    public String answerToAuthor(@RequestBody String auth) {
        return "Hello, " + auth;
    }

}
