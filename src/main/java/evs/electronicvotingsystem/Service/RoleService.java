package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.Role;

public interface RoleService {
    public Role saveRole(Role role);

    public Role updateRole(Role role, Long id);

    public Role getRoleById(Long id);

    public List<Role> getAllRoles();

    public boolean isRoleExists(String role);

    public void deleteRoleById(Long id);

    public Role getVoterRole();

    public Role getCurrentRole();

}
