package main.com.bodyconquest.networking.utilities;

import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.rendering.BodyConquest;

import java.util.HashMap;

/**
 * The type Message maker.
 */
public class MessageMaker {

    /**
     * The constant OBJECT_UPDATE_HEADER.
     */
    public static final String OBJECT_UPDATE_HEADER = "OBJECT_UPDATE_";
    /**
     * The constant TROOP_SPAWN_HEADER.
     */
    public static final String TROOP_SPAWN_HEADER = "ACTION_T_";
    /**
     * The constant LANE_ABILITY_CAST_HEADER.
     */
    public static final String LANE_ABILITY_CAST_HEADER = "ACTION_AL_";
    /**
     * The constant ABILITY_CAST_HEADER.
     */
    public static final String ABILITY_CAST_HEADER = "ACTION_AA_";
    /**
     * The constant HEALTH_HEADER.
     */
    public static final String HEALTH_HEADER = "HEALTH_";
    /**
     * The constant RESOURCES_HEADER.
     */
    public static final String RESOURCES_HEADER = "RESOURCES_";
    /**
     * The constant RACE_HEADER.
     */
    public static final String RACE_HEADER = "RACE_";
    /**
     * The constant CHOOSE_RACE_HEADER.
     */
    public static final String CHOOSE_RACE_HEADER = "CHOOSE_RACE_";
    /**
     * The constant SET_DIFFICULTY_HEADER.
     */
    public static final String SET_DIFFICULTY_HEADER = "SET_DIFFICULTY_";
    /**
     * The constant FIRST_PICKER_HEADER.
     */
    public static final String FIRST_PICKER_HEADER = "FIRST_PICKER_";
    /**
     * The constant CONFIRM_RACE_HEADER.
     */
    public static final String CONFIRM_RACE_HEADER = "CONFIRM_RACE_";
    /**
     * The constant CONFIRM_ORGAN_HEADER.
     */
    public static final String CONFIRM_ORGAN_HEADER = "CONFIRM_ORGAN_";
    /**
     * The constant START_ENCOUNTER_HEADER.
     */
    public static final String START_ENCOUNTER_HEADER = "START_ENCOUNTER_";
    /**
     * The constant POINTS_HEADER.
     */
    public static final String POINTS_HEADER = "POINTS_HEADER_";
    /**
     * The constant LOGIN_HEADER.
     */
    public static final String LOGIN_HEADER = "LOGIN_";
    /**
     * The constant REGISTER_HEADER.
     */
    public static final String REGISTER_HEADER = "REGISTER_";
    /**
     * The constant GET_LEADERBOARD_HEADER.
     */
    public static final String GET_LEADERBOARD_HEADER = "GET_LEADERBOARD_";
    /**
     * The constant SET_LEADERBOARD_HEADER.
     */
    public static final String SET_LEADERBOARD_HEADER = "SET_LEADERBOARD_";
    /**
     * The constant SET_ACHIEVEMENT_HEADER.
     */
    public static final String SET_ACHIEVEMENT_HEADER = "SET_ACHIEVEMENT_";
    /**
     * The constant SELECTED_ORGAN_HEADER.
     */
    public static final String SELECTED_ORGAN_HEADER = "SELECT_ORGAN_";
    /**
     * The constant ORGAN_CLAIMED.
     */
    public static final String ORGAN_CLAIMED = "CLAIMED_";
    /**
     * The constant USERNAME_.
     */
    public static final String USERNAME_ ="USERNAME_";

    /**
     * The constant RESOURCE_PADDING.
     */
    public static final int RESOURCE_PADDING = 3;
    /**
     * The constant HEALTH_PADDING.
     */
    public static final int HEALTH_PADDING = 3;
    /**
     * The constant COORDINATE_PADDING.
     */
    public static final int COORDINATE_PADDING = 4;
    /**
     * The constant POINTS_PADDING.
     */
    public static final int POINTS_PADDING = 4;

    /**
     * The constant PAUSE_MESSAGE.
     */
    public static final String PAUSE_MESSAGE = "PAUSE";
    /**
     * The constant EXIT_MESSAGE.
     */
    public static final String EXIT_MESSAGE = "EXIT";
    /**
     * The constant START_BODY.
     */
    public static final String START_BODY = "START_BODY";
    /**
     * The constant JOINED_MESSAGE_HEADER.
     */
    public static final String JOINED_MESSAGE_HEADER = "JOINED_";

    /**
     * Spawn troops message string.
     *
     * @param troopClass the troop class
     * @param lane       the lane
     * @param playerType the player type
     * @return the string
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
     * Cast ability message string.
     *
     * @param abilityType the ability type
     * @param xAxis       the x axis
     * @param yAxis       the y axis
     * @param playerType  the player type
     * @return the string
     */
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

    /**
     * Cast ability message string.
     *
     * @param abilityType the ability type
     * @param lane        the lane
     * @param playerType  the player type
     * @return the string
     */
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

    /**
     * Health update string.
     *
     * @param health the health
     * @param player the player
     * @return the string
     */
    public static String healthUpdate(int health, PlayerType player) {
        String message = HEALTH_HEADER;
        message += player.getEncoded();
        message += "_";
        message += String.format("%0" + HEALTH_PADDING + "d", health);
        return message;
    }

    /**
     * Resource update string.
     *
     * @param lipids   the lipids
     * @param sugars   the sugars
     * @param proteins the proteins
     * @param player   the player
     * @return the string
     */
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

    /**
     * Disease message string.
     *
     * @param disease    the disease
     * @param playerType the player type
     * @return the string
     */
    public static String diseaseMessage(Disease disease, PlayerType playerType) {
        String message = RACE_HEADER;
        message += disease.getEncoded();
        message += "_";
        message += playerType.getEncoded();
        return message;
    }

    /**
     * Choose race message string.
     *
     * @param player the player
     * @return the string
     */
    public static String chooseRaceMessage(PlayerType player) {
        String message = CHOOSE_RACE_HEADER;
        message += player.getEncoded();
        return message;
    }

    /**
     * Sets difficulty message.
     *
     * @param difficulty the difficulty
     * @return the difficulty message
     */
    public static String setDifficultyMessage(DifficultyLevel difficulty) {
        String message = SET_DIFFICULTY_HEADER;
        message += " " + difficulty.toString();
        return message;
    }

    /**
     * First picker message string.
     *
     * @param player the player
     * @return the string
     */
    public static String firstPickerMessage(PlayerType player) {
        String message = FIRST_PICKER_HEADER;
        message += player.getEncoded();
        return message;
    }

    /**
     * Confirm race message string.
     *
     * @param playerType the player type
     * @return the string
     */
    public static String confirmRaceMessage(PlayerType playerType) {
        String message = CONFIRM_RACE_HEADER;
        message += playerType.getEncoded();
        return message;
    }

    /**
     * Pause message string.
     *
     * @return the string
     */
    public static String pauseMessage() {
        return PAUSE_MESSAGE;
    }

    /**
     * Exit message string.
     *
     * @return the string
     */
    public static String exitMessage() {
        return EXIT_MESSAGE;
    }

    /**
     * Selected organ message string.
     *
     * @param organ the organ
     * @return the string
     */
    public static String selectedOrganMessage(Organ organ) {
        String message = SELECTED_ORGAN_HEADER;
        message += organ.getEncoded();
        return message;
    }

    /**
     * Confirm organ message string.
     *
     * @param organ the organ
     * @return the string
     */
    public static String confirmOrganMessage(Organ organ) {
        String message = CONFIRM_ORGAN_HEADER;
        message += organ.getEncoded();
        return message;
    }

    /**
     * Start encounter message string.
     *
     * @param organ the organ
     * @return the string
     */
    public static String startEncounterMessage(Organ organ) {
        String message = START_ENCOUNTER_HEADER;
        message += organ.getEncoded();
        return message;
    }

    /**
     * Points message string.
     *
     * @param topPlayerPoints    the top player points
     * @param bottomPlayerPoints the bottom player points
     * @return the string
     */
    public static String pointsMessage(int topPlayerPoints, int bottomPlayerPoints) {
        String message = POINTS_HEADER;

        message += String.format("%0" + POINTS_PADDING + "d", topPlayerPoints);
        message += "_";

        message += String.format("%0" + POINTS_PADDING + "d", bottomPlayerPoints);

        return message;
    }

    /**
     * Organ claim message string.
     *
     * @param playerType the player type
     * @param organ      the organ
     * @return the string
     */
    public static String organClaimMessage(PlayerType playerType, Organ organ){
        String message = ORGAN_CLAIMED;
        message += playerType.getEncoded() +"_" + organ.getEncoded();

        return message;

    }

    /**
     * Username message string.
     *
     * @param playerType the player type
     * @param username   the username
     * @return the string
     */
    public static String usernameMessage(PlayerType playerType, String username){
        String message = USERNAME_;

        message += playerType.getEncoded() + "_" + username;

        return message;
    }

    /**
     * Login message string.
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    public static String loginMessage(String username, String password) {
        String message = LOGIN_HEADER;

        message += " " + username;

        message += " " + password;

        return message;
    }

    /**
     * Logged in successfully message string.
     *
     * @param username the username
     * @return the string
     */
    public static String loggedInSuccessfullyMessage(String username) {
        String message = LOGIN_HEADER;

        message += " 1";

        message += " " + username;
        return message;
    }

    /**
     * Unsuccessful login message string.
     *
     * @param username the username
     * @return the string
     */
    public static String unsuccessfulLoginMessage(String username) {
        String message = LOGIN_HEADER;

        message += " 0";

        message += " " + username;
        return message;
    }

    /**
     * Register message string.
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    public static String registerMessage(String username, String password) {
        String message = REGISTER_HEADER;

        message += " " + username;

        message += " " + password;

        return message;
    }

    /**
     * Registered successfully message string.
     *
     * @return the string
     */
    public static String registeredSuccessfullyMessage() {
        String message = REGISTER_HEADER;

        message += " 1";
        return message;
    }

    /**
     * Unsuccessful register message string.
     *
     * @return the string
     */
    public static String unsuccessfulRegisterMessage() {
        String message = REGISTER_HEADER;

        message += " 0";
        return message;
    }

    /**
     * Gets leaderboard message.
     *
     * @return the leaderboard message
     */
    public static String getLeaderboardMessage() {
        return GET_LEADERBOARD_HEADER;
    }

    /**
     * Send leaderboard message string.
     *
     * @param board the board
     * @return the string
     */
    public static String sendLeaderboardMessage(HashMap<String, Integer> board) {
        String message = SET_LEADERBOARD_HEADER;

        for (String i : board.keySet()) {
            message += i + " " + board.get(i).toString() + " ";
        }

        System.out.println(message);

        return message;
    }

    /**
     * Send achievement message string.
     *
     * @param username the username
     * @param points   the points
     * @return the string
     */
    public static String sendAchievementMessage(String username, int points) {
        String message = SET_ACHIEVEMENT_HEADER;

        message += " " + username;

        message += " " + points;

        return message;
    }


}
