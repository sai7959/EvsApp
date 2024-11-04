package evs.electronicvotingsystem.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evs.electronicvotingsystem.POJO.Role;
import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserRole;
import evs.electronicvotingsystem.Service.RoleServiceImpl;
import evs.electronicvotingsystem.Service.UserRoleServiceImpl;
import evs.electronicvotingsystem.Service.UserServiceImpl;

@RestController
@RequestMapping("/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<String> saveUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userServiceImpl.getUserById(roleId);
        Role role = roleServiceImpl.getRoleById(roleId);
        if (user == null)
            return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
        else if (role == null)
            return new ResponseEntity<>("Role doesn't exist", HttpStatus.BAD_REQUEST);

        else if (userRoleServiceImpl.getUserRole(userId, roleId) != null) {
            return new ResponseEntity<>("User Role already exists", HttpStatus.BAD_REQUEST);

        }
        userRoleServiceImpl.saveUserRole(userId, roleId);
        return new ResponseEntity<>("User Role saved successfully", HttpStatus.CREATED);

    }

    @GetMapping("user/{userId}/role/{roleId}")
    public ResponseEntity<Object> getUserRoleById(@PathVariable Long userId, @PathVariable Long roleId) {
        UserRole userRole = userRoleServiceImpl.getUserRole(userId, roleId);
        if (userRole == null) {
            return new ResponseEntity<>("User Role doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRole, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> getAllUserRoles() {
        List<UserRole> usersRoles = userRoleServiceImpl.getAllUserRoles();
        if (usersRoles.isEmpty()) {
            return new ResponseEntity<>("No User Role records", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersRoles, HttpStatus.OK);

    }

    @DeleteMapping("user/{userId}/role/{roleId}")
    public ResponseEntity<String> deleteUserRoleById(@PathVariable Long userId, @PathVariable Long roleId) {
        UserRole userRole = userRoleServiceImpl.getUserRole(userId, roleId);
        if (userRole == null) {
            return new ResponseEntity<>("User Role doesn't exist", HttpStatus.BAD_REQUEST);
        }
        userRoleServiceImpl.deleteUserRole(userId, roleId);
        return new ResponseEntity<>("User Role deleted successfully", HttpStatus.OK);
    }

}
