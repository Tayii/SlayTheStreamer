package chronometry.monsters.city;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.Champ;

import java.util.ArrayList;

public class ChampPatch {
    public static byte CALM_UNFORGED = 0;
    public static byte CALM_FORGED = 1;
    public static byte GETTING_ANGRY = 2;
    public static byte ANGRY = 3;
    
    @SpirePatch(clz=Champ.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(Champ __instance) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "HEAVY_SLASH",
                    Intent.ATTACK
            );
            move1.add_phase(CALM_UNFORGED);
            move1.add_phase(CALM_FORGED);
            move1.add_phase(ANGRY);
            move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "DEFENSIVE_STANCE",
                    Intent.DEFEND_BUFF,
                    (String)ReflectionHacks.getPrivateStatic(__instance.getClass(), "STANCE_NAME"),
                    2
                    );
            move2.add_phase(CALM_UNFORGED);
            move2.add_effect(new DefendEffect(
                    (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "blockAmt")
            ));
            move2.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1",
                    (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "forgeAmt")
            ));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "EXECUTE",
                    Intent.ATTACK,
                    (String)ReflectionHacks.getPrivateStatic(__instance.getClass(), "EXECUTE_NAME"),
                    2
                    );
            move3.add_phase(ANGRY);
            move3.add_effect(new AttackEffect(__instance.damage.get(1), 2));
            moves.add(move3);

            IntentData move4 = new IntentData(
                    __instance.getClass(),
                    "FACE_SLAP",
                    Intent.ATTACK_DEBUFF,
                    (String)ReflectionHacks.getPrivateStatic(__instance.getClass(), "SLAP_NAME"),
                    2
                    );
            move4.add_phase(CALM_UNFORGED);
            move4.add_phase(CALM_FORGED);
            move4.add_phase(ANGRY);
            move4.add_effect(new AttackEffect(__instance.damage.get(2)));
            move4.add_effect(new DebuffFrailEffect(2));
            move4.add_effect(new DebuffVulnerableEffect(2));
            moves.add(move4);

            IntentData move5 = new IntentData(
                    __instance.getClass(),
                    "GLOAT",
                    Intent.BUFF,
                    2
                    );
            move5.add_phase(CALM_UNFORGED);
            move5.add_phase(CALM_FORGED);
            move5.add_phase(ANGRY);
            move5.add_effect(new BuffStrengthEffect(
                    (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "strAmt")
            ));
            moves.add(move5);

            IntentData move6 = new IntentData(
                    __instance.getClass(),
                    "TAUNT",
                    Intent.DEBUFF,
                    2
                    );
            move6.add_phase(CALM_UNFORGED);
            move6.add_phase(CALM_FORGED);
            move6.add_effect(new DebuffWeakEffect(2));
            move6.add_effect(new DebuffVulnerableEffect(2));
            moves.add(move6);

            IntentData move7 = new IntentData(
                    __instance.getClass(),
                    "ANGER",
                    Intent.BUFF
                    );
            move7.add_phase(GETTING_ANGRY);
            moves.add(move7);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.turn_start_func.set(__instance,
                        ChampPatch.class.getMethod("turnStart", Champ.class));
            }
            catch (NoSuchMethodException exc) { }
            try {
                AbstractMonsterPatch.after_action_func.set(__instance,
                        ChampPatch.class.getMethod("afterAction", Champ.class, IntentData.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void turnStart(Champ monster) {
        boolean thresholdReached = (boolean)ReflectionHacks.getPrivate(monster, monster.getClass(), "thresholdReached");
        byte currentPhase = AbstractMonsterPatch.current_phase.get(monster);
        if (thresholdReached && (currentPhase == CALM_UNFORGED || currentPhase == CALM_FORGED)) {
            AbstractMonsterPatch.current_phase.set(monster, GETTING_ANGRY);
        }
    }

    public static void afterAction(Champ monster, IntentData intent) {
        if (intent.equals(monster.getClass(), "DEFENSIVE_STANCE")) {
            int forgeTimes = (int)ReflectionHacks.getPrivate(monster, monster.getClass(), "forgeTimes");
            int forgeThreshold = (int)ReflectionHacks.getPrivate(monster, monster.getClass(), "forgeThreshold");
            if (forgeTimes >= forgeThreshold) {
                AbstractMonsterPatch.current_phase.set(monster, CALM_FORGED);
            }
        }
        else if (intent.equals(monster.getClass(), "ANGER")) {
            AbstractMonsterPatch.current_phase.set(monster, ANGRY);
        }
    }
}
