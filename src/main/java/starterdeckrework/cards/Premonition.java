package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.powers.BloodBarrierPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class Premonition extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "Premonition",
        0,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.PURPLE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_SCRY = 3;
    private static final int UPGRADE_SCRY = 2;
    private static final int DRAW = 1;

    public Premonition() {
        super(cardInfo);
        setMagic(BASE_SCRY, UPGRADE_SCRY);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(DRAW));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Premonition();
    }
}
