package starterdeckrework.patches.rooms;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.blue.Dualcast;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.cards.green.Survivor;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType;

import basemod.BaseMod;
import starterdeckrework.cards.*;
import starterdeckrework.StarterDeckRework;

public class PatchNeowRoom {
    @SpirePatch(clz = NeowRoom.class, method = "update")
    public static class ChooseStartingCardsPatch {
		public static Boolean shownCards = true;

        @SpirePostfixPatch
        public static void patch(NeowRoom __instance) {
			if (shownCards) {
			  return;
			}


			CardGroup cards = new CardGroup(CardGroupType.UNSPECIFIED); 

			if (AbstractDungeon.player.chosenClass == PlayerClass.IRONCLAD) {
				cards.addToTop(new Analyze());
				cards.addToTop(new Bash());
				cards.addToTop(new BloodBarrier());
				cards.addToTop(new FrenzyCard());
				cards.addToTop(new SlimeShield());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.THE_SILENT) {
				cards.addToTop(new HeelKick());
				cards.addToTop(new HiddenBlade());
				cards.addToTop(new Neutralize());
				cards.addToTop(new Survivor());
				cards.addToTop(new TearGas());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.DEFECT) {
				cards.addToTop(new DarkWeb());
				cards.addToTop(new DefensiveProtocols());
				cards.addToTop(new DryRun());
				cards.addToTop(new Dualcast());
				cards.addToTop(new Zap());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.WATCHER) {
				cards.addToTop(new CollapseReality());
				cards.addToTop(new Eruption());
				cards.addToTop(new Flagellate());
				cards.addToTop(new Premonition());
				cards.addToTop(new Vigilance());
			}

			UIStrings chooseStartingCards = CardCrawlGame.languagePack.getUIString(StarterDeckRework.makeID("ChooseStartingCards"));
			BaseMod.openCustomGridScreen(cards, 2, chooseStartingCards.TEXT[0], x -> {
				if (x.isEmpty()) {
					return;
				}

				for (AbstractCard card : x) {
					AbstractDungeon.player.masterDeck.addToTop(card);
				}

				shownCards = true;
				SaveHelper.saveIfAppropriate(SaveType.ENTER_ROOM);
			});

			if (AbstractDungeon.player.chosenClass == PlayerClass.IRONCLAD) {
				if (StarterDeckRework.swapIroncladStrikeForFrenzy) {
					AbstractDungeon.player.masterDeck.removeCard("Bash");
					AbstractDungeon.player.masterDeck.removeCard("StarterDeckRework:Frenzy");
				} else {
					AbstractDungeon.player.masterDeck.removeCard("Bash");
					AbstractDungeon.player.masterDeck.removeCard("Strike_R");
				}
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.THE_SILENT) {
				AbstractDungeon.player.masterDeck.removeCard("Neutralize");
				AbstractDungeon.player.masterDeck.removeCard("Survivor");
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.DEFECT) {
				AbstractDungeon.player.masterDeck.removeCard("Zap");
				AbstractDungeon.player.masterDeck.removeCard("Dualcast");
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.WATCHER) {
				AbstractDungeon.player.masterDeck.removeCard("Eruption");
				AbstractDungeon.player.masterDeck.removeCard("Vigilance");
			}
        }
    }

	@SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CONSTRUCTOR, paramtypez={
		String.class,
		String.class,
		AbstractPlayer.class,
		ArrayList.class
	})
	public static class ResetStartingCardChoice {
		@SpirePostfixPatch
		public static void patch(AbstractDungeon __instance, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
			if (levelId.equals(Exordium.ID) && AbstractDungeon.floorNum == 0) {
				ChooseStartingCardsPatch.shownCards = false;
			}
		}
	}
}
