package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class GreenDefendCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GreenDefend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC,
            CardColor.GREEN
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 5;
    private static final int UPGRADED_BLOCK = 1;

    public GreenDefendCard() {
        super(cardInfo, true);

        setBlock(BLOCK, UPGRADED_BLOCK);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
    }

    @Override
    public void triggerOnManualDiscard() {
        if (this.upgraded) {
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GreenDefendCard();
    }
}
