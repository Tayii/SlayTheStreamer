package chronometry.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import de.robojumper.ststwitch.TwitchVoteOption;
import de.robojumper.ststwitch.TwitchVoter;

public class TwitchVoterPatch {
    @SpirePatch(clz=TwitchVoter.class,
            method="onMessage",
            paramtypez={String.class, String.class})
    public static class FixContinueRunCrash {
        public static void Prefix(TwitchVoter __instance, String msg, String user) {
            boolean isVoting = (boolean)ReflectionHacks.getPrivate
                    (__instance, __instance.getClass(), "isVoting");
            TwitchVoteOption[] options = __instance.getOptions();
            if (!isVoting || options == null) {
                SpireReturn.Return(null);
            }
        }
    }
}
