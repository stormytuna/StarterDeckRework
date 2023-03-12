package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.TheSilent;
import starterdeckrework.util.GeneralUtils;

import java.util.ArrayList;

public class PatchTheSilent {
    @SpirePatch(clz = TheSilent.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, TheSilent instance) {
            // Remove a Strike and Defend to make silent 4/4/1/1
            deckList.remove("Strike_G");
            deckList.remove("Defend_G");

            // Swap Strikes for better Strikes
            GeneralUtils.swapAllInstances(deckList, "Strike_G", "StarterDeckRework:GreenStrike");

            // Swap Defends for better Defends
            GeneralUtils.swapAllInstances(deckList, "Defend_G", "StarterDeckRework:GreenDefend");

            return deckList;
        }
    }
}
