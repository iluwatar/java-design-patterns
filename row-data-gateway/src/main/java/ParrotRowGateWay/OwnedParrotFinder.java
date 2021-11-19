package ParrotRowGateWay;

import db.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnedParrotFinder {

    private final String findAllStatement = "Select * from ParrotDataModel.OwnedParrot";

    private final String parrotTypeId = "ParrotTypeId";
    private final String parrotName = "ParrotName";
    private final String parrotAge = "ParrotAge";
    private final String color = "Color";
    private final String tamed = "Tamed";

    public OwnedParrotGateWay findById(Integer id) throws SQLException {
        OwnedParrotGateWay ownedParrotGateWay = ParrotRegistry.getOwnedParrot(id);

        if (ownedParrotGateWay != null) {
            return ownedParrotGateWay;
        }

        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        PreparedStatement getStmt;

        if (connection != null) {
            String findByIdStatement = "Select * from OwnedParrot where OwnedParrotId=?";
            getStmt = connection.prepareStatement(findByIdStatement);
            getStmt.setInt(1, id);

            ResultSet rs = getStmt.executeQuery();

            if (rs != null && rs.next()) {
                ownedParrotGateWay = new OwnedParrotGateWay(rs.getInt("OwnedParrotId"),
                        rs.getInt(parrotTypeId),
                        rs.getString(parrotName),
                        rs.getInt(parrotAge),
                        rs.getString(color),
                        rs.getBoolean(tamed));
                ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
                db.closeConnection(connection);
                return ownedParrotGateWay;
            }

        }
        return null;
    }

    public List<OwnedParrotGateWay> findAll() throws SQLException {

        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        PreparedStatement getSizeStmt = null;
        PreparedStatement getAllOwnedParrotsStmt = null;

        List<OwnedParrotGateWay> ownedParrotGateWayList = new ArrayList<>();

        if (connection != null) {
            String getNumberOfOwnedParrots = "Select Count(*) as count from OwnedParrot";
            getSizeStmt = connection.prepareStatement(getNumberOfOwnedParrots);
            ResultSet rs = getSizeStmt.executeQuery();

            if (rs != null && rs.next()) {
                int numberOfOwnedParrots = rs.getInt("count");
                if(ParrotRegistry.getParrotRegistrySize() == numberOfOwnedParrots) {
                    ownedParrotGateWayList = ParrotRegistry.getAllOwnedParrotsInRegistry();
                } else {
                    String getAllOwnedParrots = "Select * as count from OwnedParrot";
                    getAllOwnedParrotsStmt = connection.prepareStatement(getAllOwnedParrots);
                    ResultSet ownedParrotsResult = getAllOwnedParrotsStmt.executeQuery();

                    if(ownedParrotsResult != null) {
                        while (ownedParrotsResult.next()) {
                            OwnedParrotGateWay ownedParrotGateWay = new OwnedParrotGateWay(rs.getInt("OwnedParrotId"),
                                    rs.getInt(parrotTypeId),
                                    rs.getString(parrotName),
                                    rs.getInt(parrotAge),
                                    rs.getString(color),
                                    rs.getBoolean(tamed));
                            ParrotRegistry.addOwnedParrot(ownedParrotGateWay);
                        }
                        db.closeConnection(connection);
                        ownedParrotGateWayList = ParrotRegistry.getAllOwnedParrotsInRegistry();
                    }
                }
            }
        }

        return ownedParrotGateWayList;
    }

}
