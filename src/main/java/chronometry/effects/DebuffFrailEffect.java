package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class DebuffFrailEffect extends MoveEffect {
    public DebuffFrailEffect(int turns) {
        this.effect_type = Intent.DEBUFF;
        this.multiplier = turns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ослабляет броню на "); // make frail for
        sb.append(this.multiplier);
        sb.append(" ходов "); // turns
        return sb.toString();
    }
}
