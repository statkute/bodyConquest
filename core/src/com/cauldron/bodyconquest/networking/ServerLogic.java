package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.AbilityType;
import com.cauldron.bodyconquest.constants.Assets.*;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.game_logic.Player;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;

/**
 * Server Thread responsible for dealing with game logic based on incoming messages
 */
public class ServerLogic extends Thread {

  public enum Logic {
    RACE_SELECTION_LOGIC,
    BODY_LOGIC,
    ENCOUNTER_LOGIC
  }

  private Logic currentLogic;
  private ServerReceiver serverReceiver;
  private ServerSender serverSender;
  private boolean run;
  // Encounter Logic Variables
  private EncounterState encounterState;
  // Race Selection Variables
  private Game game;

  /**
   * ServerLogic constructor
   *
   * @param serverReceiver The ServerReceiver thread of the same Server.
   */
  public ServerLogic(ServerReceiver serverReceiver, ServerSender serverSender) {
    this.serverReceiver = serverReceiver;
    this.serverSender = serverSender;
    this.run = true;
    init();
  }

  private void init() {
    currentLogic = null;
  }

  /**
   * Deals with game logic tasks of the incoming messages
   */
  public void run() {
    while (run) {
      try {
        String message = serverReceiver.receivedMessages.take();

        if (currentLogic == null) {
          System.err.println("[ERROR] No server logic has been set.");
          // Right now we throw away messages we don't interpret
          // In future we may want to add them back to the received messages list
          continue;
        }

        if (currentLogic == Logic.RACE_SELECTION_LOGIC) {
          raceSelectionLogic(message);
        } else if (currentLogic == Logic.ENCOUNTER_LOGIC) {
          encounterLogic(message);
        } else if (currentLogic == Logic.BODY_LOGIC) {
          bodyLogic(message);
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Executes tasks concerned with body screen logic that were sent with the message
   *
   * @param message the message instruction to be executed
   */
  private void bodyLogic(String message) {
  }

  /**
   * Executes tasks concerned with race selection screen logic that were sent with the message
   *
   * @param message the message instruction to be executed
   */
  private void raceSelectionLogic(String message) {
    if (message.startsWith(MessageMaker.RACE_HEADER)) {
      Disease disease;
      PlayerType playerType;

      int pointer = MessageMaker.RACE_HEADER.length();

      String encodedDisease = message.substring(pointer, pointer + Disease.getEncodedLength());
      disease = Disease.decode(encodedDisease);
      pointer += Disease.getEncodedLength() + 1;

      String encodedPlayerType =
          message.substring(pointer, pointer + PlayerType.getEncodedLength());
      playerType = PlayerType.decode(encodedPlayerType);

      if (playerType == PlayerType.PLAYER_TOP) {
        game.setPlayerTop(disease);
      } else if (playerType == PlayerType.PLAYER_BOTTOM) {
        game.setPlayerBottom(disease);
      }

      // Response message for the other player to receive so they can update the other player's selection on their screen
      String responseMessage = MessageMaker.diseaseMessage(disease, playerType);
      serverSender.sendMessage(responseMessage);

      // Does nothing for player type AI as of now
      // if(playerType == PlayerType.AI)          playerTop = new Player(playerType, disease);
    } else if (message.equals(MessageMaker.CONFIRMED_RACE)) {
      System.err.println("[ERROR] This message doesn't conform to the current logic.");
    }
  }

  /**
   * Executes tasks concerned with encounter screen logic that were sent with the message
   *
   * @param message the message instruction to be executed
   */
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

      encounterState.spawnUnit(unit, lane, playerType);
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

      String encodedPlayerType = message.substring(pointer, pointer + PlayerType.getEncodedLength());
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

      String encodedPlayerType = message.substring(pointer, pointer + PlayerType.getEncodedLength());
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

  /**
   * Sets the logic type to be dealt with in this object to be encounter state logic and sets the EncounterState object
   *
   * @param encounterState currently played encounterState
   */
  public void setEncounterLogic(EncounterState encounterState) {
    this.encounterState = encounterState;
    currentLogic = Logic.ENCOUNTER_LOGIC;
  }

  /**
   * Sets the logic type to be dealt with in this object to be race selection logic and sets the Game object
   *
   * @param game currently played Game
   */
  public void setRaceSelectionLogic(Game game) {
    this.game = game;
    currentLogic = Logic.RACE_SELECTION_LOGIC;
  }

  /**
   * Stops this thread from running
   */
  public void stopRunning() {
    run = false;
  }
}
