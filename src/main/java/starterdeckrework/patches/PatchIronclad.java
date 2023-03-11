package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Ironclad;

import java.util.ArrayList;

public class PatchIronclad {
    @SpirePatch(clz = Ironclad.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Ironclad instance) {
            // Replace the starting deck
            deckList = new ArrayList<String>();
            deckList.add("StarterDeckRework:RedStrike");
            deckList.add("StarterDeckRework:RedStrike");
            deckList.add("StarterDeckRework:RedStrike");
            deckList.add("StarterDeckRework:RedStrike");
            deckList.add("StarterDeckRework:RedDefend");
            deckList.add("StarterDeckRework:RedDefend");
            deckList.add("StarterDeckRework:RedDefend");
            deckList.add("StarterDeckRework:RedDefend");
            deckList.add("StarterDeckRework:Frenzy");
            deckList.add("Bash");

            return deckList;
        }
    }
}
