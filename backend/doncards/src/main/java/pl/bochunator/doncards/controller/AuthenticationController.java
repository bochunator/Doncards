package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.model.LoginDTO;
import pl.bochunator.doncards.model.LoginResponseDTO;
import pl.bochunator.doncards.model.RegistrationDTO;
import pl.bochunator.doncards.model.Response;
import pl.bochunator.doncards.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getEmail(), body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO loginDTO) {
        return authenticationService.loginUser(loginDTO);
    }
}
