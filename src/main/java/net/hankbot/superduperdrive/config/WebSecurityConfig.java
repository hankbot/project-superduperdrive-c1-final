package net.hankbot.superduperdrive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/h2-console/**", "/signup","/css/**", "/js/**").permitAll()
        .anyRequest().authenticated()
        .and()
          .formLogin()
          .loginPage("/login")
          .permitAll();

    // Send to home on login
    http.formLogin()
        .defaultSuccessUrl("/home");

    // @TODO: Does logout perms need to be configured?
//        .and()
//        .logout()
//        .permitAll();

    // Send back to login screen on logout
    http.logout()
        .logoutSuccessUrl("/login");
  }

}
