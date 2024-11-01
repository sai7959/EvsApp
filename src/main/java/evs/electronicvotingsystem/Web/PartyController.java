package evs.electronicvotingsystem.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.Party;
import evs.electronicvotingsystem.Service.PartyServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/party")
public class PartyController {
    @Autowired
    private PartyServiceImpl partyServiceImpl;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateParty(@Valid @RequestBody Party party, @PathVariable Long id) {
        if (party == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);
        } else if (partyServiceImpl.getPartyById(id) == null) {
            return new ResponseEntity<>("Party  does not exist", HttpStatus.BAD_REQUEST);

        }
        partyServiceImpl.updateParty(party, id);
        return new ResponseEntity<>("Party updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPartyById(@PathVariable Long id) {
        Party party = partyServiceImpl.getPartyById(id);
        if (party == null) {
            return new ResponseEntity<>("Party Id doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(party, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePartyById(@PathVariable Long id) {
        Party party = partyServiceImpl.getPartyById(id);
        if (party == null) {
            return new ResponseEntity<>("Party doesn't exist", HttpStatus.NOT_FOUND);
        }
        partyServiceImpl.deletePartyById(id);
        return new ResponseEntity<>("Party deleted successfully", HttpStatus.OK);

    }

    @GetMapping("/{partyId}/votes")
    public ResponseEntity<Object> getPartyVotes(@PathVariable Long partyId) {
        Party party = partyServiceImpl.getPartyById(partyId);
        if (party == null) {
            return new ResponseEntity<>("Party doesn't exist", HttpStatus.BAD_REQUEST);
        } else if (partyServiceImpl.getPartyVotes(partyId).isEmpty()
                || partyServiceImpl.getPartyVotes(partyId) == null) {
            return new ResponseEntity<>("No votes", HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(partyServiceImpl.getPartyVotes(partyId), HttpStatus.OK);
    }

}
