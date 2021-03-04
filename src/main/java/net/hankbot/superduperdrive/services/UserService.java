package net.hankbot.superduperdrive.services;

import net.hankbot.superduperdrive.data.UserMapper;
import net.hankbot.superduperdrive.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserMapper userMapper;
  HashService hashService;
  private Logger logger = LoggerFactory.getLogger(UserService.class);

  public UserService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  public Integer lookupUserId(String username) {
    if (userMapper.findUserIdForUsername(username) == null) {
      return 0;
    }

    return 1;
  }

  public boolean addUser(User user) {
    if (userMapper.addUser(user) < 0) {
      logger.error("Could not add new user");
      return false;
    }

    logger.info("Added new user: " + user.getUsername());
    return true;
  }

  public User lookupUser(String username) {
    return this.userMapper.findUserForUsername(username);
  }
}
