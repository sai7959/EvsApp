package evs.electronicvotingsystem.Web;

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

import evs.electronicvotingsystem.POJO.UserDetails;
import evs.electronicvotingsystem.Service.RoleServiceImpl;
import evs.electronicvotingsystem.Service.UserDetailsServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/userdetails")
public class UserDetailsController {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping
    public ResponseEntity<String> saveUserDetails(@Valid @RequestBody UserDetails userDetails) {
        if (userDetails == null || userDetails.getUser() == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if (userDetailsServiceImpl.isUserDetailsExists(userDetails.getUser())) {
            return new ResponseEntity<>("UserDetails already exists", HttpStatus.BAD_REQUEST);
        } else if (roleServiceImpl.getVoterRole() == null) {
            return new ResponseEntity<>("Voter role doesn't exist", HttpStatus.BAD_REQUEST);

        }

        userDetailsServiceImpl.saveUserDetails(userDetails);
        return new ResponseEntity<>("UserDetails saved successfully", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserDetails(@Valid @RequestBody UserDetails userDetails,
            @PathVariable Long id) {
        if (userDetails == null || userDetails.getUser() == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        } else if (userDetailsServiceImpl.getUserDetailsById(id) == null) {
            return new ResponseEntity<>("UserDetails  does not exist", HttpStatus.BAD_REQUEST);
        }
        userDetailsServiceImpl.updateUserDetails(userDetails, id);
        return new ResponseEntity<>("UserDetails updated successfully", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserDetailsById(@PathVariable Long id) {
        UserDetails userDetails = userDetailsServiceImpl.getUserDetailsById(id);
        if (userDetails == null) {
            return new ResponseEntity<>("UserDetails doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDetails, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getAllUsersDetails() {
        List<UserDetails> usersDetails = userDetailsServiceImpl.getAllUserDetails();
        if (usersDetails.isEmpty()) {
            return new ResponseEntity<>("No UserDetails records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersDetails, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserDetailsById(@PathVariable Long id) {
        UserDetails userDetails = userDetailsServiceImpl.getUserDetailsById(id);
        if (userDetails == null) {
            return new ResponseEntity<>("UserDetails doesn't exist", HttpStatus.NOT_FOUND);
        }
        userDetailsServiceImpl.deleteUserDetailsById(id);
        return new ResponseEntity<>("UserDetails deleted successfully", HttpStatus.OK);
    }

}
