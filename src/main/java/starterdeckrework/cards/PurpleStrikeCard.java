package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class PurpleStrikeCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PurpleStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC,
            CardColor.PURPLE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int MANTRA_INCREASE = 2;

    public PurpleStrikeCard() {
        super(cardInfo, true);

        setDamage(DAMAGE, UPGRADED_DAMAGE);
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (this.upgraded) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MantraPower(abstractPlayer, MANTRA_INCREASE)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PurpleStrikeCard();
    }
}
