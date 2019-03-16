package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class AttackEffect extends MoveEffect {
    public DamageInfo info;

    public AttackEffect(DamageInfo damage, int multiplier) {
        this.effect_type = Intent.ATTACK;
        this.info = damage;
        this.multiplier = multiplier;
    }

    public int number() {
        return this.info.output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("наносит "); // deals
        sb.append(this.number());
        if (this.multiplier > 1) {
            sb.append("x");
            sb.append(this.multiplier);
        }
        sb.append(" урона "); // damage
        return sb.toString();
    }
}
