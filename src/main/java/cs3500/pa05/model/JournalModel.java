package cs3500.pa05.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalModel {
  private final Map<Day, List<Task>> taskMap = new HashMap<>();
  private final Map<Day, List<Event>> eventMap = new HashMap<>();

  public JournalModel() {
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }


  public void loadBujo(Path path) {
    BujoReader reader = new BujoReader();
    reader.readBujo(path);
  }

  public void saveBujo(Path path) {
    new BujoWriter();
  }

  public void newBujo() {

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
