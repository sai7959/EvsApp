package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.UserRole;

public interface UserRoleService {
    public UserRole getUserRole(Long userId, Long roleId);

    public UserRole saveUserRole(Long userId, Long roleId);

    public List<UserRole> getAllUserRoles();

    public void deleteUserRole(Long userId, Long roleId);

    public String getRoleByUserEmail(String email);

}
