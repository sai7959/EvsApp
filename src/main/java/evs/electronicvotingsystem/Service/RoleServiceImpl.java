package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Role;
import evs.electronicvotingsystem.Repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public Role saveRole(Role role) {
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
        return roles.stream().filter(role -> role.getName().equals(roleName) && role.isActive() && !role.isDeleted())
                .count() > 0;
    }

}
