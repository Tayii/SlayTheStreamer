package chronometry;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.animations.*;

public class MonsterMessageRepeater {

	static int MsgLength = 64;

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
							msg = msg.substring(0, Math.min(msg.length(), MonsterMessageRepeater.MsgLength));
							if (msg.length() == MonsterMessageRepeater.MsgLength) {
								msg = msg.substring(0, msg.lastIndexOf(" ")) + "...";
							}
							AbstractDungeon.actionManager.addToBottom(new TalkAction(m,msg,1.5F,2.5F));
						}
					}
				}
			}
		}
	}

}
