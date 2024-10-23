package evs.electronicvotingsystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import evs.electronicvotingsystem.Security.Filters.AuthenticationFilter;
import evs.electronicvotingsystem.Security.Filters.ExceptionHandlerFilter;
import evs.electronicvotingsystem.Security.Filters.JwtAuthenticationFilter;
import evs.electronicvotingsystem.Security.Manager.CustomAuthenticationManager;
import evs.electronicvotingsystem.Service.UserRoleServiceImpl;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private CustomAuthenticationManager customAuthenticationManager;
    private UserRoleServiceImpl userRoleServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter auth = new AuthenticationFilter(customAuthenticationManager, userRoleServiceImpl, "");
        ExceptionHandlerFilter exception = new ExceptionHandlerFilter();
        JwtAuthenticationFilter jwtAuth = new JwtAuthenticationFilter();
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/home").permitAll()

                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.GET, "/user/{}").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.DELETE, "/user/{}").hasAnyAuthority("Admin")

                .requestMatchers(HttpMethod.POST, "/userdetails").permitAll()
                .requestMatchers(HttpMethod.GET, "/userdetails").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.GET, "/userdetails/{}").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.DELETE, "/userdetails/{}").hasAuthority("Admin")

                .requestMatchers(HttpMethod.POST, "/role").hasAuthority("Admin")
                .requestMatchers(HttpMethod.PUT, "/role/{}").hasAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/role").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.GET, "role/{}").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.DELETE, "role/{}").hasAnyAuthority("Admin")

                .requestMatchers(HttpMethod.POST, "/userrole/user/{userId}/role/{roleId}").hasAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/userrole").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.GET, "/userrole/user/{userId}/role/{roleId}")
                .hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.DELETE, "/userrole/user/{userId}/role/{roleId}").hasAnyAuthority("Admin")

                .requestMatchers(HttpMethod.POST, "/state").permitAll()// hasAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/state").permitAll()// hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.GET, "/state/{}").hasAnyAuthority("Admin", "Voter")
                .requestMatchers(HttpMethod.DELETE, "/state/{}").hasAuthority("Admin")
                .requestMatchers(HttpMethod.POST, "/state/{stateId}/election").permitAll()// hasAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/state/{stateId}/election").permitAll()

                .requestMatchers(HttpMethod.GET, "/election/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/election/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/election/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/election/{electionId}/party").permitAll()

                .requestMatchers(HttpMethod.GET, "/party/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/party/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/party/{id}").permitAll()

                .requestMatchers(HttpMethod.POST, "/voter-request/user/{userId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/voter-request").permitAll()
                .requestMatchers(HttpMethod.GET, "/voter-request/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/voter-request/{id}/approve").permitAll()
                .requestMatchers(HttpMethod.PUT, "/voter-request/{id}/reject").permitAll()

                .anyRequest().authenticated())
                .addFilterBefore(exception, AuthenticationFilter.class)
                .addFilter(auth).addFilterAfter(jwtAuth, AuthenticationFilter.class);

        return http.build();

    }

}
