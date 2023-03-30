package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchDefend_Red {
    private static final int UPGRADED_BLOCK = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R");

    @SpirePatch(clz = Defend_Red.class, method = "upgrade")
    public static class Defend_Red_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Defend_Red __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapIroncladDefends) {
                Class<?> defendRedClass = __instance.getClass();
                Class<?> abstractCardClass = defendRedClass.getSuperclass();

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

    @SpirePatch(clz = Defend_Red.class, method = "use")
    public static class Defend_Red_PrefixUse {
        @SpirePrefixPatch
        public static SpireReturn patch(Defend_Red __instance, AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            if (StarterDeckRework.swapIroncladDefends && __instance.upgraded) {
                int aliveMonstersCount = 0;
                for (AbstractMonster monster : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        aliveMonstersCount++;
                    }
                }

                int numTimesToGainBlock = __instance.upgraded ? aliveMonstersCount : 1;

                for (int i = 0; i < numTimesToGainBlock; i++) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(abstractPlayer, abstractPlayer, __instance.block));
                }

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }
}
