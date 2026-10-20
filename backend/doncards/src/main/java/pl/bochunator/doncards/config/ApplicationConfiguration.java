package pl.bochunator.doncards.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfiguration {

    @Value("${HOST}")
    private String host;
}
