package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.constants.AbilityType;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.constants.Disease;

/**
 * A class to create messages in the required format
 */
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


  /**
   * Builds a troop spawning message
   *
   * @param troopClass the class of the troop to be spawned
   * @param lane       the lane the troops needs to be spawned in
   * @param playerType the player which is spawning the troop
   * @return the message for spawning the specified troop where specified
   */
  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = TROOP_SPAWN_HEADER;

    message += troopClass.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += lane.getEncoded();
    return (message);
  }

  /**
   * Builds a message to cast an ability
   *
   * @param abilityType the type of the ability to be casted
   * @param xAxis       x position of where the ability needs to be casted
   * @param yAxis       y position of where the ability needs to be casted
   * @param playerType  the player which is spawning the troop
   * @return the message for casting the specified ability where specified
   */
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

  /**
   * Builds a message to cast an ability
   *
   * @param abilityType the type of the ability to be casted
   * @param lane        lane in which the ability needs to be casted
   * @param playerType  the player which is spawning the troop
   * @return the message for casting the specified ability where specified
   */
  public static String castAbilityMessage(AbilityType abilityType, Lane lane, PlayerType playerType) {
    String message = LANE_ABILITY_CAST_HEADER;

    message += abilityType.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += lane.getEncoded();

    return (message);
  }

  /**
   * Builds a message used to send updates about health
   *
   * @param health     the health of a player
   * @param playerType the player whose base health is about to be updated
   * @return the message for updating the health
   */
  public static String healthUpdate(int health, PlayerType playerType) {
    String message = HEALTH_HEADER;
    message += playerType.getEncoded();
    message += "_";
    message += String.format("%0" + HEALTH_PADDING + "d", health);
    return message;
  }

  /**
   * Builds a message used to send updates about resources
   *
   * @param lipids     lipids
   * @param sugars     sugars
   * @param proteins   proteins
   * @param playerType the player whose resources are about to be updated
   * @return the message for updating the resources
   */
  public static String resourceUpdate(int lipids, int sugars, int proteins, PlayerType playerType) {
    String message = RESOURCES_HEADER;
    message += playerType.getEncoded();
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", lipids);
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", sugars);
    message += "_";

    message += String.format("%0" + RESOURCE_PADDING + "d", proteins);

    return message;
  }

  /**
   * Builds a message used to send the selected resource type for the user
   *
   * @param disease    disease type that the user has selected
   * @param playerType the player whose disease type is about to be set
   * @return the message for selecting a disease type
   */
  public static String diseaseMessage(Disease disease, PlayerType playerType) {
    String message = RACE_HEADER;
    message += disease.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    return message;
  }

  /**
   * Builds a message used to send the selected race type for the user
   *
   * @param playerType the player whose race type is about to be set
   * @return the message for selecting a race type
   */
  public static String chooseRaceMessage(PlayerType playerType) {
    String message = CHOOSE_RACE_HEADER;
    message += playerType.getEncoded();
    return message;
  }

  /**
   * Builds a message used to send the user who is selecting first
   *
   * @param playerType the player that is selecting first
   * @return the message for setting who picks first
   */
  public static String firstPickerMessage(PlayerType playerType) {
    String message = FIRST_PICKER_HEADER;
    message += playerType.getEncoded();
    return message;
  }

  /**
   * Builds a message used to pause the game
   *
   * @return the message for pausing the game
   */
  public static String pauseMessage() {
    return PAUSE_MESSAGE;
  }

  /**
   * Builds a message used to exit the game
   *
   * @return the message for exiting the game
   */
  public static String exitMessage() {
    return EXIT_MESSAGE;
  }
}
