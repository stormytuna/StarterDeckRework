package starterdeckrework.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Defect;

import java.util.ArrayList;

public class PatchDefect {
    @SpirePatch(clz = Defect.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Defect instance) {
            deckList = new ArrayList<String>();
            deckList.add("StarterDeckRework:BlueStrike");
            deckList.add("StarterDeckRework:BlueStrike");
            deckList.add("StarterDeckRework:BlueStrike");
            deckList.add("StarterDeckRework:BlueStrike");
            deckList.add("StarterDeckRework:BlueDefend");
            deckList.add("StarterDeckRework:BlueDefend");
            deckList.add("StarterDeckRework:BlueDefend");
            deckList.add("StarterDeckRework:BlueDefend");
            deckList.add("Zap");
            deckList.add("Dualcast");

            return deckList;
        }
    }
}
