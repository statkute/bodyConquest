package main.com.bodyconquest.game_logic;

import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.Resource;
import main.com.bodyconquest.entities.BasicObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Communicator.
 */
public class Communicator {

  /** Variables */
  private CopyOnWriteArrayList<BasicObject> objects;

  private String username;

  private int bottomHealthPercentage;
  private int topHealthPercentage;

  private boolean eyeSelected;
  private boolean lungsSelected;
  private boolean brainSelected;
  private boolean intestinesSelected;
  private boolean teethSelected;
  private boolean heartSelected;

  private int lipidsTop;
  private int sugarsTop;
  private int proteinsTop;
  private int lipidsBottom;
  private int sugarsBottom;

  private int proteinsBottom;

  private int scoreBottom;
  private int scoreTop;

  private boolean picker;
  private PlayerType playerType;
  private Disease playerDisease;
  private Disease opponentDisease;

  private ArrayList<Organ> playerOrgans;
  private ArrayList<Organ> opponentOrgans;
  private Organ currentOrgan;

  private boolean startBodyScreen;
  private boolean startEncounter;

  private HashMap<String, Integer> board;
  private AtomicBoolean boardIsSet = new AtomicBoolean(false);

  private AtomicBoolean logged = new AtomicBoolean(false);
  private AtomicBoolean loggedIsSet = new AtomicBoolean(false);

  private AtomicBoolean registered = new AtomicBoolean(false);
  private AtomicBoolean registeredIsSet = new AtomicBoolean(false);
  private String usernameBottom;
  private String usernameTop;

  /** Instantiates a new Communicator. */
  public Communicator() {
    objects = new CopyOnWriteArrayList<BasicObject>();
    playerDisease = null;
    opponentDisease = null;
    playerOrgans = new ArrayList<Organ>();
    opponentOrgans = new ArrayList<Organ>();
    startBodyScreen = false;
    startEncounter = false;
    heartSelected = false;
    eyeSelected = false;
    brainSelected = false;
    teethSelected = false;
    intestinesSelected = false;
    lungsSelected = false;
  }

  /**
   * Gets all objects.
   *
   * @return the all objects
   */
  public CopyOnWriteArrayList<BasicObject> getAllObjects() {
    return objects;
  }

  /**
   * Populate object list.
   *
   * @param os the os
   */
  public void populateObjectList(CopyOnWriteArrayList<BasicObject> os) {
    this.objects = os;
  }

  /**
   * Sets bottom health percentage.
   *
   * @param health the health
   */
  public void setBottomHealthPercentage(int health) {
    bottomHealthPercentage = health;
  }

  /**
   * Sets top health percentage.
   *
   * @param health the health
   */
  public void setTopHealthPercentage(int health) {
    topHealthPercentage = health;
  }

  /**
   * Sets lipids top for resources.
   *
   * @param lipidsTop the lipids top
   */
  public void setLipidsTop(int lipidsTop) {
    this.lipidsTop = lipidsTop;
  }

  /**
   * Sets sugars top for resources.
   *
   * @param sugarsTop the sugars top
   */
  public void setSugarsTop(int sugarsTop) {
    this.sugarsTop = sugarsTop;
  }

  /**
   * Sets proteins top for resources.
   *
   * @param proteinsTop the proteins top
   */
  public void setProteinsTop(int proteinsTop) {
    this.proteinsTop = proteinsTop;
  }

  /**
   * Sets lipids bottom for resources.
   *
   * @param lipidsBottom the lipids bottom
   */
  public void setLipidsBottom(int lipidsBottom) {
    this.lipidsBottom = lipidsBottom;
  }

  /**
   * Sets sugars bottom for resources.
   *
   * @param sugarsBottom the sugars bottom
   */
  public void setSugarsBottom(int sugarsBottom) {
    this.sugarsBottom = sugarsBottom;
  }

  /**
   * Sets proteins bottom for resources.
   *
   * @param proteinsBottom the proteins bottom
   */
  public void setProteinsBottom(int proteinsBottom) {
    this.proteinsBottom = proteinsBottom;
  }

  /**
   * Gets bottom health percentage for the base.
   *
   * @return the bottom health percentage
   */
  public int getBottomHealthPercentage() {
    return bottomHealthPercentage;
  }

  /**
   * Gets top health percentage for the base.
   *
   * @return the top health percentage
   */
  public int getTopHealthPercentage() {
    return topHealthPercentage;
  }

  /**
   * Gets lipids top.
   *
   * @return the lipids top for resources
   */
  public int getLipidsTop() {
    return lipidsTop;
  }

  /**
   * Gets sugars top for resources.
   *
   * @return the sugars top
   */
  public int getSugarsTop() {
    return sugarsTop;
  }

  /**
   * Gets proteins top for resources.
   *
   * @return the proteins top
   */
  public int getProteinsTop() {
    return proteinsTop;
  }

  /**
   * Gets lipids bottom for resources.
   *
   * @return the lipids bottom
   */
  public int getLipidsBottom() {
    return lipidsBottom;
  }

  /**
   * Gets sugars bottom for resources.
   *
   * @return the sugars bottom
   */
  public int getSugarsBottom() {
    return sugarsBottom;
  }

  /**
   * Gets proteins bottom for resources.
   *
   * @return the proteins bottom
   */
  public int getProteinsBottom() {
    return proteinsBottom;
  }

  /**
   * Sets player disease.
   *
   * @param playerDisease the player disease
   */
  public void setPlayerDisease(Disease playerDisease) {
    this.playerDisease = playerDisease;
  }

  /**
   * Gets player disease.
   *
   * @return the player disease
   */
  public Disease getPlayerDisease() {
    return playerDisease;
  }

  /**
   * Gets player type.
   *
   * @return the player type
   */
  public PlayerType getPlayerType() {
    return playerType;
  }

  /**
   * Sets player type.
   *
   * @param playerType the player type
   */
  public void setPlayerType(PlayerType playerType) {
    this.playerType = playerType;
  }

  /**
   * Gets opponent disease.
   *
   * @return the opponent disease
   */
  public Disease getOpponentDisease() {
    return opponentDisease;
  }

  /**
   * Sets opponent disease.
   *
   * @param opponentDisease the opponent disease
   */
  public void setOpponentDisease(Disease opponentDisease) {
    this.opponentDisease = opponentDisease;
  }

  /**
   * Is picker boolean.
   *
   * @return the boolean
   */
  public boolean isPicker() {
    return picker;
  }

  /**
   * Sets picker.
   *
   * @param picker the picker
   */
  public void setPicker(boolean picker) {
    this.picker = picker;
  }

  /**
   * Gets score bottom.
   *
   * @return the score bottom
   */
  public int getScoreBottom() {
    return scoreBottom;
  }

  /**
   * Sets score bottom.
   *
   * @param scoreBottom the score bottom
   */
  public void setScoreBottom(int scoreBottom) {
    this.scoreBottom = scoreBottom;
  }

  /**
   * Gets score top.
   *
   * @return the score top
   */
  public int getScoreTop() {
    return scoreTop;
  }

  /**
   * Sets score top.
   *
   * @param scoreTop the score top
   */
  public void setScoreTop(int scoreTop) {
    this.scoreTop = scoreTop;
  }

  /**
   * Gets player organs.
   *
   * @return the player organs
   */
  public ArrayList<Organ> getPlayerOrgans() {
    return playerOrgans;
  }

  /**
   * Sets player organs.
   *
   * @param playerOrgans the player organs
   */
  public void setPlayerOrgans(ArrayList<Organ> playerOrgans) {
    this.playerOrgans = playerOrgans;
  }

  /**
   * Gets opponent organs.
   *
   * @return the opponent organs
   */
  public ArrayList<Organ> getOpponentOrgans() {
    return opponentOrgans;
  }

  /**
   * Sets opponent organs.
   *
   * @param opponentOrgans the opponent organs
   */
  public void setOpponentOrgans(ArrayList<Organ> opponentOrgans) {
    this.opponentOrgans = opponentOrgans;
  }

  /**
   * Sets start body screen.
   *
   * @param start the start
   */
  public void setStartBodyScreen(boolean start) {
    startBodyScreen = start;
  }

  /**
   * Gets if body screen has started.
   *
   * @return the start body screen
   */
  public boolean getStartBodyScreen() {
    return startBodyScreen;
  }

  /**
   * Gets if encounter screen has started.
   *
   * @return the start encounter
   */
  public boolean getStartEncounter() {
    return startEncounter;
  }

  /**
   * Sets start encounter.
   *
   * @param start the start
   */
  public void setStartEncounter(boolean start) {
    this.startEncounter = start;
  }

  /**
   * Gets current organ.
   *
   * @return the current organ
   */
  public Organ getCurrentOrgan() {
    return currentOrgan;
  }

  /**
   * Sets current organ.
   *
   * @param currentOrgan the current organ
   */
  public void setCurrentOrgan(Organ currentOrgan) {
    this.currentOrgan = currentOrgan;
  }

  /**
   * Gets resource.
   *
   * @param resource   the resource
   * @param playerType the player type
   * @return the resource
   */
  public int getResource(Resource resource, PlayerType playerType) {
    if (resource == Resource.SUGAR) {
      if (playerType == PlayerType.PLAYER_BOTTOM) {
        return sugarsBottom;
      } else {
        return sugarsTop;
      }
    } else if (resource == Resource.LIPID) {
      if (playerType == PlayerType.PLAYER_BOTTOM) {
        return lipidsBottom;
      } else {
        return lipidsTop;
      }
    } else {
      if (playerType == PlayerType.PLAYER_BOTTOM) {
        return proteinsBottom;
      } else {
        return proteinsTop;
      }
    }
  }

  public HashMap<String, Integer> getBoard() {
    return board;
  }

  public void setBoard(HashMap<String, Integer> board) {
    this.board = board;
  }

  public AtomicBoolean getBoardIsSet() {
    return boardIsSet;
  }

  public void setBoardIsSet(boolean boardIsSet) {
    this.boardIsSet.set(boardIsSet);
  }

  public AtomicBoolean getLogged() {
    return logged;
  }

  public void setLogged(boolean logged) {
    this.logged.set(logged);
  }

  public AtomicBoolean getLoggedIsSet() {
    return loggedIsSet;
  }

  public void setLoggedIsSet(boolean loggedIsSet) {
    this.loggedIsSet.set(loggedIsSet);
  }

  public AtomicBoolean getRegistered() {
    return registered;
  }

  public void setRegistered(boolean registered) {
    this.registered.set(registered);
  }

  public AtomicBoolean getRegisteredIsSet() {
    return registeredIsSet;
  }

  public void setRegisteredIsSet(boolean registeredIsSet) {
    this.registeredIsSet.set(registeredIsSet);
  }

  public String getUsername(PlayerType player) {
    if(player == PlayerType.PLAYER_BOTTOM) {
      return usernameBottom;
    } else {
      return usernameTop;
    }

  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * was eye selected boolean.
   *
   * @return the boolean
   */
  public boolean wasEyeSelected() {
    return eyeSelected;
  }

  /**
   * was lungs selected boolean.
   *
   * @return the boolean
   */
  public boolean wasLungsSelected() {
    return lungsSelected;
  }

  /**
   * was brain selected boolean.
   *
   * @return the boolean
   */
  public boolean wasBrainSelected() {
    return brainSelected;
  }

  /**
   * was intestines selected boolean.
   *
   * @return the boolean
   */
  public boolean wasIntestinesSelected() {
    return intestinesSelected;
  }

  /**
   * was teeth selected boolean.
   *
   * @return the boolean
   */
  public boolean wasTeethSelected() {
    return teethSelected;
  }

  /**
   * was heart selected boolean.
   *
   * @return the boolean
   */
  public boolean wasHeartSelected() {
    return heartSelected;
  }

  public void addOponentOrgan(Organ organ){
    opponentOrgans.add(organ);
  }

  public void addOrgan(Organ organ){
    playerOrgans.add(organ);
  }

  public void setUsername(PlayerType playerType, String username){
    if(playerType == PlayerType.PLAYER_TOP){
      usernameTop = username;
    }
    else if(playerType == PlayerType.PLAYER_BOTTOM){
      usernameBottom = username;
    }
  }

}
