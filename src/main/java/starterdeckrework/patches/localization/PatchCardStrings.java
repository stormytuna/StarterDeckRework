package starterdeckrework.patches.localization;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import starterdeckrework.StarterDeckRework;

import java.util.Map;

public class PatchCardStrings {
    private static Map<String, CardStrings> cards;

    @SpirePatch(clz = LocalizedStrings.class, method = "<ctor>")
    public static class LocalizedStrings_PostfixCtor {
        @SpirePostfixPatch
        public static void patch(LocalizedStrings __instance) {
            PatchCardStrings.cards = ReflectionHacks.getPrivate(__instance, LocalizedStrings.class, "cards");
        }
    }

    @SpirePatch(clz = LocalizedStrings.class, method = "getCardStrings", paramtypez = {String.class})
    public static class LocalizedStrings_PrefixGetCardStrings {
        @SpirePrefixPatch
        public static SpireReturn<CardStrings> patch(LocalizedStrings __instance, String cardName) {
            CardStrings cardStrings = PatchCardStrings.cards.get(StarterDeckRework.makeID(cardName));
            if (cardStrings != null) {
                return SpireReturn.Return(cardStrings);
            }

            return SpireReturn.Continue();
        }
    }
}
