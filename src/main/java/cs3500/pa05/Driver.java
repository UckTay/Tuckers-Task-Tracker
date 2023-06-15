package cs3500.pa05;

import cs3500.pa05.controller.JournalController;
import cs3500.pa05.model.BujoWriter;

public class Driver {
  public static void main(String[] args) {
    //new JournalController(null, null);
    new BujoWriter().writeBujo(null, null);
  }
}
