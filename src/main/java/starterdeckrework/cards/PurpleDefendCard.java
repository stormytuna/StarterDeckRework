package starterdeckrework.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class PurpleDefendCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PurpleDefend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC,
            CardColor.PURPLE
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 5;
    private static final int UPGRADED_BLOCK = 1;

    public PurpleDefendCard() {
        super(cardInfo, true);

        setBlock(BLOCK, UPGRADED_BLOCK);
        setSelfRetain(false, true);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PurpleDefendCard();
    }
}
