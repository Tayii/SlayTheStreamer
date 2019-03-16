package chronometry.effects;

import chronometry.MoveEffect;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class UniqueEffect extends MoveEffect {
    public UniqueEffect(Intent effect_type, String effect_string, int number, int multiplier) {
        this.effect_type = effect_type;
        this.effect_string = effect_string;
        this.number = number;
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        //"increases strength every turn by {num}"
        return effect_string.replaceAll("\\{num\\}", String.valueOf(this.number))
                .replaceAll("\\{mul\\}", String.valueOf(this.multiplier));
    }
}
