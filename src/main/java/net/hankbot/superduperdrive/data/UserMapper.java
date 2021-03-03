package net.hankbot.superduperdrive.data;

import net.hankbot.superduperdrive.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

  @Select("SELECT userid FROM USERS WHERE username = #{username}")
  Integer findUserIdForUsername(String username);

  @Select("SELECT * FROM USERS WHERE username = #{username}")
  User findUserForUsername(String username);

  @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
  @Options(useGeneratedKeys = true, keyProperty = "userId")
  Integer addUser(User user);

  @Delete("DELETE FROM USERS WHERE userid = #{userId)")
  Integer deleteUserForUserId(Integer userId);

}
