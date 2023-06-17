package lv.martins.homework.controller;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.PlaySiteService;
import lv.martins.homework.service.dto.KidDTO;
import lv.martins.homework.service.dto.PlaySiteDTO;
import lv.martins.homework.service.dto.PlaySiteStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/playSite")
public class PlaySiteController {

    private final PlaySiteService playSiteService;

    private final KidService kidService;

    @Autowired
    public PlaySiteController(PlaySiteService playSiteService, KidService kidService) {
        this.playSiteService = playSiteService;
        this.kidService = kidService;
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPlaySite(@RequestBody PlaySiteDTO playSiteDTO) throws ConflictException {
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(playSiteService.createPlaySite(playSiteDTO))
                        .toUri()
                )
                .build();
    }

    @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<PlaySiteDTO> getAll(){
        return playSiteService.getAll();
    }

    @GetMapping(value = "/{playSiteId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PlaySiteDTO findById(@PathVariable("playSiteId") Long playSiteId) throws NotFoundException {
        return playSiteService.findById(playSiteId);
    }

    @GetMapping(value = "/statistics", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public PlaySiteStatisticsDTO getPlaySiteStatistics(){
        return playSiteService.getPlaySiteStatistics();
    }

    @GetMapping(value = "/{playSiteId}/kid", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<KidDTO> findAllKidsByPlaySite(@PathVariable("playSiteId") Long playSiteId) throws NotFoundException {
        return kidService.findByPlaySiteId(playSiteId);
    }

    @DeleteMapping(value = "/{playSiteId}/kid/{kidId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> removeKidFromPlaySite(@PathVariable("playSiteId") Long playSiteId,
                                                        @PathVariable("kidId") Long kidId) throws NotFoundException {
        kidService.removeKidPromPlaySite(playSiteId, kidId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{playSiteId}/kid", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addKidToPlaySite(@RequestBody KidDTO kid, @PathVariable("playSiteId") Long playSiteId) throws CustomException {
        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentContextPath().path("/kid/{id}")
                        .buildAndExpand(kidService.addKidToPlaySite(kid, playSiteId))
                        .toUri())
                .build();
    }

    @DeleteMapping(value = "/{playSiteId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deletePlaySite(@PathVariable("playSiteId") Long playSiteId) throws NotFoundException {
        playSiteService.deletePlaySite(playSiteId);
        return ResponseEntity.noContent().build();
    }

}
