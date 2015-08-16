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
    public void shouldCastFireball_IfMagicPower10AndMore() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(10.0d);

        Assert.assertTrue(wand.calculateMagicPower() == 10.0d);
        wand.castFireball();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastFireball_IfMagicPowerLessThan10() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(9.0d);

        Assert.assertTrue(wand.calculateMagicPower() == 9.0d);
        wand.castFireball();
    }

    @Test
    public void shouldCastLigthing_IfMagicPower20AndMore() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(20.5d);

        Assert.assertTrue(wand.calculateMagicPower() == 20.5d);
        wand.castLighting();
    }

    @Test(expected = SpellCastException.class)
    public void shouldNotCastLigthing_IfMagicPowerLessThan20() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(18.0d);

        Assert.assertTrue(wand.calculateMagicPower() == 18.0d);
        wand.castLighting();
    }
}
