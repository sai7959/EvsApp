package evs.electronicvotingsystem.Service;

import evs.electronicvotingsystem.POJO.Vote;

public interface VoteService {
    public Vote getCastVate(Long electionId, Long partyId, Long userId);

    public Vote castVate(Vote vote);

}
