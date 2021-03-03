package net.hankbot.superduperdrive.services;

import net.hankbot.superduperdrive.data.UserMapper;
import net.hankbot.superduperdrive.models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

public class AuthenticationService implements AuthenticationProvider {

  private UserMapper userMapper;
  private HashService hashService;

  public AuthenticationService(UserMapper userMapper, HashService hashService) {
    this.hashService = hashService;
    this.userMapper = userMapper;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    User user = userMapper.findUserForUsername(username);

    if (user != null) {
      String salt = user.getSalt();
      String hashedPassword = hashService.getHashedValue(password, salt);

      if(user.getPassword().equals(hashedPassword)) {
        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
      }
    }

    return null;
  }

}
