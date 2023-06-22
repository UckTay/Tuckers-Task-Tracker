package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Represents a BujoWriter
 */
public class BujoWriter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Writes to a bujo file
   *
   * @param config  the configurations of the week
   * @param entries the entries in a week
   * @param path    the path to the file
   */
  public void writeBujo(Config config, List<Entry> entries, Path path) {
    List<TaskJson> tasks = new ArrayList<>();
    List<EventJson> events = new ArrayList<>();
    for (Entry entry : entries) {
      if (entry.getClass() == Task.class) {
        tasks.add(((Task) entry).toJson());
      } else {
        events.add(((Event) entry).toJson());
      }
    }
    try {
      File newFile = new File(path.toUri());
      if (!newFile.exists()) {
        newFile.createNewFile();
      }
      BujoJson output = new BujoJson(config.toJson(), tasks, events);
      if (config.getPassword() != null) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        SecretKey key = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            .generateSecret(new PBEKeySpec(config.getPassword().toCharArray(), salt,
                65536, 256)).getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getIV();
        try (FileOutputStream fileOut = new FileOutputStream(newFile);
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
          fileOut.flush();
          fileOut.write(salt);
          fileOut.write(iv);
          MAPPER.writeValue(cipherOut, MAPPER.convertValue(output, JsonNode.class));
        }
      } else {
        MAPPER.writeValue(newFile, MAPPER.convertValue(output, JsonNode.class));
      }

    } catch (NoSuchPaddingException | IOException | InvalidKeySpecException |
             NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }
}
