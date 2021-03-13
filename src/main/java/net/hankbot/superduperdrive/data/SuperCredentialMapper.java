package net.hankbot.superduperdrive.data;

import net.hankbot.superduperdrive.models.SuperCredential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface SuperCredentialMapper {

  @Select("SELECT credentialid, url, username, key, password, userid  FROM CREDENTIALS WHERE userid = #{userId}")
  ArrayList<SuperCredential> findCredentialsForUserId(Integer userId);

  @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "credentialId")
  Integer addCredential(SuperCredential newCredential);

  @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} WHERE credentialid = #{credentialId}")
  Integer updateCredential(SuperCredential updatedCredential);

  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
  Integer deleteCredentialForCredentialId(Integer credentialId);

  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
  Integer deleteCredentialForCredentialIdWithUserId(Integer credentialId, Integer userId);

  @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
  SuperCredential findCredentialForCredentialId(Integer credentialId);

  @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
  SuperCredential findCredentialForCredentialIdWithUserId(Integer credentialId, Integer userId);

}
