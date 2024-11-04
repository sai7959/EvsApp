package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.VoterRequest;
import evs.electronicvotingsystem.Repository.UserRepository;
import evs.electronicvotingsystem.Repository.VoterRequestRepository;

@Service
public class VoterRequestServiceImpl implements VoterRequestService {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private VoterRequestRepository voterRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public VoterRequest saveVoterRequest(VoterRequest voterRequest) {
        voterRequest.setApproved(false);
        voterRequest.setRejected(false);
        voterRequest.setActive(true);
        voterRequest.setDeleted(false);
        voterRequest.setCreatedAt(Date.from(Instant.now()));
        voterRequest.setUpdatedAt(Date.from(Instant.now()));
        voterRequest.setCreatedBy(userServiceImpl.getCurrentUser());
        voterRequest.setUpdatedBy(userServiceImpl.getCurrentUser());
        return voterRequestRepository.save(voterRequest);
    }

    @Override
    public List<VoterRequest> getVoterRequests() {
        List<VoterRequest> requests = ((List<VoterRequest>) voterRequestRepository.findAll()).stream()
                .filter(voterRequest -> voterRequest.isActive() && !voterRequest.isDeleted()
                        && !voterRequest.isApproved() && !voterRequest.isRejected())
                .collect(Collectors.toList());
        return requests.isEmpty() ? null : requests;
    }

    @Override
    public void approveVoterRequest(Long voterRequestId) {
        VoterRequest existingVoterRequest = voterRequestRepository.findById(voterRequestId)
                .filter(request -> request.isActive() && !request.isDeleted()).orElse(null);

        existingVoterRequest.setApproved(true);
        existingVoterRequest.setUpdatedAt(Date.from(Instant.now()));
        existingVoterRequest.setUpdatedBy(userServiceImpl.getCurrentUser());
        voterRequestRepository.save(existingVoterRequest);
        User user = userServiceImpl.getUserById(existingVoterRequest.getUser().getId());
        user.setEligibleToVote(true);
        userRepository.save(user);

    }

    @Override
    public void rejectVoterRequest(Long voterRequestId) {
        VoterRequest existingVoterRequest = getVoterRequestById(voterRequestId);
        existingVoterRequest.setRejected(true);
        existingVoterRequest.setUpdatedAt(Date.from(Instant.now()));
        existingVoterRequest.setUpdatedBy(userServiceImpl.getCurrentUser());
        voterRequestRepository.save(existingVoterRequest);
        User user = userServiceImpl.getUserById(existingVoterRequest.getUser().getId());
        user.setEligibleToVote(false);
        userRepository.save(user);

    }

    @Override
    public VoterRequest getVoterRequestById(Long id) {
        return voterRequestRepository.findById(id)
                .filter(request -> request.isActive() && !request.isDeleted()).orElse(null);
    }

}
