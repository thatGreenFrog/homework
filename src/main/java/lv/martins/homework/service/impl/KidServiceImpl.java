package lv.martins.homework.service.impl;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.exceptions.NotFoundException;
import lv.martins.homework.repository.KidRepository;
import lv.martins.homework.repository.PlaySiteRepository;
import lv.martins.homework.repository.entities.*;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.dto.KidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class KidServiceImpl implements KidService {

    private final Function<Kid, KidDTO> entityToDtoMapper = entity -> new KidDTO(entity.getId(), entity.getName(), entity.getAge(),
            entity.getTicketNumber(), entity.getPlaySiteId(), entity.getSpotInQueue() != null);

    private final KidRepository kidRepository;

    private final PlaySiteRepository playSiteRepository;

    @Autowired
    public KidServiceImpl(KidRepository kidRepository, PlaySiteRepository playSiteRepository) {
        this.kidRepository = kidRepository;
        this.playSiteRepository = playSiteRepository;
    }

    @Transactional
    @Override
    public long addKidToPlaySite(KidDTO kid, Long playSiteId) throws CustomException {
        Optional<PlaySite> playSite = playSiteRepository.findById(playSiteId);
        if (playSite.isEmpty())
            throw new NotFoundException("Play site does not exist");

        Kid kidEntity = kidRepository.findByTicketNumber(kid.ticketNumber());
        if (kidEntity != null) {
            throw new ConflictException(
                    "Kid is already present in '%s' play site".formatted(kidEntity.getPlaySite().getName()),
                    kidEntity.getId());
        }

        int playSiteSize = playSite.get().getPlaySiteAttractions().stream().mapToInt(PlaySiteAttraction::getSize).sum();
        long kidsOnPlaySite = playSite.get().getKids().stream().filter(k -> k.getSpotInQueue() == null).count();
        boolean spotAvailableOnPlaySite = playSiteSize - kidsOnPlaySite > 0;

        if (!spotAvailableOnPlaySite && Math.random() < 0.5)
            throw new CustomException("Kid doesn't want to stand in line", 4001);

        kidEntity = new Kid();
        kidEntity.setAge(kid.age());
        kidEntity.setName(kid.name());
        kidEntity.setTicketNumber(kid.ticketNumber());
        kidEntity.setPlaySiteId(playSiteId);
        if(!spotAvailableOnPlaySite){
            Integer nextSpotInQueue = kidRepository.findNextSpotInQueue();
            kidEntity.setSpotInQueue(nextSpotInQueue == null ? 1 : nextSpotInQueue + 1);
        }
        kidRepository.saveAndFlush(kidEntity);

        return kidEntity.getId();
    }

    @Override
    public List<KidDTO> findAllKids() {
        return kidRepository.findAll().stream().map(entityToDtoMapper).toList();
    }

    @Override
    public KidDTO findById(Long kidId) throws NotFoundException {
        Optional<Kid> kid = kidRepository.findById(kidId);
        if(kid.isEmpty())
            throw new NotFoundException("Kid does not exist");
        return entityToDtoMapper.apply(kid.get());
    }

    @Override
    public void removeKidPromPlaySite(Long playSiteId, Long kidId) throws NotFoundException {
        Optional<PlaySite> playSite = playSiteRepository.findById(playSiteId);
        if (playSite.isEmpty())
            throw new NotFoundException("Play site does not exist");

        Optional<Kid> kid = playSite.get().getKids().stream().filter(k -> Objects.equals(k.getId(), kidId)).findFirst();
        if(kid.isEmpty())
            throw new NotFoundException("Kid does not exist in this play site");

        kidRepository.delete(kid.get());

        Kid nextKidInQueue = kidRepository.findFirstByOrderBySpotInQueueAsc();
        nextKidInQueue.setSpotInQueue(null);
        kidRepository.saveAndFlush(nextKidInQueue);
    }
}
