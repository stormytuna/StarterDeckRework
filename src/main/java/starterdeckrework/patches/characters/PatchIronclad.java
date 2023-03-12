package starterdeckrework.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import starterdeckrework.StarterDeckRework;
import starterdeckrework.util.GeneralUtils;

import java.util.ArrayList;

public class PatchIronclad {
    @SpirePatch(clz = Ironclad.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Ironclad instance) {
            // Swap 1 Strike for Frenzy
            if (StarterDeckRework.swapIroncladStrikeForFrenzy) {
                deckList.remove("Strike_R");
                deckList.add("StarterDeckRework:Frenzy");
            }

            // Swap rest of Strikes for better Strikes
            if (StarterDeckRework.swapIroncladStrikes) {
                GeneralUtils.swapAllInstances(deckList, "Strike_R", "StarterDeckRework:RedStrike");
            }

            // Swap Defends for better Defends
            if (StarterDeckRework.swapIroncladDefends) {
                GeneralUtils.swapAllInstances(deckList, "Defend_R", "StarterDeckRework:RedDefend");
            }

            return deckList;
        }
    }
}
