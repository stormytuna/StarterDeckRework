package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class HeelKick extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "HeelKick",
        1,
        CardType.ATTACK,
        CardTarget.ENEMY,
        CardRarity.BASIC,
        CardColor.GREEN
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int DISCARD = 1;

    public HeelKick() {
        super(cardInfo);
        setDamage(BASE_DAMAGE, UPGRADE_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new DiscardAction(abstractPlayer, abstractPlayer, DISCARD, false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeelKick();
    }
}
