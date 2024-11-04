package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.VoterRequest;

public interface VoterRequestService {
    public VoterRequest saveVoterRequest(VoterRequest voterRequest);

    public List<VoterRequest> getVoterRequests();

    public void approveVoterRequest(Long voterRequestId);

    public void rejectVoterRequest(Long voterRequestId);

    public VoterRequest getVoterRequestById(Long id);
}
