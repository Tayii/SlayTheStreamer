package chronometry.monsters.beyond;

import basemod.ReflectionHacks;
import chronometry.IntentData;
import chronometry.effects.AttackEffect;
import chronometry.effects.BuffStrengthEffect;
import chronometry.effects.DefendEffect;
import chronometry.patches.AbstractMonsterPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;

import java.util.ArrayList;

public class DarklingPatch {
    public static byte ALIVE = 0;
    public static byte DEAD = 1;

    @SpirePatch(clz=Darkling.class,
            method=SpirePatch.CONSTRUCTOR,
            paramtypez = {float.class, float.class})
    public static class InitMoves {
        public static void Postfix(Darkling __instance, float x, float y) {
            ArrayList<IntentData> moves = new ArrayList<>();

            IntentData move1 = new IntentData(
                    __instance.getClass(),
                    "CHOMP",
                    Intent.ATTACK
            );
            move1.add_phase(ALIVE);
            move1.add_effect(new AttackEffect(__instance.damage.get(0), 2));
            moves.add(move1);

            Intent move2Intent;
            if (AbstractDungeon.ascensionLevel >= 17) {
                move2Intent = Intent.DEFEND_BUFF;
            }
            else {
                move2Intent = Intent.DEFEND;
            }
            IntentData move2 = new IntentData(
                    __instance.getClass(),
                    "HARDEN",
                    move2Intent,
                    1
            );
            move2.add_phase(ALIVE);
            move2.add_effect(new DefendEffect(12));
            if (AbstractDungeon.ascensionLevel >= 17) {
                move2.add_effect(new BuffStrengthEffect(2));
            }
            moves.add(move2);

            IntentData move3 = new IntentData(
                    __instance.getClass(),
                    "NIP",
                    Intent.ATTACK
                    );
            move3.add_phase(ALIVE);
            move3.add_effect(new AttackEffect(__instance.damage.get(1)));
            moves.add(move3);

            AbstractMonsterPatch.intent_moves.set(__instance, moves);
            try {
                AbstractMonsterPatch.turn_start_func.set(__instance,
                        GiantHeadPatch.class.getMethod("turnStart", Darkling.class));
            }
            catch (NoSuchMethodException exc) { }
        }
    }

    public static void turnStart(Darkling monster) {
        if (monster.halfDead) {
            AbstractMonsterPatch.current_phase.set(monster, DEAD);
        }
        else {
            AbstractMonsterPatch.current_phase.set(monster, ALIVE);
        }
    }
}
