package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffWeakEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.GiantHead;

import java.util.ArrayList;

public class GiantHeadPatch {
    public static byte COUNT = 0;
    public static byte TIMES_UP = 1;

    @SpirePatch(clz=GiantHead.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(GiantHead __instance) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "GLARE",
                    Intent.DEBUFF
            );
            move1.add_phase(COUNT);
            move1.add_effect(new DebuffWeakEffect(1));
            moves.add(move1);

            //IntentData move2 = new IntentData(
            //        __instance.getClass(),
            //        "IT_IS_TIME",
            //        Intent.ATTACK
            //        );
            //move2.add_phase(TIMES_UP);
            //moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "COUNT",
                    Intent.ATTACK
                    );
            move3.add_phase(COUNT);
            move3.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.turn_start_func.set(__instance,
                        GiantHeadPatch.class.getMethod("turnStart", GiantHead.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void turnStart(GiantHead monster) {
        int count = (int)ReflectionHacks.getPrivate(monster, monster.getClass(), "count");
        if (count <= 1) {
            AbstractMonsterPatch.current_phase.set(monster, TIMES_UP);
        }
    }
}
