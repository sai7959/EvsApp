package evs.electronicvotingsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

}
