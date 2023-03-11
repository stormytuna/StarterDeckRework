package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class BlueDefendCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlueDefend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC,
            CardColor.BLUE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 5;
    private static final int UPGRADED_BLOCK = 1;

    private static final int DRAW_AMOUNT = 1;

    public BlueDefendCard() {
        super(cardInfo, true);

        setBlock(BLOCK, UPGRADED_BLOCK);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));

        if (this.upgraded) {
            addToBot(new DrawCardAction(abstractPlayer, DRAW_AMOUNT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlueDefendCard();
    }
}
