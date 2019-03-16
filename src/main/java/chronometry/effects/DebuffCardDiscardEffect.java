package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class DebuffCardDiscardEffect extends MoveEffect {
    public DebuffCardDiscardEffect(String card_name, int amount) {
        this.effect_type = Intent.DEBUFF;
        this.effect_string = card_name;
        this.multiplier = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("добавляет в стопку сброса карту "); //add this card to the discard pile
        sb.append(this.effect_string);
        sb.append(" ");
        if (this.multiplier > 1) {
            sb.append("x");
            sb.append(this.multiplier);
            sb.append(" ");
        }
        return sb.toString();
    }
}
