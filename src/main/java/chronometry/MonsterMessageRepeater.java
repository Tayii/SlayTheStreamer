package chronometry;

import chronometry.patches.AbstractMonsterPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.animations.*;
import de.robojumper.ststwitch.TwitchConnection;
import de.robojumper.ststwitch.TwitchPanel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MonsterMessageRepeater {

	static int MsgLength = 64;

	public static void remindActions(AbstractMonster monster) {
		if (monster.name.length() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("@");
			sb.append(monster.name.split(" ")[0]);
			sb.append(", ваши действия: "); // your actions
			for (IntentData data: AbstractMonsterPatch.intent_moves.get(monster)) {
				if (data.cooldown == 0) {
					sb.append(data.toString());
				}
			}

			try {
				Method m = TwitchPanel.class.getDeclaredMethod("getTwitch");
				m.setAccessible(true); //if security settings allow this
				TwitchConnection conn = (TwitchConnection)m.invoke(null); //use null if the method is static
				conn.sendMessage(sb.toString());
			}
			catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exc) {

			}
		}
	}

	static void parseMessage(String msg, String user) {
		if (CardCrawlGame.isInARun()) {
			if (AbstractDungeon.getCurrRoom() != null) {
				if (AbstractDungeon.getCurrRoom().monsters != null) {
					for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
						if (m.isDying) { return; }
						String username = user;
						if (SlayTheStreamer.displayNames.containsKey(username)) {
							username = SlayTheStreamer.displayNames.get(username);
						}
						if (m.name.split(" ")[0].toLowerCase().equals(username.split(" ")[0].toLowerCase())) {
							if (msg.startsWith("#") && !AbstractMonsterPatch.had_turn.get(m)) {
								msg = msg.substring(1);
								ArrayList<IntentData> moves = AbstractMonsterPatch.intent_moves.get(m);
								for (IntentData data: moves) {
									if (data.move_name.equals(msg) && data.cooldown == 0) {
										m.setMove(data.move_name, data.intent_code, data.intent_type,
												data.getBaseDamage(), data.getMultiplier(), data.isMulti());
										m.createIntent();
										data.setCooldown(moves.size());
										AbstractMonsterPatch.had_turn.set(m, true);
										break;
									}
								}
							}
							else {
								msg = msg.substring(0, Math.min(msg.length(), MonsterMessageRepeater.MsgLength));
								if (msg.length() == MonsterMessageRepeater.MsgLength) {
									msg = msg.substring(0, msg.lastIndexOf(" ")) + "...";
								}
								AbstractDungeon.actionManager.addToBottom(new TalkAction(m, msg, 1.5F, 2.5F));
							}
						}
					}
				}
			}
		}
	}

}
