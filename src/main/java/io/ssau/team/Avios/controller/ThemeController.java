package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.model.json.ThemeJson;
import io.ssau.team.Avios.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
public class ThemeController {

    private ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }


    @PostMapping("/theme")
    public ResponseEntity<ThemeJson> createTheme(@RequestBody ThemeJson theme) {
        return ResponseEntity.status(HttpStatus.CREATED).body(themeService.createTheme(theme));
    }

    @GetMapping("/theme/{id}")
    public List<ThemeJson> getThemes(@PathVariable("id") Integer id) {
        return this.themeService.getThemeList(id);
    }

    @GetMapping("/theme/check")
    public ResponseEntity<ThemeJson> checkThemeReady(){
        ThemeJson readyTheme = themeService.getReadyTheme(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        );
        return readyTheme != null ? ResponseEntity.ok(readyTheme) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/theme/vote-up/{id}")
    public ResponseEntity subscribeToTheme(@PathVariable("id") Integer id, Principal principal){
        //todo вынести user в бин
        themeService.subscribeToTheme(id, ((User) principal).getId(), true);
        return new ResponseEntity(HttpStatus.OK);
    }

}