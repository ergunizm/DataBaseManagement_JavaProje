package com.example.utils;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

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

    public static String insertToOrganizations(Organization organization) {
        ColoredOutput.print("Inserting " + organization.toString() + " to the table " + organization.getTableName() + ".",
                ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        String query = "insert into organizations (comp_id, otype, glimit, season, price, orgdate, availability) values(?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, organization.getCompanyId());
            preparedStatement.setString(2, organization.getOrgType());
            preparedStatement.setInt(3, organization.getGuestLimit());
            preparedStatement.setString(4, organization.getSeason());
            preparedStatement.setDouble(5, organization.getPrice());
            preparedStatement.setInt(6, organization.getOrgDate());
            preparedStatement.setBoolean(7, organization.getAvailability());

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                return "Organization is added!";
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String[] getOrgTypes() {

        List<Organization> partyTypes = DBUtil.selectAllFromDB("organizations");

        Set<String> uniquePartyTypes = new HashSet<>();

        for (Organization partyType : partyTypes) {
            uniquePartyTypes.add(partyType.getOrgType());
        }

        return uniquePartyTypes.toArray(new String[0]);
    }

    public static <Organization> List<Organization> selectSeasonTypeIntersect(String searchedSeason, String type){
        ColoredOutput.print("Getting all " + type + " activities and Done on " + searchedSeason + ".",
                ColoredOutput.Color.MAGENTA_BOLD_BRIGHT);

        String query = "(SELECT DISTINCT * FROM organizations WHERE season=\'" +searchedSeason+ "\') " +
                "INTERSECT (SELECT DISTINCT * FROM organizations WHERE otype=\'" + type + "\');";

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

    public static <Organization> List<Organization> selectOrgOfCompany(String cid) {
        String query = "SELECT * FROM organizations where comp_id=?;";

        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,cid);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Organization> orgs = null;

        try {
            orgs = ResultSetToListUtil.convert(rs, "organizations");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public static String updateMaterial(String oldId, Material material) {
        String query = "update materials set mid = ?, comp_id = ?, mtype= ?, barcode = ?, price = ? WHERE mid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, material.getMaterialId());
            preparedStatement.setString(2, material.getCompanyId());
            preparedStatement.setString(3, material.getMaterialType());
            preparedStatement.setString(4, material.getBarcode());
            preparedStatement.setDouble(5, material.getPrice());
            preparedStatement.setString(6, oldId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                return "Trigger executed : Material updated successfully!";
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String updateOrganization(String oldId, Organization organization) {
        String query = "update organizations set oid = ?, comp_id = ?, otype= ?, glimit = ?, season = ?, price = ?, orgdate = ?, availability = ? WHERE oid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, organization.getOrgId());
            preparedStatement.setString(2, organization.getCompanyId());
            preparedStatement.setString(3, organization.getOrgType());
            preparedStatement.setInt(4, organization.getGuestLimit());
            preparedStatement.setString(5, organization.getSeason());
            preparedStatement.setDouble(6, organization.getPrice());
            preparedStatement.setInt(7, organization.getOrgDate());
            preparedStatement.setBoolean(8, organization.getAvailability());
            preparedStatement.setInt(9, Integer.parseInt(oldId));

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                return "Trigger executed : Organization updated successfully!";
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String deleteOrganization(String orgId) {
        String query = "delete from organizations where oid = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(orgId));

            preparedStatement.executeUpdate();

            return orgId+" is deleted!";

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String addOrgToUser(String user_ssn, int orgId) {
        String result;
        try {
            CallableStatement cstmt = connection.prepareCall("{? = call add_org_to_user(?, ?)}");
            cstmt.setInt(2, orgId);
            cstmt.setString(3, user_ssn.trim());
            cstmt.registerOutParameter(1, 12);
            cstmt.execute();

            result = cstmt.getString(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}