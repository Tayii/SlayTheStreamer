package chronometry.monsters.city;

import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.Chosen;

import java.util.ArrayList;

public class ChosenPatch {
    @SpirePatch(clz=Chosen.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Chosen __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<IntentData>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "ZAP",
                    Intent.ATTACK
            );
            move1.add_effect(new AttackEffect(__instance.damage.get(0)));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "DRAIN",
                    Intent.DEBUFF,
                    1
            );
            move2.add_effect(new DebuffWeakEffect(3));
            move2.add_effect(new BuffStrengthEffect(3));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "DEBILITATE",
                    Intent.ATTACK_DEBUFF,
                    1
            );
            move3.add_effect(new AttackEffect(__instance.damage.get(1)));
            move3.add_effect(new DebuffVulnerableEffect(2));
            moves.add(move3);

            IntentData move4 = new IntentData(
                    __instance.getClass(),
                    "HEX",
                    Intent.STRONG_DEBUFF,
                    -1
            );
            move4.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1",
                    1
            ));
            moves.add(move4);

            IntentData move5 = new IntentData(
                    __instance.getClass(),
                    "POKE",
                    Intent.ATTACK
            );
            move5.add_effect(new AttackEffect(__instance.damage.get(2), 2));
            moves.add(move5);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
