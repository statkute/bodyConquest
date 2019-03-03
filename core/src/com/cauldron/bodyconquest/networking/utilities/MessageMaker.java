package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.constants.Assets.*;
import com.cauldron.bodyconquest.constants.Disease;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MessageMaker {

  public static final String TROOP_SPAWN_HEADER = "ACTION_T_";
  public static final String ABILITY_CAST_HEADER = "ACTION_A_";
  public static final String HEALTH_HEADER = "HEALTH_";
  public static final String RESOURCES_HEADER = "RESOURCES_";
  public static final String PAUSE_MESSAGE = "PAUSE";
  public static final String EXIT_MESSAGE = "EXIT";
  public static final String RACE_HEADER = "RACE_";

  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = TROOP_SPAWN_HEADER;

    message += troopClass.encode();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.getEncoded();
    return (message);
  }

  public static String castAbilityMessage(String abilityName, int xAxis, int yAxis) {
    String message = ABILITY_CAST_HEADER;

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
    String message = HEALTH_HEADER;
    message += position.toUpperCase().charAt(0);
    message += "_";
    NumberFormat formatter = new DecimalFormat("000");
    String formattedNumber = formatter.format(health);
    message += formattedNumber;

    return message;
  }

  public static String resourceUpdate(int lipids, int sugars, int proteins, String player){
    String message = RESOURCES_HEADER;
    message += player.toUpperCase().charAt(0);
    message += "_";

    NumberFormat formatter = new DecimalFormat("000");
    String formattedLipids = formatter.format(lipids);
    message += formattedLipids;
    message += "_";

    String formattedSugars = formatter.format(sugars);
    message += formattedSugars;
    message += "_";

    String formattedProteins = formatter.format(proteins);
    message += formattedProteins;

    return message;

  }

  public static String diseaseMessage(Disease disease, PlayerType playerType) {
    String message = RACE_HEADER;
    message += disease.getEncoded();
    message += "_";
    message += playerType.encoded();
    return message;
  }

  public static String pauseMessage() {
    return PAUSE_MESSAGE;
  }

  public static String exitMessage() {
    return EXIT_MESSAGE;
  }
}
