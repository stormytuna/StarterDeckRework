package starterdeckrework.powers;

import static starterdeckrework.StarterDeckRework.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BloodBarrierPower extends BasePower {
    public static final String POWER_ID = makeID("BloodBarrier");

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private int hpLoss;

    public BloodBarrierPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        hpLoss = 1;
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new LoseHPAction(owner, owner, hpLoss, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new GainBlockAction(owner, this.amount));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.hpLoss++;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.hpLoss + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}
