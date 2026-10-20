package pl.bochunator.doncards.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bochunator.doncards.appuser.model.AppRole;
import pl.bochunator.doncards.appuser.model.AppUser;
import pl.bochunator.doncards.appuser.repository.AppUserRepository;
import pl.bochunator.doncards.card.model.Card;
import pl.bochunator.doncards.card.repository.CardRepository;
import pl.bochunator.doncards.deck.model.Deck;
import pl.bochunator.doncards.deck.repository.DeckRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    @Bean
    CommandLineRunner run() {
        return args -> {
            if (appUserRepository.findById(1L).isPresent()) {
                return;
            }

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setRole(AppRole.ADMIN);
            String adminPasswordHash = passwordEncoder.encode("Admin123!");
            admin.setPassword(adminPasswordHash);
            admin.setCreatedAt(LocalDateTime.now());
            appUserRepository.save(admin);

            AppUser test = new AppUser();
            test.setUsername("test");
            test.setEmail("test@test.test");
            String testPasswordHash = passwordEncoder.encode("Test123!");
            test.setPassword(testPasswordHash);
            test.setCreatedAt(LocalDateTime.now());
            appUserRepository.save(test);

            AppUser secondTest = new AppUser();
            secondTest.setUsername("test2");
            secondTest.setEmail("test2@test.test");
            String secondTestPasswordHash = passwordEncoder.encode("Test123!");
            secondTest.setPassword(secondTestPasswordHash);
            secondTest.setCreatedAt(LocalDateTime.now());
            appUserRepository.save(secondTest);

            Deck greetings = new Deck();
            greetings.setAppUser(admin);
            greetings.setName("Greetings!");
            greetings.setDescription("Welcome to Doncards! This is the official greeting deck. Take a look around and enjoy your stay!");
            greetings.setCreatedAt(LocalDateTime.now());
            greetings.setViewCount((long) Integer.MAX_VALUE);
            deckRepository.save(greetings);

            Deck aDeck = new Deck();
            aDeck.setAppUser(test);
            aDeck.setName("a");
            aDeck.setDescription("Example set");
            aDeck.setCreatedAt(LocalDateTime.now());
            aDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(aDeck);

            List<Card> aCards = new ArrayList<>(List.of(
                    new Card("przedimki nieokreślone", "a/an", aDeck),
                    new Card("nad/powyżej", "above", aDeck),
                    new Card("za granicą", "abroad", aDeck),
                    new Card("nieobecny", "absent", aDeck),
                    new Card("wypadek", "accident", aDeck),
                    new Card("żołędź", "acorn", aDeck),
                    new Card("przez", "across", aDeck),

                    new Card("ogłoszenie", "ad", aDeck),
                    new Card("dodawać", "add", aDeck),
                    new Card("adres", "address", aDeck),
                    new Card("podziwiać", "admire", aDeck),
                    new Card("dorosły", "adult", aDeck),
                    new Card("przewaga", "advantage", aDeck),
                    new Card("przygoda", "adventure", aDeck),
                    new Card("rada", "advice", aDeck),

                    new Card("antena", "aerial", aDeck),
                    new Card("antena", "satellite dish", aDeck),
                    new Card("bać się", "be afraid", aDeck),
                    new Card("po", "after", aDeck),
                    new Card("przeciw", "against", aDeck),
                    new Card("wiek", "age", aDeck),
                    new Card("zgadzać", "agree", aDeck),
                    new Card("powietrze", "air", aDeck),
                    new Card("samolot", "airplane", aDeck),

                    new Card("lotnisko", "airport", aDeck),
                    new Card("budzik", "alarm clock", aDeck),
                    new Card("kosmita", "alien", aDeck),
                    new Card("żywy", "alive", aDeck),
                    new Card("wszystko", "all", aDeck),
                    new Card("alergia", "allergy", aDeck),
                    new Card("sam", "alone", aDeck),
                    new Card("także", "also", aDeck),

                    new Card("zawsze", "always", aDeck),
                    new Card("karetka", "ambulance", aDeck),
                    new Card("wśród", "among", aDeck),
                    new Card("park rozrywki", "amusement park", aDeck),
                    new Card("kotwica", "anchor", aDeck),
                    new Card("i", "and", aDeck),
                    new Card("anioł", "angel", aDeck),
                    new Card("zły", "angry", aDeck),
                    new Card("gniewny", "angry", aDeck),

                    new Card("odpowiedź", "answer", aDeck),
                    new Card("akwarium", "aquarium", aDeck),
                    new Card("salon gier", "arcade", aDeck),
                    new Card("strzała", "arrow", aDeck),
                    new Card("pytać", "ask", aDeck),
                    new Card("astronauta", "astronaut", aDeck),
                    new Card("sportowiec", "athlete", aDeck),
                    new Card("obudzony", "awake", aDeck)
            ));
            cardRepository.saveAll(aCards);

            Deck animalsDeck = new Deck();
            animalsDeck.setAppUser(test);
            animalsDeck.setName("animals");
            animalsDeck.setDescription("Example set");
            animalsDeck.setCreatedAt(LocalDateTime.now());
            animalsDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(animalsDeck);

            List<Card> animalsCards = new ArrayList<>(List.of(
                    new Card("zwierzęta", "animals", animalsDeck),
                    new Card("pies", "dog", animalsDeck),
                    new Card("kot", "cat", animalsDeck),
                    new Card("kogut", "cock", animalsDeck),
                    new Card("kogut", "rooster", animalsDeck),
                    new Card("królik", "rabbit", animalsDeck),
                    new Card("kura", "hen", animalsDeck),
                    new Card("mysz", "mouse", animalsDeck),
                    new Card("kaczka", "duck", animalsDeck),
                    new Card("osioł", "donkey", animalsDeck),
                    new Card("owca", "sheep", animalsDeck),
                    new Card("gęś", "goose", animalsDeck),
                    new Card("świnia", "pig", animalsDeck),
                    new Card("koń", "horse", animalsDeck),
                    new Card("krowa", "cow", animalsDeck),

                    new Card("wielbłąd", "camel", animalsDeck),
                    new Card("żaba", "frog", animalsDeck),
                    new Card("tygrys", "tiger", animalsDeck),
                    new Card("niedźwiedź", "bear", animalsDeck),
                    new Card("żyrafa", "giraffe", animalsDeck),
                    new Card("lew", "lion", animalsDeck),
                    new Card("wąż", "snake", animalsDeck),
                    new Card("wilk", "wolf", animalsDeck),
                    new Card("lis", "fox", animalsDeck),
                    new Card("wiewiórka", "squirrel", animalsDeck),
                    new Card("małpa", "monkey", animalsDeck),
                    new Card("kangur", "kangaroo", animalsDeck),
                    new Card("słoń", "elephant", animalsDeck)
            ));
            cardRepository.saveAll(animalsCards);

            Deck bDeck = new Deck();
            bDeck.setAppUser(test);
            bDeck.setName("b");
            bDeck.setDescription("Example set");
            bDeck.setCreatedAt(LocalDateTime.now());
            bDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(bDeck);

            List<Card> bCards = new ArrayList<>(List.of(
                    new Card("dziecko", "baby", bDeck),
                    new Card("plecak", "backpack", bDeck),
                    new Card("plecak", "rucksack", bDeck),
                    new Card("zły", "bad", bDeck),
                    new Card("niedobry", "bad", bDeck),
                    new Card("torba", "bag", bDeck),
                    new Card("upiec", "bake", bDeck),
                    new Card("piłka", "ball", bDeck),
                    new Card("balon", "balloon", bDeck),

                    new Card("zespół muzyczny", "band", bDeck),
                    new Card("grill", "barbecue", bDeck),
                    new Card("koszyk", "basket", bDeck),
                    new Card("kąpać się", "bathe", bDeck),

                    new Card("być", "be", bDeck),
                    new Card("plaża", "beach", bDeck),
                    new Card("broda", "beard", bDeck),
                    new Card("uderzyć", "beat", bDeck),
                    new Card("piękny", "beautiful", bDeck),
                    new Card("bóbr", "beaver", bDeck),
                    new Card("bo", "because", bDeck),
                    new Card("ponieważ", "because", bDeck),
                    new Card("stać się", "become", bDeck),

                    new Card("przed", "before", bDeck),
                    new Card("za", "behind", bDeck),
                    new Card("z tyłu", "behind", bDeck),
                    new Card("dzwonek", "bell", bDeck),
                    new Card("pomiędzy", "between", bDeck),

                    new Card("duży", "big", bDeck),
                    new Card("rachunek", "bill", bDeck),
                    new Card("ptak", "bird", bDeck),
                    new Card("urodziny", "birthday", bDeck),

                    new Card("tablica", "blackboard", bDeck),
                    new Card("krew", "blood", bDeck),
                    new Card("czerwienić się", "blush", bDeck),
                    new Card("łódź", "boat", bDeck),
                    new Card("zagotować", "boil", bDeck),
                    new Card("kość", "bone", bDeck),
                    new Card("ognisko", "bonfire", bDeck),
                    new Card("książka", "book", bDeck),

                    new Card("znudzony", "bored", bDeck),
                    new Card("szef", "boss", bDeck),
                    new Card("butelka", "bottle", bDeck),
                    new Card("pudełko", "box", bDeck),
                    new Card("chłopiec", "boy", bDeck),
                    new Card("mózg", "brain", bDeck),
                    new Card("gałąź", "branch", bDeck),
                    new Card("stłuc", "break", bDeck),
                    new Card("złamać", "break", bDeck),

                    new Card("most", "bridge", bDeck),
                    new Card("jasny", "bright", bDeck),
                    new Card("miotła", "broom", bDeck),
                    new Card("szczotka", "brush", bDeck),

                    new Card("budować", "build", bDeck),
                    new Card("palić", "burn", bDeck),
                    new Card("autobus", "bus", bDeck),
                    new Card("zajęty", "busy", bDeck),
                    new Card("ale", "but", bDeck),
                    new Card("motyl", "butterfly", bDeck),
                    new Card("guzik", "button", bDeck),
                    new Card("kupować", "buy", bDeck)
            ));
            cardRepository.saveAll(bCards);

            Deck bathroomDeck = new Deck();
            bathroomDeck.setAppUser(test);
            bathroomDeck.setName("bathroom");
            bathroomDeck.setDescription("Example set");
            bathroomDeck.setCreatedAt(LocalDateTime.now());
            bathroomDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(bathroomDeck);

            List<Card> bathroomCards = new ArrayList<>(List.of(
                    new Card("łazienka", "bathroom", bathroomDeck),
                    new Card("ręcznik", "towel", bathroomDeck),
                    new Card("wieszak na ręcznik", "towel rail", bathroomDeck),
                    new Card("półka", "shelf", bathroomDeck),
                    new Card("lustro", "mirror", bathroomDeck),
                    new Card("papier toaletowy", "toilet paper", bathroomDeck),
                    new Card("prysznic", "shower", bathroomDeck),
                    new Card("wanna", "bath", bathroomDeck),
                    new Card("waga łazienkowa", "bathroom scales", bathroomDeck),
                    new Card("gąbka", "sponge", bathroomDeck),
                    new Card("umywalka", "washbasin", bathroomDeck),
                    new Card("muszla klozetowa", "toilet", bathroomDeck)
            ));
            cardRepository.saveAll(bathroomCards);
        };
    }
}
