package lv.martins.homework.controller;

import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.dto.KidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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

    @GetMapping("/kid")
    public List<KidDTO> findAllKids(){
        return kidService.findAllKids();
    }

    @GetMapping("/kid/{kidId}")
    public KidDTO findById(@PathVariable("kidId") Long kidId) throws NotFoundException {
        return kidService.findById(kidId);
    }

    @GetMapping("/playSite/{playSiteId}/kid")
    public List<KidDTO> findAllKidsByPlaySite(@PathVariable("playSiteId") Long playSiteId) throws NotFoundException {
        return kidService.findByPlaySiteId(playSiteId);
    }

    @DeleteMapping("/playSite/{playSiteId}/kid/{kidId}")
    public ResponseEntity<Object> removeKidFromPlaySite(@PathVariable("playSiteId") Long playSiteId,
                                                        @PathVariable("kidId") Long kidId) throws NotFoundException {
        kidService.removeKidPromPlaySite(playSiteId, kidId);
        return ResponseEntity.noContent().build();
    }

}
