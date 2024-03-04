package io.muzoo.ssc.activityportal.backend.config;

import io.muzoo.ssc.activityportal.backend.SimpleResponseDTO;
import io.muzoo.ssc.activityportal.backend.auth.OurUserDetailsService;
import io.muzoo.ssc.activityportal.backend.util.AjaxUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

/**
 * File for configuring the security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	/**
	 * having an autowired makes a circular dependency
	 */
	OurUserDetailsService ourUserDetailsService = new OurUserDetailsService();

	/**
	 * Setting the user details service and encoder
	 */
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(ourUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * the password encoder
	 * @return the desired encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * The security filter
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						//allow all access
						.requestMatchers("/", "/api/login", "/api/logout", "/api/whoami").permitAll()
						//allow OPTIONS access
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						//if authenticated, allow access
						.anyRequest().authenticated()
				)
				//handling the exception
				.exceptionHandling((exceptionHandling) ->
						exceptionHandling
								.authenticationEntryPoint(new JsonHTTP403ForbiddenEntryPoint())
				);

		return http.build();
	}

	/**
	 * User details
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return ourUserDetailsService;
	}

	/**
	 * What will be sent when entry is forbidden
	 */
	class JsonHTTP403ForbiddenEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request,
							 HttpServletResponse response,
							 AuthenticationException authException) throws IOException, ServletException {
			String ajaxJson = AjaxUtils.convertToString(
					SimpleResponseDTO
					.builder()
					.success(false)
					.message("Forbidden")
					.build()
			);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(ajaxJson);
		}
	}
}
