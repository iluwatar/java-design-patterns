package com.iluwatar.InheritanceMapper;


import com.iluwatar.InheritanceMapper.ClassObject.Bowler;
import com.iluwatar.InheritanceMapper.ClassObject.Cricketer;
import com.iluwatar.InheritanceMapper.ClassObject.Footballer;
import com.iluwatar.InheritanceMapper.ClassObject.Player;

/**Main Vehicle Mapper that encapsulate all other Mappers.
 */
public class PlayerMapper extends Mapper {
    /**instance of FootballerMapper.
     */
    private final FootballerMapper footballerMapper = new FootballerMapper();
    /**instance of BowlerMapper.
     */
    private final BowlerMapper bowlerMapper = new BowlerMapper();
    /**instance of CricketerMapper.
     */
    private final CricketerMapper cricketerMapper = new CricketerMapper();

    /**method that return a mapper for a vehicle subclass.
     * @param p Player
     * @return Mapper
     */
    private AbstractPlayerMapper<? extends Player> mapperFor(final Player p) {
        if (p instanceof Footballer) {
            return footballerMapper;
        } else if (p instanceof Bowler) {
            return bowlerMapper;
        } else if (p instanceof Cricketer) {
            return cricketerMapper;
        } else {
            return null;
        }
    }

    /**update method to be called by client.
     * @param p Player
     * @return Player
     */
    Player update(final Player p) {
        Player found = mapperFor(p).find(p.getId());
        if (found != null) {
            mapperFor(p).update(p);
            return p;
        }
        return null;
    }
    /**insert method to be called by client.
     * @param p Player
     * @return Vehicle
     */
    Player insert(final Player p) {
        mapperFor(p).insert(p);
        return p;
    }

    /**delete method to be called by client.
     * @param p Player
     */
    void delete(final Player p) {
        mapperFor(p).delete(p);
    }

    /**find method used by client to find Player using id.
     * @param id Player id
     * @return Player
     */
    Player find(final int id) {
        Player result;
        result = footballerMapper.find(id);

        if (result == null) {
            result = cricketerMapper.find(id);
        }
        if (result == null) {
            result = bowlerMapper.find(id);
        }

        return result;
    }
}
