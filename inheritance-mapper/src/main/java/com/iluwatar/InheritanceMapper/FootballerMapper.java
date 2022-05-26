package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Footballer;
import com.iluwatar.InheritanceMapper.ClassObject.Player;

/**
 * Concrete Mapper for Footballer class.
 */
public class FootballerMapper extends AbstractPlayerMapper<Footballer> {

    /**
     * override Save method for database update.
     *
     * @param p      Footballer class object to save
     * @param update weather to save as an update or insert
     * @return Footballer
     */
    public Footballer save(final Footballer p, final boolean update) {
        return (Footballer) super.save(p, update);
    }

    /**
     * override load method.
     *
     * @param rows a list of Player from database
     * @return Footballer
     */
    public Footballer load(final Player rows) {
        Player p = super.load(rows);
        if (p instanceof Footballer) {
            return (Footballer) p;
        } else {
            return null;
        }
    }

    /**
     * find method for Footballer class.
     *
     * @param id Player id
     * @return Footballer
     */
    public Footballer find(final int id) {
        Player rows = abstractFind(id);
        return load(rows);
    }
    /**
     * update method for Footballer class.
     *
     * @param p Player
     * @return Footballer
     */
    @Override
    public Footballer update(final Player p) {
        Footballer f = (Footballer) p;
        save(f, true);
        return f;
    }

    /**
     * insert method for Footballer class.
     *
     * @param p Player
     * @return Footballer
     */
    @Override
    public Footballer insert(final Player p) {
        Footballer f = (Footballer) p;
        save(f, false);
        return f;
    }

}
