package evs.electronicvotingsystem.Security.Filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import evs.electronicvotingsystem.Constants.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(SecurityConstants.Authorization);
        if (header == null || !(header.startsWith(SecurityConstants.BEARER))) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.replace(SecurityConstants.BEARER, "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET)).build().verify(token).getSubject();
        String role = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET)).build().verify(token).getClaim("role")
                .asString().replace("\"", "");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!role.equals("")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        System.out.println("\n\n" + role + "\n\n");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                (role.equals("")) ? null : authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

}
