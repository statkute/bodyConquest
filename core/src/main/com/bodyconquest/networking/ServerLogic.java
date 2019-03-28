package main.com.bodyconquest.networking;

import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.database.DatabaseManager;
import main.com.bodyconquest.entities.DifficultyLevel;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.gamestates.EncounterState;
import main.com.bodyconquest.networking.utilities.MessageMaker;

import java.util.HashMap;

/** Server Thread responsible for dealing with game logic based on incoming messages */
public class ServerLogic extends Thread {

  /** Possible Server Logics. */
  public enum LogicType {
    RACE_SELECTION_LOGIC,
    BODY_LOGIC,
    ENCOUNTER_LOGIC,
    DATABASE_LOGIC,
    WAITING_LOGIC
  }

  private LogicType currentLogicType;

  private ServerReceiver serverReceiver;
  private ServerSender serverSender;

  private boolean run;

  // Encounter LogicType Variables
  private EncounterState encounterState;

  // Race Selection Variables
  private Game game;
  private boolean topPlayerReady;
  private boolean bottomPlayerReady;

  private DatabaseManager dbManager;

  //private RaceSelectionLogic raceSelectionLogic;

  /**
   * Constructor.
   *
   * @param serverReceiver The ServerReceiver thread of the same Server.
   */
  public ServerLogic(ServerReceiver serverReceiver, ServerSender serverSender) {
    this.serverReceiver = serverReceiver;
    this.serverSender = serverSender;
    this.run = true;
    topPlayerReady = false;
    bottomPlayerReady = false;
    init();
    dbManager = new DatabaseManager();
  }

  private void init() {
    currentLogicType = null;
  }

  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (run) {
      try {
        String message = serverReceiver.receivedMessages.take();

        if (currentLogicType == null) {
          System.err.println("[ERROR] No server logic has been set.\n\t" + message);
          // Right now we throw away messages we don't interpret
          // In future we may want to add them back to the received messages list
          continue;
        }

         if(message.startsWith(MessageMaker.USERNAME_)){
          PlayerType player;
          int pointer = MessageMaker.USERNAME_.length();

          String encodedPlayerType = message.substring(pointer,pointer + PlayerType.getEncodedLength());
          player = PlayerType.decode(encodedPlayerType);
          pointer +=PlayerType.getEncodedLength() +1;

          if(player == PlayerType.PLAYER_BOTTOM)
            game.usernameBottom = message.substring(pointer);
          else if(player == PlayerType.PLAYER_TOP){
            game.usernameTop = message.substring(pointer);
          }
          serverSender.sendMessage(message);
          continue;
        }

        if (currentLogicType == LogicType.WAITING_LOGIC) waitingLogic(message);
        if (currentLogicType == LogicType.RACE_SELECTION_LOGIC) raceSelectionLogic(message);
        if (currentLogicType == LogicType.ENCOUNTER_LOGIC) encounterLogic(message);
        if (currentLogicType == LogicType.BODY_LOGIC) bodyLogic(message);
        if (currentLogicType == LogicType.DATABASE_LOGIC) databaseLogic(message);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void waitingLogic(String message) {
    int pointer;

    if(message.startsWith(MessageMaker.JOINED_MESSAGE_HEADER)){
      // Needs implementing
    }
  }

  private void databaseLogic(String message) {
    int pointer;

    if (message.startsWith(MessageMaker.REGISTER_HEADER)) {
      pointer = MessageMaker.REGISTER_HEADER.length();

      message = message.substring(pointer + 1);

      String[] values = message.split(" ");

      String username = values[0];
      String password = values[1];

      boolean response = dbManager.addUser(username, password);

      if (response == true) {
        serverSender.sendMessage(MessageMaker.registeredSuccessfullyMessage());
      } else {
        serverSender.sendMessage(MessageMaker.unsuccessfulRegisterMessage());
      }
    } else if (message.startsWith(MessageMaker.LOGIN_HEADER)) {
      pointer = MessageMaker.LOGIN_HEADER.length();

      message = message.substring(pointer);

      String[] values = message.split(" ");

      String username = values[1];
      String password = values[2];

      boolean response = dbManager.checkUser(username, password);
      if (response == true) {
        serverSender.sendMessage(MessageMaker.loggedInSuccessfullyMessage(username));
      } else {
        serverSender.sendMessage(MessageMaker.unsuccessfulLoginMessage(username));
      }
    } else if (message.startsWith(MessageMaker.GET_LEADERBOARD_HEADER)) {
      pointer = MessageMaker.GET_LEADERBOARD_HEADER.length();

      HashMap<String, Integer> board = new HashMap<>();
      board.putAll(dbManager.getLeaderboard());

      serverSender.sendMessage(MessageMaker.sendLeaderboardMessage(board));
    } else if (message.startsWith(MessageMaker.SET_ACHIEVEMENT_HEADER)) {
      pointer = MessageMaker.GET_LEADERBOARD_HEADER.length();

      message = message.substring(pointer);

      String[] values = message.split(" ");

      String username = values[1];
      Integer points = Integer.parseInt(values[2]);

      boolean response = dbManager.insertAchievement(username, points);

      /*if (response == true) {
        serverSender.sendMessage(MessageMaker.addedAchievementSuccessfullyMessage());
      } else {
        serverSender.sendMessage(MessageMaker.unsuccessfulMessage());
      }*/

    }
  }

  private void bodyLogic(String message) {
    int pointer;

    if(message.startsWith(MessageMaker.CONFIRM_ORGAN_HEADER)) {
      Organ organ;

      pointer = MessageMaker.CONFIRM_ORGAN_HEADER.length();

      String encodedOrgan = message.substring(pointer, pointer + Organ.getEncodedLength());
      organ = Organ.decode(encodedOrgan);

      game.startEncounterState(organ);
      serverSender.sendMessage(MessageMaker.startEncounterMessage(organ));
    }
    else if(message.startsWith(MessageMaker.SELECTED_ORGAN_HEADER)) {
      Organ organ;
      pointer = MessageMaker.SELECTED_ORGAN_HEADER.length();

      String encodedOrgan = message.substring(pointer, pointer + Organ.getEncodedLength());
      organ = Organ.decode(encodedOrgan);

      serverSender.sendMessage(MessageMaker.selectedOrganMessage(organ));
    }
  }

  private void raceSelectionLogic(String message) {
    int pointer;
    if (message.startsWith(MessageMaker.RACE_HEADER)) {
      Disease disease;
      PlayerType playerType;

      pointer = MessageMaker.RACE_HEADER.length();

      String encodedDisease = message.substring(pointer, pointer + Disease.getEncodedLength());
      disease = Disease.decode(encodedDisease);
      pointer += Disease.getEncodedLength() + 1;

      String encodedPlayerType =
              message.substring(pointer, pointer + PlayerType.getEncodedLength());
      playerType = PlayerType.decode(encodedPlayerType);

      if (playerType == PlayerType.PLAYER_TOP) game.setPlayerTop(disease);
      if (playerType == PlayerType.PLAYER_BOTTOM) game.setPlayerBottom(disease);

      // Response message for the other player to receive so they can update the other player's
      // selection on their screen
      String responseMessage = MessageMaker.diseaseMessage(disease, playerType);
      serverSender.sendMessage(responseMessage);

      // Does nothing for player type AI as of now
      // if(playerType == PlayerType.AI)          playerTop = new Player(playerType, disease);
    } else if (message.startsWith(MessageMaker.CONFIRM_RACE_HEADER)) {
      PlayerType player;

      pointer = MessageMaker.CONFIRM_RACE_HEADER.length();

      String encodedPlayerType = message.substring(pointer, pointer + PlayerType.getEncodedLength());
      player = PlayerType.decode(encodedPlayerType);
      //serverSender.sendMessage(MessageMaker.chooseRaceMessage(player == PlayerType.PLAYER_BOTTOM ? PlayerType.PLAYER_TOP : PlayerType.PLAYER_BOTTOM));
      if(player == PlayerType.PLAYER_BOTTOM) {
        bottomPlayerReady = true;
      } else {
        topPlayerReady = true;
      }
      serverSender.sendMessage(MessageMaker.chooseRaceMessage(player == PlayerType.PLAYER_BOTTOM ? PlayerType.PLAYER_TOP : PlayerType.PLAYER_BOTTOM));

      if(game.getGameType() == GameType.SINGLE_PLAYER || bottomPlayerReady && topPlayerReady) {
        serverSender.sendMessage(MessageMaker.firstPickerMessage(PlayerType.PLAYER_BOTTOM));
        game.setLastPicker(PlayerType.PLAYER_BOTTOM);
        game.startBodyState();
        serverSender.sendMessage(MessageMaker.START_BODY);
      } else {
//        serverSender.sendMessage(MessageMaker.chooseRaceMessage(player == PlayerType.PLAYER_BOTTOM ? PlayerType.PLAYER_TOP : PlayerType.PLAYER_BOTTOM));
      }
    } else if (message.startsWith(MessageMaker.SET_DIFFICULTY_HEADER)) {
        pointer = MessageMaker.SET_DIFFICULTY_HEADER.length();

        message = message.substring(pointer + 1);

        if (message.equals("HARD")) {
            game.setDifficulty(DifficultyLevel.HARD);
        } else {
            game.setDifficulty(DifficultyLevel.EASY);
        }
    } else {
      System.err.println("[ERROR] This message doesn't conform to the current logic.");
    }
  }

  private void encounterLogic(String message) {
    int pointer;
    if (message.startsWith(MessageMaker.TROOP_SPAWN_HEADER)) {
      pointer = MessageMaker.TROOP_SPAWN_HEADER.length();

      UnitType unit =
          UnitType.decode(message.substring(pointer, pointer + UnitType.getEncodedLength()));
      pointer += UnitType.getEncodedLength() + 1;

      PlayerType playerType =
          PlayerType.decode(message.substring(pointer, pointer + PlayerType.getEncodedLength()));
      pointer += PlayerType.getEncodedLength() + 1;

      Lane lane = Lane.decode(message.substring(pointer, pointer + Lane.getEncodedLength()));
      //System.out.println("TRYING TO SPAWN NOW");

        encounterState.spawnUnit(unit, lane, playerType, false);
      //System.out.println("FINISHED SPAWNING");

    } else if (message.startsWith(MessageMaker.ABILITY_CAST_HEADER)) {

      AbilityType abilityType;
      PlayerType player;
      int xDest;
      int yDest;

      pointer = MessageMaker.ABILITY_CAST_HEADER.length();

      String encodedAbility = message.substring(pointer, pointer + AbilityType.getEncodedLength());
      abilityType = AbilityType.decode(encodedAbility);
      pointer += AbilityType.getEncodedLength() + 1;

      String encodedPlayerType =
          message.substring(pointer, pointer + PlayerType.getEncodedLength());
      player = PlayerType.decode(encodedPlayerType);
      pointer += PlayerType.getEncodedLength() + 1;

      String xString = message.substring(pointer, pointer + MessageMaker.COORDINATE_PADDING);
      xDest = Integer.parseInt(xString);
      pointer += MessageMaker.COORDINATE_PADDING + 1;

      String yString = message.substring(pointer, pointer + MessageMaker.COORDINATE_PADDING);
      yDest = Integer.parseInt(yString);

      encounterState.castAbility(abilityType, player, xDest, yDest);

    } else if (message.startsWith(MessageMaker.LANE_ABILITY_CAST_HEADER)) {

      AbilityType abilityType;
      PlayerType player;
      Lane lane;

      pointer = MessageMaker.LANE_ABILITY_CAST_HEADER.length();

      String encodedAbility = message.substring(pointer, pointer + AbilityType.getEncodedLength());
      abilityType = AbilityType.decode(encodedAbility);
      pointer += AbilityType.getEncodedLength() + 1;

      String encodedPlayerType =
          message.substring(pointer, pointer + PlayerType.getEncodedLength());
      player = PlayerType.decode(encodedPlayerType);
      pointer += PlayerType.getEncodedLength() + 1;

      String encodedLane = message.substring(pointer, pointer + Lane.getEncodedLength());
      lane = Lane.decode(encodedLane);

      encounterState.castAbility(abilityType, player, lane);

    } else if (message.equals("PAUSE")) {
      // TO DO: pause the game
    } else if (message.equals("EXIT")) {
      // TO DO: exit the game
    }
  }

  public void setEncounterLogic(EncounterState encounterState) {
    this.encounterState = encounterState;
    currentLogicType = LogicType.ENCOUNTER_LOGIC;
  }

  public void setRaceSelectionLogic(Game game) {
    this.game = game;
    currentLogicType = LogicType.RACE_SELECTION_LOGIC;
  }

  public void setBodyLogic() {
    currentLogicType = LogicType.BODY_LOGIC;
  }

  public void setDatabaseLogic(Game game) {
    currentLogicType = LogicType.DATABASE_LOGIC;
    this.game = game;
  }

  public void setWaitingLogic() { currentLogicType = LogicType.WAITING_LOGIC; }

  public void stopRunning() {
    run = false;
  }

//  private class RaceSelectionLogic implements Logic{
//
//    private boolean topPlayerConfirmed;
//    private boolean bottomPlayerConfirmed;
//
//    RaceSelectionLogic(){
//      topPlayerConfirmed = false;
//      bottomPlayerConfirmed = false;
//    }
//
//    @Override
//    public void interpret(String message) {
//      if (message.startsWith(MessageMaker.RACE_HEADER)) {
//        Disease disease;
//        PlayerType playerType;
//
//        int pointer = MessageMaker.RACE_HEADER.length();
//
//        String encodedDisease = message.substring(pointer, pointer + Disease.getEncodedLength());
//        disease = Disease.decode(encodedDisease);
//        pointer += Disease.getEncodedLength() + 1;
//
//        String encodedPlayerType =
//                message.substring(pointer, pointer + PlayerType.getEncodedLength());
//        playerType = PlayerType.decode(encodedPlayerType);
//
//        if (playerType == PlayerType.PLAYER_TOP) game.setPlayerTop(disease);
//        if (playerType == PlayerType.PLAYER_BOTTOM) game.setPlayerBottom(disease);
//
//        // Response message for the other player to receive so they can update the other player's
//        // selection on their screen
//        String responseMessage = MessageMaker.diseaseMessage(disease, playerType);
//        serverSender.sendMessage(responseMessage);
//
//        // Does nothing for player type AI as of now
//        // if(playerType == PlayerType.AI)          playerTop = new Player(playerType, disease);
//      } else if (message.equals(MessageMaker.CONFIRM_RACE)) {
//
//      } else {
//        System.err.println("[ERROR] This message doesn't conform to the current logic.");
//      }
//    }
//  }
}
