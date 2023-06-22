package cs3500.pa05.controller;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Represents a CustomGUIEvent
 */
public class CustomGuiEvent extends Event {

  /**
   * Symbolizes a new update GUI Event.
   */
  public static final EventType<CustomGuiEvent> UPDATE_GUI_EVENT =
      new EventType<>(Event.ANY, "update gui event");

  /**
   * Construct a new {@code Event} with the specified event type. The source
   * and target of the event is set to {@code NULL_SOURCE_TARGET}.
   */
  public CustomGuiEvent() {
    super(UPDATE_GUI_EVENT);
  }
}
