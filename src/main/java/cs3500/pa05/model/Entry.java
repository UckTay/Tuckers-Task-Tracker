package cs3500.pa05.model;

/**
 * Represents an Entry.
 */
public abstract class Entry {
  private Day dayOfTheWeek;
  private String name;
  private String description;
  private int index;


  /**
   * Constructs an instance of Entry.
   *
   * @param dayOfTheWeek the day of the entry
   * @param name the name of the entry
   * @param description the description of the entry
   */
  public Entry(Day dayOfTheWeek, String name, String description) {
    this.dayOfTheWeek = dayOfTheWeek;
    this.name = name;
    this.description = description;
  }

  /**
   * Creates an instance of Entry with no description.
   *
   * @param dayOfWeek the day of the entry
   * @param name the name of the entry
   */
  public Entry(Day dayOfWeek, String name) {
    this(dayOfWeek, name, null);
  }

  /**
   * Gets the day of the entry.
   *
   * @return the day
   */
  public Day getDayOfTheWeek() {
    return dayOfTheWeek;
  }

  /**
   * gets the name of the entry
   *
   * @return the name of the entry
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the description of the entry.
   *
   * @return the description of the entry
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the index of the entry.
   *
   * @return the index of the entry
   */
  public int getIndex() {
    return index;
  }

}
