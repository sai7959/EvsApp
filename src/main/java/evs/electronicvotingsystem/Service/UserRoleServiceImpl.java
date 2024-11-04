package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Role;
import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserRole;
import evs.electronicvotingsystem.Repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Override
    public UserRole getUserRole(Long userId, Long roleId) {
        User user = userServiceImpl.getUserById(userId);
        Role role = roleServiceImpl.getRoleById(roleId);
        List<UserRole> userRoles = (List<UserRole>) userRoleRepository.findAll();
        return userRoles.stream().filter(userRole -> userRole.getUser().equals(user) && userRole.getRole().equals(role)
                && userRole.isActive() && !userRole.isDeleted()).findFirst().orElse(null);

    }

    @Override
    public UserRole saveUserRole(Long userId, Long roleId) {
        User user = userServiceImpl.getUserById(userId);
        Role role = roleServiceImpl.getRoleById(roleId);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setCreatedAt(Date.from(Instant.now()));
        userRole.setUpdatedAt(Date.from(Instant.now()));
        userRole.setActive(true);
        userRole.setDeleted(false);
        userRole.setUpdatedBy(userServiceImpl.getCurrentUser());
        userRole.setCreatedBy(userServiceImpl.getCurrentUser());
        System.out.println(userRole.getId() + " " + userRole.getUser() + " " + userRole.getRole());
        return userRoleRepository.save(userRole);

    }

    @Override
    public List<UserRole> getAllUserRoles() {
        List<UserRole> userRoles = (List<UserRole>) userRoleRepository.findAll();
        return userRoles.stream().filter(userRole -> userRole.isActive() && !userRole.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        UserRole userRole = getUserRole(userId, roleId);
        userRole.setActive(false);
        userRole.setDeleted(true);
        userRole.setUpdatedAt(Date.from(Instant.now()));
        userRole.setUpdatedBy(userServiceImpl.getCurrentUser());
        userRoleRepository.save(userRole);
    }

    @Override
    public String getRoleByUserEmail(String email) {
        List<User> users = userServiceImpl.getAllUsers();
        if (users.isEmpty())
            return "";

        User currentUser = users.stream()
                .filter(user -> user.getEmail().equals(email) && user.isActive() && !user.isDeleted()).findFirst()
                .orElse(null);
        if (currentUser == null) {
            return "";
        }
        System.out.println(currentUser.getEmail() + " " + currentUser.getId());
        List<UserRole> userRoles = (List<UserRole>) userRoleRepository.findAll();
        if (userRoles.isEmpty()) {
            return "";
        }
        UserRole currentUserRole = userRoles.stream()
                .filter(userRole -> userRole.getUser().getId() == currentUser.getId())
                .findFirst().orElse(null);
        if (currentUserRole == null) {
            return "";
        }

        Role role = currentUserRole.getRole();

        return (role == null) ? "" : role.getName();
    }

}
