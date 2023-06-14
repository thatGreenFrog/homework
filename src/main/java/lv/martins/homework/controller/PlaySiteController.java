package lv.martins.homework.controller;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.PlaySiteService;
import lv.martins.homework.service.dto.PlaySiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class PlaySiteController {

    private final PlaySiteService playSiteService;

    @Autowired
    public PlaySiteController(PlaySiteService playSiteService) {
        this.playSiteService = playSiteService;
    }

    @PostMapping(path = "/playSite")
    public ResponseEntity<Object> createPlaySite(@RequestBody PlaySiteDTO playSiteDTO) throws ConflictException {
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(playSiteService.createPlaySite(playSiteDTO))
                        .toUri()
                )
                .build();
    }

    @GetMapping(path = "/playSite")
    public List<PlaySiteDTO> getAll(){
        return playSiteService.getAll();
    }

    @GetMapping(path = "/playSite/{playSiteId}")
    public PlaySiteDTO findById(@PathVariable("playSiteId") Long playSiteId) throws NotFoundException {
        return playSiteService.findById(playSiteId);
    }

}
