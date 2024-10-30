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

                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.GET, "/user")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "/user/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.DELETE, "/user/{}").hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/userdetails").permitAll()
                .requestMatchers(HttpMethod.GET, "/userdetails")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "/userdetails/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.DELETE, "/userdetails/{}").hasAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/role").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.PUT, "/role/{}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/role")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "role/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.DELETE, "role/{}").hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/userrole/user/{userId}/role/{roleId}")
                .hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/userrole")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "/userrole/user/{userId}/role/{roleId}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.DELETE, "/userrole/user/{userId}/role/{roleId}")
                .hasAnyAuthority(AppConstants.ADMIN)

                .requestMatchers(HttpMethod.POST, "/state").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.GET, "/state")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.GET, "/state/{}")
                .hasAnyAuthority(AppConstants.ADMIN, AppConstants.VOTER)
                .requestMatchers(HttpMethod.DELETE, "/state/{}").hasAuthority(AppConstants.ADMIN)
                .requestMatchers(HttpMethod.POST, "/state/{stateId}/election").hasAuthority(AppConstants.ADMIN)
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

                .requestMatchers(HttpMethod.POST, "vote/election/{electionId}/party/{partyId}/user/{userId}")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "vote/election/{electionId}/party/{partyId}/user/{userId}").permitAll()

                .requestMatchers(HttpMethod.POST, "result/election/{electionId}").permitAll()

                .anyRequest().authenticated())
                .addFilterBefore(exception, AuthenticationFilter.class)
                .addFilter(auth).addFilterAfter(jwtAuth, AuthenticationFilter.class);

        return http.build();

    }

}
