package net.hankbot.superduperdrive.data;

import net.hankbot.superduperdrive.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * FROM USERS WHERE username = #{username}")
  User findUserWithUsername(String username);

  @Insert("INSERT INTO USERS (username, salt, password, firstname, lastnet) VALUES (#{userName}, #{salt}, #{password}, #{firstname}, #{lastname})")
  int addUser(User user);

  @Delete("DELETE FROM USERS WHERE userid = #{userId)")
  Integer deleteUserWithUserId(Integer userId);

}
