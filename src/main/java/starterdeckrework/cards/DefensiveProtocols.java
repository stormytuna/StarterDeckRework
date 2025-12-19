package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class DefensiveProtocols extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "DefensiveProtocols",
        1,
        CardType.SKILL,
        CardTarget.ALL,
        CardRarity.BASIC,
        CardColor.BLUE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_BLOCK = 5;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BASE_LOCK_ON = 1;
    private static final int UPGRADE_LOCK_ON = 1;

    public DefensiveProtocols() {
        super(cardInfo);
        setBlock(BASE_BLOCK, UPGRADE_BLOCK);
        setMagic(BASE_LOCK_ON, UPGRADE_LOCK_ON);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));

        for (AbstractMonster monster : (AbstractDungeon.getCurrRoom().monsters.monsters)) {
            addToBot(new ApplyPowerAction(monster, abstractPlayer, new LockOnPower(monster, magicNumber)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefensiveProtocols();
    }
}
