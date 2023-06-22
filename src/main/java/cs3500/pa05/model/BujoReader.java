package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.ConfigJson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Represents a BujoReader
 */
public class BujoReader {
  private final List<Entry> entryList = new ArrayList<>();
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * reads a given bujo file
   *
   * @param path the path to the bujo file
   * @return the configurations of the week
   */
  public Config readBujo(Path path) {
    JsonNode fileContents;
    try {
      fileContents = MAPPER.readTree(path.toFile());
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file path");
    }
    return mapBujo(fileContents);
  }

  /**
   * Reads a given encrypted bujo file.
   *
   * @param path     the path to the bujo file
   * @param password the password for decryption
   * @return the config for the bujo file
   */
  public Config readBujo(Path path, String password) {
    try (FileInputStream fileIn = new FileInputStream(path.toFile())) {
      byte[] salt = new byte[16];
      fileIn.read(salt);
      byte[] iv = new byte[16];
      fileIn.read(iv);
      //Gets the key salt and iv from the start of the encrypted file
      SecretKey key = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
          .generateSecret(new PBEKeySpec(password.toCharArray(), salt,
              65536, 256)).getEncoded(), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
      CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
      InputStreamReader inputReader = new InputStreamReader(cipherIn);
      JsonNode fileContents = MAPPER.readTree(inputReader);
      return mapBujo(fileContents);
    } catch (IOException
             | NoSuchAlgorithmException
             | InvalidKeySpecException
             | NoSuchPaddingException
             | InvalidAlgorithmParameterException
             | InvalidKeyException ex) {
      System.out.println("error decrypting file");
    }
    return null;
  }

  private Config mapBujo(JsonNode fileContents) {
    MAPPER.convertValue(fileContents, BujoJson.class);
    Config config = MAPPER.convertValue(fileContents.get("config"), ConfigJson.class).toConfig();
    BujoJson entryGetter = MAPPER.convertValue(fileContents, BujoJson.class);
    entryList.addAll(entryGetter.generateTasks());
    entryList.addAll(entryGetter.generateEvents());
    return config;
  }

  /**
   * Gets the entries in a given bujo file.
   *
   * @param entryType the kind of entry
   * @param <T>       the type of entry
   * @return a list of that entry type
   */
  public <T> List<T> getEntry(Class<T> entryType) {
    List<T> list = new ArrayList<>();
    for (Entry entry : entryList) {
      if (entryType.isInstance(entry)) {
        list.add(entryType.cast(entry));
      }
    }
    return list;
  }
}
