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

            Deck bedroomDeck = new Deck();
            bedroomDeck.setAppUser(test);
            bedroomDeck.setName("bedroom");
            bedroomDeck.setDescription("Example set");
            bedroomDeck.setCreatedAt(LocalDateTime.now());
            bedroomDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(bedroomDeck);

            List<Card> bedroomCards = new ArrayList<>(List.of(
                    new Card("sypialnia", "bedroom", bedroomDeck),
                    new Card("lampa", "lamp", bedroomDeck),
                    new Card("budzik", "alarm clock", bedroomDeck),
                    new Card("poduszka", "pillow", bedroomDeck),
                    new Card("komoda", "chest of drawers", bedroomDeck),
                    new Card("szafa", "wardrobe", bedroomDeck),
                    new Card("toaletka", "dressing table", bedroomDeck),
                    new Card("koc", "blanket", bedroomDeck),
                    new Card("piżama", "pyjamas", bedroomDeck),
                    new Card("łóżko", "bed", bedroomDeck),
                    new Card("kołdra", "duvet", bedroomDeck),
                    new Card("stolik nocny", "bedside table", bedroomDeck),
                    new Card("krzesło", "chair", bedroomDeck)
            ));
            cardRepository.saveAll(bedroomCards);

            Deck bicycleDeck = new Deck();
            bicycleDeck.setAppUser(test);
            bicycleDeck.setName("bicycyle/bike");
            bicycleDeck.setDescription("Example set");
            bicycleDeck.setCreatedAt(LocalDateTime.now());
            bicycleDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(bicycleDeck);

            List<Card> bicycleCards = new ArrayList<>(List.of(
                    new Card("rower", "bicycle", bicycleDeck),
                    new Card("rower", "bike", bicycleDeck),
                    new Card("siodełko", "seat", bicycleDeck),
                    new Card("hamulec", "brake", bicycleDeck),
                    new Card("dzwonek", "bell", bicycleDeck),
                    new Card("kierownica", "handlebars", bicycleDeck),
                    new Card("światło", "light", bicycleDeck),
                    new Card("pompka", "pump", bicycleDeck),
                    new Card("błotnik", "fender", bicycleDeck),
                    new Card("koło", "wheal", bicycleDeck),
                    new Card("pedał", "pedal", bicycleDeck),
                    new Card("łańcuch", "chain", bicycleDeck),
                    new Card("przerzutka", "gearshift", bicycleDeck),
                    new Card("podpórka", "kickstand", bicycleDeck),
                    new Card("szprychy", "spokes", bicycleDeck),
                    new Card("opona", "tyre", bicycleDeck),
                    new Card("bagażnik", "carrier", bicycleDeck)
            ));
            cardRepository.saveAll(bicycleCards);

            Deck bodyDeck = new Deck();
            bodyDeck.setAppUser(test);
            bodyDeck.setName("bicycyle/bike");
            bodyDeck.setDescription("Example set");
            bodyDeck.setCreatedAt(LocalDateTime.now());
            bodyDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(bodyDeck);

            List<Card> bodyCards = new ArrayList<>(List.of(
                    new Card("kostka", "ankle", bodyDeck),
                    new Card("łydka", "calf", bodyDeck),
                    new Card("pupa", "bottom", bodyDeck),
                    new Card("brzuch", "stomach", bodyDeck),
                    new Card("biodro", "hip", bodyDeck),
                    new Card("talia", "waist", bodyDeck),
                    new Card("plecy", "back", bodyDeck),
                    new Card("ramię", "arm", bodyDeck),
                    new Card("bark", "shoulder", bodyDeck),
                    new Card("szyja", "neck", bodyDeck),
                    new Card("podbródek", "chin", bodyDeck),
                    new Card("ucho", "ear", bodyDeck),
                    new Card("włosy", "hair", bodyDeck),
                    new Card("oko", "eye", bodyDeck),
                    new Card("głowa", "head", bodyDeck),
                    new Card("nose", "nose", bodyDeck),
                    new Card("usta", "mouth", bodyDeck),
                    new Card("kciuk", "thumb", bodyDeck),
                    new Card("dłoń", "hand", bodyDeck),
                    new Card("palec", "finger", bodyDeck),
                    new Card("nadgarstek", "wrist", bodyDeck),
                    new Card("łokieć", "elbow", bodyDeck),
                    new Card("klatka piersiowa", "chest", bodyDeck),
                    new Card("kolano", "knee", bodyDeck),
                    new Card("palec u nogi", "toe", bodyDeck),
                    new Card("stopa", "foot", bodyDeck),
                    new Card("pięta", "heel", bodyDeck),
                    new Card("noga", "leg", bodyDeck),
                    new Card("udo", "thigh", bodyDeck),
                    new Card("goleń", "shin", bodyDeck)
            ));
            cardRepository.saveAll(bodyCards);

            Deck breakfastDeck = new Deck();
            breakfastDeck.setAppUser(test);
            breakfastDeck.setName("breakfast");
            breakfastDeck.setDescription("Example set");
            breakfastDeck.setCreatedAt(LocalDateTime.now());
            breakfastDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(breakfastDeck);

            List<Card> breakfastCards = new ArrayList<>(List.of(
                    new Card("śniadanie", "breakfast", breakfastDeck),
                    new Card("kiełbaska", "sausage", breakfastDeck),
                    new Card("cukier", "sugar", breakfastDeck),
                    new Card("kawa", "coffee", breakfastDeck),
                    new Card("mleko", "milk", breakfastDeck),
                    new Card("szynka", "ham", breakfastDeck),
                    new Card("bułka", "roll", breakfastDeck),
                    new Card("miód", "honey", breakfastDeck),
                    new Card("jajko", "egg", breakfastDeck),
                    new Card("grzanka", "toast", breakfastDeck),
                    new Card("masło", "butter", breakfastDeck),
                    new Card("dżem", "jam", breakfastDeck),
                    new Card("płatki zbożowe", "cereal", breakfastDeck),
                    new Card("rogal", "croissant", breakfastDeck),
                    new Card("chleb", "bread", breakfastDeck),
                    new Card("herbata", "tea", breakfastDeck),
                    new Card("herbatnik", "biscuit", breakfastDeck)
                    ));
            cardRepository.saveAll(breakfastCards);

            Deck cDeck = new Deck();
            cDeck.setAppUser(test);
            cDeck.setName("c");
            cDeck.setDescription("Example set");
            cDeck.setCreatedAt(LocalDateTime.now());
            cDeck.setViewCount((long) (Math.random() * Integer.MAX_VALUE));
            deckRepository.save(cDeck);

            List<Card> cCards = new ArrayList<>(List.of(
                    new Card("kawiarnia", "café", cDeck),
                    new Card("kalkulator", "calculator", cDeck),
                    new Card("kalendarz", "calendar", cDeck),
                    new Card("wołać", "call", cDeck),
                    new Card("spokojny", "calm", cDeck),
                    new Card("aparat fotograficzny", "camera", cDeck),
                    new Card("pole namiotowe", "campsite", cDeck),
                    new Card("puszka", "can", cDeck),
                    new Card("móc", "can", cDeck),
                    new Card("świeca", "candle", cDeck),
                    new Card("stołówka", "canteen", cDeck),
                    new Card("karty", "cards", cDeck),
                    new Card("troszczyć się", "care", cDeck),
                    new Card("dbać", "care", cDeck),
                    new Card("nieść", "carry", cDeck),
                    new Card("kreskówki", "cartoons", cDeck),
                    new Card("gotówka", "cash", cDeck),
                    new Card("kaseta", "cassette", cDeck),
                    new Card("zamek", "castle", cDeck),
                    new Card("złapać", "catch", cDeck),
                    new Card("gąsienica", "caterpillar", cDeck),
                    new Card("płyta kompaktowa", "CD", cDeck),
                    new Card("odtwarzacz płyt", "CD player", cDeck),
                    new Card("świętować", "celebrate", cDeck),
                    new Card("środek", "centre", cDeck),
                    new Card("łańcuch", "chain", cDeck),
                    new Card("kreda", "chalk", cDeck),
                    new Card("reszta", "change", cDeck),
                    new Card("zmieniać się", "change", cDeck),
                    new Card("tani", "cheap", cDeck),
                    new Card("apteka", "chemist's", cDeck),
                    new Card("apteka", "pharmacy", cDeck),
                    new Card("kasztan", "chestnut", cDeck),
                    new Card("guma do żucia", "chewing gum", cDeck),
                    new Card("dzieci", "children", cDeck),
                    new Card("czekolada", "chocolate", cDeck),
                    new Card("wybierać", "choose", cDeck),
                    new Card("Boże Narodzenie", "Christmas", cDeck),
                    new Card("kościół", "church", cDeck),
                    new Card("kino", "cinema", cDeck),
                    new Card("cyrk", "circus", cDeck),
                    new Card("miasto", "city", cDeck),
                    new Card("klasa", "classroom", cDeck),
                    new Card("czysty", "clean", cDeck),
                    new Card("wspinać się", "climb", cDeck),
                    new Card("chmura", "cloud", cDeck),
                    new Card("moneta", "coin", cDeck),
                    new Card("zimno", "cloud", cDeck),
                    new Card("przeziębienie", "cloud", cDeck),
                    new Card("grzebień", "comb", cDeck),
                    new Card("przyjść", "come", cDeck),
                    new Card("komiks", "comic", cDeck),
                    new Card("zawody", "competition", cDeck),
                    new Card("konkurs", "competition", cDeck),
                    new Card("koncert", "concert", cDeck),
                    new Card("gratulacje", "congratulations", cDeck),
                    new Card("połączyć", "connect", cDeck),
                    new Card("rozmowa", "conversation", cDeck),
                    new Card("gotować", "cook", cDeck),
                    new Card("chłodny", "cool", cDeck),
                    new Card("kopiować", "copy", cDeck),
                    new Card("róg", "corner", cDeck),
                    new Card("kąt", "corner", cDeck),
                    new Card("płatki kukurydziane", "cornflakes", cDeck),
                    new Card("kosztować", "cost", cDeck),
                    new Card("przebranie", "costume", cDeck),
                    new Card("domek", "cottage", cDeck),
                    new Card("bawełna", "cotton", cDeck),
                    new Card("kaszleć", "cough", cDeck),
                    new Card("kaszel", "cough", cDeck),
                    new Card("liczyć", "count", cDeck),
                    new Card("kraj", "country", cDeck),
                    new Card("państwo", "country", cDeck),
                    new Card("kowboj", "cowboy", cDeck),
                    new Card("pękniecie", "crack", cDeck),
                    new Card("przechodzić", "cross", cDeck),
                    new Card("krzyżówka", "crossword", cDeck),
                    new Card("płakać", "cry", cDeck),
                    new Card("filiżanka", "cup", cDeck),
                    new Card("klient", "customer", cDeck),
                    new Card("ciąć", "cut", cDeck)
            ));
            cardRepository.saveAll(cCards);
        };
    }
}
