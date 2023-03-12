package starterdeckrework.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.Watcher;
import starterdeckrework.StarterDeckRework;
import starterdeckrework.util.GeneralUtils;

import java.util.ArrayList;

public class PatchWatcher {
    @SpirePatch(clz = Watcher.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Watcher instance) {
            // Swap Strikes for better Strikes
            if (StarterDeckRework.swapWatcherStrikes) {
                GeneralUtils.swapAllInstances(deckList, "Strike_P", "StarterDeckRework:PurpleStrike");
            }

            // Swap Defends for better Defends
            if (StarterDeckRework.swapWatcherDefends) {
                GeneralUtils.swapAllInstances(deckList, "Defend_P", "StarterDeckRework:PurpleDefend");
            }

            return deckList;
        }
    }
}
