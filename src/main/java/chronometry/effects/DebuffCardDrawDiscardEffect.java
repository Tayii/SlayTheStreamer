package chronometry.effects;

import chronometry.MoveEffect;

public class DebuffCardDrawDiscardEffect extends MoveEffect {
    public DebuffCardDrawDiscardEffect(String card_name, int amount) {
        this.effect_string = card_name;
        this.number = amount;
    }
}
