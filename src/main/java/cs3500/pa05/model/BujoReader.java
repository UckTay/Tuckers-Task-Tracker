package cs3500.pa05.model;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BujoReader {
  List<Entry> entryList = new ArrayList<>();

  public void readBujo(Path path) {
    try {
      Scanner s = new Scanner(path);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file path");
    }
  }

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
