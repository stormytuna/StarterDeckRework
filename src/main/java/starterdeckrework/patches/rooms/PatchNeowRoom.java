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
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType;

import basemod.BaseMod;
import starterdeckrework.cards.*;
import starterdeckrework.StarterDeckRework;

public class PatchNeowRoom {
    @SpirePatch(clz = NeowRoom.class, method = "update")
    public static class ChooseStartingCardsPatch {
		public static Boolean shownCards = true;
		public static CardGroup previouslyChosenCards = null;

        @SpirePostfixPatch
        public static void patch(NeowRoom __instance) {
			if (shownCards) {
			  return;
			}

			ArrayList<AbstractCard> allCards = new ArrayList<AbstractCard>(); 

			if (AbstractDungeon.player.chosenClass == PlayerClass.IRONCLAD) {
				allCards.add(new Analyze());
				allCards.add(new Bash());
				allCards.add(new BloodBarrier());
				allCards.add(new FrenzyCard());
				allCards.add(new SlimeShield());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.THE_SILENT) {
				allCards.add(new HeelKick());
				allCards.add(new HiddenBlade());
				allCards.add(new Neutralize());
				allCards.add(new Survivor());
				allCards.add(new TearGas());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.DEFECT) {
				allCards.add(new DarkWeb());
				allCards.add(new DefensiveProtocols());
				allCards.add(new DryRun());
				allCards.add(new Dualcast());
				allCards.add(new Zap());
			} else if (AbstractDungeon.player.chosenClass == PlayerClass.WATCHER) {
				allCards.add(new CollapseReality());
				allCards.add(new Eruption());
				allCards.add(new Flagellate());
				allCards.add(new Premonition());
				allCards.add(new Vigilance());
			} else {
				return;
			}

			if (previouslyChosenCards == null) {
				CardGroup cards = new CardGroup(CardGroupType.UNSPECIFIED);
				for (int i = 0; i < StarterDeckRework.startingCardChoices; i++) {
				  int nextCardIndex = AbstractDungeon.cardRandomRng.random(0, allCards.size() - 1);
				  AbstractCard nextCard = allCards.remove(nextCardIndex);
				  cards.addToTop(nextCard);
				}

				previouslyChosenCards = cards;
			}

			if (previouslyChosenCards.size() > 2) {
				UIStrings chooseStartingCards = CardCrawlGame.languagePack.getUIString(StarterDeckRework.makeID("ChooseStartingCards"));
				BaseMod.openCustomGridScreen(previouslyChosenCards, 2, chooseStartingCards.TEXT[0], cards -> {
				  if (cards.size() != 2) {
					return;
				  }

				  addChosenCardsToDeck(cards);
				});
			} else {
				addChosenCardsToDeck(previouslyChosenCards.group);
			}

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

		private static void addChosenCardsToDeck(ArrayList<AbstractCard> cards) {
			for (AbstractCard card : cards) {
				AbstractDungeon.player.masterDeck.addToTop(card);
			}

			shownCards = true;
			SaveHelper.saveIfAppropriate(SaveType.ENTER_ROOM);
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
				ChooseStartingCardsPatch.previouslyChosenCards = null;
			}
		}
	}
}
