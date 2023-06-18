package cs3500.pa05.model;

/**
 * Represents the Day.
 */
public enum Day {
  /**
   * The day Sunday.
   */
  SUNDAY,
  /**
   * The day Monday.
   */
  MONDAY,
  /**
   * The day Tuesday.
   */
  TUESDAY,
  /**
   * The day Wednesday.
   */
  WEDNESDAY,
  /**
   * The day Thursday.
   */
  THURSDAY,
  /**
   * The day Friday.
   */
  FRIDAY,
  /**
   * The day Saturday
   */
  SATURDAY;

  @Override
  public String toString() {
    return this.name().charAt(0) + this.name().substring(1).toLowerCase();
  }
}
