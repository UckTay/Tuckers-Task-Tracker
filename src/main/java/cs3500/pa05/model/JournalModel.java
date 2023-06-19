package cs3500.pa05.model;

import cs3500.pa05.view.EventCreationPrompt;
import cs3500.pa05.view.TaskCreationPrompt;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JournalModel {

  private final Map<Class<? extends Entry>, Map<Day, List<?>>> classMap = new HashMap<>();
  private Map<Day, List<Task>> taskMap = new HashMap<>();
  private Map<Day, List<Event>> eventMap = new HashMap<>();
  private Config config = new Config();

  public JournalModel() {
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }

  public void newWeek() {
    taskMap = new HashMap<>();
    eventMap = new HashMap<>();
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }
  public void loadBujo(Path path) {
    BujoReader reader = new BujoReader();
    config = reader.readBujo(path);
    newWeek();
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

  public List<Event> getAllEvents() {
    List<Event> events = new ArrayList<>();
    eventMap.keySet().forEach(day-> events.addAll(eventMap.get(day)));
    return events;
  }
  public void addTask(Task task) {
    taskMap.get(task.getDayOfTheWeek()).add(task);
  }
  public void addEvent(Event event) {
      eventMap.get(event.getDayOfTheWeek()).add(event);
  }

  public void moveUp(Entry entry) {
    if (entry instanceof Task) {
      moveElement((Task) entry, taskMap.get(entry.getDayOfTheWeek()), -1);
    } else if (entry instanceof Event) {
      moveElement((Event) entry, eventMap.get(entry.getDayOfTheWeek()), -1);
    }
  }

  public void moveDown(Entry entry) {
    if (entry instanceof Task) {
      moveElement((Task) entry, taskMap.get(entry.getDayOfTheWeek()), 1);
    } else if (entry instanceof Event) {
      moveElement((Event) entry, eventMap.get(entry.getDayOfTheWeek()), 1);
    }
  }

  public void takesieBacksie(Entry entry) {
    if (entry instanceof Event) {
      eventMap.get(entry.getDayOfTheWeek()).remove(entry);
    } else if (entry instanceof Task) {
      taskMap.get(entry.getDayOfTheWeek()).remove(entry);
    }
  }

  public void mindChange(Entry entry, Runnable updateGUI) {
    if (entry instanceof Event) {
      new EventCreationPrompt((Event) entry, (newEntry -> {
        Event newEvent = (Event) newEntry;
        List<Event> list = eventMap.get(newEvent.getDayOfTheWeek());
        int index = list.indexOf(newEvent);
        if (!list.contains(newEntry)) {
          addEvent(newEvent);
          takesieBacksie(entry);
        } else {
          list.set(index, newEvent);
        }
      }), updateGUI);
    } else if (entry instanceof Task){
      new TaskCreationPrompt((Task) entry, (newEntry -> {
        Task newTask = (Task) newEntry;
        List<Task> list = taskMap.get(newTask.getDayOfTheWeek());
        int index = list.indexOf(newTask);
        if (!list.contains(newEntry)) {
          addTask(newTask);
          takesieBacksie(entry);
        } else {
          list.set(index, newTask);
        }
      }), updateGUI);
    }
  }

  private <T> void moveElement(T entry, List<T> list, int offset) {
    int index = list.indexOf(entry);
    if (index == -1 || (index + offset) < 0 || (index + offset) > list.size() - 1)  {
      return;
    }
    T temp = list.get(index + offset);
    list.set(index + offset, entry);
    list.set(index, temp);
  }

}
