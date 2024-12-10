package edu.ntnu.idi.idatt;

import java.util.Scanner;

/**
 * App entry point.
 *
 * <p>Run this class to start the Food Conservation Application.</p>
 */
public class Main {

  /**
   * The main method serves as the application's entry point.
   *
   * <p>It initializes the {@link UserInterface},
   * which manages the user's interaction with the application.</p>
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    UserInterface ui = new UserInterface();

    ui.init();
    ui.start(scanner);
  }
}

