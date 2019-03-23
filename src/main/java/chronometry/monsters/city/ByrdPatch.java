package chronometry.monsters.city;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.BuffStrengthEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.Byrd;

import java.util.ArrayList;

public class ByrdPatch {
    public static byte FLIGHT = 0;
    public static byte CRAWL = 1;

    @SpirePatch(clz=Byrd.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Byrd __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "PECK",
                    Intent.ATTACK
            );
            move1.add_phase(FLIGHT);
            move1.add_effect(new AttackEffect(
                    __instance.damage.get(0),
                    (int) ReflectionHacks.getPrivate(__instance, __instance.getClass(), "peckCount")
            ));
            moves.add(move1);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "SWOOP",
                    Intent.ATTACK
            );
            move3.add_phase(FLIGHT);
            move3.add_effect(new AttackEffect(__instance.damage.get(1)));
            moves.add(move3);

            IntentData move6 = new IntentData(
                    __instance.getClass(),
                    "CAW",
                    Intent.BUFF,
                    1
            );
            move6.add_phase(FLIGHT);
            move6.add_effect(new BuffStrengthEffect(1));
            moves.add(move6);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.turn_start_func.set(__instance,
                        ByrdPatch.class.getMethod("turnStart", Byrd.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void turnStart(Byrd monster) {
        boolean isFlying = (boolean)ReflectionHacks.getPrivate(monster, monster.getClass(), "isFlying");
        if (isFlying) {
            AbstractMonsterPatch.current_phase.set(monster, FLIGHT);
        }
        else {
            AbstractMonsterPatch.current_phase.set(monster, CRAWL);
        }
    }
}
