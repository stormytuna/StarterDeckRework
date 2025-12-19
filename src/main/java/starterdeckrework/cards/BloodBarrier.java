package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.powers.BloodBarrierPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class BloodBarrier extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "BloodBarrier",
        1,
        CardType.POWER,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;

    public BloodBarrier() {
        super(cardInfo);
        setEthereal(true);
        setBlock(BASE_BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BloodBarrierPower(abstractPlayer, this.block)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodBarrier();
    }
}
