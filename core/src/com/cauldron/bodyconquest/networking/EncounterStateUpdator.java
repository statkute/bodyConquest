package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.game_logic.utils.Timer;
import com.cauldron.bodyconquest.networking.utilities.Serialization;
import com.cauldron.bodyconquest.screens.RaceSelection;

public class EncounterStateUpdator extends Thread {
  private ServerSender serverSender;
  public EncounterStateUpdator(ServerSender serverSender) {
    this.serverSender = serverSender;
  }

  public void run() {
    while (true) {
      // get the encounterState
      // String json = Serialization.serialize(encounterState);
      // Message message = "ENCOUNTERSTATE_UPDATE_"
      // message += json
      // serverSender.sendMessage(message)
      
      Timer.startTimer(600);
    }
  }
}
