package jdbccensus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for the FiredUp database.
 *
 * @author Domonic Senesi & Cara Tang
 * @version 2017.08.17
 *
 *  Version 2017.08.17 - Changes & Additions
 *  Takes valid input from parser/main and uses it to query database and return results
 *  Changed internet database access to local
 *  Method created - getCustomersFromState
 *  Method removed - readcustomersbasics
 *
 */
public class FiredUpDB {

    private static final String FIREDUP_DB = "jdbc:sqlite:FiredUp.db";

    private static final String CUSTOMER_SQL = "SELECT CustomerID, Name, City, StateProvince FROM CUSTOMER";
    private static final String EMAIL_SQL = "SELECT EmailAddress FROM EMAIL WHERE FK_CustomerID = ?";

    private static final String STATE_SQL = " SELECT CustomerID, Name, City, StateProvince FROM CUSTOMER WHERE StateProvince = ?";

    /**
     * Read all customers from the FiredUp database and return them as a list of Customer objects
     * @return a list of customers from the FiredUp database
     */
    public List<Customer> readCustomers() {
        ArrayList<Customer> customers = readCustomersFromState(Main.getStateSearch());

        readEmailAddresses(customers);
        return customers;
    }


    /**
     * @return a connection to the FiredUp database
     * @throws SQLException if unable to connect
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FIREDUP_DB);
    }


    /**
     * Read from the FiredUp database customers from the given state
     * @param state the state of interest (US state or Canadian province)
     * @return a list of customers from the given state
     */
    private ArrayList<Customer> readCustomersFromState(String searchWord) {
        ArrayList<Customer> customers = new ArrayList<>();
        try
         {
             Connection conn = getConnection();

             PreparedStatement stmt = conn.prepareStatement(STATE_SQL);
             stmt.setString(1, searchWord);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                    customers.add(new Customer(rs.getInt("CustomerID"),
                            rs.getString("Name"),
                            rs.getString("City"),
                            rs.getString("StateProvince")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


    /**
     * Read email addresses from the database for each customer in the given list,
     * adding the email addresses found to the corresponding Customer object
     * @param customers list of customers whose email addresses should be read
     */
    private void readEmailAddresses(ArrayList<Customer> customers) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(EMAIL_SQL)
        ) {
            for (Customer cust : customers) {
                stmt.setInt(1, cust.getId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    cust.addEmailAddress(rs.getString("EmailAddress"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
