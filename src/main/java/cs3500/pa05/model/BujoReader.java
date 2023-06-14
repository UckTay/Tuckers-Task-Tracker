package cs3500.pa05.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

public class BujoReader {
  List<Entry> entryList = new ArrayList<>();

  public List<Task> getTasks() {
    List<Task> list = new ArrayList<>();
    for (Entry entry : entryList) {
      if (entry.isTask()) {
        list.add((Task) entry);
      }
    }
    return list;
  }

  public List<Event> getEvents() {
    List<Event> list = new ArrayList<>();
    for (Entry entry : entryList) {
      if (entry.isEvent()) {
        list.add((Event) entry);
      }
    }
    return list;
  }

}
