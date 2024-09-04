package pl.bochunator.doncards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bochunator.doncards.exception.UserIdNotFoundException;
import pl.bochunator.doncards.exception.username.UsernameIsNotValidException;
import pl.bochunator.doncards.exception.username.UsernameNotFoundException;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.repository.ApplicationUserRepository;
import pl.bochunator.doncards.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ValidationUtils validationUtils;

    public ApplicationUser getApplicationUserByUsername(String username) {
        if (!validationUtils.isValidUsername(username)) {
            throw new UsernameIsNotValidException(username);
        }
        return applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public ApplicationUser getApplicationUserById(Long userId) {
        return applicationUserRepository.findByUserId(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId));
    }
}
