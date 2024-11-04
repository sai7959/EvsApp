package evs.electronicvotingsystem.Web;

import java.time.Instant;
import java.util.Date;
import java.util.List;

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
import evs.electronicvotingsystem.POJO.State;
import evs.electronicvotingsystem.Repository.ElectionRepository;
import evs.electronicvotingsystem.Service.StateServiceImpl;
import evs.electronicvotingsystem.Service.UserServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/state")
public class StateController {
    @Autowired
    private StateServiceImpl stateServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ElectionRepository electionRepository;

    @PostMapping
    public ResponseEntity<String> saveState(@Valid @RequestBody State state) {

        if (state == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if ((stateServiceImpl.isStateExists(state.getName()))) {
            return new ResponseEntity<>("State already exists", HttpStatus.BAD_REQUEST);
        }

        stateServiceImpl.saveState(state);
        return new ResponseEntity<>("State saved successfully", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateState(@Valid @RequestBody State state, @PathVariable Long id) {
        if (state == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if (stateServiceImpl.getStateById(id) == null) {
            return new ResponseEntity<>("State  does not exist", HttpStatus.BAD_REQUEST);
        }
        stateServiceImpl.updateState(state, id);
        return new ResponseEntity<>("State updated successfully", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStateById(@PathVariable Long id) {
        State state = stateServiceImpl.getStateById(id);

        if (state == null) {
            return new ResponseEntity<>("State doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(state, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getAllStates() {
        List<State> states = stateServiceImpl.getAllStates();
        if (states.isEmpty()) {
            return new ResponseEntity<>("No State records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(states, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStateById(@PathVariable Long id) {
        State state = stateServiceImpl.getStateById(id);
        if (state == null) {
            return new ResponseEntity<>("State doesn't exist", HttpStatus.NOT_FOUND);
        }
        stateServiceImpl.deleteStateById(id);
        return new ResponseEntity<>("State deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{stateId}/election")
    public ResponseEntity<String> addElection(@Valid @RequestBody Election election, @PathVariable Long stateId) {
        State state = stateServiceImpl.getStateById(stateId);
        if (state == null)
            return new ResponseEntity<>("State doesn't exist", HttpStatus.BAD_REQUEST);

        else if (stateServiceImpl.isElectionExistsForState(stateId, election.getName()))
            return new ResponseEntity<>("Election name already exists for the state", HttpStatus.BAD_REQUEST);

        election.setState(state);
        election.setActive(true);
        election.setDeleted(false);
        election.setCreatedAt(Date.from(Instant.now()));
        election.setCreatedBy(userServiceImpl.getCurrentUser());
        election.setUpdatedAt(Date.from(Instant.now()));
        election.setUpdatedBy(userServiceImpl.getCurrentUser());
        electionRepository.save(election);
        return new ResponseEntity<>("Election added successfully", HttpStatus.CREATED);

    }

    @GetMapping("/{stateId}/election")
    public ResponseEntity<Object> getStateElections(@PathVariable Long stateId) {
        State state = stateServiceImpl.getStateById(stateId);
        if (state == null) {
            return new ResponseEntity<>("State doesn't exist", HttpStatus.BAD_REQUEST);

        }
        List<Election> elections = stateServiceImpl.getStateElections(stateId);

        if (elections.isEmpty()) {
            return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(elections, HttpStatus.OK);

    }

}
