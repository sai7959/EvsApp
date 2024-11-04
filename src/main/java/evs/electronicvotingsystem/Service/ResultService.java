package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.Vote;

public interface ResultService {
    public void declareResult(Long electionId);

    public List<Vote> getElectionVotes(Long electionId);

}
