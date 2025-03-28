package com.dg.f1fantasyback;

import com.dg.f1fantasyback.service.FixturesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class F1FantasyBackApplication implements CommandLineRunner {

    private final FixturesService fixturesService;

    public F1FantasyBackApplication(FixturesService fixturesService) {
        this.fixturesService = fixturesService;
    }

    public static void main(String[] args) {
        SpringApplication.run(F1FantasyBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fixturesService.load();
    }


}
