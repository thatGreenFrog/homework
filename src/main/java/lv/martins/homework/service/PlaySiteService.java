package lv.martins.homework.service;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.service.dto.PlaySiteDTO;

import java.util.List;

public interface PlaySiteService {

    long createPlaySite(PlaySiteDTO playSiteDTO) throws ConflictException;

    List<PlaySiteDTO> getAll();

}
