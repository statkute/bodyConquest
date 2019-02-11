package com.cauldron.bodyconquest.networking;

public class MessageMaker {
  public String spawnTroopsMessage(String troopClass, int lane){
    String message = "ACTION_T_";

    if (troopClass.equals("bacteria")){
      message += "B_";
    } else {
      message += "F_"; //flu
    }
    message += lane;
    return (message);
  }

  public String castAbilityMessage(String abilityName, int xAxis, int yAxis){
    String message = "ACTION_A_";

    if (abilityName.equals("fireball")){
      message += "F_";
    } else {
      message += "W_"; //water blast
    }
    message += (xAxis + "_" + yAxis);
    return (message);
  }

  public String pauseMessage(){
    return("PAUSE");
  }

  public String exitMessage(){
    return("EXIT");
  }


}
