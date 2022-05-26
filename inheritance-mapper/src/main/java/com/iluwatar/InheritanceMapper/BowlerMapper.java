package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Bowler;
import com.iluwatar.InheritanceMapper.ClassObject.Player;

/**
 * Concrete Mapper for Bowler class.
 */
public class BowlerMapper extends AbstractPlayerMapper<Bowler> {
    /**
     * override Save method for database update.
     * @param p      Bowler class object to save
     * @param update weather to save as an update or insert
     * @return Bowler
     */
    public Bowler save(final Bowler p, final boolean update) {
        return (Bowler) super.save(p, update);
    }

    /**
     * override load method.
     * @param rows a list of Player from database
     * @return Bowler
     */
    public Bowler load(final Player rows) {
        Player v = super.load(rows);
        if (v instanceof Bowler) {
            return (Bowler) v;
        } else {
            return null;
        }
    }

    /**
     * find method for Bowler class.
     *
     * @param id Player id
     * @return Bowler
     */
    public Bowler find(final int id) {
        Player rows = abstractFind(id);
        return load(rows);
    }

    /**
     * update method for Bowler class.
     * @param p Player
     * @return Bowler
     */
    @Override
    public Bowler update(final Player p) {
        Bowler b = (Bowler) p;
        save(b, true);
        return b;
    }

    /**
     * insert method for Bowler class.
     * @param p Player
     * @return Bowler
     */
    @Override
    public Bowler insert(final Player p) {
        Bowler b = (Bowler) p;
        save(b, false);
        return b;
    }
}
