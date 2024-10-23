package evs.electronicvotingsystem.Service;

import evs.electronicvotingsystem.POJO.Party;

public interface PartyService {

    public Party getPartyById(Long id);

    public Party updateParty(Party party, Long id);

    public void deletePartyById(Long id);

}
