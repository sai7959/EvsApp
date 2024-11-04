package evs.electronicvotingsystem.Security.Manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import evs.electronicvotingsystem.Service.UserServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserServiceImpl userServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (userServiceImpl.isUserValid(authentication.getPrincipal().toString(),
                authentication.getCredentials().toString())) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal().toString(),
                    authentication.getCredentials().toString());

        } else {
            throw new BadCredentialsException("");

        }

    }

}
