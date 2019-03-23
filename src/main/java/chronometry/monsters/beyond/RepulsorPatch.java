package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffCardDrawEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Repulsor;

import java.util.ArrayList;

public class RepulsorPatch {
    @SpirePatch(clz=Repulsor.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Repulsor __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "DAZE",
                    Intent.DEBUFF,
                    1
            );
            move1.add_effect(new DebuffCardDrawEffect(
                    Dazed.NAME,
                    (int) ReflectionHacks.getPrivate(__instance, __instance.getClass(), "dazeAmt")
            ));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "ATTACK",
                    Intent.ATTACK
                    );
            move2.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
