package evs.electronicvotingsystem.Security.Filters;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import evs.electronicvotingsystem.Constants.SecurityConstants;
import evs.electronicvotingsystem.POJO.User;
import evs.electronicvotingsystem.Security.Manager.CustomAuthenticationManager;
import evs.electronicvotingsystem.Service.UserRoleServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private CustomAuthenticationManager authenticationManager;
    private UserRoleServiceImpl userRoleServiceImpl;
    private String role;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());
            role = userRoleServiceImpl.getRoleByUserEmail(authentication.getPrincipal().toString());
            System.out.println("\n\n"+role+"\n\n");
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.getWriter().write("User doesn't exist");
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.SECRET);
        String token = JWT.create().withSubject(authResult.getPrincipal().toString()).withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION)).sign(algorithm);
        response.addHeader(SecurityConstants.Authorization, SecurityConstants.BEARER + token);

    }

}
