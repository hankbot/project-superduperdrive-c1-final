package net.hankbot.superduperdrive.services;

import net.hankbot.superduperdrive.data.SuperCredentialMapper;
import net.hankbot.superduperdrive.models.SuperCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {

  private SuperCredentialMapper credentialMapper;
  private EncryptionService encryptionService;
  private Logger logger = LoggerFactory.getLogger(CredentialService.class);

  public CredentialService(SuperCredentialMapper credentialMapper, EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.encryptionService = encryptionService;
  }

  public ArrayList<SuperCredential> userCredentialList(Integer userId) {
    return credentialMapper.findCredentialsForUserId(userId);
  }

  public boolean addCredentialForUserId(Integer userId, SuperCredential newCredential) {
    newCredential.setUserId(userId);

    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(), encodedKey);

    newCredential.setPassword(encryptedPassword);
    newCredential.setKey(encodedKey);

    credentialMapper.addCredential(newCredential);

    return true;
  }

  public boolean updateCredentialForUserId(Integer userId, SuperCredential updatedCredential) {
    updatedCredential.setUserId(userId);

    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);

    String encryptedPassword = encryptionService.encryptValue(updatedCredential.getPassword(), encodedKey);

    updatedCredential.setPassword(encryptedPassword);
    updatedCredential.setKey(encodedKey);

    updatedCredential.setPassword(encryptedPassword);
    credentialMapper.updateCredential(updatedCredential);

    return true;
  }

  public boolean deleteCredential(Integer credentialId) {
    credentialMapper.deleteCredentialForCredentialId(credentialId);
    return true;
  }

  public boolean deleteCredential(Integer credentialId, Integer userId) {
    credentialMapper.deleteCredentialForCredentialIdWithUserId(credentialId, userId);
    return true;
  }

  public SuperCredential credentialForId(Integer credentialId) {
    SuperCredential credential = credentialMapper.findCredentialForCredentialId(credentialId);

    if (credential != null) {
      String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    return credential;
  }

  public SuperCredential credentialForId(Integer credentialId, Integer userId) {
    SuperCredential credential = credentialMapper.findCredentialForCredentialIdWithUserId(credentialId, userId);

    if (credential != null) {
      String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
      credential.setPassword(decryptedPassword);
    }

    return credential;
  }

}
