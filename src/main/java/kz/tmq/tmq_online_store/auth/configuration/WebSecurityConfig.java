package kz.tmq.tmq_online_store.auth.configuration;

import kz.tmq.tmq_online_store.auth.security.JwtFilter;
import kz.tmq.tmq_online_store.auth.security.oauth2.CustomOAuth2UserService;
import kz.tmq.tmq_online_store.auth.security.oauth2.OAuth2SuccessHandler;
import org.springframework.boot.web.servlet.context.WebApplicationContextServletContextAwareProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    public WebSecurityConfig(@Lazy JwtFilter jwtFilter, OAuth2SuccessHandler oAuth2SuccessHandler, CustomOAuth2UserService customOAuth2UserService) {
        this.jwtFilter = jwtFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/**")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
