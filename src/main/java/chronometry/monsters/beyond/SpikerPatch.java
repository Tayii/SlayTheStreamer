package chronometry.monsters.beyond;

import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.UniqueEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Spiker;

import java.util.ArrayList;

public class SpikerPatch {
    @SpirePatch(clz=Spiker.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Spiker __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "ATTACK",
                    Intent.ATTACK
            );
            move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "BUFF_THORNS",
                    Intent.BUFF,
                    1
                    );
            move2.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1",
                    2
            ));
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
