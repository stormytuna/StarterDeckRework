package starterdeckrework.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import javassist.*;
import starterdeckrework.StarterDeckRework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatchDefend_Green {
    private static final int UPGRADED_BLOCK = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_G");

    @SpirePatch(clz = Defend_Green.class, method = "upgrade")
    public static class Defend_Green_PrefixUpgrade {
        @SpirePrefixPatch
        public static SpireReturn patch(Defend_Green __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            if (StarterDeckRework.swapSilentDefends) {
                Class<?> defendGreenClass = __instance.getClass();
                Class<?> abstractCardClass = defendGreenClass.getSuperclass();

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

    @SpirePatch(clz = Defend_Green.class, method = "<ctor>")
    public static class Defend_Green_AddTriggerOnManualDiscard {
        @SpireRawPatch
        public static void raw(CtBehavior ctMethodToPatch) throws CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            CtMethod triggerOnManualDiscard = CtNewMethod.make(CtClass.voidType, "triggerOnManualDiscard", new CtClass[] {  }, null, "{ if (this.upgraded) { com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, this.block)); } }", ctClass);
            ctClass.addMethod(triggerOnManualDiscard);
        }
    }
}
