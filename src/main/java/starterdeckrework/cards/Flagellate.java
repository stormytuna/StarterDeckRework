package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import starterdeckrework.powers.FlagellatePower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class Flagellate extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "Flagellate",
        1,
        CardType.ATTACK,
        CardTarget.ENEMY,
        CardRarity.BASIC,
        CardColor.PURPLE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int POWER_AMOUNT = 4;

    public Flagellate() {
        super(cardInfo);
        setDamage(BASE_DAMAGE, UPGRADE_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new FlagellatePower(abstractPlayer, POWER_AMOUNT)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Flagellate();
    }
}
