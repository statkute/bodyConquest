package main.com.bodyconquest.game_logic.utils;

/** The type Timer. */
public class Timer {
  /**
   * Start timer boolean.
   *
   * @param miliseconds the miliseconds
   * @return the boolean
   */
  public static boolean startTimer(int miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
