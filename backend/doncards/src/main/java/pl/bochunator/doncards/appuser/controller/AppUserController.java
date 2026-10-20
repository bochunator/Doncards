package pl.bochunator.doncards.appuser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bochunator.doncards.appuser.model.AppUserDetailsResponse;
import pl.bochunator.doncards.appuser.service.AppUserService;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{id}") // PublicProfilePage
    @Operation(summary = "Get user")
    public AppUserDetailsResponse findById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    @GetMapping("/me") // MyProfilePage
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user by token")
    public AppUserDetailsResponse findByToken(@AuthenticationPrincipal Jwt jwt) {
        Long appUserId = Long.valueOf(jwt.getSubject());
        return appUserService.findById(appUserId);
    }
}
