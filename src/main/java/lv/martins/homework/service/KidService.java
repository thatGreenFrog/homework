package lv.martins.homework.service;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.service.dto.KidDTO;

public interface KidService {

    long addKidToPlaySite(KidDTO kid, Long playSiteId) throws ConflictException;

}
