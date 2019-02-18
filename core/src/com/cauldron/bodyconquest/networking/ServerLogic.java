package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Constants.*;
import com.cauldron.bodyconquest.gamestates.EncounterState;

/** Server Thread responsible for dealing with game logic based on incoming messages */
public class ServerLogic extends Thread {
  public ServerReceiver serverReceiver;
  private EncounterState encounterState;

  /**
   * ServerLogic initialisation
   *
   * @param serverReceiver the ServerReceiver thread of the same Server
   */
  public ServerLogic(ServerReceiver serverReceiver, EncounterState encounterState) {
    this.serverReceiver = serverReceiver;
    this.encounterState = encounterState;
  }

  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (true) {
      try {
        String message = serverReceiver.receivedMessages.take();
        char clientID = message.charAt(0);
        String command = message.substring(1);
        if (command.startsWith("ACTION")){
          if (command.startsWith("ACTION_T")){
            System.out.println(command.substring(9, 12));
            UnitType unit = UnitType.decode(command.substring(9, 12));
            System.out.println(command.substring(13, 15));
            PlayerType playerType = PlayerType.decode(command.substring(13, 15));
            System.out.println(command.substring(command.length() - 1));
            Lane lane = Lane.decode(command.substring(command.length() - 1));
            encounterState.spawnUnit(unit, lane, playerType);
          } else if (command.startsWith("ACTION_A")){
            int x_Axis = Integer.parseInt(command.substring(11, 13));
            int y_Axis = Integer.parseInt(command.substring(command.length() - 2));
            if (command.charAt(9) == 'F'){
              //TO DO: cast fireball spell from clientID at location x y
            } else if (command.charAt(9) == 'W'){
              //TO DO: cast water blast spell from clientID at location x y
            }
          }
        } else if (command.equals("PAUSE")){
          // TO DO: pause the game
        } else if (command.equals("EXIT")){
          //TO DO: exit the game
        } else if (command.startsWith("RACE_")){
          String race = command.substring(5);
          //TO DO: set a race for the user
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
