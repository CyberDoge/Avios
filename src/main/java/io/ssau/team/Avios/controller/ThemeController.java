package io.ssau.team.Avios.controller;

import io.ssau.team.Avios.model.User;
import io.ssau.team.Avios.model.json.ThemeJson;
import io.ssau.team.Avios.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ThemeJson getThemeById(@PathVariable("id") Integer id) {
        Integer userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return this.themeService.getTheme(id, userId);
    }
    @GetMapping("/themes/{id}")
    public List<ThemeJson> getThemes(@PathVariable("id") Integer id) {
        Integer userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return this.themeService.getThemeList(id, userId);
    }

    @GetMapping("/theme/check")
    public ResponseEntity<ThemeJson> checkThemeReady() {
        ThemeJson readyTheme = themeService.getReadyTheme(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
        );
        return readyTheme != null ? ResponseEntity.ok(readyTheme) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/theme/vote-up/{id}")
    public ResponseEntity<Boolean> subscribeToThemeAndVoteUp(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(themeService.subscribeToTheme(id,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                true), HttpStatus.OK);
    }

    @PutMapping("/theme/vote-down/{id}")
    public ResponseEntity<Boolean> subscribeToThemeAndVoteDown(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(themeService.subscribeToTheme(id,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                false), HttpStatus.OK);
    }

}