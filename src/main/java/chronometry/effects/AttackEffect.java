package chronometry.effects;

import chronometry.MoveEffect;
import chronometry.SlayTheStreamer;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AttackEffect extends MoveEffect {
    public DamageInfo info;
    public AbstractMonster monster;
    public Method getNumber = null;
    public Method getMultiplier = null;

    public AttackEffect(DamageInfo damage) {
        this(damage, 1);
    }

    public AttackEffect(AbstractMonster monster, Method getNumber) {
        this.monster = monster;
        this.getNumber = getNumber;
        this.multiplier = 1;
    }

    public AttackEffect(DamageInfo damage, int multiplier) {
        this.info = damage;
        this.multiplier = multiplier;
    }

    public AttackEffect(DamageInfo damage, AbstractMonster monster, Method getMultiplier) {
        this.info = damage;
        this.monster = monster;
        this.getMultiplier = getMultiplier;
    }

    public int number() {
        if (this.getNumber != null) {
            try {
                return (int)this.getNumber.invoke(null, this.monster);
            }
            catch (IllegalAccessException | InvocationTargetException exc) {
                SlayTheStreamer.logger.info("catched error in number() AttackEffect of monster ".concat(this.monster.name));
                SlayTheStreamer.logger.info(exc);
                return 0;
            }
        }
        else {
            return this.info.output;
        }
    }

    public int multiplier() {
        if (this.getMultiplier != null) {
            try {
                this.multiplier = (int)this.getMultiplier.invoke(null, this.monster);
            }
            catch (IllegalAccessException | InvocationTargetException exc) {
                SlayTheStreamer.logger.info("catched error in multiplier() AttackEffect of monster ".concat(this.monster.name));
                SlayTheStreamer.logger.info(exc);
            }
        }
        return this.multiplier;
    }
}
