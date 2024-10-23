package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserDetails;
import evs.electronicvotingsystem.POJO.UserRole;
import evs.electronicvotingsystem.Repository.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    RoleServiceImpl roleServiceImpl;

    @Override
    public UserDetails saveUserDetails(UserDetails userDetails) {
        User user = userServiceImpl.saveUser(userDetails.getUser());
        userDetails.setUser(user);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(roleServiceImpl.getRoleById(2L));
        // userRole = userRoleServiceImpl.saveUserRole(userRole);
        userDetails.setActive(true);
        userDetails.setDeleted(false);
        userDetails.setCreatedAt(Date.from(Instant.now()));
        userDetails.setUpdatedAt(Date.from(Instant.now()));
        userDetails.setUpdatedBy(userServiceImpl.getCurrentUser());
        userDetails.setCreatedBy(userServiceImpl.getCurrentUser());

        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails, Long id) {
        UserDetails existingUserDetails = getUserDetailsById(id);
        existingUserDetails.setUser(userDetails.getUser());
        existingUserDetails.setName(userDetails.getName());
        existingUserDetails.setDateOfBirth(userDetails.getDateOfBirth());
        existingUserDetails.setGender(userDetails.getGender());
        existingUserDetails.setAddress(userDetails.getAddress());
        existingUserDetails.setMobileNumber(userDetails.getMobileNumber());
        existingUserDetails.setDistrict(userDetails.getDistrict());
        existingUserDetails.setUpdatedAt(Date.from(Instant.now()));
        existingUserDetails.setUpdatedBy(userServiceImpl.getCurrentUser());

        return userDetailsRepository.save(existingUserDetails);
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.findById(id).filter(userDetail -> userDetail.isActive() && !userDetail.isDeleted())
                .orElse(null);

    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        List<UserDetails> usersDetails = (List<UserDetails>) userDetailsRepository.findAll();
        return usersDetails.stream().filter(userDetails -> userDetails.isActive() && !userDetails.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserDetailsById(Long id) {
        UserDetails userDetails = getUserDetailsById(id);
        userDetails.setActive(false);
        userDetails.setDeleted(true);
        userDetails.setUpdatedAt(Date.from(Instant.now()));
        userDetails.setUpdatedBy(userServiceImpl.getCurrentUser());
        userDetailsRepository.save(userDetails);
    }

    @Override
    public boolean isUserDetailsExists(User user) {
        List<UserDetails> userDetails = (List<UserDetails>) userDetailsRepository.findAll();
        return userDetails.stream().filter(
                userDetail -> userDetail.getUser().equals(user) && userDetail.isActive() && !userDetail.isDeleted())
                .count() > 0;

    }

}
