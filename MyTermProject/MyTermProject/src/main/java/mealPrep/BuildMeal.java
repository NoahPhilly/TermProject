package mealPrep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuildMeal
 */
@WebServlet("/BuildMeal")
public class BuildMeal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildMeal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String carb = request.getParameter("carb");
	        String protein = request.getParameter("protein");
	        String fat = request.getParameter("fat");
	        
	        String carbFind = null;
	        String protFind = null;
	        String fatFind = null;
	        
	        double totalPrice = 0;
	        int totalCals = 0;
	        

	        Connection connection = null;
	        String insertSql = " INSERT INTO MealPrepMeal (id, CARB_ITEM, PROTEIN_ITEM, FAT_ITEM, PRICE_ITEM, CALORIES) values (default, ?, ?, ?, ?, ?)";

	        try {
	           MyDBConnection.getDBConnection();
	           connection = MyDBConnection.connection;
	           PreparedStatement preparedStmt = null;
	           PreparedStatement protPreparedStmt = null;
	           PreparedStatement fatPreparedStmt = null;
	           
	           if (carb.isEmpty()) {
	              System.out.println("Carb does not exist!");
	            } else {
	               String carbSelectSQL ="SELECT * FROM MealPrepCarbs WHERE CARB_ITEM='" + carb + "'";
	               preparedStmt = connection.prepareStatement(carbSelectSQL);
	             
	            }
	           ResultSet rs = preparedStmt.executeQuery();

	           while (rs.next()) {
	              int id = rs.getInt("id");
	              carbFind = rs.getString("carb_item").trim();
	              totalPrice += rs.getDouble("price_item");
	              totalCals += rs.getInt("calorie_count");
	              
	           }
	           rs.close();
	           preparedStmt.close();
	          
	           
	           
	           if (protein.isEmpty()) {
		              System.out.println("Protein does not exist!");
		            } else {
		               String proteinSelectSQL = "SELECT * FROM MealPrepProtein WHERE PROTEIN_ITEM='" + protein + "'";
		               protPreparedStmt = connection.prepareStatement(proteinSelectSQL);
		            
		            }
	           
		           ResultSet proteinRs = protPreparedStmt.executeQuery();

		           while ( proteinRs.next()) {
		        	   int id = proteinRs.getInt("id");
		               protFind =  proteinRs.getString("PROTEIN_ITEM").trim();
		              totalPrice += proteinRs.getDouble("PRICE_ITEM");
		              totalCals += proteinRs.getInt("calorie_count");
		             
		              
		           }
		           
		           proteinRs.close();
		           protPreparedStmt.close();
		           
		           
		           if (fat.isEmpty()) {
			              System.out.println("Fat does not exist!");
			            } else {
			               String selectSQL = "SELECT * FROM MealPrepFats WHERE FAT_ITEM='" + fat + "'";
			               fatPreparedStmt = connection.prepareStatement(selectSQL);
			            }
		           
			           ResultSet fatRs = fatPreparedStmt.executeQuery();

			           while ( fatRs.next()) {
			        	   int id = fatRs.getInt("id");
			              fatFind =  fatRs.getString("fat_item").trim();
			              totalPrice += fatRs.getDouble("price_item");
			              totalCals += fatRs.getInt("calorie_count");
			              
			           }
			           proteinRs.close();
			           fatPreparedStmt.close();
	           
	           PreparedStatement preparedInsertStmt = connection.prepareStatement(insertSql);
	           preparedInsertStmt.setString(1, carbFind);
	           preparedInsertStmt.setString(2, protFind);
	           preparedInsertStmt.setString(3, fatFind);
	           preparedInsertStmt.setDouble(4, totalPrice);
	           preparedInsertStmt.setInt(5, totalCals);
	           
	           preparedInsertStmt.execute();
	           
	          
	           String selectAllMealsSQL = "SELECT * FROM MealPrepMeal";
	           PreparedStatement allMeals = connection.prepareStatement(selectAllMealsSQL);
	           ResultSet allSet = allMeals.executeQuery();
	           PrintWriter out = response.getWriter();
	   	        String title = "The Meals you Built!";
	   	        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	   	       response.setContentType("text/html");
	   	       out.println(docType + //
	   	              "<html>\n" + //
	   	              "<head><title>" + title + "</title></head>\n" + //
	   	              "<body bgcolor=\"#f0f0f0\">\n" + //
	   	              "<h2 align=\"center\">" + title + "</h2>\n");
	   	       
	           while(allSet.next()) {
	        	   String finalCarb = allSet.getString("carb_item").trim();
	        	   String finalProt = allSet.getString("protein_item").trim();
	        	   String finalFat = allSet.getString("fat_item").trim();
	        	   Double price = allSet.getDouble("price_item");
	        	   int calories = allSet.getInt("calories");
	   	        out.println(docType + //
	   	              "<ul>\n" + //

	   	              "  <li><b>Carb</b>: " + finalCarb + "\n" + //
	   	              "  <li><b>Protein</b>: " + finalProt + "\n" + //
	   	              "  <li><b>Fat</b>: " + finalFat + "\n" + //
	   	              "  <li><b>Price</b>: " + price + "\n" + //
	   	              "  <li><b>Calories</b>: " + calories + "\n" + //
	   	             

	   	              "</ul>\n");   
	           }
	           out.println("<a href=/MyTermProject/BuildMeal.html>Search Data</a> <br>");
		   	     out.println("</body></html>");
	           
	           
	           
	           connection.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	     }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
