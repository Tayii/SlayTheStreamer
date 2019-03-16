package chronometry;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import chronometry.effects.AttackEffect;

import java.util.ArrayList;

public class IntentData {
    public byte intent_code;
    public Intent intent_type;
    public String move_name;
    public ArrayList<MoveEffect> effects;
    public int cooldown = 0;
    public int max_cooldown = 0;
    public ArrayList<Integer> phase_availability;

    public IntentData(Class<? extends AbstractMonster> monsterClass, String intentCode, Intent intentType) {
        this(monsterClass, intentCode, intentType, 0);
    }

    public IntentData(Class<? extends AbstractMonster> monsterClass, String intentCode,
                      Intent intent_type, int max_cooldown) {
        this(monsterClass, intentCode, intent_type,
                SlayTheStreamer.localizedMonsterMoves.get(monsterClass.getSimpleName()).get(intentCode),
                max_cooldown);
    }

    public IntentData(Class<? extends AbstractMonster> monsterClass, String intentCode,
                      Intent intent_type, String move_name) {
        this(monsterClass, intentCode, intent_type, move_name, 0);
    }

    public IntentData(Class<? extends AbstractMonster> monsterClass, String intentCode,
                      Intent intent_type, String move_name, int max_cooldown) {
        this.intent_code = (byte) ReflectionHacks.getPrivateStatic(monsterClass, intentCode);
        this.intent_type = intent_type;
        this.move_name = move_name;
        this.max_cooldown = max_cooldown;
        this.effects = new ArrayList<>();
        this.phase_availability = new ArrayList<>();
    }

    public void add_effect(MoveEffect effect) {
        this.effects.add(effect);
    }
    public void add_phase(int phase_num) { this.phase_availability.add(phase_num); }

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
            if (move instanceof AttackEffect && move.multiplier > 1) {
                return move.multiplier;
            }
        }
        return 0;
    }

    public boolean isMulti() {
        return this.getMultiplier() > 1;
    }

    public void setCooldown() {
        this.cooldown = this.max_cooldown + 1;
    }

    public void refreshCooldown() {
        if (this.cooldown > 0) {
            this.cooldown -= 1;
        }
    }

    public boolean isAvailable(int phase_num) {
        return this.phase_availability.size() == 0 || this.phase_availability.contains(phase_num);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(this.move_name);
        sb.append(" ");
        for (MoveEffect move: this.effects) {
            sb.append(move.toString());
            sb.append(" ");
        }
        return sb.toString();
    }
}
