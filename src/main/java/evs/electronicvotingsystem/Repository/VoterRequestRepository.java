package evs.electronicvotingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import evs.electronicvotingsystem.POJO.VoterRequest;

@Repository
public interface VoterRequestRepository extends JpaRepository<VoterRequest, Long> {

}
