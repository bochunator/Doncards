package pl.bochunator.doncards.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.authentication.model.LoginRequest;
import pl.bochunator.doncards.authentication.model.RegisterRequest;
import pl.bochunator.doncards.authentication.model.RegisterResponse;
import pl.bochunator.doncards.authentication.model.TokenResponse;
import pl.bochunator.doncards.authentication.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login") // LoginPage
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/register") // RegisterPage
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest dto) {
        return authenticationService.register(dto);
    }
}
