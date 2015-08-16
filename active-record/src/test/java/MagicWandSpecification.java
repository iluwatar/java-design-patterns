import com.iluwatar.active.record.SpellCastException;
import com.iluwatar.active.record.WandCoreType;
import com.iluwatar.active.record.WandWoodType;
import com.iluwatar.active.record.MagicWand;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static  org.junit.Assert.assertTrue;

/**
 * Created by Stephen Lazarionok.
 */
public class MagicWandSpecification {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldCastFireball_IfMagicPower10AndMore() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(10.0d);

        assertTrue(wand.calculateMagicPower() == 10.0d);
        wand.castFireball();
    }

    @Test
    public void shouldNotCastFireball_IfMagicPowerLessThan10() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(9.0d);

        assertTrue(wand.calculateMagicPower() == 9.0d);
        exception.expect(SpellCastException.class);

        wand.castFireball();
    }

    @Test
    public void shouldCastLigthing_IfMagicPower20AndMore() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(20.5d);

        assertTrue(wand.calculateMagicPower() == 20.5d);
        wand.castLighting();
    }

    @Test
    public void shouldNotCastLigthing_IfMagicPowerLessThan20() throws SpellCastException {

        final MagicWand wand = new MagicWand();
        wand.setWood(WandWoodType.WINE);
        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(18.0d);

        assertTrue(wand.calculateMagicPower() == 18.0d);
        exception.expect(SpellCastException.class);
        wand.castLighting();
    }
}
