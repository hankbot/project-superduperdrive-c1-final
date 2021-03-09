package net.hankbot.superduperdrive.services;

import net.hankbot.superduperdrive.data.SuperFileMapper;
import net.hankbot.superduperdrive.models.SuperFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.ArrayList;

@Service
public class FileService {

  private SuperFileMapper fileMapper;
  private Logger logger = LoggerFactory.getLogger(FileService.class);
  private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

  public FileService(SuperFileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public ArrayList<SuperFile> userFileList(Integer userId) {
    logger.info("User ID: " + userId);
    return fileMapper.findFilesForUserId(userId);
  }

  public boolean addFileForUserId(Integer userId, MultipartFile upload) {
    byte[] fileData;

    try {
      fileData = upload.getBytes();
    }
    catch (Exception e) {
      logger.error("Could not read uploaded file into Blob");
      return false;
    }

    String fileName = upload.getOriginalFilename();

    // Very limited set of filetypes identified
    String contentType = URLConnection.guessContentTypeFromName(fileName);

    if (contentType == null) {
      contentType = DEFAULT_CONTENT_TYPE;
    }

    SuperFile uploadedFile = new SuperFile(
        null,
        fileName,
        contentType,
        String.valueOf(upload.getSize()),
        userId,
        fileData
        );

    fileMapper.addFile(uploadedFile);

    return true;
  }

  public boolean deleteFile(Integer fileId) {
    fileMapper.deleteFileForFileId(fileId);
    return true;
  }

  public SuperFile fileForId(Integer fileId) {
    SuperFile file = fileMapper.findFileForFileId(fileId);

    return file;
  }

  public boolean fileExistsForNameWithUserId(String filename, Integer userId) {
    ArrayList<Integer> matches = fileMapper.findFilesForNameWithUserId(filename, userId);

    if (matches.size() > 0) {
      return true;
    }

    return false;
  }
}
