package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.Constants.AppConstants;
import evs.electronicvotingsystem.POJO.Role;
import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserRole;
import evs.electronicvotingsystem.Repository.RoleRepository;
import evs.electronicvotingsystem.Repository.UserRoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Role saveRole(Role role) {
        role.setName(role.getName().toUpperCase());
        role.setActive(true);
        role.setDeleted(false);
        role.setCreatedAt(Date.from(Instant.now()));
        role.setUpdatedAt(Date.from(Instant.now()));
        role.setCreatedBy(userServiceImpl.getCurrentUser());
        role.setUpdatedBy(userServiceImpl.getCurrentUser());
        return roleRepository.save(role);

    }

    @Override
    public Role updateRole(Role role, Long id) {
        Role existingRole = getRoleById(id);
        existingRole.setName(role.getName());
        existingRole.setUpdatedAt(Date.from(Instant.now()));
        existingRole.setUpdatedBy(userServiceImpl.getCurrentUser());
        return roleRepository.save(existingRole);

    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).filter(role -> role.isActive() && !role.isDeleted()).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles.stream().filter(role -> role.isActive() && !role.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRoleById(id);
        role.setActive(false);
        role.setDeleted(true);
        role.setUpdatedAt(Date.from(Instant.now()));
        role.setUpdatedBy(userServiceImpl.getCurrentUser());
        roleRepository.save(role);
    }

    @Override
    public boolean isRoleExists(String roleName) {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles.stream()
                .filter(role -> role.getName().equals(roleName.toUpperCase()) && role.isActive() && !role.isDeleted())
                .count() > 0;
    }

    @Override
    public Role getVoterRole() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles.stream()
                .filter(role -> role.getName().equals(AppConstants.VOTER) && role.isActive() && !role.isDeleted())
                .findFirst().orElse(null);
    }

    @Override
    public Role getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = userServiceImpl.getAllUsers();
        if (users.isEmpty()) {
            return null;
        }
        User user = users.stream()
                .filter(current -> current.getEmail().equals(authentication.getPrincipal().toString()))
                .findFirst().orElse(null);
        if (user == null) {
            return null;
        }

        Role currentRole = ((List<UserRole>) userRoleRepository.findAll()).stream()
                .filter(userRole -> userRole.getUser().getId() == user.getId()).findFirst().map(role -> role.getRole())
                .orElse(null);

        return currentRole;

    }
}
