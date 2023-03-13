package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchStrike_Green {
    private static final int UPGRADED_DAMAGE = 2;
    private static final int DRAW_NEXT_TURN = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_G");

    @SpirePatch(clz = Strike_Green.class, method = "upgrade")
    public static class Strike_Green_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Strike_Green __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapSilentStrikes) {
                Class<?> strikeGreenClass = __instance.getClass();
                Class<?> abstractCardClass = strikeGreenClass.getSuperclass();

                Method upgradeName = abstractCardClass.getDeclaredMethod("upgradeName");
                upgradeName.setAccessible(true);
                upgradeName.invoke(__instance);

                Method upgradeDamage = abstractCardClass.getDeclaredMethod("upgradeDamage", int.class);
                upgradeDamage.setAccessible(true);
                upgradeDamage.invoke(__instance, UPGRADED_DAMAGE);

                __instance.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                __instance.initializeDescription();

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Strike_Green.class, method = "use")
    public static class Strike_Green_PostfixUpgrade {
        @SpirePostfixPatch
        public static void patch(Strike_Green __instance, AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            if (__instance.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DrawCardNextTurnPower(abstractPlayer, DRAW_NEXT_TURN)));
            }
        }
    }
}
