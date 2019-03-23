package main.com.bodyconquest.networking.utilities;

import main.com.bodyconquest.constants.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageMakerTest {

  @Test
  public void spawnTroopsMessage() {
    String target1 = MessageMaker.spawnTroopsMessage(UnitType.BACTERIA, Lane.MIDDLE, PlayerType.PLAYER_TOP);
    assertEquals("ACTION_T_BAC_PT_M", target1);

    String target2 = MessageMaker.spawnTroopsMessage(UnitType.FUNGUS, Lane.TOP, PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_FNG_PB_T", target2);

    String target3 = MessageMaker.spawnTroopsMessage(UnitType.BACTERIA, Lane.MIDDLE, PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_BAC_PB_M", target3);

    String target4 = MessageMaker.spawnTroopsMessage(UnitType.FUNGUS, Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_T_FNG_PB_B", target4);

    String target5 = MessageMaker.spawnTroopsMessage(UnitType.VIRUS, Lane.BOTTOM, PlayerType.PLAYER_TOP);
    assertEquals("ACTION_T_VIR_PT_B", target5);
  }

  @Test
  public void castAbilityMessage() {
    String target1 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Lane.MIDDLE, PlayerType.PLAYER_TOP);
    assertEquals("ACTION_AL_RGM_PT_M", target1);

    String target2 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Lane.TOP, PlayerType.PLAYER_TOP);
    assertEquals("ACTION_AL_RGM_PT_T", target2);

    String target3 = MessageMaker.castAbilityMessage(AbilityType.RIGOR_MORTIS, Lane.TOP, PlayerType.PLAYER_BOTTOM);
    assertEquals("ACTION_AL_RGM_PB_T", target3);
  }

  @Test
  public void healthUpdate() {
    String target1 = MessageMaker.healthUpdate(99, PlayerType.PLAYER_TOP);
    assertEquals("HEALTH_PT_099", target1);

    String target2 = MessageMaker.healthUpdate(100, PlayerType.PLAYER_TOP);
    assertEquals("HEALTH_PT_100", target2);

    String target3 = MessageMaker.healthUpdate(7, PlayerType.PLAYER_BOTTOM);
    assertEquals("HEALTH_PB_007", target3);

    String target4 = MessageMaker.healthUpdate(0, PlayerType.PLAYER_BOTTOM);
    assertEquals("HEALTH_PB_000", target4);
  }

  @Test
  public void resourceUpdate() {
    String target1 = MessageMaker.resourceUpdate(100, 100, 100, PlayerType.PLAYER_BOTTOM);
    assertEquals("RESOURCES_PB_100_100_100", target1);

    String target2 = MessageMaker.resourceUpdate(0, 3, 19, PlayerType.PLAYER_TOP);
    assertEquals("RESOURCES_PT_000_003_019", target2);

    String target3 = MessageMaker.resourceUpdate(30, 87, 0, PlayerType.PLAYER_TOP);
    assertEquals("RESOURCES_PT_030_087_000", target3);
  }

  @Test
  public void diseaseMessage() {
    String target1 = MessageMaker.diseaseMessage(Disease.INFLUENZA, PlayerType.PLAYER_TOP);
    assertEquals("RACE_FLU_PT", target1);

    String target2 = MessageMaker.diseaseMessage(Disease.ROTAVIRUS, PlayerType.PLAYER_BOTTOM);
    assertEquals("RACE_RVI_PB", target2);

    String target3 = MessageMaker.diseaseMessage(Disease.MEASLES, PlayerType.PLAYER_BOTTOM);
    assertEquals("RACE_MES_PB", target3);

    String target4 = MessageMaker.diseaseMessage(Disease.ROTAVIRUS, PlayerType.PLAYER_TOP);
    assertEquals("RACE_RVI_PT", target4);
  }

  @Test
  public void confirmRaceMessage() {
    String target1 = MessageMaker.confirmRaceMessage(PlayerType.PLAYER_TOP);
    assertEquals("CONFIRM_RACE_PT", target1);

    String target2 = MessageMaker.confirmRaceMessage(PlayerType.PLAYER_BOTTOM);
    assertEquals("CONFIRM_RACE_PB", target2);
  }

  @Test
  public void confirmOrganMessage() {
    String target1 = MessageMaker.confirmOrganMessage(Organ.BRAIN);
    assertEquals("CONFIRM_ORGAN_BRA", target1);

    String target2 = MessageMaker.confirmOrganMessage(Organ.EYES);
    assertEquals("CONFIRM_ORGAN_EYE", target2);

    String target3 = MessageMaker.confirmOrganMessage(Organ.HEART);
    assertEquals("CONFIRM_ORGAN_HRT", target3);

    String target4 = MessageMaker.confirmOrganMessage(Organ.LUNGS);
    assertEquals("CONFIRM_ORGAN_LNG", target4);

    String target5 = MessageMaker.confirmOrganMessage(Organ.TEETH);
    assertEquals("CONFIRM_ORGAN_TEE", target5);

    String target6 = MessageMaker.confirmOrganMessage(Organ.INTESTINES);
    assertEquals("CONFIRM_ORGAN_INT", target6);
  }

  @Test
  public void startEncounterMessage() {
    String target1 = MessageMaker.startEncounterMessage(Organ.BRAIN);
    assertEquals("START_ENCOUNTER_BRA", target1);

    String target2 = MessageMaker.startEncounterMessage(Organ.EYES);
    assertEquals("START_ENCOUNTER_EYE", target2);

    String target3 = MessageMaker.startEncounterMessage(Organ.HEART);
    assertEquals("START_ENCOUNTER_HRT", target3);

    String target4 = MessageMaker.startEncounterMessage(Organ.LUNGS);
    assertEquals("START_ENCOUNTER_LNG", target4);

    String target5 = MessageMaker.startEncounterMessage(Organ.TEETH);
    assertEquals("START_ENCOUNTER_TEE", target5);

    String target6 = MessageMaker.startEncounterMessage(Organ.INTESTINES);
    assertEquals("START_ENCOUNTER_INT", target6);
  }
}