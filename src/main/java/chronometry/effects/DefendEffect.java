package chronometry.effects;

import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import chronometry.MoveEffect;

public class DefendEffect extends MoveEffect {
    public DefendEffect(int block) {
        this.effect_type = Intent.DEFEND;
        this.number = block;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("получает "); // gains
        sb.append(this.number);
        sb.append(" блока "); // block
        return sb.toString();
    }
}
