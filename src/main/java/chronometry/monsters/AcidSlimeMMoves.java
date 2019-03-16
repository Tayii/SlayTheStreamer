package chronometry.monsters;

import basemod.ReflectionHacks;
import chronometry.patches.AbstractMonsterPatch;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffWeakEffect;
import chronometry.effects.DebuffCardDiscardEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;

import java.util.ArrayList;

public class AcidSlimeMMoves {
    @SpirePatch(clz=AcidSlime_M.class,
                method=SpirePatch.CONSTRUCTOR,
                paramtypez = {float.class, float.class, int.class, int.class})
    public static class AcidSlime_MInitMoves {
        public static void Postfix(AcidSlime_M __instance, float x, float y, int poisonAmount, int newHealth) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    (byte)ReflectionHacks.getPrivateStatic(__instance.getClass(), "WOUND_TACKLE"),
                    Intent.ATTACK_DEBUFF,
                    AcidSlime_M.MOVES[0]
            );
            move1.add_effect(new AttackEffect(
                    __instance.damage.get(0),
                    1
            ));
            move1.add_effect(new DebuffCardDiscardEffect(
                    Slimed.NAME,
                    1
            ));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    (byte)ReflectionHacks.getPrivateStatic(__instance.getClass(), "NORMAL_TACKLE"),
                    Intent.ATTACK,
                    "Normal Tackle"
            );
            move2.add_effect(new AttackEffect(
                    __instance.damage.get(1),
                    1
            ));
            moves.add(move2);

            IntentData move4 = new IntentData(
                    (byte)ReflectionHacks.getPrivateStatic(__instance.getClass(), "WEAK_LICK"),
                    Intent.DEBUFF,
                    AcidSlime_M.MOVES[1]
            );
            move4.add_effect(new DebuffWeakEffect(
                    1
            ));
            moves.add(move4);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
