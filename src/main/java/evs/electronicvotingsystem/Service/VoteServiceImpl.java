package evs.electronicvotingsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import evs.electronicvotingsystem.POJO.Vote;
import evs.electronicvotingsystem.Repository.VoteRepository;
import java.util.*;
import java.time.Instant;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public Vote getCastVate(Long electionId, Long partyId, Long userId) {
        return ((List<Vote>) voteRepository.findAll()).stream()
                .filter(vote -> vote.getElection().getId() == electionId && vote.getParty().getId() == partyId
                        && vote.getUser() == userServiceImpl.getUserById(userId) && vote.isActive()
                        && !vote.isDeleted())
                .findFirst().orElse(null);

    }

    @Override
    public Vote castVate(Vote vote) {
        vote.setCreatedAt(Date.from(Instant.now()));
        vote.setCreatedBy(userServiceImpl.getCurrentUser());
        vote.setUpdatedAt(Date.from(Instant.now()));
        vote.setUpdatedBy(userServiceImpl.getCurrentUser());
        vote.setActive(true);
        vote.setDeleted(false);
        return voteRepository.save(vote);

    }

}
