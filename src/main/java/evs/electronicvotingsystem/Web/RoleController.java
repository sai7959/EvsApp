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

import evs.electronicvotingsystem.POJO.Role;
import evs.electronicvotingsystem.Service.RoleServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping
    public ResponseEntity<String> saveRole(@Valid @RequestBody Role role) {

        if (role == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if (roleServiceImpl.isRoleExists(role.getName())) {
            return new ResponseEntity<>("Role already exists", HttpStatus.BAD_REQUEST);
        }

        roleServiceImpl.saveRole(role);
        return new ResponseEntity<>("Role saved successfully", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@Valid @RequestBody Role role, @PathVariable Long id) {
        if (role == null) {
            return new ResponseEntity<>("Please provide valid data", HttpStatus.BAD_REQUEST);

        }

        else if (roleServiceImpl.getRoleById(id) == null) {
            return new ResponseEntity<>("Role  does not exist", HttpStatus.BAD_REQUEST);
        }
        roleServiceImpl.updateRole(role, id);
        return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoleById(@PathVariable Long id) {
        Role role = roleServiceImpl.getRoleById(id);
        if (role == null) {
            return new ResponseEntity<>("Role doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getAllRoless() {
        List<Role> roles = roleServiceImpl.getAllRoles();
        if (roles.isEmpty()) {
            return new ResponseEntity<>("No Role records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Long id) {
        Role role = roleServiceImpl.getRoleById(id);
        if (role == null) {
            return new ResponseEntity<>("Role doesn't exist", HttpStatus.BAD_REQUEST);
        }
        roleServiceImpl.deleteRoleById(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }

}
