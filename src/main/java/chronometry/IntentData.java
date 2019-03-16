package chronometry;

import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import chronometry.effects.AttackEffect;
import chronometry.effects.UniqueEffect;

import java.util.ArrayList;

public class IntentData {
    public byte intent_code;
    public Intent intent_type;
    public String move_name;
    public ArrayList<MoveEffect> effects;
    public int cooldown;

    public IntentData(byte intent_code, Intent intent_type, String move_name) {
        this.intent_code = intent_code;
        this.intent_type = intent_type;
        this.move_name = move_name;
        this.effects = new ArrayList<MoveEffect>();
    }

    public void add_effect(MoveEffect effect) {
        this.effects.add(effect);
    }

    public int getBaseDamage() {
        for (MoveEffect move: this.effects) {
            if (move instanceof AttackEffect) {
                return ((AttackEffect)move).info.base;
            }
        }
        return -1;
    }

    public int getMultiplier() {
        for (MoveEffect move: this.effects) {
            if (move instanceof AttackEffect) {
                return move.multiplier;
            }
        }
        return 0;
    }

    public boolean isMulti() {
        return this.getMultiplier() > 0;
    }

    public void setCooldown(int move_size) {
        if (this.isUnique()) {
            this.cooldown = 1;
        }
    }

    public void refreshCooldown() {
        if (this.cooldown > 0 && !this.isUnique()) {
            this.cooldown -= 1;
        }
    }

    public boolean isUnique() {
        for (MoveEffect move: this.effects) {
            if (move instanceof UniqueEffect) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(this.move_name);
        sb.append(" ");
        for (MoveEffect move: this.effects) {
            sb.append(move.toString());
        }
        return sb.toString();
    }
}
