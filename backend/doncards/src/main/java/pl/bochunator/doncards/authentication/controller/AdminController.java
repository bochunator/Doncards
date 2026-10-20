package pl.bochunator.doncards.authentication.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public String greetings() {
        return "You have administrator access";
    }
}
