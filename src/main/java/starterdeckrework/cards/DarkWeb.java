package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class DarkWeb extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "DarkWeb",
        2,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.BLUE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_BLOCK = 12;
    private static final int UPGRADE_BLOCK = 4;

    public DarkWeb() {
        super(cardInfo);
        setBlock(BASE_BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new ChannelAction(new Dark()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkWeb();
    }
}
