package starterdeckrework.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.TheSilent;
import starterdeckrework.StarterDeckRework;

import java.util.ArrayList;

public class PatchTheSilent {
    @SpirePatch(clz = TheSilent.class, method = "getStartingDeck")
    public static class GetStartingDeckPatch {
        @SpirePostfixPatch
        public static ArrayList<String> patch(ArrayList<String> deckList, TheSilent instance) {
            if (StarterDeckRework.removeSilentStrikeAndDefend) {
                deckList.remove("Strike_G");
                deckList.remove("Defend_G");
            }

            return deckList;
        }
    }
}
