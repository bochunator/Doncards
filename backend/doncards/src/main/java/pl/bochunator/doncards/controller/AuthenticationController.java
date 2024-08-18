package pl.bochunator.doncards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bochunator.doncards.dto.request.applicationuser.CreateApplicationUserRequestDTO;
import pl.bochunator.doncards.dto.request.applicationuser.LoginApplicationUserRequestDTO;
import pl.bochunator.doncards.dto.response.applicationuser.LoginApplicationUserResponseDTO;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody CreateApplicationUserRequestDTO applicationUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(applicationUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginApplicationUserResponseDTO> loginUser(@RequestBody LoginApplicationUserRequestDTO applicationUserDto) {
        return ResponseEntity.ok(authenticationService.loginUser(applicationUserDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApplicationUser> verifyUserByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(authenticationService.verifyUserByToken(jwt));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ApplicationUser> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(authenticationService.findByUsername(username));
    }

}
