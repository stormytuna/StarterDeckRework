package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class HiddenBlade extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "HiddenBlade",
        1,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.GREEN
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int DRAW = 2;
    private static final int BASE_SHIVS = 1;
    private static final int UPGRADE_SHIVS = 1;

    public HiddenBlade() {
        super(cardInfo);
        setMagic(BASE_SHIVS, UPGRADE_SHIVS);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(DRAW));
        addToBot(new MakeTempCardInHandAction(new Shiv(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HiddenBlade();
    }
}
