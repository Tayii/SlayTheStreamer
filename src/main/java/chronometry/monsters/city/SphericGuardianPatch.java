package chronometry.monsters.city;

import chronometry.IntentData;
import chronometry.effects.*;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;

import java.util.ArrayList;

public class SphericGuardianPatch {
    public static byte INITIAL = 0;
    public static byte FIGHT = 1;

    @SpirePatch(clz=SphericGuardian.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(SphericGuardian __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "BIG_ATTACK",
                    Intent.ATTACK
            );
            move1.add_phase(FIGHT);
            move1.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "INITIAL_BLOCK_GAIN",
                    Intent.DEFEND
            );
            move2.add_phase(INITIAL);
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "BLOCK_ATTACK",
                    Intent.ATTACK_DEFEND
            );
            move3.add_phase(FIGHT);
            move3.add_effect(new AttackEffect(__instance.damage.get(0)));
            move3.add_effect(new DefendEffect(15));
            moves.add(move3);

            IntentData move4 = new IntentData(
                    __instance.getClass(),
                    "FRAIL_ATTACK",
                    Intent.ATTACK_DEBUFF,
                    5
                    );
            move4.add_phase(FIGHT);
            move4.add_effect(new AttackEffect(__instance.damage.get(0)));
            move4.add_effect(new DebuffFrailEffect(5));
            moves.add(move4);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.after_action_func.set(__instance,
                        SphericGuardianPatch.class.getMethod("afterAction", SphericGuardian.class, IntentData.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void afterAction(SphericGuardian monster, IntentData intent) {
        if (intent.equals(monster.getClass(), "INITIAL_BLOCK_GAIN")) {
            AbstractMonsterPatch.current_phase.set(monster, FIGHT);
        }
    }
}
