package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class Analyze extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "Analyze",
        0,
        CardType.SKILL,
        CardTarget.ALL_ENEMY,
        CardRarity.BASIC,
        CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_VULNERABLE = 1;
    private static final int UPGRADE_VULNERABLE = 1;

    public Analyze() {
        super(cardInfo);
        setExhaust(true);
        setMagic(BASE_VULNERABLE, UPGRADE_VULNERABLE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (AbstractMonster monster : (AbstractDungeon.getCurrRoom().monsters.monsters)) {
            addToBot(new ApplyPowerAction(monster, abstractPlayer, new VulnerablePower(monster, magicNumber, false)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Analyze();
    }
}
