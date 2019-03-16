package main.com.bodyconquest.networking.utilities;

import main.com.bodyconquest.constants.AbilityType;
import main.com.bodyconquest.constants.Assets;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageMakerTest {

  @Test
  public void spawnTroopsMessage() {
    String target1 = MessageMaker.spawnTroopsMessage(Assets.UnitType.BACTERIA, Assets.Lane.MIDDLE, Assets.PlayerType.PLAYER_TOP);
    assertEquals("ACTION_T_BAC_PT_M", target1);

    String target2 = MessageMaker.spawnTroopsMessage(Assets.UnitType.VIRUS, Assets.Lane.TOP, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_VIR_PB_T", target2);

    String target3 = MessageMaker.spawnTroopsMessage(Assets.UnitType.BACTERIA, Assets.Lane.MIDDLE, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_BAC_PB_M", target3);

    String target4 = MessageMaker.spawnTroopsMessage(Assets.UnitType.VIRUS, Assets.Lane.BOTTOM, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_VIR_PB_B", target4);

    String target5 = MessageMaker.spawnTroopsMessage(Assets.UnitType.FLU, Assets.Lane.BOTTOM, Assets.PlayerType.PLAYER_TOP);
    assertEquals("ACTION_T_FLU_PT_B", target5);
  }

  @Test
  public void castAbilityMessage() {
    String target1 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Assets.Lane.MIDDLE, Assets.PlayerType.PLAYER_TOP);
    assertEquals("ACTION_AL_RGM_PT_M", target1);

    String target2 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Assets.Lane.TOP, Assets.PlayerType.PLAYER_TOP);
    assertEquals("ACTION_AL_RGM_PT_T", target2);

    String target3 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Assets.Lane.TOP, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_AL_RGM_PB_T", target3);
  }

  @Test
  public void healthUpdate() {
    String target1 = MessageMaker.healthUpdate(99, Assets.PlayerType.PLAYER_TOP);
    assertEquals("HEALTH_PT_099", target1);

    String target2 = MessageMaker.healthUpdate(100, Assets.PlayerType.PLAYER_TOP);
    assertEquals("HEALTH_PT_100", target2);

    String target3 = MessageMaker.healthUpdate(7, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("HEALTH_PB_007", target3);

    String target4 = MessageMaker.healthUpdate(0, Assets.PlayerType.PLAYER_BOTTOM);
    assertEquals("HEALTH_PB_000", target4);
  }

  @Test
  public void resourceUpdate() {}

  @Test
  public void diseaseMessage() {}

  @Test
  public void confirmRaceMessage() {}

  @Test
  public void confirmOrganMessage() {}

  @Test
  public void startEncounterMessage() {}
}