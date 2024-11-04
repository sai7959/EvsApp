package evs.electronicvotingsystem.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.Vote;
import evs.electronicvotingsystem.Service.ElectionServiceImpl;
import evs.electronicvotingsystem.Service.PartyServiceImpl;
import evs.electronicvotingsystem.Service.UserServiceImpl;
import evs.electronicvotingsystem.Service.VoteServiceImpl;

@RestController
@RequestMapping("/vote")
public class VoterController {
    @Autowired
    private VoteServiceImpl voteServiceImpl;
    @Autowired
    private ElectionServiceImpl electionServiceImpl;
    @Autowired
    private PartyServiceImpl partyServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/election/{electionId}/party/{partyId}/user/{userId}")
    public ResponseEntity<Object> getVote(@PathVariable Long electionId, @PathVariable Long partyId,
            @PathVariable Long userId) {
        Vote vote = voteServiceImpl.getCastVate(electionId, partyId, userId);
        if (vote == null) {
            return new ResponseEntity<>("No record", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @PostMapping("/election/{electionId}/party/{partyId}/user/{userId}")
    public ResponseEntity<String> castVote(@PathVariable Long electionId, @PathVariable Long partyId,
            @PathVariable Long userId) {

        if (voteServiceImpl.getCastVate(electionId, partyId, userId) == null) {
            Election election = electionServiceImpl.getElectionById(electionId);
            Party party = partyServiceImpl.getPartyById(partyId);
            User user = userServiceImpl.getUserById(userId);
            if (election == null) {
                return new ResponseEntity<>("Election doesn't exist", HttpStatus.BAD_REQUEST);
            } else if (party == null) {
                return new ResponseEntity<>("Party doesn't exist", HttpStatus.BAD_REQUEST);
            } else if (user == null) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            } else if (!user.isEligibleToVote()) {
                return new ResponseEntity<>("Not eligible to vote", HttpStatus.BAD_REQUEST);
            }
            Vote vote = new Vote();
            vote.setElection(election);
            vote.setParty(party);
            vote.setUser(user);
            voteServiceImpl.castVate(vote);
            return new ResponseEntity<>("Casted vote successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Already casted vote", HttpStatus.BAD_REQUEST);
    }

}
