package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.Organ;
import com.cauldron.bodyconquest.entities.BasicObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Communicator {

  private CopyOnWriteArrayList<BasicObject> objects;
  private int bottomHealthPercentage;
  private int topHealthPercentage;
  private int lipidsTop;
  private int sugarsTop;
  private int proteinsTop;
  private int lipidsBottom;
  private int sugarsBottom;
  private int proteinsBottom;

  private boolean picker;

  private Assets.PlayerType playerType;

  private Disease playerDisease;
  private Disease opponentDisease;

  private ArrayList<Organ> playerOrgans;
  private ArrayList<Organ> opponentOrgans;

  private Organ currentOrgan;

  private boolean startBodyScreen;
  private boolean startEncounter;

  public Communicator() {
    objects = new CopyOnWriteArrayList<BasicObject>();
    playerDisease = null;
    opponentDisease = null;
    playerOrgans = new ArrayList<Organ>();
    opponentOrgans = new ArrayList<Organ>();
    startBodyScreen = false;
    startEncounter = false;
  }

  public CopyOnWriteArrayList<BasicObject> getAllObjects() {
    return objects;
  }

  public void populateObjectList(CopyOnWriteArrayList<BasicObject> os) {
    this.objects = os;
  }

  public void setBottomHealthPercentage(int health) {
    bottomHealthPercentage = health;
  }

  public void setTopHealthPercentage(int health) {
    topHealthPercentage = health;
  }

  public void setLipidsTop(int lipidsTop) {
    this.lipidsTop = lipidsTop;
  }

  public void setSugarsTop(int sugarsTop) {
    this.sugarsTop = sugarsTop;
  }

  public void setProteinsTop(int proteinsTop) {
    this.proteinsTop = proteinsTop;
  }

  public void setLipidsBottom(int lipidsBottom) {
    this.lipidsBottom = lipidsBottom;
  }

  public void setSugarsBottom(int sugarsBottom) {
    this.sugarsBottom = sugarsBottom;
  }

  public void setProteinsBottom(int proteinsBottom) {
    this.proteinsBottom = proteinsBottom;
  }

  public int getBottomHealthPercentage() {
    return bottomHealthPercentage;
  }

  public int getTopHealthPercentage() {
    return topHealthPercentage;
  }

  public int getLipidsTop() {
    return lipidsTop;
  }

  public int getSugarsTop() {
    return sugarsTop;
  }

  public int getProteinsTop() {
    return proteinsTop;
  }

  public int getLipidsBottom() {
    return lipidsBottom;
  }

  public int getSugarsBottom() {
    return sugarsBottom;
  }

  public int getProteinsBottom() {
    return proteinsBottom;
  }

  public void setPlayerDisease(Disease playerDisease) {
    this.playerDisease = playerDisease;
  }

  public Disease getPlayerDisease() {
    return playerDisease;
  }

  public Assets.PlayerType getPlayerType() {
    return playerType;
  }

  public void setPlayerType(Assets.PlayerType playerType) {
    this.playerType = playerType;
  }

  public Disease getOpponentDisease() {
    return opponentDisease;
  }

  public void setOpponentDisease(Disease opponentDisease) {
    this.opponentDisease = opponentDisease;
  }

  public boolean isPicker() {
    return picker;
  }

  public void setPicker(boolean picker) {
    this.picker = picker;
  }

  public ArrayList<Organ> getPlayerOrgans() {
    return playerOrgans;
  }

  public void setPlayerOrgans(ArrayList<Organ> playerOrgans) {
    this.playerOrgans = playerOrgans;
  }

  public ArrayList<Organ> getOpponentOrgans() {
    return opponentOrgans;
  }

  public void setOpponentOrgans(ArrayList<Organ> opponentOrgans) {
    this.opponentOrgans = opponentOrgans;
  }

  public void setStartBodyScreen(boolean start) {
    startBodyScreen = start;
  }

  public boolean getStartBodyScreen() {
    return startBodyScreen;
  }

  public boolean getStartEncounter() {
    return startEncounter;
  }

  public void setStartEncounter(boolean start) {
    this.startEncounter = start;
  }

  public Organ getCurrentOrgan() {
    return currentOrgan;
  }

  public void setCurrentOrgan(Organ currentOrgan) {
    this.currentOrgan = currentOrgan;
  }
}
