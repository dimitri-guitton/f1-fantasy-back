package com.dg.f1fantasyback;

import com.dg.f1fantasyback.model.entity.Driver;
import com.dg.f1fantasyback.model.entity.Team;
import com.dg.f1fantasyback.model.entity.User;
import com.dg.f1fantasyback.model.enums.Role;
import com.dg.f1fantasyback.repository.DriverRepository;
import com.dg.f1fantasyback.repository.TeamRepository;
import com.dg.f1fantasyback.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            // Read JSON file
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> teams = mapper.readValue(
                    new File("python/f1_teams_drivers.json"),
                    new TypeReference<>() {
                    }
            );

            // Process each team
            for (Map<String, Object> teamData : teams) {
                // Download team logo
                String shortLogo = (String) teamData.get("short_logo");
                String logoFileName = shortLogo.substring(shortLogo.lastIndexOf("/") + 1);
                downloadImage(shortLogo, logoFileName);

                String fullLogo = (String) teamData.get("full_logo");
                fullLogo = fullLogo.replace("team logos", "team%20logos");

                String teamName = fullLogo.substring(fullLogo.lastIndexOf("/") + 1);
                String fullLogoFileName = teamName.replace(" ", "-") + ".avif";
                String encodedTeamName = teamName.replace(" ", "%20");
                fullLogo = fullLogo.replace(teamName, encodedTeamName);

                downloadImage(fullLogo, fullLogoFileName);

                Team team = Team.builder()
                        .label((String) teamData.get("name"))
                        .logo(logoFileName)
                        .fullLogo(fullLogoFileName)
                        .build();
                teamRepository.save(team);

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> driversData = (List<Map<String, Object>>) teamData.get("drivers");
                List<Driver> drivers = driversData.stream()
                        .map(driverData -> {
                            String[] nameParts = ((String) driverData.get("name")).split(" ");
                            String imageUrl = (String) driverData.get("image");
                            String imageFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1) + ".avif";

                            // Download driver image
                            downloadImage(imageUrl, imageFileName);

                            return Driver.builder()
                                    .firstName(nameParts[0])
                                    .lastName(nameParts[1])
                                    .profilePicture(imageFileName)
                                    .team(team)
                                    .build();
                        })
                        .collect(Collectors.toList());

                driverRepository.saveAll(drivers);
            }
        }
    }

    // Add this method to download images
    private void downloadImage(String imageUrl, String fileName) {
        try {
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream in = new URL(imageUrl).openStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Error downloading image: " + imageUrl);
            e.printStackTrace();
        }
    }
}
