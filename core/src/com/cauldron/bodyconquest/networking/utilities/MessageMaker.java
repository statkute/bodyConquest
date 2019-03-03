package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.constants.AbilityType;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.constants.Disease;

public class MessageMaker {

  public static final String OBJECT_UPDATE_HEADER = "OBJECT_UPDATE_";
  public static final String TROOP_SPAWN_HEADER = "ACTION_T_";
  public static final String LANE_ABILITY_CAST_HEADER = "ACTION_AL_";
  public static final String ABILITY_CAST_HEADER = "ACTION_AA_";
  public static final String HEALTH_HEADER = "HEALTH_";
  public static final String RESOURCES_HEADER = "RESOURCES_";
  public static final String RACE_HEADER = "RACE_";
  public static final String CHOOSE_RACE_HEADER = "CHOOSE_RACE_";
  public static final String FIRST_PICKER_HEADER = "FIRST_PICKER_";

  public static final int RESOURCE_PADDING = 3;
  public static final int HEALTH_PADDING = 3;
  public static final int COORDINATE_PADDING = 4;

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

  public static String castAbilityMessage(AbilityType abilityType, int xAxis, int yAxis, PlayerType playerType) {
    String message = ABILITY_CAST_HEADER;

    String xPadded = String.format("%0" + COORDINATE_PADDING + "d", xAxis);
    String yPadded = String.format("%0" + COORDINATE_PADDING + "d", yAxis);

    message += abilityType.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += xPadded;
    message += "_";
    message += yPadded;

    return (message);
  }

  public static String castAbilityMessage(AbilityType abilityType, Lane lane, PlayerType playerType) {
    String message = LANE_ABILITY_CAST_HEADER;

    message += abilityType.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += lane.getEncoded();

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
