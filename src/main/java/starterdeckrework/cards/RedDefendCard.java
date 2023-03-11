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

public class RedDefendCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RedDefend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC,
            CardColor.RED
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 5;
    private static final int ENERGY_NEXT_TURN = 1;

    public RedDefendCard() {
        super(cardInfo, true);

        setBlock(BLOCK);
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int numTimesToGainBlock = this.upgraded ? AbstractDungeon.getCurrRoom().monsters.monsters.size() : 1;

        for (int i = 0; i < numTimesToGainBlock; i++) {
            addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RedDefendCard();
    }
}
