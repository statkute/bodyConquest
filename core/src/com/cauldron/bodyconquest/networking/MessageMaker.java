package com.cauldron.bodyconquest.networking;

public class MessageMaker {
  public static String spawnTroopsMessage(String troopClass, int lane){
    String message = "ACTION_T_";

    if (troopClass.equals("bacteria")){
      message += "B_";
    } else {
      message += "F_"; //flu
    }
    message += lane;
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
