package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Assets.*;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.game_logic.Player;
import com.cauldron.bodyconquest.gamestates.EncounterState;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;

/** Server Thread responsible for dealing with game logic based on incoming messages */
public class ServerLogic extends Thread {

  /** Possible Server Logics. */
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
  private Player playerBottom;
  private Player playerTop;

  /**
   * Constructor.
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
    playerBottom = null;
    playerTop = null;
  }

  /** Deals with game logic tasks of the incoming messages */
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

        if (currentLogic == Logic.RACE_SELECTION_LOGIC) raceSelectionLogic(message);
        if (currentLogic == Logic.ENCOUNTER_LOGIC) encounterLogic(message);
        if (currentLogic == Logic.BODY_LOGIC) bodyLogic(message);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void bodyLogic(String message) {}

  private void raceSelectionLogic(String message) {
    if (message.startsWith(MessageMaker.RACE_HEADER)) {
      Disease disease;
      PlayerType playerType;

      int pointer = MessageMaker.RACE_HEADER.length();

      String encodedDisease = message.substring(pointer, pointer + Disease.getEncodedLength());
      disease = Disease.decode(encodedDisease);
      pointer += Disease.getEncodedLength() + 1;

      String encodedPlayerType = message.substring(pointer, pointer + PlayerType.getEncodedLength());
      playerType = PlayerType.decode(encodedPlayerType);
      pointer += PlayerType.getEncodedLength() + 1;

      if(playerType == PlayerType.PLAYER_TOP) {
        playerTop = new Player(playerType, disease);
        String responseMessage = MessageMaker.diseaseMessage(disease, playerType);
        serverSender.sendMessage(responseMessage);
      }
      if(playerType == PlayerType.PLAYER_BOTTOM)  playerBottom = new Player(playerType, disease);
      // Does nothing for player type AI as of now
      // if(playerType == PlayerType.AI)          playerTop = new Player(playerType, disease);
    } else if (message.equals(MessageMaker.CONFIRMED_RACE)) {
      System.err.println("[ERROR] This message doesn't conform to the current logic.");
    }
  }

  private void encounterLogic(String message) {
      if (message.startsWith(MessageMaker.TROOP_SPAWN_HEADER)) {
        int pointer = MessageMaker.TROOP_SPAWN_HEADER.length();

        UnitType unit = UnitType.decode(message.substring(pointer, pointer + UnitType.getEncodedLength()));
        pointer += UnitType.getEncodedLength() + 1;

        PlayerType playerType = PlayerType.decode(message.substring(pointer, pointer + PlayerType.getEncodedLength()));
        pointer += PlayerType.getEncodedLength() + 1;

        Lane lane = Lane.decode(message.substring(pointer, pointer + Lane.getEncodedLength()));
        System.out.println("TRYING TO SPAWN NOW");

        encounterState.spawnUnit(unit, lane, playerType);
        System.out.println("FINISHED SPAWNING");
      } else if (message.startsWith(MessageMaker.ABILITY_CAST_HEADER)) {
        int x_Axis = Integer.parseInt(message.substring(11, 13));
        int y_Axis = Integer.parseInt(message.substring(message.length() - 2));
        if (message.charAt(9) == 'F') {
          // TO DO: cast fireball spell from clientID at location x y
        } else if (message.charAt(9) == 'W') {
          // TO DO: cast water blast spell from clientID at location x y
}
    } else if (message.equals("PAUSE")) {
      // TO DO: pause the game
    } else if (message.equals("EXIT")) {
      // TO DO: exit the game
    }
  }

  public void setEncounterLogic(EncounterState encounterState) {
    this.encounterState = encounterState;
    currentLogic = Logic.ENCOUNTER_LOGIC;
  }

  public void setRaceSelectionLogic(Player playerBottom, Player playerTop) {
    this.playerBottom = playerBottom;
    this.playerTop = playerTop;
    currentLogic = Logic.RACE_SELECTION_LOGIC;
  }

  public void stopRunning() {
    run = false;
  }
}
