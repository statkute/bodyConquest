package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Constants.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MessageMaker {

  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = "ACTION_T_";

    message += troopClass.encode();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.encoded();
    return (message);
  }

  public static String castAbilityMessage(String abilityName, int xAxis, int yAxis) {
    String message = "ACTION_A_";

    if (abilityName.equals("fireball")) {
      message += "F_";
    } else {
      message += "W_"; // water blast
    }
    if (xAxis < 10) {
      message += ("0" + xAxis);
    } else {
      message += xAxis;
    }
    message += "_";
    if (yAxis < 10) {
      message += ("0" + yAxis);
    } else {
      message += yAxis;
    }

    return (message);
  }


  public static String healthUpdate(int health, String position) {
    String message = "HEALTH_";
    message += position.toUpperCase().charAt(0);
    message += "_";
    NumberFormat formatter = new DecimalFormat("000");
    String s = formatter.format(health);
    message += s;

    return message;
  }

  public static String raceMessage(String race) {
    String message = "RACE_";
    message += race;
    return message;
  }

  public static String pauseMessage() {
    return ("PAUSE");
  }

  public static String exitMessage() {
    return ("EXIT");
  }
}
