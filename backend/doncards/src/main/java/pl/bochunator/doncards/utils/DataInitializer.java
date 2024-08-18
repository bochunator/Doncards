package pl.bochunator.doncards.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bochunator.doncards.configuration.DoncardsApplicationConfiguration;
import pl.bochunator.doncards.model.ApplicationUser;
import pl.bochunator.doncards.model.Card;
import pl.bochunator.doncards.model.Deck;
import pl.bochunator.doncards.model.Role;
import pl.bochunator.doncards.repository.ApplicationUserRepository;
import pl.bochunator.doncards.repository.CardRepository;
import pl.bochunator.doncards.repository.DeckRepository;
import pl.bochunator.doncards.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final String ADMIN = "ADMIN";
    private final String USER = "USER";
    private final DoncardsApplicationConfiguration doncardsApplicationConfiguration;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Bean
    CommandLineRunner run() {
        return args -> {
            if (roleRepository.findByAuthority(ADMIN).isPresent()) {
                return;
            }
            Role adminRole = new Role(ADMIN);
            Role userRole = new Role(USER);
            roleRepository.saveAll(Set.of(adminRole, userRole));
            ApplicationUser admin = ApplicationUser.builder()
                    .username(doncardsApplicationConfiguration.getAdminUsername())
                    .password(passwordEncoder.encode(doncardsApplicationConfiguration.getAdminPassword()))
                    .authorities(Set.of(adminRole, userRole))
                    .build();
            ApplicationUser test = ApplicationUser.builder()
                    .email("test@test.test")
                    .username("test")
                    .password(passwordEncoder.encode("Test123!"))
                    .authorities(Set.of(userRole))
                    .build();
            applicationUserRepository.saveAll(Set.of(admin, test));
            Set<Card> aCards = new HashSet<>(Set.of(
                    new Card("przedimki nieokreślone", "a/an"),
                    new Card("nad/powyżej", "above"),
                    new Card("za granicą", "abroad"),
                    new Card("nieobecny", "absent"),
                    new Card("wypadek", "accident"),
                    new Card("żołędź", "acorn"),
                    new Card("przez", "across"),

                    new Card("ogłoszenie", "ad"),
                    new Card("dodawać", "add"),
                    new Card("adres", "address"),
                    new Card("podziwiać", "admire"),
                    new Card("dorosły", "adult"),
                    new Card("przewaga", "advantage"),
                    new Card("przygoda", "adventure"),
                    new Card("rada", "advice"),

                    new Card("antena", "aerial"),
                    new Card("antena", "satellite dish"),
                    new Card("bać się", "be afraid"),
                    new Card("po", "after"),
                    new Card("przeciw", "against"),
                    new Card("wiek", "age"),
                    new Card("zgadzać", "agree"),
                    new Card("powietrze", "air"),
                    new Card("samolot", "airplane"),

                    new Card("lotnisko", "airport"),
                    new Card("budzik", "alarm clock"),
                    new Card("kosmita", "alien"),
                    new Card("żywy", "alive"),
                    new Card("wszystko", "all"),
                    new Card("alergia", "allergy"),
                    new Card("sam", "alone"),
                    new Card("także", "also"),

                    new Card("zawsze", "always"),
                    new Card("karetka", "ambulance"),
                    new Card("wśród", "among"),
                    new Card("park rozrywki", "amusement park"),
                    new Card("kotwica", "anchor"),
                    new Card("i", "and"),
                    new Card("anioł", "angel"),
                    new Card("zły", "angry"),
                    new Card("gniewny", "angry"),

                    new Card("odpowiedź", "answer"),
                    new Card("akwarium", "aquarium"),
                    new Card("salon gier", "arcade"),
                    new Card("strzała", "arrow"),
                    new Card("pytać", "ask"),
                    new Card("astronauta", "astronaut"),
                    new Card("sportowiec", "athlete"),
                    new Card("obudzony", "awake")
            ));
            Set<Card> animalsCards = new HashSet<>(Set.of(
                    new Card("zwierzęta", "animals"),
                    new Card("pies", "dog"),
                    new Card("kot", "cat"),
                    new Card("kogut", "cock"),
                    new Card("kogut", "rooster"),
                    new Card("królik", "rabbit"),
                    new Card("kura", "hen"),
                    new Card("mysz", "mouse"),
                    new Card("kaczka", "duck"),
                    new Card("osioł", "donkey"),
                    new Card("owca", "sheep"),
                    new Card("gęś", "goose"),
                    new Card("świnia", "pig"),
                    new Card("koń", "horse"),
                    new Card("krowa", "cow"),

                    new Card("wielbłąd", "camel"),
                    new Card("żaba", "frog"),
                    new Card("tygrys", "tiger"),
                    new Card("niedźwiedź", "bear"),
                    new Card("żyrafa", "giraffe"),
                    new Card("lew", "lion"),
                    new Card("wąż", "snake"),
                    new Card("wilk", "wolf"),
                    new Card("lis", "fox"),
                    new Card("wiewiórka", "squirrel"),
                    new Card("małpa", "monkey"),
                    new Card("kangur", "kangaroo"),
                    new Card("słoń", "elephant")
            ));
            Set<Card> bCards = new HashSet<>(Set.of(
                    new Card("dziecko", "baby"),
                    new Card("plecak", "backpack"),
                    new Card("plecak", "rucksack"),
                    new Card("zły", "bad"),
                    new Card("niedobry", "bad"),
                    new Card("torba", "bag"),
                    new Card("upiec", "bake"),
                    new Card("piłka", "ball"),
                    new Card("balon", "balloon"),

                    new Card("zespół muzyczny", "band"),
                    new Card("grill", "barbecue"),
                    new Card("koszyk", "basket"),
                    new Card("kąpać się", "bathe"),

                    new Card("być", "be"),
                    new Card("plaża", "beach"),
                    new Card("broda", "beard"),
                    new Card("uderzyć", "beat"),
                    new Card("piękny", "beautiful"),
                    new Card("bóbr", "beaver"),
                    new Card("bo", "because"),
                    new Card("ponieważ", "because"),
                    new Card("stać się", "become"),

                    new Card("przed", "before"),
                    new Card("za", "behind"),
                    new Card("z tyłu", "behind"),
                    new Card("dzwonek", "bell"),
                    new Card("pomiędzy", "between"),

                    new Card("duży", "big"),
                    new Card("rachunek", "bill"),
                    new Card("ptak", "bird"),
                    new Card("urodziny", "birthday"),

                    new Card("tablica", "blackboard"),
                    new Card("krew", "blood"),
                    new Card("czerwienić się", "blush"),
                    new Card("łódź", "boat"),
                    new Card("zagotować", "boil"),
                    new Card("kość", "bone"),
                    new Card("ognisko", "bonfire"),
                    new Card("książka", "book"),

                    new Card("znudzony", "bored"),
                    new Card("szef", "boss"),
                    new Card("butelka", "bottle"),
                    new Card("pudełko", "box"),
                    new Card("chłopiec", "boy"),
                    new Card("mózg", "brain"),
                    new Card("gałąź", "branch"),
                    new Card("stłuc", "break"),
                    new Card("złamać", "break"),

                    new Card("most", "bridge"),
                    new Card("jasny", "bright"),
                    new Card("miotła", "broom"),
                    new Card("szczotka", "brush"),

                    new Card("budować", "build"),
                    new Card("palić", "burn"),
                    new Card("autobus", "bus"),
                    new Card("zajęty", "busy"),
                    new Card("ale", "but"),
                    new Card("motyl", "butterfly"),
                    new Card("guzik", "button"),
                    new Card("kupować", "buy")
            ));
            Set<Card> bathroomCards = new HashSet<>(Set.of(
                    new Card("łazienka", "bathroom"),
                    new Card("ręcznik", "towel"),
                    new Card("wieszak na ręcznik", "towel rail"),
                    new Card("półka", "shelf"),
                    new Card("lustro", "mirror"),
                    new Card("papier toaletowy", "toilet paper"),
                    new Card("prysznic", "shower"),
                    new Card("wanna", "bath"),
                    new Card("waga łazienkowa", "bathroom scales"),
                    new Card("gąbka", "sponge"),
                    new Card("umywalka", "washbasin"),
                    new Card("muszla klozetowa", "toilet")
            ));
            cardRepository.saveAll(Stream.of(
                            aCards,
                            animalsCards,
                            bCards,
                            bathroomCards
                            )
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet()));
            Set<Deck> decks = new HashSet<>(Set.of(
                    new Deck(test, "a", new ArrayList<>(aCards)),
                    new Deck(test, "animals", new ArrayList<>(animalsCards)),
                    new Deck(test, "b", new ArrayList<>(bCards)),
                    new Deck(test, "bathroom", new ArrayList<>(bathroomCards))
            ));
            deckRepository.saveAll(decks);
        };
    }

}
