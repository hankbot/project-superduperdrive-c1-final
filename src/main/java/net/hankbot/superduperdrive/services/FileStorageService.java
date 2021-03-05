package net.hankbot.superduperdrive.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

  @Value("${net.hankbot.superduperdrive.files.root-dir}")
  private String uploadDirName;
  private Path uploadPath;
  private Logger logger = LoggerFactory.getLogger(FileStorageService.class);

  // Post construct root directory check
  @PostConstruct
  void postConstruct()  {
    uploadPath = Paths.get(uploadDirName);

    // Create uploads directory if it doesn't exist
    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectory(uploadPath);
      }
      catch (IOException e) {
        throw new RuntimeException("Could not create the uploads directory at: " + uploadPath.toAbsolutePath().toString());
      }

    }

    // Sanity checks on uploadPath
    if (!Files.isWritable(uploadPath)) {
      throw new RuntimeException("The uploads directory is not writable");
    }
  }

  // Read file info
//  public File fileInfo(String fileName) {
//    // Check if file exists
//
//    // Read file
//
//    // Return File
//  }

  // Add File to dir
  public boolean addFile(MultipartFile sourceUpload) {
    // Check source file

    // Write source file to disk

    // Return result status

    return true;
  }

  // Delete File in dir
  public boolean deleteFile(String fileName) {
    // Issue delete command

    // return result status
    return true;
  }

  // Formats the file name to a standardized convention
  private String nomalizeFileName(String fileName) {
    String normalizedFileName = null;

    return normalizedFileName;
  }


}
