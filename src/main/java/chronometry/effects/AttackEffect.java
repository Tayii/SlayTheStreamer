package chronometry.effects;

import basemod.ReflectionHacks;
import chronometry.MoveEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackEffect extends MoveEffect {
    public DamageInfo info;
    public AbstractMonster monster;
    public String multiplierVariableName = null;

    public AttackEffect(DamageInfo damage) {
        this(damage, 1);
    }

    public AttackEffect(DamageInfo damage, int multiplier) {
        this.info = damage;
        this.multiplier = multiplier;
    }

    public AttackEffect(DamageInfo damage, AbstractMonster monster, String variableName) {
        this.info = damage;
        this.monster = monster;
        this.multiplierVariableName = variableName;
    }

    public int number() {
        return this.info.output;
    }

    public int multiplier() {
        if (multiplierVariableName != null) {
            return (int) ReflectionHacks.getPrivate(this.monster, this.monster.getClass(), this.multiplierVariableName);
        }
        else {
            return this.multiplier;
        }
    }
}
