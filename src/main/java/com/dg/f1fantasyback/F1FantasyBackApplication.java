package com.dg.f1fantasyback;

import com.dg.f1fantasyback.model.entity.Driver;
import com.dg.f1fantasyback.model.entity.Team;
import com.dg.f1fantasyback.model.entity.User;
import com.dg.f1fantasyback.model.enums.Role;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.TeamRepository;
import com.dg.f1fantasyback.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class F1FantasyBackApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final TeamRepository teamRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public F1FantasyBackApplication(UserRepository userRepository, DriverRepository driverRepository, TeamRepository teamRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(F1FantasyBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = User.builder().username("my-admin").password(passwordEncoder.encode("admin")).enabled(true).role(Role.ROLE_ADMIN).build();
            userRepository.save(user);

            User user2 = User.builder().username("my-user").password(passwordEncoder.encode("user")).enabled(true).role(Role.ROLE_USER).build();
            userRepository.save(user2);
        }

        if (teamRepository.count() == 0) {
            // Mercedes
            Team mercedes = Team.builder().label("Mercedes").build();
            teamRepository.save(mercedes);

            Driver russell = Driver.builder().firstName("George").lastName("Russell").profilePicture("empty").team(mercedes).build();
            Driver antonelli = Driver.builder().firstName("Kimi").lastName("Antonelli").profilePicture("empty").team(mercedes).build();
            driverRepository.saveAll(Arrays.asList(russell, antonelli));

            // McLaren
            Team mclaren = Team.builder().label("McLaren").build();
            teamRepository.save(mclaren);

            Driver norris = Driver.builder().firstName("Lando").lastName("Norris").profilePicture("empty").team(mclaren).build();
            Driver piastri = Driver.builder().firstName("Oscar").lastName("Piastri").profilePicture("empty").team(mclaren).build();
            driverRepository.saveAll(Arrays.asList(norris, piastri));

            // Ferrari
            Team ferrari = Team.builder().label("Ferrari").build();
            teamRepository.save(ferrari);

            Driver leclerc = Driver.builder().firstName("Charles").lastName("Leclerc").profilePicture("empty").team(ferrari).build();
            Driver hamilton = Driver.builder().firstName("Lewis").lastName("Hamilton").profilePicture("empty").team(ferrari).build();
            driverRepository.saveAll(Arrays.asList(leclerc, hamilton));

            // Red Bull Racing
            Team redbull = Team.builder().label("Red Bull Racing").build();
            teamRepository.save(redbull);

            Driver verstappen = Driver.builder().firstName("Max").lastName("Verstappen").profilePicture("empty").team(redbull).build();
            Driver lawson = Driver.builder().firstName("Liam").lastName("Lawson").profilePicture("empty").team(redbull).build();
            driverRepository.saveAll(Arrays.asList(verstappen, lawson));

            // Williams
            Team williams = Team.builder().label("Williams").build();
            teamRepository.save(williams);

            Driver albon = Driver.builder().firstName("Alexander").lastName("Albon").profilePicture("empty").team(williams).build();
            Driver sainz = Driver.builder().firstName("Carlos").lastName("Sainz").profilePicture("empty").team(williams).build();
            driverRepository.saveAll(Arrays.asList(albon, sainz));

            // Haas
            Team haas = Team.builder().label("Haas").build();
            teamRepository.save(haas);

            Driver ocon = Driver.builder().firstName("Esteban").lastName("Ocon").profilePicture("empty").team(haas).build();
            Driver bearman = Driver.builder().firstName("Oliver").lastName("Bearman").profilePicture("empty").team(haas).build();
            driverRepository.saveAll(Arrays.asList(ocon, bearman));

            // Aston Martin
            Team astonMartin = Team.builder().label("Aston Martin").build();
            teamRepository.save(astonMartin);

            Driver stroll = Driver.builder().firstName("Lance").lastName("Stroll").profilePicture("empty").team(astonMartin).build();
            Driver alonso = Driver.builder().firstName("Fernando").lastName("Alonso").profilePicture("empty").team(astonMartin).build();
            driverRepository.saveAll(Arrays.asList(stroll, alonso));

            // Kick Sauber
            Team sauber = Team.builder().label("Kick Sauber").build();
            teamRepository.save(sauber);

            Driver hulkenberg = Driver.builder().firstName("Nico").lastName("Hulkenberg").profilePicture("empty").team(sauber).build();
            Driver bortoleto = Driver.builder().firstName("Gabriel").lastName("Bortoleto").profilePicture("empty").team(sauber).build();
            driverRepository.saveAll(Arrays.asList(hulkenberg, bortoleto));

            // Racing Bulls
            Team racingBulls = Team.builder().label("Racing Bulls").build();
            teamRepository.save(racingBulls);

            Driver hadjar = Driver.builder().firstName("Isack").lastName("Hadjar").profilePicture("empty").team(racingBulls).build();
            Driver tsunoda = Driver.builder().firstName("Yuki").lastName("Tsunoda").profilePicture("empty").team(racingBulls).build();
            driverRepository.saveAll(Arrays.asList(hadjar, tsunoda));

            // Alpine
            Team alpine = Team.builder().label("Alpine").build();
            teamRepository.save(alpine);

            Driver gasly = Driver.builder().firstName("Pierre").lastName("Gasly").profilePicture("empty").team(alpine).build();
            Driver doohan = Driver.builder().firstName("Jack").lastName("Doohan").profilePicture("empty").team(alpine).build();
            driverRepository.saveAll(Arrays.asList(gasly, doohan));
        }
    }
}
