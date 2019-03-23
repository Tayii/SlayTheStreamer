package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Donu;

import java.util.ArrayList;

public class DonuPatch {
    @SpirePatch(clz=Donu.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(Donu __instance) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move0 = new IntentData(
                    __instance.getClass(),
                    "BEAM",
                    Intent.ATTACK
            );
            move0.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            moves.add(move0);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "CIRCLE_OF_PROTECTION",
                    Intent.BUFF,
                    (String) ReflectionHacks.getPrivateStatic(__instance.getClass(), "CIRCLE_NAME"),
                    1
                    );
            move2.add_effect(new EveryAllyEffect());
            move2.add_effect(new BuffStrengthEffect(3));
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
