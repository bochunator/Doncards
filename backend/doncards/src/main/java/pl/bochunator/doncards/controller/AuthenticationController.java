package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.model.LoginDTO;
import pl.bochunator.doncards.model.LoginResponseDTO;
import pl.bochunator.doncards.model.RegistrationDTO;
import pl.bochunator.doncards.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegistrationDTO registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(registrationDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO loginDTO) {
        return authenticationService.loginUser(loginDTO);
    }
}
