package cs3500.pa05.model;

public abstract class Entry {

  private Day dayOfTheWeek;
  private String name;
  private String description;
  private int index;


  public Entry(Day dayOfTheWeek, String name, String description) {
    this.dayOfTheWeek = dayOfTheWeek;
    this.name = name;
    this.description = description;
  }

  public Entry(Day dayOfWeek, String name) {
    this(dayOfWeek, name, null);
  }

  public Day getDayOfTheWeek() {
    return dayOfTheWeek;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getIndex() {
    return index;
  }

}
