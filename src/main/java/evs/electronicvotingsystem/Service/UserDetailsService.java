package evs.electronicvotingsystem.Service;

import java.util.List;

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserDetails;

public interface UserDetailsService {
    public UserDetails saveUserDetails(UserDetails userDetails);

    public UserDetails updateUserDetails(UserDetails userDetails, Long id);

    public UserDetails getUserDetailsById(Long id);

    public List<UserDetails> getAllUserDetails();

    public boolean isUserDetailsExists(User user);

    public void deleteUserDetailsById(Long id);

}
