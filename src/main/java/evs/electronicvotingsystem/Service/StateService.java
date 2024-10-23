package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.State;

public interface StateService {
    public State saveState(State state);

    public State updateState(State state, Long id);

    public State getStateById(Long id);

    public List<State> getAllStates();

    public boolean isStateExists(String stateName);

    public boolean isElectionExistsForState(Long stateId, String electionname);

    public void deleteStateById(Long id);

    public List<Election> getStateElections(Long stateId);

}
