package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.Election;
import evs.electronicvotingsystem.POJO.State;
import evs.electronicvotingsystem.Repository.ElectionRepository;
import evs.electronicvotingsystem.Repository.StateRepository;

@Service
public class StateServiceImpl implements StateService {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public State saveState(State state) {
        state.setActive(true);
        state.setDeleted(false);
        state.setCreatedAt(Date.from(Instant.now()));
        state.setCreatedBy(userServiceImpl.getCurrentUser());
        state.setUpdatedAt(Date.from(Instant.now()));
        state.setUpdatedBy(userServiceImpl.getCurrentUser());

        return stateRepository.save(state);
    }

    @Override
    public State updateState(State state, Long id) {
        State existingState = getStateById(id);
        existingState.setName(state.getName());
        existingState.setPopulation(state.getPopulation());
        existingState.setUpdatedAt(Date.from(Instant.now()));
        existingState.setUpdatedBy(userServiceImpl.getCurrentUser());
        return stateRepository.save(existingState);

    }

    @Override
    public State getStateById(Long id) {
        return stateRepository.findById(id).filter(state -> state.isActive() && !state.isDeleted()).orElse(null);
    }

    @Override
    public List<State> getAllStates() {
        List<State> states = (List<State>) stateRepository.findAll();
        return states.stream().filter(state -> state.isActive() && !state.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public boolean isStateExists(String stateName) {
        List<State> states = (List<State>) stateRepository.findAll();
        return states.stream()
                .filter(state -> state.getName().equals(stateName) && state.isActive() && !state.isDeleted())
                .count() > 0;
    }

    @Override
    public void deleteStateById(Long id) {
        State state = getStateById(id);
        state.setActive(false);
        state.setDeleted(true);
        state.setUpdatedAt(Date.from(Instant.now()));
        state.setUpdatedBy(userServiceImpl.getCurrentUser());
        stateRepository.save(state);

    }

    @Override
    public boolean isElectionExistsForState(Long stateId, String electionname) {
        State state = getStateById(stateId);
        List<Election> elections = (List<Election>) electionRepository.findAll();
        return elections.stream().filter(
                election -> election.getState().equals(state) && election.getName().equals(electionname)
                        && election.isActive() && !election.isDeleted())
                .count() > 0;

    }

    @Override
    public List<Election> getStateElections(Long stateId) {
        State state = getStateById(stateId);
        List<Election> elections = (List<Election>) electionRepository.findAll();
        return elections.stream()
                .filter(election -> election.getState().equals(state) && election.isActive() && !election.isDeleted())
                .collect(Collectors.toList());

    }

}
