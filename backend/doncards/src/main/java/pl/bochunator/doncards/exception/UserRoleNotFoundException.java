package pl.bochunator.doncards.exception;

public class UserRoleNotFoundException extends RuntimeException {

    public UserRoleNotFoundException() {
        super("Role 'USER' not found");
    }

}
