import com.iluwatar.active.record.SpellCastException;
import com.iluwatar.active.record.WandCoreType;
import com.iluwatar.active.record.WandWoodType;
import com.iluwatar.active.record.MagicWand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Stephen Lazarionok.
 */
public class MagicWandSpecification {

    @Test
    public void shouldCastFireball_IfMaigPower10AndMore() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(10.0d);

        Assert.assertTrue(stick.calculateMagicPower() == 10.0d);
        stick.castFireball();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastFireball_IfMaigPowerLessThan10() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(9.0d);

        Assert.assertTrue(stick.calculateMagicPower() == 9.0d);
        stick.castFireball();
    }

    @Test
    public void shouldCastLigthing_IfMaigPower20AndMore() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(20.5d);

        Assert.assertTrue(stick.calculateMagicPower() == 20.5d);
        stick.castLighting();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastLigthing_IfMaigPowerLessThan20() throws SpellCastException {

        final MagicWand stick = new MagicWand();
        stick.setWood(WandWoodType.WINE);
        stick.setCore(WandCoreType.DRAGON_HEARTSTRING);
        stick.setLengthInches(18.0d);

        Assert.assertTrue(stick.calculateMagicPower() == 18.0d);
        stick.castLighting();
    }
}
