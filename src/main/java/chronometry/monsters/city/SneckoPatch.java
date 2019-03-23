package chronometry.monsters.city;

import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.Snecko;

import java.util.ArrayList;

public class SneckoPatch {
    @SpirePatch(clz=Snecko.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Snecko __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "GLARE",
                    Intent.STRONG_DEBUFF,
                    __instance.MOVES[0],
                    -1
            );
            move1.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1"
            ));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "BITE",
                    Intent.ATTACK,
                    __instance.MOVES[2]
            );
            move2.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "TAIL",
                    Intent.ATTACK_DEBUFF,
                    __instance.MOVES[1]
            );
            move3.add_effect(new AttackEffect(__instance.damage.get(1)));
            move3.add_effect(new DebuffVulnerableEffect(2));
            if (AbstractDungeon.ascensionLevel >= 17) {
                move3.add_effect(new DebuffWeakEffect(2));
            }
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
