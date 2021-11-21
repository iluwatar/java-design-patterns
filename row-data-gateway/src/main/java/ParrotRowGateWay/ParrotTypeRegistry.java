package ParrotRowGateWay;

import ParrotDataModel.ParrotType;
import db.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class ParrotTypeRegistry {

    private static Map<Integer, ParrotType> parrotTypeMap = new HashMap<>();

    private ParrotTypeRegistry() {
    }

    private static void loadParrotTypeRegistry() throws SQLException {
        DataBaseConnection db = new DataBaseConnection();
        Connection connection = db.getConnection();
        PreparedStatement getStmt = null;

        if (connection != null) {
            String getAllParrotTypes = "Select * from ParrotType";
            getStmt = connection.prepareStatement(getAllParrotTypes);
            ResultSet parrotTypeResultSet = getStmt.executeQuery();

            if (parrotTypeResultSet != null) {
                while (parrotTypeResultSet.next()) {
                    ParrotType parrotType = new ParrotType(parrotTypeResultSet.getInt("ParrotTypeId"),
                            parrotTypeResultSet.getString("Species"));
                    parrotTypeMap.put(parrotType.getParrotTypeId(), parrotType);
                }
                db.closeConnection(connection);
            }
        }
    }

    /**
     * Get Parrot Type by Id from Registry
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public static ParrotType getParrotTypeById(int id) throws SQLException {
        if (parrotTypeMap.isEmpty()) {
            loadParrotTypeRegistry();
        }

        ParrotType parrotType = null;
        if (parrotTypeMap.containsKey(id)) {
            parrotType = parrotTypeMap.get(id);
        }

        return parrotType;
    }

    /**
     * Print each Parrot Type that is in Registry
     */
    public static void printParrotTypeInformation() {
        for (Map.Entry<Integer, ParrotType> entry : parrotTypeMap.entrySet()) {
            System.out.println("Id = " + entry.getKey() + ", Species = " + entry.getValue().getSpecies());
        }
    }

}
