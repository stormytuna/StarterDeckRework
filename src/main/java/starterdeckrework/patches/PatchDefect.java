package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Defect;
import starterdeckrework.util.GeneralUtils;

import java.util.ArrayList;

public class PatchDefect {
    @SpirePatch(clz = Defect.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Defect instance) {
            // Swap Strikes for better Strikes
            GeneralUtils.swapAllInstances(deckList, "Strike_B", "StarterDeckRework:BlueStrike");

            // Swap Defends for better Defends
            GeneralUtils.swapAllInstances(deckList, "Defend_B", "StarterDeckRework:BlueDefend");

            return deckList;
        }
    }
}
