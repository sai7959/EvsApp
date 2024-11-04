package evs.electronicvotingsystem.Web;

import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.Service.ElectionServiceImpl;
import evs.electronicvotingsystem.Service.UserServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/election")
public class ElectionController {
    @Autowired
    private ElectionServiceImpl electionServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateElection(@Valid @RequestBody Election election, @PathVariable Long id) {
        if (election == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);
        } else if (electionServiceImpl.getElectionById(id) == null) {
            return new ResponseEntity<>("Election  does not exist", HttpStatus.BAD_REQUEST);

        }
        electionServiceImpl.updateElection(election, id);
        return new ResponseEntity<>("Election updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getElectionById(@PathVariable Long id) {
        Election election = electionServiceImpl.getElectionById(id);
        if (election == null) {
            return new ResponseEntity<>("Election Id doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(election, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteElectionById(@PathVariable Long id) {
        Election election = electionServiceImpl.getElectionById(id);
        if (election == null) {
            return new ResponseEntity<>("Election doesn't exist", HttpStatus.NOT_FOUND);
        }
        electionServiceImpl.deleteElectionById(id);
        return new ResponseEntity<>("Election deleted successfully", HttpStatus.OK);

    }

    @PostMapping("/{electionId}/party")
    public ResponseEntity<String> addPartyToElection(@PathVariable Long electionId, @Valid @RequestBody Party party) {
        Election election = electionServiceImpl.getElectionById(electionId);
        if (election == null) {
            return new ResponseEntity<>("Election doesn't exist", HttpStatus.NOT_FOUND);

        } else if (electionServiceImpl.isPartyExists(electionId, party.getName())) {
            return new ResponseEntity<>("Party alreday exists", HttpStatus.BAD_REQUEST);
        }
        party.setElection(election);
        party.setActive(true);
        party.setDeleted(false);
        party.setCreatedAt(Date.from(Instant.now()));
        party.setUpdatedAt(Date.from(Instant.now()));
        party.setUpdatedBy(userServiceImpl.getCurrentUser());
        party.setCreatedBy(userServiceImpl.getCurrentUser());
        election.getParties().add(party);
        electionServiceImpl.addPartyToElection(election);
        return new ResponseEntity<>("Party added successfully", HttpStatus.OK);

    }

    @GetMapping("/{electionId}/party")
    public ResponseEntity<Object> getElectionparties(@PathVariable Long electionId) {
        if (electionServiceImpl.getElectionparties(electionId) == null
                || electionServiceImpl.getElectionparties(electionId).isEmpty()) {
            return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(electionServiceImpl.getElectionparties(electionId), HttpStatus.OK);

    }

}
