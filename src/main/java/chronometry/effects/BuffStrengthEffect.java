package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class BuffStrengthEffect extends MoveEffect {
    public BuffStrengthEffect(int strength) {
        this.effect_type = Intent.BUFF;
        this.number = strength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("получает +"); // gains
        sb.append(this.number);
        sb.append(" силы "); // strength
        return sb.toString();
    }
}
