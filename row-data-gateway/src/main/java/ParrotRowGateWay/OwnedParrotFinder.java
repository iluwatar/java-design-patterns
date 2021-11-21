package ParrotRowGateWay;

import db.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnedParrotFinder {

    private static final String FIND_ALL = "Select * from OwnedParrot";
    private static final String FIND_BY_ID = "Select * from OwnedParrot where OwnedParrotId=?";
    private static final String COUNT_FROM_OWNED_PARROT = "Select Count(*) as count from OwnedParrot";

    public static final String OWNED_PARROT_ID = "OwnedParrotId";
    public static final String PARROT_TYPE_ID = "ParrotTypeId";
    public static final String PARROT_NAME = "ParrotName";
    public static final String PARROT_AGE = "ParrotAge";
    public static final String COLOR = "Color";
    public static final String TAMED = "Tamed";

    private DataBaseConnection db;

    public OwnedParrotFinder() {
    }

    /**
     * Class Constructor with db connection as param
     *
     * @param db
     */
    public OwnedParrotFinder(DataBaseConnection db) {
        this.db = db;
    }

    /**
     * Find a OwnedParrot from Database by Id and return a gateway object
     *
     * @param id
     * @return OwnedParrotGateWay
     * @throws SQLException
     */
    public OwnedParrotGateWay findById(Integer id) throws SQLException {
        OwnedParrotGateWay ownedParrotGateWay = ParrotRegistry.getOwnedParrot(id);

        if (ownedParrotGateWay != null) {
            return ownedParrotGateWay;
        }

        if (db == null) {
            db = new DataBaseConnection();
        }
        try (Connection connection = db.getConnection()) {
            PreparedStatement getStmt;

            if (connection != null) {

                getStmt = connection.prepareStatement(FIND_BY_ID);
                getStmt.setInt(1, id);

                ResultSet rs = getStmt.executeQuery();

                if (rs != null && rs.next()) {
                    ownedParrotGateWay = new OwnedParrotGateWay(rs.getInt(OWNED_PARROT_ID),
                            rs.getInt(PARROT_TYPE_ID),
                            rs.getString(PARROT_NAME),
                            rs.getInt(PARROT_AGE),
                            rs.getString(COLOR),
                            rs.getBoolean(TAMED));
                    ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
                    getStmt.close();
                    db.closeConnection(connection);
                    return ownedParrotGateWay;
                }
            }
        }
        return null;
    }

    /**
     * Find all entries in the database for OwnedParrot and return a list of gateway objects
     *
     * @return
     * @throws SQLException
     */
    public List<OwnedParrotGateWay> findAll() throws SQLException {

        if (db == null) {
            db = new DataBaseConnection();
        }
        Connection connection = db.getConnection();
        PreparedStatement getSizeStmt = null;
        PreparedStatement getAllOwnedParrotsStmt = null;

        List<OwnedParrotGateWay> ownedParrotGateWayList = new ArrayList<>();

        if (connection != null) {

            getSizeStmt = connection.prepareStatement(COUNT_FROM_OWNED_PARROT);
            ResultSet rs = getSizeStmt.executeQuery();

            if (rs != null && rs.next()) {
                int numberOfOwnedParrots = rs.getInt("count");
                if (ParrotRegistry.getParrotRegistrySize() == numberOfOwnedParrots) {
                    ownedParrotGateWayList = ParrotRegistry.getAllOwnedParrotsInRegistry();
                } else {
                    getAllOwnedParrotsStmt = connection.prepareStatement(FIND_ALL);
                    ResultSet ownedParrotsResult = getAllOwnedParrotsStmt.executeQuery();

                    if (ownedParrotsResult != null) {
                        while (ownedParrotsResult.next()) {
                            OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(rs.getInt(OWNED_PARROT_ID),
                                    rs.getInt(PARROT_TYPE_ID),
                                    rs.getString(PARROT_NAME),
                                    rs.getInt(PARROT_AGE),
                                    rs.getString(COLOR),
                                    rs.getBoolean(TAMED));
                            ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
                        }
                        rs.close();
                        ownedParrotsResult.close();
                        getSizeStmt.close();
                        getAllOwnedParrotsStmt.close();
                        db.closeConnection(connection);
                        ownedParrotGateWayList = ParrotRegistry.getAllOwnedParrotsInRegistry();
                    }
                }
            }
        }

        return ownedParrotGateWayList;
    }

}
