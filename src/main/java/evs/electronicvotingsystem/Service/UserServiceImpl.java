package evs.electronicvotingsystem.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.POJO.UserDetails;
import evs.electronicvotingsystem.Repository.UserDetailsRepository;
import evs.electronicvotingsystem.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository detailsRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(); // Default strength
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setDeleted(false);
        user.setCastedVote(false);
        user.setCreatedAt(Date.from(Instant.now()));
        user.setUpdatedAt(Date.from(Instant.now()));
        return userRepository.save(user);

    }

    @Override
    public User updateUser(User user, Long id) {
        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        existingUser.setUpdatedAt(Date.from(Instant.now()));
        return userRepository.save(existingUser);

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).filter(users -> users.isActive() && !users.isDeleted())
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().filter(user -> user.isActive() && !user.isDeleted()).collect(Collectors.toList());

    }

    @Override
    public void deleteUserById(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        user.setDeleted(true);
        user.setUpdatedAt(Date.from(Instant.now()));
        userRepository.save(user);
    }

    @Override
    public boolean isUserValid(String email, String password) {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .filter(user -> user.getEmail().equals(email)
                        && bCryptPasswordEncoder.matches(password, user.getPassword())
                        && user.isActive() && !user.isDeleted())
                .count() > 0;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .filter(user -> user.getEmail().equals(authentication.getPrincipal().toString()) && user.isActive()
                        && !user.isDeleted())
                .findFirst().orElse(null);

    }

    @Override
    public boolean isEmailExists(String email) {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().filter(user -> user.getEmail().equals(email) && user.isActive() && !user.isDeleted())
                .count() > 0;
    }

    @Override
    public boolean isEligibleToVote(User user) {
        UserDetails userDetails = (((List<UserDetails>) detailsRepository.findAll())).stream().filter(
                userDetail -> userDetail.getUser().equals(user) && userDetail.isActive() && !userDetail.isDeleted())
                .findFirst().orElse(null);
        System.out.println(userDetails.getDateOfBirth());
        LocalDate dateOfBirth = LocalDate.parse(userDetails.getDateOfBirth().toString());
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears() > 18;

    }

}
