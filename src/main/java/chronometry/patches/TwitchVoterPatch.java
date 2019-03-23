package chronometry.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import de.robojumper.ststwitch.TwitchVoteOption;
import de.robojumper.ststwitch.TwitchVoter;

import java.util.Set;
import java.util.regex.Pattern;

public class TwitchVoterPatch {
    @SpirePatch(clz=TwitchVoter.class,
            method="onMessage",
            paramtypez={String.class, String.class})
    public static class FixContinueRunCrash {
        public static void Replace(TwitchVoter __instance, String msg, String user) {
            boolean isVoting = (boolean)ReflectionHacks.getPrivate
                    (__instance, __instance.getClass(), "isVoting");
            TwitchVoteOption[] options = __instance.getOptions();
            if (isVoting && options != null) {
                for (TwitchVoteOption option : options) {
                    Pattern matchPattern = (Pattern) ReflectionHacks.getPrivate
                            (option, option.getClass(), "matchPattern");
                    Set<String> votedUsernames = (Set<String>) ReflectionHacks.getPrivate
                            (__instance, __instance.getClass(), "votedUsernames");
                    if (matchPattern.matcher(msg).matches() && !votedUsernames.contains(user)) {
                        ++option.voteCount;
                        votedUsernames.add(user);
                    }
                }
            }
        }
    }
}
