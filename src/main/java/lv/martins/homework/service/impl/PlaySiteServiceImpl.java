package lv.martins.homework.service.impl;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.repository.PlaySiteAttractionRepository;
import lv.martins.homework.repository.PlaySiteRepository;
import lv.martins.homework.repository.entities.PlaySite;
import lv.martins.homework.repository.entities.PlaySiteAttraction;
import lv.martins.homework.service.PlaySiteService;
import lv.martins.homework.service.dto.AttractionDTO;
import lv.martins.homework.service.dto.AttractionType;
import lv.martins.homework.service.dto.PlaySiteDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaySiteServiceImpl implements PlaySiteService {

    private PlaySiteRepository playSiteRepository;

    private PlaySiteAttractionRepository playSiteAttractionRepository;

    public PlaySiteServiceImpl(PlaySiteRepository playSiteRepository, PlaySiteAttractionRepository playSiteAttractionRepository) {
        this.playSiteRepository = playSiteRepository;
        this.playSiteAttractionRepository = playSiteAttractionRepository;
    }

    @Override
    public long createPlaySite(PlaySiteDTO playSiteDTO) throws ConflictException {
        PlaySite playSite = playSiteRepository.findByName(playSiteDTO.name());
        if (playSite != null)
            throw new ConflictException("PlaySite already exists", playSite.getId());
        playSite = new PlaySite(null, playSiteDTO.name());
        playSiteRepository.save(playSite);

        PlaySite finalPlaySite = playSite;
        playSiteDTO.attractions()
                .stream()
                .map(a -> {
                    PlaySiteAttraction playSiteAttractions = new PlaySiteAttraction();
                    playSiteAttractions.setPlaySiteId(finalPlaySite.getId());
                    playSiteAttractions.setSize(a.attractionType().equals(AttractionType.DOUBLE_SWING) ? 2 : a.size());
                    playSiteAttractions.setType(a.attractionType().name());
                    return playSiteAttractions;
                })
                .forEach(a -> playSiteAttractionRepository.save(a));

        return playSite.getId();
    }

    @Override
    public List<PlaySiteDTO> getAll() {
        return playSiteRepository.findAll()
                .stream()
                .map(p -> new PlaySiteDTO(
                        p.getId(),
                        p.getName(),
                        p.getPlaySiteAttractions()
                                .stream()
                                .map(pa -> new AttractionDTO(
                                        pa.getId(),
                                        AttractionType.valueOf(pa.getType()),
                                        pa.getSize()
                                        )
                                )
                                .toList()
                        )
                )
                .toList();
    }

}
