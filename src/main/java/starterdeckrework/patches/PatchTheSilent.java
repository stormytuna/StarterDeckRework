package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.TheSilent;

import java.util.ArrayList;

public class PatchTheSilent {
    @SpirePatch(clz = TheSilent.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, TheSilent instance) {
            deckList = new ArrayList<String>();
            deckList.add("StarterDeckRework:GreenStrike");
            deckList.add("StarterDeckRework:GreenStrike");
            deckList.add("StarterDeckRework:GreenStrike");
            deckList.add("StarterDeckRework:GreenStrike");
            deckList.add("StarterDeckRework:GreenDefend");
            deckList.add("StarterDeckRework:GreenDefend");
            deckList.add("StarterDeckRework:GreenDefend");
            deckList.add("StarterDeckRework:GreenDefend");
            deckList.add("Neutralize");
            deckList.add("Survivor");

            return deckList;
        }
    }
}
