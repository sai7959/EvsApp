package evs.electronicvotingsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
