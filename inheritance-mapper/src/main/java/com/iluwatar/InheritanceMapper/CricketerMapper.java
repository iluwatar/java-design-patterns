package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Cricketer;
import com.iluwatar.InheritanceMapper.ClassObject.Player;

/**
 * Concrete Mapper for Cricketer class.
 */
public class CricketerMapper extends AbstractPlayerMapper<Cricketer> {
    /**override Save method for database update.
     *
     * @param p      Cricketer class object to save
     * @param update weather to save as an update or insert
     * @return Cricketer
     */
    public Cricketer save(final Cricketer p, final boolean update) {
        return (Cricketer) super.save(p, update);
    }

    /**override load method.
     *
     * @param rows a list of Player from database
     * @return Cricketer
     */
    public Cricketer load(final Player rows) {
        Player v = super.load(rows);
        if (v instanceof Cricketer) {
            return (Cricketer) v;
        } else {
            return null;
        }
    }

    /**find method for Cricketer class.
     *
     * @param id Player id
     * @return Cricketer
     */
    public Cricketer find(final int id) {
        Player rows = abstractFind(id);
        return load(rows);
    }

    /**
     * update method for Cricketer class.
     *
     * @param p Player
     * @return Cricketer
     */
    @Override
    public Cricketer update(final Player p) {
        Cricketer c = (Cricketer) p;
        save(c, true);
        return c;
    }

    /**
     * insert method for Cricketer class.
     *
     * @param p Player
     * @return Cricketer
     */
    @Override
    public Cricketer insert(final Player p) {
        Cricketer c = (Cricketer) p;
        save(c, false);
        return c;
    }
}
