package starterdeckrework.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;

import starterdeckrework.util.CardInfo;

import static starterdeckrework.StarterDeckRework.makeID;

public class CollapseReality extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
        "CollapseReality",
        1,
        CardType.SKILL,
        CardTarget.SELF,
        CardRarity.BASIC,
        CardColor.PURPLE
    );

    public static final String ID = makeID(cardInfo.baseId);

    public CollapseReality() {
        super(cardInfo);
        cardsToPreview = new Miracle();
    }

    public void renderCardTip(SpriteBatch sb) {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.stance.ID.equals("Wrath")) {
                cardsToPreview = new Smite();
            } else if (AbstractDungeon.player.stance.ID.equals("Calm")) {
                cardsToPreview = new Safety();
            } else {
                cardsToPreview = new Miracle();
            }
        }
        
        super.renderCardTip(sb);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractCard card;
        if (AbstractDungeon.player.stance.ID.equals("Wrath")) {
            card = new Smite();
        } else if (AbstractDungeon.player.stance.ID.equals("Calm")) {
            card = new Safety();
        } else {
            card = new Miracle();
        }

        if (upgraded) {
            card.upgrade();
        }

        addToBot(new MakeTempCardInHandAction(card));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CollapseReality();
    }
}
