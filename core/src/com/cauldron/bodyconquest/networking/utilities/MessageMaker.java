package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.constants.Ability;
import com.cauldron.bodyconquest.constants.Assets.*;
import com.cauldron.bodyconquest.constants.Disease;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MessageMaker {

  public static final String OBJECT_UPDATE_HEADER = "OBJECT_UPDATE_";
  public static final String TROOP_SPAWN_HEADER = "ACTION_T_";
  public static final String ABILITY_CAST_HEADER = "ACTION_A_";
  public static final String HEALTH_HEADER = "HEALTH_";
  public static final String RESOURCES_HEADER = "RESOURCES_";
  public static final String RACE_HEADER = "RACE_";
  public static final String CHOOSE_RACE_HEADER = "CHOOSE_RACE_";
  public static final String FIRST_PICKER_HEADER = "FIRST_PICKER_";

  public static final int RESOURCE_PADDING = 3;
  public static final int HEALTH_PADDING = 3;
  public static final int COORDINATE_PADDING = 3;

  public static final String CONFIRMED_RACE = "CONFIRMED_RACE";
  public static final String PAUSE_MESSAGE = "PAUSE";
  public static final String EXIT_MESSAGE = "EXIT";


  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = TROOP_SPAWN_HEADER;

    message += troopClass.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += lane.getEncoded();
    return (message);
  }

  public static String castAbilityMessage(Ability ability, int xAxis, int yAxis, PlayerType playerType) {
    String message = "ACTION_AA_";

    String xPadded = String.format("%04d", xAxis);
    String yPadded = String.format("%04d", yAxis);

//    if (ability.equals("fireball")) {
//      message += "F_";
//    } else {
//      message += "W_"; // water blast
//    }
//    if (xAxis < 10) {
//      message += ("0" + xAxis);
//    } else {
//      message += xAxis;
//    }
//    message += "_";
//    if (yAxis < 10) {
//      message += ("0" + yAxis);
//    } else {
//      message += yAxis;
//    }

    message += ability.getEncoded();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += xPadded;
    message += "_";
    message += yPadded;

    return (message);
  }

  public static String castAbilityMessage(Ability ability, Lane lane, PlayerType playerType) {
    String message = "ACTION_AL_";
  public static String castAbilityMessage(String abilityName, int xAxis, int yAxis) {
    String message = ABILITY_CAST_HEADER;

    message += ability.getEncoded();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.encoded();
    return (message);
  }


  public static String healthUpdate(int health, PlayerType player) {
    String message = HEALTH_HEADER;
    message += player.getEncoded();
    message += "_";
    message += String.format("%0" + HEALTH_PADDING + "d", health);
    return message;
  }

  public static String resourceUpdate(int lipids, int sugars, int proteins, PlayerType player){
    String message = RESOURCES_HEADER;
    message += player.getEncoded();
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", lipids);
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", sugars);
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", proteins);

    return message;

  }

  public static String diseaseMessage(Disease disease, PlayerType playerType) {
    String message = RACE_HEADER;
    message += disease.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    return message;
  }

  public static String chooseRaceMessage(PlayerType player) {
    String message = CHOOSE_RACE_HEADER;
    message += player.getEncoded();
    return message;
  }

  public static String firstPickerMessage(PlayerType player) {
    String message = FIRST_PICKER_HEADER;
    message += player.getEncoded();
    return message;
  }

  public static String pauseMessage() {
    return PAUSE_MESSAGE;
  }

  public static String exitMessage() {
    return EXIT_MESSAGE;
  }
}
