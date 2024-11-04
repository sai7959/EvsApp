package evs.electronicvotingsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.Election;

@Repository
public interface ElectionRepository extends CrudRepository<Election, Long> {

}
