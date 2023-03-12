package starterdeckrework.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.TheSilent;
import starterdeckrework.StarterDeckRework;
import starterdeckrework.util.GeneralUtils;

import java.util.ArrayList;

public class PatchTheSilent {
    @SpirePatch(clz = TheSilent.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, TheSilent instance) {
            // Remove a Strike and Defend to make silent 4/4/1/1
            if (StarterDeckRework.removeSilentStrikeAndDefend) {
                deckList.remove("Strike_G");
                deckList.remove("Defend_G");
            }

            // Swap Strikes for better Strikes
            if (StarterDeckRework.swapSilentStrikes) {
                GeneralUtils.swapAllInstances(deckList, "Strike_G", "StarterDeckRework:GreenStrike");
            }

            // Swap Defends for better Defends
            if (StarterDeckRework.swapSilentDefends) {
                GeneralUtils.swapAllInstances(deckList, "Defend_G", "StarterDeckRework:GreenDefend");
            }

            return deckList;
        }
    }
}
