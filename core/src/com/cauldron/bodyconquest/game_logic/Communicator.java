package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.entities.BasicObject;

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
  private Disease enemyDisease;

  public Communicator() {
    objects = new CopyOnWriteArrayList<BasicObject>();
    playerDisease = null;
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

  public Disease getEnemyDisease() {
    return enemyDisease;
  }

  public void setEnemyDisease(Disease enemyDisease) {
    this.enemyDisease = enemyDisease;
  }

  public boolean isPicker() {
    return picker;
  }

  public void setPicker(boolean picker) {
    this.picker = picker;
  }
}
