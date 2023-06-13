package lv.martins.homework.service.impl;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.repository.KidRepository;
import lv.martins.homework.repository.PlaySiteKidRepository;
import lv.martins.homework.repository.entities.Kid;
import lv.martins.homework.repository.entities.PlaySiteKid;
import lv.martins.homework.service.KidService;
import lv.martins.homework.service.dto.KidDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KidServiceImpl implements KidService {

    private final KidRepository kidRepository;

    private final PlaySiteKidRepository playSiteKidRepository;

    @Autowired
    public KidServiceImpl(KidRepository kidRepository, PlaySiteKidRepository playSiteKidRepository) {
        this.kidRepository = kidRepository;
        this.playSiteKidRepository = playSiteKidRepository;
    }

    @Override
    public long addKidToPlaySite(KidDTO kid, Long playSiteId) throws ConflictException {
        Kid kidEntity = kidRepository.findByName(kid.name());
        if(kidEntity != null) {
            String playSiteName = kidEntity.getPlaySite().getPlaySite().getName();
            throw new ConflictException("Kid is already present in %s play site".formatted(playSiteName), kidEntity.getId());
        }

        kidEntity = new Kid();
        kidEntity.setAge(kid.age());
        kidEntity.setName(kid.name());
        kidEntity.setTicketNumber(kid.ticketNumber());
        kidRepository.save(kidEntity);

        PlaySiteKid playSiteKid = new PlaySiteKid();
        playSiteKid.setPlaySiteId(playSiteId);
        playSiteKid.setKidId(kidEntity.getId());
        playSiteKidRepository.save(playSiteKid);

        return kidEntity.getId();
    }
}
