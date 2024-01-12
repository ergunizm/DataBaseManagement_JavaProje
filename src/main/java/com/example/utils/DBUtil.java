package com.example.utils;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.models.Material;
import com.example.models.Model;
import com.example.models.Organization;

/**
 * Utility class for interacting with the database.
 */
public class DBUtil {
    private static Connection connection = null;

    private static final String JDBC_URL = "jdbc:postgresql://localhost/party_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";

    // ------------------------------------------------------------------------------------------------------------------------
    static {
        try {
            Class.forName("org.postgresql.Driver");
            ColoredOutput.print("PostgreSQL JDBC Driver Registered!", ColoredOutput.Color.GREEN_BOLD_BRIGHT);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ========================================================================================================================
    /**
     * Returns a connection to the database.
     *
     * @return a Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     */
    public static void startConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            ColoredOutput.print("Database connection is working.", ColoredOutput.Color.GREEN_BOLD_BRIGHT);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Repositories for generic CRUD operations on the database
    // ========================================================================================================================
    /**
     * Retrieves all records from the specified table in the database.
     *
     * @param modelName the name of the table to retrieve records from
     * @param <T>       the type of the objects in the resulting list
     * @return a list of objects representing the records from the table
     */
    public static <T> List<T> selectAllFromDB(String modelName) {
        String query = "SELECT * FROM " + modelName + ";";

        // execute the query
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<T> list = null;

        // convert the result set to a list of objects
        try {
            list = ResultSetToListUtil.convert(rs, modelName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ColoredOutput.print("Retrieved " + list.size() + " records from " + modelName
                + ".", ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        return list;
    }

    // ------------------------------------------------------------------------------------------------------------------------
    public static void insertToDB(Model object) {
        ColoredOutput.print("Inserting " + object.toString() + " to the table " + object.getTableName() + ".",
                ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        String query = "INSERT INTO " + object.getTableName() + " VALUES (" + object.toString() + ");";

        // execute the query
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGuestLimit(Organization organization, int newNumber) {
        ColoredOutput.print("Updating " + organization.getGuestLimit() + " to the " + newNumber + ".",
                ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        String query = "UPDATE organizations SET glimit=" +newNumber+ " WHERE oid=" + organization.getOrgId() + ";";

        // execute the query
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <Organization> List<Organization> selectSeasonTypeUnion(String searchedSeason, String type){
        ColoredOutput.print("Getting all " + type + " activities or Done on " + searchedSeason + ".",
                ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        String query = "(SELECT DISTINCT * FROM organizations WHERE season=" +searchedSeason+ ") " +
                "UNION (SELECT DISTINCT * FROM organizations WHERE otype=" + type + ";";

        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Organization> orgs = null;

        try {
            orgs = ResultSetToListUtil.convert(rs, "organizations");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orgs;
    }

    public static <MatNumberOfUser> List<MatNumberOfUser> getMatNumbersOfUsersView() {
        String query = "SELECT * FROM materialNumberOfUsers;";

        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<MatNumberOfUser> mnou = null;

        try {
            mnou = ResultSetToListUtil.convert(rs, "matnumberofusers");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mnou;
    }

    public static Array getOwnedMaterials(String ssn) {
        Array arr = null;
        try {
            CallableStatement cstmt = connection.prepareCall("{? = call materialsof_user(?)}");
            cstmt.setString(2, ssn);
            cstmt.registerOutParameter(1, 2003);
            cstmt.execute();

            arr = cstmt.getArray(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return arr;
    }

    public static double getMatPrice(String ssn) {
        double price = 0;
        try {
            CallableStatement cstmt = connection.prepareCall("{? = call material_priceof_user(?)}");
            cstmt.setString(2, ssn);
            cstmt.registerOutParameter(1, Types.DOUBLE);
            cstmt.execute();

            price = cstmt.getDouble(1);

            System.out.println(price);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

}
