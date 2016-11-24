// Giorgio Latour
// Publisher App for Quotations
// IHRTLUHC
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ViewerDAO {

    Connection connection;
    Statement stmt;
    String currentUser;
    PreparedStatement pstmt;
    PreparedStatement pstmt2;
    ArrayList<String> quotesList;

    public Boolean logIn(String username, String password) {
        connectDB();

        // Determine if user exists and log in.
        Boolean success = false;
        currentUser = username;
        try {
            String logInQuerySQL = "select username from users where username= ?"
                    + "and password= ?";

            pstmt = connection.prepareStatement(logInQuerySQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                success = true;
            }

        } catch (SQLException ex) {
            return success;
        }
        return success;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categoriesList = new ArrayList<>();
        try {
            // Get categories from database.
            String getCategoriesSQL = "select categoryname from categories";
            ResultSet categories = stmt.executeQuery(getCategoriesSQL);

            // Add the category names to an array list. The categories are stored
            // in a column so we have to move to the next row after adding each 
            // category to the arraylist.
            while (categories.next()) {
                categoriesList.add(categories.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categoriesList;
    }

    public ArrayList<String> getQuotesList(String categoryname) {
        quotesList = new ArrayList<>();
        ResultSet quotes = null;
        try {
            // Get categories from database.
            String getQuoteSQL = "select quote from quotes where category = ? order by likes desc";
            pstmt = connection.prepareStatement(getQuoteSQL);
            pstmt.setString(1, categoryname);

            quotes = pstmt.executeQuery();
          
            if (!quotes.next()) {
                quotesList.add("No quotes yet!");
                return quotesList;
            }

            // Checking if the result set has any results moves the cursor to the
            // next line. In order to do the while loop below correctly, we need
            // to move back to the 0 position.
            quotes.previous();

            // Add the quotes to an array list. The quotes are stored
            // in a column so we have to move to the next row after adding each 
            // quote to the arraylist.
            while (quotes.next()) {
                quotesList.add(quotes.getString(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quotesList;
    }    
    
    public int getQuoteNum(String quote) {
        int quoteNum = 0;
        try {
            String getQuoteNumSQL = "select quotenum from quotes where quote = ?";
            pstmt = connection.prepareStatement(getQuoteNumSQL);
            pstmt.setString(1, quote);

            ResultSet quoteNumber = pstmt.executeQuery();

            if (quoteNumber.next()) {
                quoteNum = quoteNumber.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quoteNum;
    }

    public String establishLike(int quoteNumber, String username) {
        String establishLikeStatus = "null";            
        if (!checkUserLiked(quoteNumber, username)) {
            try {
                String establishLike = "insert into likes (quotenum, username)"
                        + " values (?, ?)";
                pstmt = connection.prepareStatement(establishLike);
                pstmt.setString(1, Integer.toString(quoteNumber));
                pstmt.setString(2, username);

                pstmt.execute();

                establishLikeStatus = username + " liked this quote!";

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            establishLikeStatus = "You already liked this quote!";
        }

        return establishLikeStatus;
    }

    public Boolean checkUserLiked(int quoteNumber, String username) {
        Boolean Liked = false;
        try {
            String checkUserLikedSQL = "select username from likes where quotenum"
                    + "= ? and username = ?";
            pstmt = connection.prepareStatement(checkUserLikedSQL);
            pstmt.setString(1, Integer.toString(quoteNumber));
            pstmt.setString(2, username);

            ResultSet checkUserResult = pstmt.executeQuery();

            if (checkUserResult.next()) {
                Liked = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Liked;

    }

    public String getQuoteSubmitter(int quoteNumber) {
        String user = "null";
        try {
            String getQuoteSubmitterSQL = "select username from quotes where quotenum"
                    + " = ?";
            pstmt = connection.prepareStatement(getQuoteSubmitterSQL);
            pstmt.setString(1, Integer.toString(quoteNumber));

            ResultSet quoteSubmitterResult = pstmt.executeQuery();

            if (quoteSubmitterResult.next()) {
                user = quoteSubmitterResult.getString(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public void updateReccommendsCount(int quoteNumber) {
        int numberOfReccommends = 0;
        try {
            String getReccommendsCountSQL = "select COUNT(*) from likes where quotenum"
                    + " = ?";
            pstmt = connection.prepareStatement(getReccommendsCountSQL);
            pstmt.setString(1, Integer.toString(quoteNumber));

            ResultSet quoteSubmitterResult = pstmt.executeQuery();

            if (quoteSubmitterResult.next()) {
                numberOfReccommends = quoteSubmitterResult.getInt(1);
            }

            String setReccommendsCountSQL = "UPDATE quotes SET likes = ? WHERE quotenum = ?";

            pstmt2 = connection.prepareStatement(setReccommendsCountSQL);
            pstmt2.setString(1, Integer.toString(numberOfReccommends));
            pstmt2.setString(2, Integer.toString(quoteNumber));
            pstmt2.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quotations?user=root&password=cmsc250");
            stmt = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
