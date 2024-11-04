package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.POJO.State;
import evs.electronicvotingsystem.Repository.ElectionRepository;
import evs.electronicvotingsystem.Repository.PartyRepository;

@Service
public class ElectionServiceImpl implements ElectionService {
    @Autowired
    private StateServiceImpl stateServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private PartyRepository partyRepository;

    @Override
    public Election saveElection(Election election, Long stateId) {
        State state = stateServiceImpl.getStateById(stateId);
        election.setState(state);
        election.setUpdatedAt(Date.from(Instant.now()));
        election.setCreatedAt(Date.from(Instant.now()));
        election.setCreatedBy(userServiceImpl.getCurrentUser());
        election.setUpdatedBy(userServiceImpl.getCurrentUser());
        return electionRepository.save(election);

    }

    @Override
    public Election getElectionById(Long id) {
        return electionRepository.findById(id).filter(election -> election.isActive() && !election.isDeleted())
                .orElse(null);
    }

    @Override
    public Election updateElection(Election election, Long id) {
        Election existingElection = getElectionById(id);
        existingElection.setName(election.getName());
        existingElection.setElectionDate(election.getElectionDate());
        existingElection.setUpdatedAt(Date.from(Instant.now()));
        existingElection.setUpdatedBy(userServiceImpl.getCurrentUser());
        return electionRepository.save(existingElection);
    }

    @Override
    public void deleteElectionById(Long id) {
        Election election = getElectionById(id);
        election.setActive(false);
        election.setDeleted(true);
        election.setUpdatedAt(Date.from(Instant.now()));
        election.setUpdatedBy(userServiceImpl.getCurrentUser());
        electionRepository.save(election);
    }

    @Override
    public boolean isPartyExists(Long electionId, String partyName) {
        return ((List<Party>) partyRepository.findAll()).stream()
                .filter(party -> party.getElection().getId() == electionId
                        && party.getName().equals(partyName)
                        && party.isActive() && !party.isDeleted())
                .count() > 0;
    }

    @Override
    public Election addPartyToElection(Election election) {
        return electionRepository.save(election);

    }

    @Override
    public List<Party> getElectionparties(Long electionId) {
        Election election = getElectionById(electionId);
        if (election == null) {
            return null;
        }
        return election.getParties().stream().filter(party -> party.isActive() && !party.isDeleted()).toList();
    }

}
