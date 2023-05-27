package com.ecommerce.admin.config;

import com.ecommerce.admin.jwt.JwtAuthEntryPoint;
import com.ecommerce.admin.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,  //enables @Secured annotation
        jsr250Enabled = true,   //enables @RolesAllowed annotation
        prePostEnabled = true   //enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations.
)
public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminDetailsService adminDetailsService;

    //Uncomment this if you want to use JWT authentication

//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;
//    @Autowired
//    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**", "/vendor/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/forgot_password").permitAll()
                .antMatchers("/api/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/api/login").permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/api/do-login")
                    .defaultSuccessUrl("/api/home")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                    .key("sth_very_secured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    //It is recommended to use POST instead of GET if CSRF is enabled,
                    // but it is disabled, so we use GET instead
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout", "GET"))
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/api/login?logout");

        //Uncomment this if you want to use JWT authentication

//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
//                .and()
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());                           //Option 1
//        auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder());  //Option 2
    }

    //Uncomment this if you want to use JWT authentication

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}