package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.POJO.Vote;
import evs.electronicvotingsystem.Repository.PartyRepository;

@Service
public class PartyServiceImpl implements PartyService {
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public Party getPartyById(Long id) {
        return partyRepository.findById(id).filter(party -> party.isActive() && !party.isDeleted()).orElse(null);

    }

    @Override
    public Party updateParty(Party party, Long id) {
        Party existingParty = getPartyById(id);
        existingParty.setCandidateName(party.getCandidateName());
        existingParty.setName(party.getName());
        existingParty.setUpdatedAt(Date.from(Instant.now()));
        existingParty.setUpdatedBy(userServiceImpl.getCurrentUser());
        return partyRepository.save(existingParty);

    }

    @Override
    public void deletePartyById(Long id) {
        Party party = getPartyById(id);
        party.setActive(false);
        party.setDeleted(true);
        party.setUpdatedAt(Date.from(Instant.now()));
        party.setUpdatedBy(userServiceImpl.getCurrentUser());
        partyRepository.save(party);
    }

    @Override
    public List<Vote> getPartyVotes(Long partyId) {
        Party party = getPartyById(partyId);
        return party.getVotes();

    }

}
