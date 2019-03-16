package chronometry.patches;

import chronometry.IntentData;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;


@SpirePatch(clz=AbstractMonster.class,
            method=SpirePatch.CLASS)
public class AbstractMonsterPatch {
    public static SpireField<ArrayList<IntentData>> intent_moves = new SpireField<>(() -> null);
    public static SpireField<Integer> current_phase = new SpireField<>(() -> 0);
    public static SpireField<Boolean> is_player = new SpireField<>(() -> false);
    public static SpireField<Boolean> had_turn = new SpireField<>(() -> false);
}
