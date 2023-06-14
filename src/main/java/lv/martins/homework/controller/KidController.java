package lv.martins.homework.controller;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.dto.KidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class KidController {

    private final KidService kidService;

    @Autowired
    public KidController(KidService kidService) {
        this.kidService = kidService;
    }

    @PostMapping(path = "/playSite/{playSiteId}/kid")
    public ResponseEntity<Object> addKidToPlaySite(@RequestBody KidDTO kid, @PathVariable("playSiteId") Long playSiteId) throws CustomException {
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/kid/{id}")
                .buildAndExpand(kidService.addKidToPlaySite(kid, playSiteId))
                .toUri())
                .build();
    }

}
