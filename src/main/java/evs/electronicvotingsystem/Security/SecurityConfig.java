package evs.electronicvotingsystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import evs.electronicvotingsystem.Constants.AppConstants;
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
                .requestMatchers(HttpMethod.GET, "/home")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)

                .requestMatchers(HttpMethod.POST, "/user").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.PUT, "/user/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/user")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "/user/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.DELETE, "/user/{}").hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/userdetails").permitAll()
                .requestMatchers(HttpMethod.GET, "/userdetails")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER,
                        AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "/userdetails/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.DELETE, "/userdetails/{}").hasAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/role").hasAuthority(AppConstants.ADMIN)
                // .requestMatchers(HttpMethod.PUT, "/role/{}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/role")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "role/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.DELETE, "role/{}").hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/userrole/user/{userId}/role/{roleId}")
                .hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/userrole")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "/userrole/user/{userId}/role/{roleId}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.DELETE, "/userrole/user/{userId}/role/{roleId}")
                .hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/state").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/state")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "/state/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.DELETE, "/state/{}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.POST, "/state/{stateId}/election").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/state/{stateId}/election").hasAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.GET, "/election/{id}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)

                // .requestMatchers(HttpMethod.PUT, "/election/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/election/{id}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.POST, "/election/{electionId}/party").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "election/{electionId}/party").hasAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.GET, "/party/{id}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER, AppConstants.ELECTORIAL_OFFICER)
                // .requestMatchers(HttpMethod.PUT, "/party/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/party/{id}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/party/{partyId}/votes").hasAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/voter-request/user/{userId}").hasAuthority(AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "/voter-request")
                .hasAnyAuthority(AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.GET, "/voter-request/{id}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.ELECTORIAL_OFFICER, AppConstants.VOTER)
                .requestMatchers(HttpMethod.PUT, "/voter-request/{id}/approve")
                .hasAuthority(AppConstants.ELECTORIAL_OFFICER)
                .requestMatchers(HttpMethod.PUT, "/voter-request/{id}/reject")
                .hasAuthority(AppConstants.ELECTORIAL_OFFICER)

                .requestMatchers(HttpMethod.POST, "vote/election/{electionId}/party/{partyId}/user/{userId}")
                .hasAuthority(AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "vote/election/{electionId}/party/{partyId}/user/{userId}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.ELECTORIAL_OFFICER, AppConstants.VOTER)

                .requestMatchers(HttpMethod.POST, "result/election/{electionId}").hasAuthority(AppConstants.ADMIN)

                .anyRequest().authenticated())
                .addFilterBefore(exception, AuthenticationFilter.class)
                .addFilter(auth).addFilterAfter(jwtAuth, AuthenticationFilter.class);

        return http.build();

    }

}
