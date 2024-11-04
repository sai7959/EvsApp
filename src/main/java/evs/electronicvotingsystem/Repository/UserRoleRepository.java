package evs.electronicvotingsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}
