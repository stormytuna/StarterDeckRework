package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchDefend_Purple {
    private static final int UPGRADED_BLOCK = 2;
    private static final int SCRY_AMOUNT = 2;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_P");

    @SpirePatch(clz = Defend_Watcher.class, method = "upgrade")
    public static class Defend_Purple_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Defend_Watcher __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapWatcherDefends) {
                Class<?> defendPurpleClass = __instance.getClass();
                Class<?> abstractCardClass = defendPurpleClass.getSuperclass();

                Method upgradeName = abstractCardClass.getDeclaredMethod("upgradeName");
                upgradeName.setAccessible(true);
                upgradeName.invoke(__instance);

                Method upgradeBlock = abstractCardClass.getDeclaredMethod("upgradeBlock", int.class);
                upgradeBlock.setAccessible(true);
                upgradeBlock.invoke(__instance, UPGRADED_BLOCK);

                __instance.selfRetain = true;

                __instance.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                __instance.initializeDescription();

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Defend_Watcher.class, method = "use")
    public static class Defend_Purple_PostfixUse {
        @SpirePostfixPatch
        public static void use(Defend_Watcher __instance, AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            if (StarterDeckRework.swapWatcherDefends && __instance.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ScryAction(SCRY_AMOUNT));
            }
        }
    }
}
