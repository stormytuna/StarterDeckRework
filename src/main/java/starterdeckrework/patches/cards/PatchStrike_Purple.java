package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchStrike_Purple {
    private static final int UPGRADED_DAMAGE = 2;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_P");

    @SpirePatch(clz = Strike_Purple.class, method = "upgrade")
    public static class Strike_Purple_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Strike_Purple __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapWatcherStrikes) {
                Class<?> strikePurpleClass = __instance.getClass();
                Class<?> abstractCardClass = strikePurpleClass.getSuperclass();

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

    @SpirePatch(clz = Strike_Purple.class, method = "use")
    public static class Strike_Purple_PostfixUpgrade {
        @SpirePostfixPatch
        public static void patch(Strike_Purple __instance, AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            if (StarterDeckRework.swapWatcherStrikes && __instance.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Insight(), 1, true, true, false));
            }
        }
    }
}
