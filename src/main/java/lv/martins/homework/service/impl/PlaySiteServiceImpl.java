package lv.martins.homework.service.impl;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.repository.PlaySiteAttractionRepository;
import lv.martins.homework.repository.PlaySiteRepository;
import lv.martins.homework.repository.entities.PlaySite;
import lv.martins.homework.repository.entities.PlaySiteAttraction;
import lv.martins.homework.service.PlaySiteService;
import lv.martins.homework.service.dto.AttractionDTO;
import lv.martins.homework.service.dto.AttractionType;
import lv.martins.homework.service.dto.PlaySiteDTO;
import lv.martins.homework.service.dto.PlaySiteStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PlaySiteServiceImpl implements PlaySiteService {

    /**
     * Reusable function to map entity to DTO
     */
    private final Function<PlaySite, PlaySiteDTO> entityToDtoMapper = p -> new PlaySiteDTO(
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
                    .toList(),
            calculatePlaySiteUtilization(p)
    );

    private final PlaySiteRepository playSiteRepository;

    private final PlaySiteAttractionRepository playSiteAttractionRepository;

    @Autowired
    public PlaySiteServiceImpl(PlaySiteRepository playSiteRepository,
                               PlaySiteAttractionRepository playSiteAttractionRepository) {
        this.playSiteRepository = playSiteRepository;
        this.playSiteAttractionRepository = playSiteAttractionRepository;
    }

    /**
     * Creates new play site with specified attractions
     * @param playSiteDTO play site provided in request
     * @return id of newly created play site
     * @throws ConflictException is thrown if play site with the same name already exists
     */
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
                .forEach(playSiteAttractionRepository::save);

        return playSite.getId();
    }

    /**
     *
     * @return all play sites present in DB
     */
    @Override
    public List<PlaySiteDTO> getAll() {
        return playSiteRepository.findAll()
                .stream()
                .map(entityToDtoMapper)
                .toList();
    }

    /**
     *
     * @param playSiteId play site id provided in request path param
     * @return play site with provided id
     * @throws NotFoundException is thrown if play site with specified id doesn't exist
     */
    @Override
    public PlaySiteDTO findById(Long playSiteId) throws NotFoundException {
        Optional<PlaySite> playSite = playSiteRepository.findById(playSiteId);
        if(playSite.isEmpty())
            throw new NotFoundException("Play site does not exist");
        return entityToDtoMapper.apply(playSite.get());
    }

    /**
     * Returns play site statistics: Total kids on all play sites, total kids in queue,
     * total space available in all play sites, total play site utilization
     * @return play site statistics
     */
    @Override
    public PlaySiteStatisticsDTO getPlaySiteStatistics() {
        List<PlaySite> playSites = playSiteRepository.findAll();
        Long totalPlaySiteSize = playSites.stream()
                .mapToLong(p -> p.getPlaySiteAttractions()
                        .stream()
                        .mapToInt(PlaySiteAttraction::getSize)
                        .sum())
                .sum();
        Long totalKidsOnSites = playSites.stream()
                .mapToLong(p -> p.getKids().stream()
                        .filter(k -> k.getSpotInQueue() == null)
                        .count())
                .sum();
        Long totalKidsInQueue = playSites.stream()
                .mapToLong(p -> p.getKids().stream()
                        .filter(k -> k.getSpotInQueue() != null)
                        .count())
                .sum();
        Integer totalUtilization = (int)(((double)totalKidsOnSites / (double)totalPlaySiteSize) * 100);
        return new PlaySiteStatisticsDTO(totalPlaySiteSize, totalKidsOnSites, totalKidsInQueue, totalUtilization);
    }

    private Integer calculatePlaySiteUtilization(PlaySite p){
        if(p.getPlaySiteAttractions().stream().anyMatch(a -> !a.getType().equals(AttractionType.DOUBLE_SWING.name()))) {
            return (int)(((double) p.getKids().size() / (double) p.getPlaySiteAttractions().stream().mapToInt(PlaySiteAttraction::getSize).sum()) * 100);
        }
        else if (p.getKids().size() < p.getPlaySiteAttractions().stream().mapToInt(PlaySiteAttraction::getSize).sum()) {
            return 0;
        }
        else{
            return 100;
        }
    }

}
