package com.dg.f1fantasyback.controller.racing;

import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.service.ConstructorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/constructors")
public class ConstructorController {
    private final ConstructorService constructorService;

    public ConstructorController(ConstructorService constructorService) {
        this.constructorService = constructorService;
    }

    @GetMapping
    public Iterable<ConstructorDto> getUserTeams() {
        return constructorService.getTeams();
    }

    @GetMapping("/{id}")
    public ConstructorDto getUserTeamById(@PathVariable Integer id) {
        return constructorService.getTeamById(id);
    }
}
