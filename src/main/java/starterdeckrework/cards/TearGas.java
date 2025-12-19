package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.powers.TearGasPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class TearGas extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "TearGas",
        2,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.GREEN
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_BLOCK = 9;
    private static final int UPGRADE_BLOCK = 3;
    private static final int BASE_POISON = 2;
    private static final int UPGRADE_POISON = 1;

    public TearGas() {
        super(cardInfo);
        setBlock(BASE_BLOCK, UPGRADE_BLOCK);
        setMagic(BASE_POISON, UPGRADE_POISON);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new TearGasPower(abstractPlayer, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TearGas();
    }
}
