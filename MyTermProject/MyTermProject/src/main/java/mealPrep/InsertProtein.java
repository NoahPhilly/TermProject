package mealPrep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertProtein
 */
@WebServlet("/InsertProtein")
public class InsertProtein extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertProtein() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String proteinItem = request.getParameter("proteinItem");
        String calories = request.getParameter("calories");
        String price = request.getParameter("price");
        

        Connection connection = null;
        String insertSql = " INSERT INTO MealPrepProtein (id, PROTEIN_ITEM, CALORIE_COUNT, PRICE_ITEM) values (default, ?, ?, ?)";

        try {
           MyDBConnection.getDBConnection();
           connection = MyDBConnection.connection;
           PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
           preparedStmt.setString(1, proteinItem);
           preparedStmt.setString(2, calories);
           preparedStmt.setString(3, price);
           
           preparedStmt.execute();
           connection.close();
        } catch (Exception e) {
           e.printStackTrace();
        }

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Added to Proteins!";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType + //
              "<html>\n" + //
              "<head><title>" + title + "</title></head>\n" + //
              "<body bgcolor=\"#f0f0f0\">\n" + //
              "<h2 align=\"center\">" + title + "</h2>\n" + //
              "<ul>\n" + //

              "  <li><b>Protein</b>: " + proteinItem + "\n" + //
              "  <li><b>Calories</b>: " + calories + "\n" + //
              "  <li><b>Price</b>: " + price + "\n" + //
             

              "</ul>\n");

       out.println("<a href=/MyTermProject/BuildMeal.html>Search Data</a> <br>");
       out.println("</body></html>");
     }

     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
     }

  }

