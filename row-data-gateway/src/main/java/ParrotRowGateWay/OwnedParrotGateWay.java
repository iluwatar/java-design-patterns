package ParrotRowGateWay;

import ParrotDataModel.OwnedParrot;
import db.DataBaseConnection;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class OwnedParrotGateWay {

    private DataBaseConnection db;

    public OwnedParrotGateWay(DataBaseConnection db, OwnedParrot ownedParrot) {
        this.db = db;
        this.ownedParrotId = ownedParrot.getOwnedParrotId();
        this.parrotTypeId = ownedParrot.getParrotTypeId();
        this.parrotName = ownedParrot.getParrotName();
        this.parrotAge = ownedParrot.getParrotAge();
        this.color = ownedParrot.getColor();
        this.tamed = ownedParrot.getTamed();
    }

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public OwnedParrotGateWay(Integer ownedParrotId, Integer parrotTypeId, String parrotName,
                              Integer parrotAge, String color, boolean tamed) {
        this.ownedParrotId = ownedParrotId;
        this.parrotTypeId = parrotTypeId;
        this.parrotName = parrotName;
        this.parrotAge = parrotAge;
        this.color = color;
        this.tamed = tamed;
    }

    public OwnedParrotGateWay(OwnedParrot ownedParrot) {
        this.ownedParrotId = ownedParrot.getOwnedParrotId();
        this.parrotTypeId = ownedParrot.getParrotTypeId();
        this.parrotName = ownedParrot.getParrotName();
        this.parrotAge = ownedParrot.getParrotAge();
        this.color = ownedParrot.getColor();
        this.tamed = ownedParrot.getTamed();
    }

    private Integer ownedParrotId;

    private Integer parrotTypeId;

    private String parrotName;

    private Integer parrotAge;

    private String color;

    private Boolean tamed;

    /**
     * Inserts a new record into the db
     *
     * @throws SQLException
     */
    public void insert() throws SQLException {

        if (db == null) {
            db = new DataBaseConnection();
        }

        Connection connection = db.getConnection();
        PreparedStatement insertStmt = null;

        if (connection != null) {
            String getAllParrotTypes = "INSERT INTO OwnedParrot (ParrotTypeId, ParrotName, ParrotAge, Color, Tamed) " +
                    "VALUES (?, ?, ?, ?, ?);";
            insertStmt = connection.prepareStatement(getAllParrotTypes);
            insertStmt.setInt(1, parrotTypeId);
            insertStmt.setString(2, parrotName);
            insertStmt.setInt(3, parrotAge);
            insertStmt.setString(4, color);
            insertStmt.setBoolean(5, tamed);

            Integer insertResults = insertStmt.executeUpdate();

            // get id after insert and update this object
            String findByIdStatement = "Select MAX(OwnedParrotId) as OwnedParrotId from OwnedParrot";
            PreparedStatement getStmt = connection.prepareStatement(findByIdStatement);

            ResultSet rs = getStmt.executeQuery();

            if (rs != null && rs.next()) {
                this.ownedParrotId = rs.getInt("OwnedParrotId");
            }

            if (insertResults == 1) {
                ParrotRegistry.addOwnedParrot(this);
                LOGGER.log(Level.INFO, "Successfully inserted into OwnedParrots Table");
            } else {
                LOGGER.log(Level.INFO, "Failed to insert id: " + ownedParrotId);
            }
            getStmt.close();
            rs.close();
        }
        insertStmt.close();
    }

    /**
     * Updates the current record
     *
     * @throws SQLException
     */
    public void update() throws SQLException {
        if (db == null) {
            db = new DataBaseConnection();
        }
        Connection connection = db.getConnection();
        PreparedStatement updateStmt = null;

        if (connection != null) {
            String getAllParrotTypes = "UPDATE OwnedParrot " +
                    "SET ParrotName=?, ParrotAge=?, Color=?, Tamed=? " +
                    "WHERE ParrotTypeId=?";
            updateStmt = connection.prepareStatement(getAllParrotTypes);
            updateStmt.setString(1, parrotName);
            updateStmt.setInt(2, parrotAge);
            updateStmt.setString(3, color);
            updateStmt.setBoolean(4, tamed);
            updateStmt.setInt(5, parrotTypeId);

            Integer insertResults = updateStmt.executeUpdate();
            if (insertResults == 1) {
                ParrotRegistry.updateOwnedParrot(this);
                LOGGER.log(Level.INFO, "Successfully updated OwnedParrots Table");
            } else {
                LOGGER.log(Level.INFO, "Failed to update id: " + ownedParrotId);
            }
            updateStmt.close();
        }
    }

    /**
     * Deleted the current record
     *
     * @throws SQLException
     */
    public void delete() throws SQLException {
        if (db == null) {
            db = new DataBaseConnection();
        }
        Connection connection = db.getConnection();
        PreparedStatement deleteStmt = null;

        if (connection != null) {
            String getAllParrotTypes = "DELETE FROM OwnedParrot " +
                    "WHERE ParrotTypeId=?";
            deleteStmt = connection.prepareStatement(getAllParrotTypes);
            deleteStmt.setInt(1, parrotTypeId);

            Integer insertResults = deleteStmt.executeUpdate();
            if (insertResults == 1) {
                ParrotRegistry.removeOwnedParrot(this);
                LOGGER.log(Level.INFO, "Successfully deleted OwnedParrots Table");
            } else {
                LOGGER.log(Level.INFO, "Failed to delete id: " + ownedParrotId);
            }
            deleteStmt.close();
        }

    }

}
