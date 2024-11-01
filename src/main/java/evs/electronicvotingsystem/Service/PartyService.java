package evs.electronicvotingsystem.Service;

import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.POJO.Vote;

import java.util.*;

public interface PartyService {

    public Party getPartyById(Long id);

    public Party updateParty(Party party, Long id);

    public void deletePartyById(Long id);

    public List<Vote> getPartyVotes(Long partId);

}
