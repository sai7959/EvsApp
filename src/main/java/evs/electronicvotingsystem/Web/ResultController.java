package evs.electronicvotingsystem.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.Vote;
import evs.electronicvotingsystem.Service.ResultServiceImpl;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private ResultServiceImpl resultServiceImpl;

    @PostMapping("/election/{electionId}")
    public ResponseEntity<String> declareResult(@PathVariable Long electionId) {

        List<Vote> votes = resultServiceImpl.getElectionVotes(electionId);
        if (votes == null) {
            return new ResponseEntity<>("votes are none", HttpStatus.BAD_REQUEST);
        }
        resultServiceImpl.declareResult(electionId);
        return new ResponseEntity<>("Declared", HttpStatus.OK);

    }

}
