package lv.martins.homework.controller;

import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.dto.KidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kid")
public class KidController {

    private final KidService kidService;

    @Autowired
    public KidController(KidService kidService) {
        this.kidService = kidService;
    }

    @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<KidDTO> findAllKids(){
        return kidService.findAllKids();
    }

    @GetMapping(value = "/{kidId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public KidDTO findById(@PathVariable("kidId") Long kidId) throws NotFoundException {
        return kidService.findById(kidId);
    }

}
