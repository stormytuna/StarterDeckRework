package starterdeckrework.powers;

import static starterdeckrework.StarterDeckRework.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class TearGasPower extends BasePower {
    public static final String POWER_ID = makeID("TearGas");

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public TearGasPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount)  {
        if (info.owner != null && info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != owner) {
            flash();
            addToTop(new ApplyPowerAction(info.owner, owner, new PoisonPower(info.owner, owner, amount)));
        }

        return damageAmount;
    }

    public void atStartOfTurn()  {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
