package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomGUIEvent extends Event {
  public static final EventType<CustomGUIEvent> UPDATE_GUI_EVENT =
      new EventType<>(Event.ANY, "update gui event");
  /**
   * Construct a new {@code Event} with the specified event type. The source
   * and target of the event is set to {@code NULL_SOURCE_TARGET}.
   *
   * @param eventType the event type
   */
  public CustomGUIEvent() {
    super(UPDATE_GUI_EVENT);
  }
}
