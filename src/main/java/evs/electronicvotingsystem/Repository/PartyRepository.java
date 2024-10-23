package evs.electronicvotingsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.Party;

@Repository
public interface PartyRepository extends CrudRepository<Party, Long> {

}
