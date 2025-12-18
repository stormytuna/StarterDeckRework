package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class SlimeShield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "SlimeShield",
        1,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_BLOCK = 10;
    private static final int UPGRADE_BLOCK = 4;

    public SlimeShield() {
        super(cardInfo);
        setBlock(BASE_BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, block));
        addToBot(new MakeTempCardInHandAction(new Slimed()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SlimeShield();
    }
}
