package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class DebuffWeakEffect extends MoveEffect {
    public DebuffWeakEffect(int turns) {
        this.effect_type = Intent.DEBUFF;
        this.multiplier = turns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ослабляет атаку на "); // make weak for
        sb.append(this.multiplier);
        sb.append(" ходов "); // turns
        return sb.toString();
    }
}
