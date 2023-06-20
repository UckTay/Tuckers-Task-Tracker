package cs3500.pa05.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The model that contains the core functionality of the journal
 */
public class JournalModel {

  private final Map<Class<? extends Entry>, Map<Day, List<?>>> classMap = new HashMap<>();
  private Map<Day, List<Task>> taskMap = new HashMap<>();
  private Map<Day, List<Event>> eventMap = new HashMap<>();
  private Config config = new Config();

  /**
   * Creates an instance of the model.
   */
  public JournalModel() {
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }

  /**
   * Resets the week, clearing all task and events.
   */
  public void newWeek() {
    taskMap = new HashMap<>();
    eventMap = new HashMap<>();
    for (Day day : Day.values()) {
      taskMap.put(day, new ArrayList<>());
      eventMap.put(day, new ArrayList<>());
    }
  }

  /**
   * Loads and opens a given bujo file.
   *
   * @param path the path to the bujo file
   */
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

  /**
   * Saves the contents of the journal to a bujo file.
   *
   * @param path the location of the new bujo file
   */
  public void saveBujo(Path path) {
    BujoWriter writer = new BujoWriter();
    List<Entry> entries = new ArrayList<>();
    for(Day key : Day.values()) {
      entries.addAll(taskMap.get(key));
      entries.addAll(eventMap.get(key));
    }
    writer.writeBujo(config, entries, path);
  }

  /**
   * Gets the tasks of a given day.
   *
   * @param day the given day
   * @return the tasks on the day
   */
  public List<Task> getDaysTasks(Day day) {
    return taskMap.getOrDefault(day, null);
  }

  /**
   * Gets the events of a given day.
   *
   * @param day the given day
   * @return the events on the day
   */
  public List<Event> getDaysEvent(Day day) {
    return eventMap.getOrDefault(day, null);
  }

  /**
   * Gets all the tasks in the week
   *
   * @return a list containing all tasks
   */
  public List<Task> getAllTasks() {
    List<Task> tasks = new ArrayList<>();
    taskMap.keySet().forEach(day -> tasks.addAll(taskMap.get(day)));
    return tasks;
  }

  /**
   * Gets all the events in the week.
   *
   * @return a list containing all events
   */
  public List<Event> getAllEvents() {
    List<Event> events = new ArrayList<>();
    eventMap.keySet().forEach(day-> events.addAll(eventMap.get(day)));
    return events;
  }

  /**
   * Adds a task to the week.
   *
   * @param task the new task.
   */
  public void addTask(Task task) {
    taskMap.get(task.getDayOfTheWeek()).add(task);
  }

  /**
   * Adds an event to the week.
   *
   * @param event the new event.
   */
  public void addEvent(Event event) {
      eventMap.get(event.getDayOfTheWeek()).add(event);
  }

  /**
   * Moves an entry up on a given day.
   *
   * @param entry the given entry
   */
  public void moveUp(Entry entry) {
    if (entry instanceof Task) {
      moveElement((Task) entry, taskMap.get(entry.getDayOfTheWeek()), -1);
    } else if (entry instanceof Event) {
      moveElement((Event) entry, eventMap.get(entry.getDayOfTheWeek()), -1);
    }
  }

  /**
   * Moves an entry down on a given day.
   *
   * @param entry the given entry
   */
  public void moveDown(Entry entry) {
    if (entry instanceof Task) {
      moveElement((Task) entry, taskMap.get(entry.getDayOfTheWeek()), 1);
    } else if (entry instanceof Event) {
      moveElement((Event) entry, eventMap.get(entry.getDayOfTheWeek()), 1);
    }
  }

  /**
   * Removes an entry from the week.
   *
   * @param entry the entry being removed.
   */
  public void takesieBacksie(Entry entry) {
    if (entry instanceof Event) {
      eventMap.get(entry.getDayOfTheWeek()).remove(entry);
    } else if (entry instanceof Task) {
      taskMap.get(entry.getDayOfTheWeek()).remove(entry);
    }
  }

  /**
   * Gets the configurations of the week.
   *
   * @return the configurations.
   */
  public Config getConfig() {
    return config;
  }

  /**
   * Changes the contents of an entry.
   *
   * @param oldEntry The entry's old contents
   * @param newEntry the entry's new contents
   */
  public void mindChange(Entry oldEntry, Entry newEntry) {
    if (newEntry instanceof Event newEvent) {
      List<Event> list = eventMap.get(newEvent.getDayOfTheWeek());
      int index = list.indexOf(oldEntry);
      if (!list.contains(oldEntry)) {
          addEvent(newEvent);
          takesieBacksie(oldEntry);
        } else {
          list.set(index, newEvent);
        }
    } else if (newEntry instanceof Task newTask) {
      List<Task> list = taskMap.get(newTask.getDayOfTheWeek());
            int index = list.indexOf(oldEntry);
      if (!list.contains(oldEntry)) {
        addTask(newTask);
        takesieBacksie(oldEntry);
      } else {
        list.set(index, newTask);
      }
    }

  }

  /**
   * Moves an element up or down a list.
   *
   * @param entry the entry being moved
   * @param list the list that contians the entry
   * @param offset how much the element is being moved
   * @param <T> what type of entry the entry is.
   */
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
