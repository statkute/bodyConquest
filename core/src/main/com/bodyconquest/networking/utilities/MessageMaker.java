package main.com.bodyconquest.networking.utilities;

import main.com.bodyconquest.constants.*;

import java.util.HashMap;

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
  public static final String CONFIRM_RACE_HEADER = "CONFIRM_RACE_";
  public static final String CONFIRM_ORGAN_HEADER = "CONFIRM_ORGAN_";
  public static final String START_ENCOUNTER_HEADER = "START_ENCOUNTER_";
  public static final String POINTS_HEADER = "POINTS_HEADER_";
  public static final String LOGIN_HEADER = "LOGIN_";
  public static final String REGISTER_HEADER = "REGISTER_";
  public static final String GET_LEADERBOARD_HEADER = "GET_LEADERBOARD_";
  public static final String SET_LEADERBOARD_HEADER = "SET_LEADERBOARD_";
  public static final String SET_ACHIEVEMENT_HEADER = "SET_ACHIEVEMENT_";
  public static final String SELECTED_ORGAN_HEADER = "SELECT_ORGAN_";
  public static final String ORGAN_CLAIMED = "CLAIMED_";
  public static final String USERNAME_ ="USERNAME_";

  public static final int RESOURCE_PADDING = 3;
  public static final int HEALTH_PADDING = 3;
  public static final int COORDINATE_PADDING = 4;
  public static final int POINTS_PADDING = 4;

  public static final String PAUSE_MESSAGE = "PAUSE";
  public static final String EXIT_MESSAGE = "EXIT";
  public static final String START_BODY = "START_BODY";
  public static final String JOINED_MESSAGE_HEADER = "JOINED_";

  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = TROOP_SPAWN_HEADER;

    message += troopClass.getEncoded();
    message += "_";
    message += playerType.getEncoded();
    message += "_";
    message += lane.getEncoded();
    return (message);
  }

  public static String castAbilityMessage(
      AbilityType abilityType, int xAxis, int yAxis, PlayerType playerType) {
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

  public static String castAbilityMessage(
          AbilityType abilityType, Lane lane, PlayerType playerType) {
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

  public static String resourceUpdate(int lipids, int sugars, int proteins, PlayerType player) {
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

  public static String confirmRaceMessage(PlayerType playerType) {
    String message = CONFIRM_RACE_HEADER;
    message += playerType.getEncoded();
    return message;
  }

  public static String pauseMessage() {
    return PAUSE_MESSAGE;
  }

  public static String exitMessage() {
    return EXIT_MESSAGE;
  }

  public static String selectedOrganMessage(Organ organ) {
    String message = SELECTED_ORGAN_HEADER;
    message += organ.getEncoded();
    return message;
  }

  public static String confirmOrganMessage(Organ organ) {
    String message = CONFIRM_ORGAN_HEADER;
    message += organ.getEncoded();
    return message;
  }

  public static String startEncounterMessage(Organ organ) {
    String message = START_ENCOUNTER_HEADER;
    message += organ.getEncoded();
    return message;
  }

  public static String pointsMessage(int topPlayerPoints, int bottomPlayerPoints) {
    String message = POINTS_HEADER;

    message += String.format("%0" + POINTS_PADDING + "d", topPlayerPoints);
    message += "_";

    message += String.format("%0" + POINTS_PADDING + "d", bottomPlayerPoints);

    return message;
  }

  public static String organClaimMessage(PlayerType playerType, Organ organ){
    String message = ORGAN_CLAIMED;
    message += playerType.getEncoded() +"_" + organ.getEncoded();

    return message;

  }

  public static String usernameMessage(PlayerType playerType, String username){
    String message = USERNAME_;

    message += playerType.getEncoded() + "_" + username;

    return message;
  }

  public static String loginMessage(String username, String password) {
    String message = LOGIN_HEADER;

    message += " " + username;

    message += " " + password;

    return message;
  }

  public static String loggedInSuccessfullyMessage(String username) {
    String message = LOGIN_HEADER;

    message += " 1";

    message += " " + username;
    return message;
  }

  public static String unsuccessfulLoginMessage(String username) {
    String message = LOGIN_HEADER;

    message += " 0";

    message += " " + username;
    return message;
  }

  public static String registerMessage(String username, String password) {
    String message = REGISTER_HEADER;

    message += " " + username;

    message += " " + password;

    return message;
  }

  public static String registeredSuccessfullyMessage() {
    String message = REGISTER_HEADER;

    message += " 1";
    return message;
  }

  public static String unsuccessfulRegisterMessage() {
    String message = REGISTER_HEADER;

    message += " 0";
    return message;
  }

  public static String getLeaderboardMessage() {
    return GET_LEADERBOARD_HEADER;
  }

  public static String sendLeaderboardMessage(HashMap<String, Integer> board) {
    String message = SET_LEADERBOARD_HEADER;

    for (String i : board.keySet()) {
      message += i + " " + board.get(i).toString() + " ";
    }

    System.out.println(message);

    return message;
  }

  public static String sendAchievementMessage(String username, int points) {
    String message = SET_ACHIEVEMENT_HEADER;

    message += " " + username;

    message += " " + points;

    return message;
  }



}
