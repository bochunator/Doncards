package pl.bochunator.doncards.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @Operation(summary = "Hello API")
    @GetMapping("/")
    public String showGreetings() {
        return "Hello, API is available here: <a href='/swagger-ui.html' style='color: #00FF00; text-decoration: none;'>Swagger UI </a>"
                + "<a href='/logout' style='color: #FF0000; text-decoration: none;'>Logout</a>";
    }

}
