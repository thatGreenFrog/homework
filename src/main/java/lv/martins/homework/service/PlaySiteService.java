package lv.martins.homework.service;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.service.dto.PlaySiteDTO;
import lv.martins.homework.service.dto.PlaySiteStatisticsDTO;

import java.util.List;

public interface PlaySiteService {

    long createPlaySite(PlaySiteDTO playSiteDTO) throws ConflictException;

    List<PlaySiteDTO> getAll();

    PlaySiteDTO findById(Long playSiteId) throws NotFoundException;

    PlaySiteStatisticsDTO getPlaySiteStatistics();

    void deletePlaySite(Long playSiteId) throws NotFoundException;

}
