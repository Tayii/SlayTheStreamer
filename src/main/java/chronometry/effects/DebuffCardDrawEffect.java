package chronometry.effects;

import chronometry.MoveEffect;

public class DebuffCardDrawEffect extends MoveEffect {
    public DebuffCardDrawEffect(String card_name, int amount) {
        this.effect_string = card_name;
        this.number = amount;
    }
}
