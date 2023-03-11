package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class FrenzyCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Frenzy",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC,
            CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int HP_LOSS = 2;
    private static final int STRENGTH_GAIN = 1;
    private static final int UPGRADED_STRENGTH_GAIN = 1;

    public FrenzyCard() {
        super(cardInfo);

        setMagic(STRENGTH_GAIN, UPGRADED_STRENGTH_GAIN);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractPlayer, new DamageInfo(abstractPlayer, HP_LOSS, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, this.magicNumber), 1, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FrenzyCard();
    }
}
