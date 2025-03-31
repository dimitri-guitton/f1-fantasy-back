package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.service.FixturesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GlobalController {

    private final FixturesService fixturesService;

    public GlobalController(FixturesService fixturesService) {
        this.fixturesService = fixturesService;
    }

    @GetMapping("/reload-results")
    public Map<String, String> reloadResults() {
        fixturesService.reloadRaceResults();

        Map<String, String> res = new HashMap<>();
        res.put("status", "ok");

        return res;

    }
}
