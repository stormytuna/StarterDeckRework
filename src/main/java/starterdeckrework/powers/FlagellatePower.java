package starterdeckrework.powers;

import static starterdeckrework.StarterDeckRework.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class FlagellatePower extends BasePower {
    public static final String POWER_ID = makeID("Flagellate");

    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private boolean tookDamage = false;

    public FlagellatePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount)  {
        if (info.owner != null && info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != owner && damageAmount > 0) {
            tookDamage = true;
        }

        return damageAmount;
    }

    public void atStartOfTurn()  {
        if (tookDamage) {
            // Was originally in onAttacked but entering Divinity during enemies' turn brings you out of it during your turn
            flash();
            addToTop(new ApplyPowerAction(owner, owner, new MantraPower(owner, this.amount)));
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
