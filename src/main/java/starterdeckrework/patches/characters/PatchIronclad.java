package starterdeckrework.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.Ironclad;
import starterdeckrework.StarterDeckRework;

import java.util.ArrayList;

public class PatchIronclad {
    @SpirePatch(clz = Ironclad.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, Ironclad instance) {
            if (StarterDeckRework.swapIroncladStrikeForFrenzy) {
                deckList.remove("Strike_R");
                deckList.add("StarterDeckRework:Frenzy");
            }

            return deckList;
        }
    }
}
