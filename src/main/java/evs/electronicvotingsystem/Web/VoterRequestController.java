package evs.electronicvotingsystem.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.VoterRequest;
import evs.electronicvotingsystem.Service.UserDetailsServiceImpl;
import evs.electronicvotingsystem.Service.UserServiceImpl;
import evs.electronicvotingsystem.Service.VoterRequestServiceImpl;

@RestController
@RequestMapping("/voter-request")
public class VoterRequestController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private VoterRequestServiceImpl voterRequestServiceImpl;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> requestVote(@PathVariable Long userId) {
        User user = userServiceImpl.getUserById(userId);
        if (user == null)
            return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
        else if (!userDetailsServiceImpl.isUserDetailsExists(user)) {
            return new ResponseEntity<>("UserDetails doesn't exist", HttpStatus.BAD_REQUEST);
        }

        else if (!userServiceImpl.isEligibleToVote(user)) {
            return new ResponseEntity<>("User age is not eleigible to vote", HttpStatus.BAD_REQUEST);
        }
        VoterRequest voterRequest = new VoterRequest();
        voterRequest.setUser(user);
        voterRequestServiceImpl.saveVoterRequest(voterRequest);
        return new ResponseEntity<>("Request submitted successfully", HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getVoterRequests() {
        List<VoterRequest> voterRequests = voterRequestServiceImpl.getVoterRequests();
        if (voterRequests == null) {
            return new ResponseEntity<>("No records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(voterRequests, HttpStatus.OK);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveVoterRequest(@PathVariable Long id) {
        VoterRequest voterRequest = voterRequestServiceImpl.getVoterRequestById(id);
        if (voterRequest == null) {
            return new ResponseEntity<>("Request Id doesn't exist", HttpStatus.BAD_REQUEST);

        }
        voterRequestServiceImpl.approveVoterRequest(id);

        return new ResponseEntity<>("Approved", HttpStatus.OK);

    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectVoterRequest(@PathVariable Long id) {
        VoterRequest voterRequest = voterRequestServiceImpl.getVoterRequestById(id);
        if (voterRequest == null) {
            return new ResponseEntity<>("Request Id doesn't exist", HttpStatus.BAD_REQUEST);

        }
        voterRequestServiceImpl.rejectVoterRequest(id);
        return new ResponseEntity<>("Rejected", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVoterRequestById(@PathVariable Long id) {
        VoterRequest voterRequest = voterRequestServiceImpl.getVoterRequestById(id);
        if (voterRequest == null) {
            return new ResponseEntity<>("Request doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(voterRequest, HttpStatus.OK);

    }

}
