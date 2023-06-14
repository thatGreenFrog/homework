package lv.martins.homework.service;

import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.dto.KidDTO;

import java.util.List;

public interface KidService {

    long addKidToPlaySite(KidDTO kid, Long playSiteId) throws CustomException;

    List<KidDTO> findAllKids();

    KidDTO findById(Long kidId) throws NotFoundException;

    List<KidDTO> findByPlaySiteId(Long playSiteId) throws NotFoundException;

    void removeKidPromPlaySite(Long playSiteId, Long kidId) throws NotFoundException;

}
