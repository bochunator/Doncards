package pl.bochunator.doncards.dto.request.applicationuser;

import lombok.Getter;

@Getter
public class CreateApplicationUserRequestDTO {

    private String email;

    private String username;

    private String password;

}
