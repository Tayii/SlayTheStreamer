package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.SlayTheStreamer;
import chronometry.effects.AttackEffect;
import chronometry.effects.DebuffWeakEffect;
import chronometry.effects.UniqueEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReptomancerPatch {
    public static byte CAN_SPAWN = 0;
    public static byte CANNOT_SPAWN = 1;

    @SpirePatch(clz=Reptomancer.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {})
    public static class InitMoves {
        public static void Postfix(Reptomancer __instance) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "SNAKE_STRIKE",
                    Intent.ATTACK_DEBUFF
            );
            move1.add_phase(CAN_SPAWN);
            move1.add_phase(CANNOT_SPAWN);
            move1.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            move1.add_effect(new DebuffWeakEffect(1));
            moves.add(move1);

            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "SPAWN_DAGGER",
                    Intent.UNKNOWN
                    );
            move2.add_phase(CAN_SPAWN);
            move2.add_effect(new UniqueEffect(
                    __instance.getClass(),
                    "UNIQUE_1",
                    2
            ));
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "BIG_BITE",
                    Intent.ATTACK_DEBUFF
                    );
            move3.add_phase(CAN_SPAWN);
            move3.add_phase(CANNOT_SPAWN);
            move3.add_effect(new AttackEffect(__instance.damage.get(1)));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.turn_start_func.set(__instance,
                        ReptomancerPatch.class.getMethod("turnStart", Reptomancer.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void turnStart(Reptomancer monster) {
        try {
            Method m = monster.getClass().getMethod("canSpawn");
            boolean canSpawn = (boolean)m.invoke(monster);
            if (canSpawn) {
                AbstractMonsterPatch.current_phase.set(monster, CAN_SPAWN);
            }
            else {
                AbstractMonsterPatch.current_phase.set(monster, CANNOT_SPAWN);
            }
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exc) {
            SlayTheStreamer.logger.info("catched error in turnStart Reptomancer ".concat(monster.name));
            SlayTheStreamer.logger.info(exc);
        }
    }
}
