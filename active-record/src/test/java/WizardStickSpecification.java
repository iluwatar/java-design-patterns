import com.iluwatar.active.record.SpellCastException;
import com.iluwatar.active.record.WandCoreType;
import com.iluwatar.active.record.WandWoodType;
import com.iluwatar.active.record.MagicWand;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by Stephen Lazarionok.
 */
public class WizardStickSpecification {

    @Test
    public void shouldCastFireball_IfMaigPower10AndMore() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(10.0d);

        Assert.that(stick.calculateMagicPower() == 10.0d, "Magic power is calculated incorrectly");
        stick.castFireball();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastFireball_IfMaigPowerLessThan10() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(9.0d);

        Assert.that(stick.calculateMagicPower() == 9.0d, "Magic power is calculated incorrectly");
        stick.castFireball();
    }

    @Test
    public void shouldCastLigthing_IfMaigPower20AndMore() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(20.5d);

        Assert.that(stick.calculateMagicPower() == 20.5d, "Magic power is calculated incorrectly");
        stick.castLighting();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastLigthing_IfMaigPowerLessThan20() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(18.0d);

        Assert.that(stick.calculateMagicPower() == 18.0d, "Magic power is calculated incorrectly");
        stick.castLighting();
    }
}
