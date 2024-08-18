package pl.bochunator.doncards.dto.response.applicationuser;

import lombok.Builder;
import lombok.Getter;
import pl.bochunator.doncards.model.ApplicationUser;

@Builder
@Getter
public class LoginApplicationUserResponseDTO {

    private ApplicationUser user;

    private String jwt;

}
