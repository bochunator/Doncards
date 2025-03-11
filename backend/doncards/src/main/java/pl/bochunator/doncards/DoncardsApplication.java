package pl.bochunator.doncards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *     The {@link jakarta.persistence.Entity @Entity} annotation requires a default constructor.
 *     Although {@link lombok.RequiredArgsConstructor @RequiredArgsConstructor} might work in some cases,
 *     it can cause problems. It's recommended to use {@link lombok.NoArgsConstructor @NoArgsConstructor} instead.
 * </p>
 * <p>
 *     The {@link lombok.Builder @Builder} annotation requires the {@link lombok.AllArgsConstructor @AllArgsConstructor} annotation
 *     if you want to use {@link lombok.NoArgsConstructor @NoArgsConstructor} together with the builder pattern.
 * </p>
 * <p>
 *     For objects returned by a {@link org.springframework.web.bind.annotation.RestController @RestController},
 *     Jackson requires the {@link lombok.Getter @Getter} annotation to properly serialize the response.
 * </p>
 * <p>
 *     When saving entities with a specified ID (other than null), using a {@link java.util.List List} instead of a {@link java.util.Set Set}
 *     is recommended to ensure proper database persistence.
 * </p>
 */

@SpringBootApplication
public class DoncardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoncardsApplication.class, args);
	}

}
