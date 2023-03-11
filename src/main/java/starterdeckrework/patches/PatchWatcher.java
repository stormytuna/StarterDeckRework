package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.Watcher;

import java.util.ArrayList;

public class PatchWatcher {
    @SpirePatch(clz = Watcher.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Watcher instance) {
            // Replace the starting deck
            deckList = new ArrayList<String>();
            deckList.add("StarterDeckRework:PurpleStrike");
            deckList.add("StarterDeckRework:PurpleStrike");
            deckList.add("StarterDeckRework:PurpleStrike");
            deckList.add("StarterDeckRework:PurpleStrike");
            deckList.add("StarterDeckRework:PurpleDefend");
            deckList.add("StarterDeckRework:PurpleDefend");
            deckList.add("StarterDeckRework:PurpleDefend");
            deckList.add("StarterDeckRework:PurpleDefend");
            deckList.add("Eruption");
            deckList.add("Vigilance");

            return deckList;
        }
    }
}
