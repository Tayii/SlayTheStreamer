package chronometry.monsters;

import basemod.ReflectionHacks;
import chronometry.patches.AbstractMonsterPatch;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.UniqueEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;

import java.util.ArrayList;

public class CultistMoves {
    @SpirePatch(clz=Cultist.class,
                method=SpirePatch.CONSTRUCTOR,
                paramtypez = {float.class, float.class, boolean.class})
    public static class CultistInitMoves {
        public static void Postfix(Cultist __instance, float x, float y, boolean talk) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    (byte)ReflectionHacks.getPrivateStatic(__instance.getClass(), "DARK_STRIKE"),
                    Intent.ATTACK,
                    "Dark Strike"
            );
            move1.add_effect(new AttackEffect(
                    __instance.damage.get(0),
                    0
            ));
            moves.add(move1);

            IntentData move3 = new IntentData(
                    (byte)ReflectionHacks.getPrivateStatic(__instance.getClass(), "INCANTATION"),
                    Intent.BUFF,
                    Cultist.MOVES[2]
            );
            move3.add_effect(new UniqueEffect(
                    Intent.BUFF,
                    "повышает силу каждый ход на {num}",
                    (int)ReflectionHacks.getPrivate(__instance, __instance.getClass(), "ritualAmount"),
                    0
            ));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
