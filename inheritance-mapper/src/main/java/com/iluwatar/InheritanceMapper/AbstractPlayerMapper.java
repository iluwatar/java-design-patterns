package com.iluwatar.InheritanceMapper;



import com.iluwatar.InheritanceMapper.ClassObject.Player;



/**
 * an abstract Player mapper to be inherited by concrete Player mapper.
 *
 * @param <T> concrete Player class
 */
public abstract class AbstractPlayerMapper<T> extends Mapper {
    /**
     * Save method for database update.
     *
     * @param p     Player class object to save
     * @param update whelther to save as an update or insert
     * @return saved Player
     */
    public Player save(final Player p, final boolean update) {
        super.save(p, update);
        return p;
    }
    /**
     * method for loading Player object.
     *
     * @param rows a list of Player from database
     * @return loaded Player
     */
    public Player load(final Player rows) {
        return super.load(rows);
    }




    /**
     * abstract update method for concrete mapper to override.
     *
     * @param p Player
     * @return Player
     */
    public abstract T update(Player p);

    /**
     * abstract insert method for concrete mapper to override.
     *
     * @param p Player
     * @return Player
     */
    public abstract T insert(Player p);
    /**
     * abstract find method for concrete mapper to override.
     *
     * @param id Player id
     * @return Player
     */
    public abstract T find(int id);




}
