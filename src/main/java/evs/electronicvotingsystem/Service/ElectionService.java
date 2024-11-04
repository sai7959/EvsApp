package evs.electronicvotingsystem.Service;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.Party;

import java.util.List;

public interface ElectionService {
    public Election saveElection(Election election, Long stateId);

    public Election getElectionById(Long id);

    public Election updateElection(Election election, Long id);

    public void deleteElectionById(Long id);

    public boolean isPartyExists(Long electionId, String partyName);

    public Election addPartyToElection(Election election);

    public List<Party> getElectionparties(Long electionId);

}
