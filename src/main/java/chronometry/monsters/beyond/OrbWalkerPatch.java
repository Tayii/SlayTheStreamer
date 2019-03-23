package chronometry.monsters.beyond;

import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffCardDrawDiscardEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;

import java.util.ArrayList;

public class OrbWalkerPatch {
    @SpirePatch(clz=OrbWalker.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(OrbWalker __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "LASER",
                    Intent.ATTACK_DEBUFF
            );
            move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            move1.add_effect(new DebuffCardDrawDiscardEffect(Burn.NAME, 1));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "CLAW",
                    Intent.ATTACK
                    );
            move2.add_effect(new AttackEffect(__instance.damage.get(1)));
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
