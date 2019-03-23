package chronometry.monsters.city;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BookOfStabbingPatch {
    @SpirePatch(clz=BookOfStabbing.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(BookOfStabbing __instance) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "STAB",
                    Intent.ATTACK
            );
            try {
                Method m = BookOfStabbingPatch.class.getMethod("getStabCount", BookOfStabbing.class);
                move1.add_effect(new AttackEffect(__instance.damage.get(0), __instance, m));
            }
            catch (NoSuchMethodException exc) {
                move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            }
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "BIG_STAB",
                    Intent.ATTACK
            );
            move2.add_effect(new AttackEffect(__instance.damage.get(1)));
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }

    public static int getStabCount(BookOfStabbing monster) {
        return (int) ReflectionHacks.getPrivate(monster, monster.getClass(), "stabCount");
    }
}
