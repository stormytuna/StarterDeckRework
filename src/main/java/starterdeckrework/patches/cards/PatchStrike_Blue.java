package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchStrike_Blue {
    private static final int UPGRADED_COST = 0;
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_B");

    @SpirePatch(clz = Strike_Blue.class, method = "upgrade")
    public static class Strike_Blue_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Strike_Blue __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapDefectStrikes) {
                Class<?> strikeBlueClass = __instance.getClass();
                Class<?> abstractCardClass = strikeBlueClass.getSuperclass();

                Method upgradeName = abstractCardClass.getDeclaredMethod("upgradeName");
                upgradeName.setAccessible(true);
                upgradeName.invoke(__instance);

                Method upgradeBaseCost = abstractCardClass.getDeclaredMethod("upgradeBaseCost", int.class);
                upgradeBaseCost.setAccessible(true);
                upgradeBaseCost.invoke(__instance, UPGRADED_COST);

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }
}
