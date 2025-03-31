package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.*;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import com.dg.f1fantasyback.model.enums.Role;
import com.dg.f1fantasyback.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FixturesService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final DriverRepository driverRepository;
    private final GrandPrixRepository grandPrixRepository;
    private final RaceRepository raceRepository;
    private final RaceResultRepository raceResultRepository;
    private final FantasyRacePointRepository fantasyRacePointRepository;

    public FixturesService(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           TeamRepository teamRepository,
                           DriverRepository driverRepository,
                           GrandPrixRepository grandPrixRepository,
                           RaceRepository raceRepository,
                           RaceResultRepository raceResultRepository,
                           FantasyRacePointRepository fantasyRacePointRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
        this.grandPrixRepository = grandPrixRepository;
        this.raceRepository = raceRepository;
        this.raceResultRepository = raceResultRepository;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
    }

    public void load() throws IOException {
        loadUsers();
        loadTeams();
        loadGrandPrixFixtures();
        loadRaceResult();
    }

    public void reloadRaceResults() {
        raceResultRepository.deleteAll();
        fantasyRacePointRepository.deleteAll();
        raceRepository.updateIsCalculatedBy(false);
        loadRaceResult();
    }

    private void loadUsers() {
        if (userRepository.count() > 0) {
            return;
        }

        User user = User.builder().username("my-admin").password(passwordEncoder.encode("admin")).enabled(true).role(
                Role.ROLE_ADMIN).build()
                ;
        userRepository.save(user);

        User user2 = User.builder()
                         .username("my-user")
                         .password(passwordEncoder.encode("user"))
                         .enabled(true)
                         .role(Role.ROLE_USER)
                         .build()
                ;
        userRepository.save(user2);
    }

    private void loadTeams() throws IOException {
        if (teamRepository.count() > 0) {
            return;
        }

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
                            .build()
                    ;
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
                                              .collect(Collectors.toList())
                    ;

            driverRepository.saveAll(drivers);
        }
    }

    private void loadGrandPrixFixtures() throws IOException {
        if (grandPrixRepository.count() > 0) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File("python/race_2025.json"));
        JsonNode racesNode = rootNode.get("races");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        for (JsonNode raceNode : racesNode) {
            JsonNode sessions = raceNode.get("sessions");
            GrandPrix grandPrix = GrandPrix.builder()
                                           .label(raceNode.get("name").asText())
                                           .startAt(LocalDateTime.parse(sessions.get("fp1").asText(), formatter))
                                           .endAt(LocalDateTime.parse(sessions.get("gp").asText(), formatter))
                                           .build()
                    ;

            Race qualifying = Race.builder()
                                  .grandPrix(grandPrix)
                                  .label("Qualifiquation")
                                  .startAt(sessions.has("qualifying") ?
                                                   LocalDateTime.parse(sessions.get("qualifying").asText(),
                                                                       formatter) : null)
                                  .type(RaceTypeEnum.QUALIFYING)
                                  .isCalculated(false)
                                  .build()
                    ;

            Race gp = Race.builder()
                          .grandPrix(grandPrix)
                          .label("GP")
                          .startAt(sessions.has("gp") ?
                                           LocalDateTime.parse(sessions.get("gp").asText(), formatter) : null)
                          .type(RaceTypeEnum.GP)
                          .isCalculated(false)
                          .build()
                    ;

            grandPrix.setRaces(Set.of(qualifying, gp));

            grandPrixRepository.save(grandPrix);
        }
    }

    private void loadRaceResult() {
        if (raceResultRepository.count() > 0) {
            return;
        }

        List<Race> races = raceRepository.findAllByOrderByTypeAscIdAsc();
        List<Driver> drivers = driverRepository.findAll();

        List<RaceResult> raceResults = new ArrayList<>();
        Map<String, Integer> qualifyingPos = new HashMap<>();
        races.forEach(race -> {
            Collections.shuffle(drivers);
            AtomicInteger position = new AtomicInteger(0);
            AtomicBoolean dnf = new AtomicBoolean(false);

            AtomicBoolean driverOfTheDay = new AtomicBoolean(false);
            AtomicBoolean isFastestLap = new AtomicBoolean(false);


            drivers.forEach(driver -> {
                String key = race.getGrandPrix().getId().toString() + '_' + driver.getId().toString();
                Integer startPosition = null;

                if (race.getType() == RaceTypeEnum.GP) {
                    startPosition = qualifyingPos.getOrDefault(key, null);

                    if (startPosition == null) {
                        throw new PointCalculatorException("The race (" + race.getId() + ") need qualifying result for driver (" + driver.getId() + ")");
                    }
                }

                if (position.get() > 15 && !dnf.get()) {
                    dnf.set(new Random().nextInt(5) == 0);  // 20% chance of DNF
                }

                if (!driverOfTheDay.get() && position.get() > 5) {
                    driverOfTheDay.set(true);
                } else if (!driverOfTheDay.get()) {
                    driverOfTheDay.set(new Random().nextInt(3) == 0); // 33%
                }

                if (!isFastestLap.get() && position.get() > 3) {
                    isFastestLap.set(true);
                } else if (!isFastestLap.get()) {
                    isFastestLap.set(new Random().nextInt(3) == 0); // 33%
                }

                int nbOvertakes = new Random().nextInt(10);


                RaceResult raceResult = RaceResult.builder()
                                                  .driver(driver)
                                                  .race(race)
                                                  .startPosition(startPosition)
                                                  .endPosition(position.getAndIncrement())
                                                  .dnf(dnf.get())
                                                  .driverOfTheDay(driverOfTheDay.get())
                                                  .fastestLap(isFastestLap.get())
                                                  .nbOvertakes(nbOvertakes)
                                                  .build()
                        ;
                raceResults.add(raceResult);

                if (race.getType() == RaceTypeEnum.QUALIFYING) {
                    qualifyingPos.put(key, position.get());
                }
            });
        });

        raceResultRepository.saveAll(raceResults);
    }

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
