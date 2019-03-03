package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.utilities.MessageMaker;
import com.cauldron.bodyconquest.networking.utilities.Serialization;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientLogic extends Thread {

  private enum Logic {
    RACE_SELECTION_LOGIC,
    ENCOUNTER_LOGIC
  }

  private Logic currentLogic;

  private ClientReceiver clientReceiver;
  private Communicator communicator;
  private boolean run;

  public ClientLogic(ClientReceiver clientReceiver, Communicator communicator) {
    this.clientReceiver = clientReceiver;
    this.communicator = communicator;
    this.run = true;
    currentLogic = null;
  }
  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (run) {
      try {
        String message = clientReceiver.receivedMessages.take();

        if (currentLogic == null) {
          System.err.println("[ERROR] No client logic has been set.");
          continue;
        }

        if (currentLogic == Logic.ENCOUNTER_LOGIC) encounterLogic(message);
        if (currentLogic == Logic.RACE_SELECTION_LOGIC) raceSelectionLogic(message);

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void raceSelectionLogic(String message) {
    int pointer;
    if (message.startsWith(MessageMaker.RACE_HEADER)) {
      Disease disease;
      Assets.PlayerType player;

      pointer = MessageMaker.RACE_HEADER.length();

      String encodedDisease = message.substring(pointer, pointer + Disease.getEncodedLength());
      disease = Disease.decode(encodedDisease);
      pointer += Disease.getEncodedLength() + 1;

      String encodedPlayerType =
          message.substring(pointer, pointer + Assets.PlayerType.getEncodedLength());
      player = Assets.PlayerType.decode(encodedPlayerType);

      if (communicator.getPlayerType() != player) communicator.setEnemyDisease(disease);
    } else if (message.startsWith(MessageMaker.FIRST_PICKER_HEADER)) {
      Assets.PlayerType firstPicker;

      pointer = MessageMaker.FIRST_PICKER_HEADER.length();

      String encodedPlayerType =
          message.substring(pointer, pointer + Assets.PlayerType.getEncodedLength());
      firstPicker = Assets.PlayerType.decode(encodedPlayerType);

      communicator.setPicker(firstPicker == communicator.getPlayerType());

    } else if (message.startsWith(MessageMaker.CHOOSE_RACE_HEADER)) {
      Assets.PlayerType player;

      pointer = MessageMaker.CHOOSE_RACE_HEADER.length();

      String encodedPlayerType =
          message.substring(pointer, pointer + Assets.PlayerType.getEncodedLength());
      player = Assets.PlayerType.decode(encodedPlayerType);

      if (player == communicator.getPlayerType()) communicator.setPicker(true);
    }
  }

  private void encounterLogic(String message) throws IOException {
    int pointer;
    if (message.startsWith(MessageMaker.OBJECT_UPDATE_HEADER)) {
      pointer = MessageMaker.OBJECT_UPDATE_HEADER.length();
      String json = message.substring(pointer);
      CopyOnWriteArrayList<BasicObject> objects = Serialization.deserialize(json);
      communicator.populateObjectList(objects);

    } else if (message.startsWith(MessageMaker.HEALTH_HEADER)) {
      Assets.PlayerType player;
      int health;

      pointer = MessageMaker.HEALTH_HEADER.length();

      String encodedPlayerType =
          message.substring(pointer, pointer + Assets.PlayerType.getEncodedLength());
      player = Assets.PlayerType.decode(encodedPlayerType);
      pointer += Assets.PlayerType.getEncodedLength() + 1;

      String healthString = message.substring(pointer);
      health = Integer.parseInt(healthString);

      if (player == Assets.PlayerType.PLAYER_BOTTOM) {
        communicator.setBottomHealthPercentage(health);
      } else if (player == Assets.PlayerType.PLAYER_TOP || player == Assets.PlayerType.AI) {
        communicator.setTopHealthPercentage(health);
      }
    } else if (message.startsWith(MessageMaker.RESOURCES_HEADER)) {
      System.out.println("THE MESSAGE: " + message);

      Assets.PlayerType player;
      int lipids;
      int sugars;
      int proteins;

      pointer = MessageMaker.RESOURCES_HEADER.length();

      String encodedPlayerType =
          message.substring(pointer, pointer + Assets.PlayerType.getEncodedLength());
      player = Assets.PlayerType.decode(encodedPlayerType);
      pointer += Assets.PlayerType.getEncodedLength() + 1;

      String lipidsString = message.substring(pointer, pointer + MessageMaker.RESOURCE_PADDING);
      lipids = Integer.parseInt(lipidsString);
      pointer += MessageMaker.RESOURCE_PADDING + 1;

      String sugarsString = message.substring(pointer, pointer + MessageMaker.RESOURCE_PADDING);
      sugars = Integer.parseInt(sugarsString);
      pointer += MessageMaker.RESOURCE_PADDING + 1;

      String proteinsString = message.substring(pointer, pointer + MessageMaker.RESOURCE_PADDING);
      proteins = Integer.parseInt(proteinsString);

      // Lots of this doesn't make sense, the client doesn't need to know their opponents resources
      // As it doesn't need to print it or make any decisions based on it
      // Also I dont know if both players are sent both resources? If so they shouldn't
      if (player == Assets.PlayerType.PLAYER_BOTTOM) {
        communicator.setLipidsBottom(lipids);
        communicator.setSugarsBottom(sugars);
        communicator.setProteinsBottom(proteins);
      } else {
        communicator.setLipidsTop(lipids);
        communicator.setSugarsTop(sugars);
        communicator.setProteinsTop(proteins);
      }
    }
  }

  public void setRaceSelectionLogic() {
    currentLogic = Logic.RACE_SELECTION_LOGIC;
  }

  public void setEncounterLogic() {
    currentLogic = Logic.ENCOUNTER_LOGIC;
  }

  public void stopRunning() {
    run = false;
  }
}