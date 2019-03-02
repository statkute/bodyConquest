package com.cauldron.bodyconquest.networking.utilities;

import com.cauldron.bodyconquest.constants.Ability;
import com.cauldron.bodyconquest.constants.Assets.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MessageMaker {

  public static String spawnTroopsMessage(UnitType troopClass, Lane lane, PlayerType playerType) {
    String message = "ACTION_T_";

    message += troopClass.encode();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.encoded();
    return (message);
  }

  public static String castAbilityMessage(Ability ability, int xAxis, int yAxis, PlayerType playerType) {
    String message = "ACTION_AA_";

    String xPadded = String.format("%04d", xAxis);
    String yPadded = String.format("%04d", yAxis);

//    if (ability.equals("fireball")) {
//      message += "F_";
//    } else {
//      message += "W_"; // water blast
//    }
//    if (xAxis < 10) {
//      message += ("0" + xAxis);
//    } else {
//      message += xAxis;
//    }
//    message += "_";
//    if (yAxis < 10) {
//      message += ("0" + yAxis);
//    } else {
//      message += yAxis;
//    }

    message += ability.getEncoded();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += xPadded;
    message += "_";
    message += yPadded;

    return (message);
  }

  public static String castAbilityMessage(Ability ability, Lane lane, PlayerType playerType) {
    String message = "ACTION_AL_";

    message += ability.getEncoded();
    message += "_";
    message += playerType.encoded();
    message += "_";
    message += lane.encoded();
    return (message);
  }


  public static String healthUpdate(int health, String position) {
    String message = "HEALTH_";
    message += position.toUpperCase().charAt(0);
    message += "_";
    NumberFormat formatter = new DecimalFormat("000");
    String formattedNumber = formatter.format(health);
    message += formattedNumber;

    return message;
  }

  public static String resourceUpdate(int lipids, int sugars, int proteins, String player){
    String message = "RESOURCES_";
    message += player.toUpperCase().charAt(0);
    message += "_";

    NumberFormat formatter = new DecimalFormat("000");
    String formattedLipids = formatter.format(lipids);
    message += formattedLipids;
    message += "_";

    String formattedSugars = formatter.format(sugars);
    message += formattedSugars;
    message += "_";

    String formattedProteins = formatter.format(proteins);
    message += formattedProteins;

    return message;

  }

  public static String raceMessage(String race) {
    String message = "RACE_";
    message += race;
    return message;
  }

  public static String pauseMessage() {
    return ("PAUSE");
  }

  public static String exitMessage() {
    return ("EXIT");
  }
}
