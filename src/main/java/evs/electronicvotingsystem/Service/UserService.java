package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.User;

public interface UserService {
    public User saveUser(User user);

    public User updateUser(User user, Long id);

    public User getUserById(Long id);

    public boolean isUserValid(String email, String password);

    public List<User> getAllUsers();

    public boolean isEmailExists(String email);

    public void deleteUserById(Long id);

    public User getCurrentUser();

    public boolean isEligibleToVote(User user);

}
