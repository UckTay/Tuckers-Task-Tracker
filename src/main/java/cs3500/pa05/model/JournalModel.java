package cs3500.pa05.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalModel {
  private Map<Day, List<Task>> taskMap = new HashMap<>();
  private Map<Day, List<Event>> eventMap = new HashMap<>();
  private Config config = new Config();

  public JournalModel() {
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }

  public void loadBujo(Path path) {
    BujoReader reader = new BujoReader();
    config = reader.readBujo(path);
    taskMap = new HashMap<>();
    eventMap = new HashMap<>();
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
    List<Task> tasks = reader.getEntry(Task.class);
    for(Task task : tasks) {
      taskMap.get(task.getDayOfTheWeek()).add(task);
    }
    List<Event> events = reader.getEntry(Event.class);
    for(Event event : events) {
      eventMap.get(event.getDayOfTheWeek()).add(event);
    }
  }

  public void saveBujo(Path path) {
    BujoWriter writer = new BujoWriter();
    List<Entry> entries = new ArrayList<>();
    for(Day key : Day.values()) {
      entries.addAll(taskMap.get(key));
      entries.addAll(eventMap.get(key));
    }
    writer.writeBujo(config, entries, path);
  }

  public List<Task> getDaysTasks(Day day) {
    return taskMap.getOrDefault(day, null);
  }

  public List<Event> getDaysEvent(Day day) {
    return eventMap.getOrDefault(day, null);
  }

  public List<Task> getAllTasks() {
    List<Task> tasks = new ArrayList<>();
    taskMap.keySet().forEach(day -> tasks.addAll(taskMap.get(day)));
    return tasks;
  }
  public void addTask(Task task) {
    taskMap.get(task.getDayOfTheWeek()).add(task);
  }
  public void addEvent(Event event) {
      eventMap.get(event.getDayOfTheWeek()).add(event);
  }

}
