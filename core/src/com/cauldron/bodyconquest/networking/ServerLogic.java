package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Constants.*;
import com.cauldron.bodyconquest.gamestates.EncounterState;

/** Server Thread responsible for dealing with game logic based on incoming messages */
public class ServerLogic extends Thread {
  public ServerReceiver serverReceiver;
  private EncounterState encounterState;
  private boolean run;

  /**
   * ServerLogic initialisation
   *
   * @param serverReceiver the ServerReceiver thread of the same Server
   */
  public ServerLogic(ServerReceiver serverReceiver, EncounterState encounterState) {
    this.serverReceiver = serverReceiver;
    this.encounterState = encounterState;
    this.run = true;
  }

  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (run) {
      try {
        String message = serverReceiver.receivedMessages.take();
        if (message.startsWith("ACTION")){
            if (message.startsWith("ACTION_T")){
            UnitType unit = UnitType.decode(message.substring(9, 12));
            PlayerType playerType = PlayerType.decode(message.substring(13, 15));
            Lane lane = Lane.decode(message.substring(message.length() - 1));
            System.out.println("TRYING TO SPAWN NOW");

            encounterState.spawnUnit(unit, lane, playerType);
              System.out.println("FINISHED SPAWNING");
          } else if (message.startsWith("ACTION_A")){
            int x_Axis = Integer.parseInt(message.substring(11, 13));
            int y_Axis = Integer.parseInt(message.substring(message.length() - 2));
            if (message.charAt(9) == 'F'){
              //TO DO: cast fireball spell from clientID at location x y
            } else if (message.charAt(9) == 'W'){
              //TO DO: cast water blast spell from clientID at location x y
            }
          }
        } else if (message.equals("PAUSE")){
          // TO DO: pause the game
        } else if (message.equals("EXIT")){
          //TO DO: exit the game
        } else if (message.startsWith("RACE_")){
          String race = message.substring(5);
          //TO DO: set a race for the user
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  public void stopRunning(){
    run = false;
  }
}
