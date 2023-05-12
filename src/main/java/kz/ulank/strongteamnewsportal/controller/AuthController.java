package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.ulank.strongteamnewsportal.model.request.SigninRequest;
import kz.ulank.strongteamnewsportal.model.request.SignupRequest;
import kz.ulank.strongteamnewsportal.model.response.AuthResponse;

import kz.ulank.strongteamnewsportal.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Ulan on 5/12/2023
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth Controller APIs")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @SecurityRequirements
    @Operation(
            summary = "Sign up operation",
            description = "Registers a new user."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @SecurityRequirements
    @Operation(
            summary = "Sign in operation",
            description = "Authenticates a user."
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody SigninRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(
            summary = "Refresh token operation",
            description = "Refreshes the user's authentication token."
    )
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(
            summary = "Logout operation",
            description = "Logs out the user."
    )
    @GetMapping("/logout")
    public void logout() {
        log.debug("Logout user");
    }

}
