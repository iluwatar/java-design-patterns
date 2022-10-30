package com.iluwatar.activeobject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * ActiveRow attempts to implement the fundamental ruby on rails 'Active Record' design pattern in
 * Java.
 */
public class ActiveRow {

	String id;
	DB dataBase;
	Connection con;
	String read;
	String delete;
	String write;
	int columnCount;
	ArrayList<String> columns;
	ArrayList<String> contents = new ArrayList<>();

	/**
	 * @param dataBase A Database object which handles opening the connection.
	 * @param id       The unique identifier of a row.
	 */
	public ActiveRow(DB dataBase, String id) {
		this.dataBase = dataBase;
		this.id = id;
		initialise();
	}

	@Rowverride
	public void initialise() {
		try {
			con = DriverManager
					.getConnection("jdbc:mysql://" + dataBase.getDbDomain() + "/" + dataBase.getDbName(),
							dataBase.getUsername(), dataBase.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Statement statement = con.createStatement();
			ResultSet columnSet = statement.executeQuery(
					"SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`");
			ArrayList<String> columnNames = new ArrayList<String>();
			ResultSetMetaData rsmd = columnSet.getMetaData();
			columnCount = rsmd.getColumnCount();
			for (int i = 1; i < columnCount + 1; i++) {
				String name = rsmd.getColumnName(i);
				columnNames.add(name);
			}
			this.columns = columnNames;
			read =
					"SELECT * FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "` WHERE ID="
							+ this.id;
			delete = "DELETE FROM `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`"
					+ " WHERE ID = '" + this.id + "';";
			write = "INSERT INTO `" + dataBase.getDbName() + "`.`" + dataBase.getTableName() + "`";
			statement.close();
			columnSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	@Rowverride
	public void write() {
		StringBuilder query = new StringBuilder();
		query.append(write);
		query.append(" VALUES (");
		for (String con : this.contents) {
			if (contents.indexOf(con) != this.contents.size() - 1) {
				query.append("'");
				query.append(con);
				query.append("' ");
				query.append(",");
			} else {
				query.append("'");
				query.append(con);
				query.append("' ");
				query.append(")");
			}
		}

		try {
			PreparedStatement stmt = con.prepareStatement(query.toString());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Rowverride
	public void delete() {
		if (this.read().equals(new ArrayList<String>())) {
			System.out.println("Row does not exist.");
			return;
		}
		try {
			con.prepareStatement(delete).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Rowverride
	public ArrayList<String> read() {
		try {
			Statement statement = con.createStatement();

			ResultSet rowResult = statement.executeQuery(read);
			while (rowResult.next()) {
				for (int col = 1; col <= columnCount; col++) {
					Object value = rowResult.getObject(col);
					if (value != null) {
						contents.add(value.toString());
					}
				}
			}
			rowResult.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.contents;
	}

}
