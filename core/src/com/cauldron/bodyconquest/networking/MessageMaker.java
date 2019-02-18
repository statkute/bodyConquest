package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.Lane;
import com.cauldron.bodyconquest.constants.PlayerType;
import com.cauldron.bodyconquest.constants.UnitType;

public class MessageMaker {
  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType){
    String message = "ACTION_T_";

    message += troopClass.encode();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.encoded();
    return (message);
  }

  public static String castAbilityMessage(String abilityName, int xAxis, int yAxis){
    String message = "ACTION_A_";

    if (abilityName.equals("fireball")){
      message += "F_";
    } else {
      message += "W_"; //water blast
    }
    if (xAxis < 10){
      message += ("0" + xAxis);
    }else {
      message += xAxis;
    }
    message += "_";
    if (yAxis < 10){
      message += ("0" + yAxis);
    }else {
      message += yAxis;
    }

    return (message);
  }

  public static String pauseMessage(){
    return("PAUSE");
  }

  public static String exitMessage(){
    return("EXIT");
  }


}
