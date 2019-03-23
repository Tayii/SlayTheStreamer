package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.BuffStrengthEffect;
import chronometry.effects.DebuffFrailEffect;
import chronometry.effects.DebuffWeakEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Maw;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MawPatch {
    @SpirePatch(clz=Maw.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Maw __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "ROAR",
                    Intent.STRONG_DEBUFF,
                    -1
            );
            int terrifyDur = (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "terrifyDur");
            move2.add_effect(new DebuffWeakEffect(terrifyDur));
            move2.add_effect(new DebuffFrailEffect(terrifyDur));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "SLAM",
                    Intent.ATTACK
                    );
            move3.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move3);

            IntentData move4 = new IntentData(
                    __instance.getClass(),
                    "DROOL",
                    Intent.BUFF,
                    1
                    );
            move4.add_effect(new BuffStrengthEffect(
                    (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "strUp")
            ));
            moves.add(move4);

            IntentData move5 = new IntentData(
                    __instance.getClass(),
                    "NOMNOMNOM",
                    Intent.ATTACK,
                    1
                    );
            try {
                Method m = MawPatch.class.getMethod("getNomCount", Maw.class);
                move5.add_effect(new AttackEffect(__instance.damage.get(1), __instance, m));
            }
            catch (NoSuchMethodException exc) {
                move5.add_effect(new AttackEffect(__instance.damage.get(1)));
            }
            moves.add(move5);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }

    public static int getNomCount(Maw monster) {
        int turnCount = (int)ReflectionHacks.getPrivate(monster, monster.getClass(), "turnCount");
        return turnCount / 2;
    }
}
