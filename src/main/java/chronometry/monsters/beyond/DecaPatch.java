package chronometry.monsters.beyond;

import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Deca;

import java.util.ArrayList;

public class DecaPatch {
    @SpirePatch(clz=Deca.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(Deca __instance) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move0 = new IntentData(
                    __instance.getClass(),
                    "BEAM",
                    Intent.ATTACK_DEBUFF
            );
            move0.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            move0.add_effect(new DebuffCardDiscardEffect(Dazed.NAME, 2));
            moves.add(move0);

            Intent move2Intent = Intent.DEFEND;
            if (AbstractDungeon.ascensionLevel >= 19) {
                move2Intent = Intent.DEFEND_BUFF;
            }
            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "SQUARE_OF_PROTECTION",
                    move2Intent,
                    1
                    );
            move2.add_effect(new EveryAllyEffect());
            move2.add_effect(new DefendEffect(16));
            if (AbstractDungeon.ascensionLevel >= 19) {
                move2.add_effect(new EveryAllyEffect());
                move2.add_effect(new UniqueEffect(
                        __instance.getClass(),
                        "UNIQUE_1",
                        3
                ));
            }
            moves.add(move2);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
