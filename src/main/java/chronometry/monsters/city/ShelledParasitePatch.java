package chronometry.monsters.city;

import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffFrailEffect;
import chronometry.effects.UniqueEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.ShelledParasite;

import java.util.ArrayList;

public class ShelledParasitePatch {
    @SpirePatch(clz=ShelledParasite.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(ShelledParasite __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "FELL",
                    Intent.ATTACK_DEBUFF,
                    1
            );
            move1.add_effect(new AttackEffect(__instance.damage.get(1)));
            move1.add_effect(new DebuffFrailEffect(2));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "DOUBLE_STRIKE",
                    Intent.ATTACK
            );
            move2.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "LIFE_SUCK",
                    Intent.ATTACK_BUFF
            );
            move3.add_effect(new AttackEffect(__instance.damage.get(2)));
            move3.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1"
            ));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
        }
    }
}
