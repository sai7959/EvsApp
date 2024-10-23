package evs.electronicvotingsystem.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.Service.UserServiceImpl;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<Object> home() {
        return new ResponseEntity<>(userServiceImpl.getCurrentUser().getEmail(), HttpStatus.OK);
    }

}
