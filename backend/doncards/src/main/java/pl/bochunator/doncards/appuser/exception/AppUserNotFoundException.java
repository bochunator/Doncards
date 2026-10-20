package pl.bochunator.doncards.appuser.exception;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(Long id) {
        super(String.format("Not found user with id = '%s'.", id));
    }
}
