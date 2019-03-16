package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class DebuffVulnerableEffect extends MoveEffect {
    public DebuffVulnerableEffect(int turns) {
        this.effect_type = Intent.DEBUFF;
        this.multiplier = turns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("накладывает уязвимость на "); // make vulnerable for
        sb.append(this.multiplier);
        sb.append(" ходов "); // turns
        return sb.toString();
    }
}
