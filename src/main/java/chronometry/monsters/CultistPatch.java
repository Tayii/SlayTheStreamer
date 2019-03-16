package chronometry.monsters;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.UniqueEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

import java.util.ArrayList;

public class CultistPatch {
    @SpirePatch(clz=Cultist.class,
                method=SpirePatch.CONSTRUCTOR,
                paramtypez = {float.class, float.class, boolean.class})
    public static class InitMoves {
        public static void Postfix(Cultist __instance, float x, float y, boolean talk) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "DARK_STRIKE",
                    Intent.ATTACK
            );
            move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move1);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "INCANTATION",
                    Intent.BUFF,
                    (String)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "INCANTATION_NAME")
            );
            int ritualAmount = (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "ritualAmount");
            if (AbstractDungeon.ascensionLevel >= 17) {
                ritualAmount += 1;
            }
            move3.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1",
                    ritualAmount
            ));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
