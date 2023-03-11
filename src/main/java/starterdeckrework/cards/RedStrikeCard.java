package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class RedStrikeCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RedStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC,
            CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int ENERGY_NEXT_TURN = 1;

    public RedStrikeCard() {
        super(cardInfo, true);

        setDamage(DAMAGE, UPGRADED_DAMAGE);
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        if (this.upgraded) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EnergizedBluePower(abstractPlayer, ENERGY_NEXT_TURN)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RedStrikeCard();
    }
}
