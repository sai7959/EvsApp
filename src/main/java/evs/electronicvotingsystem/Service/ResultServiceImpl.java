package evs.electronicvotingsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.POJO.Result;
import evs.electronicvotingsystem.POJO.Vote;
import evs.electronicvotingsystem.Repository.ResultRepository;
import evs.electronicvotingsystem.Repository.VoteRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ElectionServiceImpl electionServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ResultRepository resultRepository;

    @Override
    public void declareResult(Long electionId) {
        List<Vote> electionVotes = ((List<Vote>) voteRepository.findAll()).stream()
                .filter(vote -> vote.getElection().getId() == electionId && vote.isActive() && !vote.isDeleted())
                .toList();

        electionVotes
                .forEach(vote -> System.out.println(
                        vote.getElection().getId() + " " + vote.getParty().getId() + " " + vote.getUser().getId()));
        Map<Object, Long> partyWithCount = electionVotes.stream()
                .collect(Collectors.groupingBy(vote -> vote.getParty(), Collectors.counting()));
        partyWithCount.forEach((party, count) -> System.out.println(((Party) party).getId()
                + " " + count));

        Optional<Map.Entry<Object, Long>> partyWithHighestCount = partyWithCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        System.out.println(partyWithHighestCount.get().getKey() + " " + partyWithHighestCount.get().getValue());
        Result result = new Result();
        result.setElection(electionServiceImpl.getElectionById(electionId));
        result.setParty((Party) partyWithHighestCount.get().getKey());
        result.setCreatedAt(Date.from(Instant.now()));
        result.setUpdatedAt(Date.from(Instant.now()));
        result.setUpdatedBy(userServiceImpl.getCurrentUser());
        result.setCreatedBy(userServiceImpl.getCurrentUser());
        result.setActive(true);
        result.setDeleted(false);
        resultRepository.save(result);

    }

    @Override
    public List<Vote> getElectionVotes(Long electionId) {
        return ((List<Vote>) voteRepository.findAll()).stream()
                .filter(vote -> vote.getElection().getId() == electionId && vote.isActive() && !vote.isDeleted())
                .toList();
    }

}
