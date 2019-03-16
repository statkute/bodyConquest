package main.com.bodyconquest.game_logic.utils;

public class Timer {
  public static boolean startTimer(int miliseconds){
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
