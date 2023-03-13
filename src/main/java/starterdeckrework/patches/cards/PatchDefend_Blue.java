package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchDefend_Blue {
    private static final int UPGRADED_BLOCK = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_B");

    @SpirePatch(clz = Defend_Blue.class, method = "upgrade")
    public static class Defend_Blue_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Defend_Blue __instance) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            if (StarterDeckRework.swapDefectDefends) {
                Class<?> defendBlueClass = __instance.getClass();
                Class<?> abstractCardClass = defendBlueClass.getSuperclass();

                Method upgradeName = abstractCardClass.getDeclaredMethod("upgradeName");
                upgradeName.setAccessible(true);
                upgradeName.invoke(__instance);

                Method upgradeBlock = abstractCardClass.getDeclaredMethod("upgradeBlock", int.class);
                upgradeBlock.setAccessible(true);
                upgradeBlock.invoke(__instance, UPGRADED_BLOCK);

                __instance.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                __instance.initializeDescription();

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Defend_Blue.class, method = "use")
    public static class Defend_Blue_PostfixUse {
        @SpirePostfixPatch
        public static void patch(Defend_Blue __instance, AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            if (__instance.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(abstractPlayer, DRAW_AMOUNT));
            }
        }
    }

}
