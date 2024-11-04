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

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.Service.UserServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {

        if (user == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        } else if (userServiceImpl.isEmailExists(user.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        userServiceImpl.saveUser(user);
        return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        if (user == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if (userServiceImpl.getUserById(id) == null) {
            return new ResponseEntity<>("User  does not exist", HttpStatus.BAD_REQUEST);
        }
        userServiceImpl.updateUser(user, id);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id);

        if (user == null) {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>("No User records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
        }
        userServiceImpl.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
