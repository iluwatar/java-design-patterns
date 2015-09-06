package com.iluwatar.active.record.sample;

import com.iluwatar.active.record.MagicWand;
import com.iluwatar.active.record.SpellCastException;
import com.iluwatar.active.record.WandCoreType;
import com.iluwatar.active.record.WandWoodType;

/**
 * Created by Stephen Lazarionok.
 */
public class Application {

    public static void main(final String[] args) throws SpellCastException {

        final MagicWand harryPotterWand = new MagicWand();

        harryPotterWand.setWood(WandWoodType.WINE);
        harryPotterWand.setCore(WandCoreType.PHOENIX_FEATHER);
        harryPotterWand.setLengthInches(11.0d);

        final long wandId = harryPotterWand.save();

        final MagicWand wand = MagicWand.find(wandId);

        wand.castFireball();

        wand.setCore(WandCoreType.DRAGON_HEARTSTRING);
        wand.setLengthInches(21.0d);

        wand.update();

        MagicWand.find(wandId).castLighting();
        MagicWand.find(wandId).delete();
    }
}
