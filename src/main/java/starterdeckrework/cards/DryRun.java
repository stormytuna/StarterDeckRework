package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.powers.DryRunPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class DryRun extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "DryRun",
        1,
        CardType.POWER,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.BLUE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_STACKS = 4;
    private static final int UPGRADE_STACKS = 2;

    public DryRun() {
        super(cardInfo);
        setMagic(BASE_STACKS, UPGRADE_STACKS);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DryRunPower(abstractPlayer, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DryRun();
    }
}
