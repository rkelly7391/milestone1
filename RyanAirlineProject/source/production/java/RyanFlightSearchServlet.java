import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class RyanFlight Will allow the user to search for a
 * flight
 * 
 * @author ryan.kelly5
 */
@WebServlet(description = "RyanFlightSearches", 
urlPatterns = {"/FlightSearch", "/flight-search" },
loadOnStartup = 1)
public class RyanFlightSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Create a list of airpots
	private final String[] AIRPORT = {"BOS", "DCA", "BWI", "MAN", "DUB"};

	private final String[] CITY = {"Boston", "Washing DC", "Manchester", "Dublin"};
	
	// Constants 
	private final String SEARCH = "/RyanFlight/flight-search";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// go to the flight search
		this.showFlightSearch(response);

	}

	/**
	 *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	switch(request.getRequestURI())
    {
        case SEARCH:
            showSearchResults(request, response);
            break;
        default:
        	error(response);
            break;
    }
			

	}

	/**
	 * Displays the flight search page
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showFlightSearch(HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = this.writeHeader(response);
		// create the xml page for the form.

		writer.append("<h3>Flight Search Form:</h3>\r\n");
		writer.append("<form method=\"POST\" action=\"flight-search\" ")
        .append("enctype=\"multipart/form-data\">\r\n");
		writer.append("Flight Departure<br/>\r\n");
		writer.append("<select required name=\"departure\"> \r\n");
        writer.append("<option value=\"\" disabled selected hidden=\"true\">Departure </option> \r\n");
        for (int i = 0; i < CITY.length; i++) {
        	writer.append("<option value=\"" + AIRPORT[i] + "\">" + CITY[i] + "</option> \r\n");

        }
		writer.append("</select><br/><br/> \r\n");
		writer.append("Flight Destination<br/>\r\n");
		
        writer.append("<select required name=\"destination\"> \r\n");
        writer.append("<option value=\"\" disabled selected hidden=\"true\">Destination </option> \r\n");
        for (int i = 0; i < CITY.length; i++) {
        	writer.append("<option value=\"" + AIRPORT[i] + "\">" + CITY[i] + "</option> \r\n");

        }
		writer.append("</select><br/><br/> \r\n");
		writer.append("Travel Date<br/>\r\n");
		writer.append("<input required type=\"date\" name=\"date\"/><br/><br/>\r\n");
		writer.append("Number of Passengers(max 25)<br/>\r\n");
		writer.append("<input required type=\"number\" name=\"numberOfPassengers\" min=\"1\" max=\"25\"/><br/><br/>\r\n");
		writer.append("<button>");
		writer.append("<text>Submit</text>");
		writer.append("</button>");
		writer.append("</form>\r\n");

		this.writeFooter(writer);
	}

	/**
	 * Creates the header for the pages.  Uses styles.css for style sheet
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private PrintWriter writeHeader(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n")
				.append("    <head>\r\n")
				.append("<link rel=\"stylesheet\" href=\"styles.css\">\r\n")
				.append("        <title>Flight Search</title>\r\n")
				.append("    </head>\r\n")
				.append("    <body>\r\n");

		return writer;
	}

	private void writeFooter(PrintWriter writer) {
		writer.append("    </body>\r\n").append("</html>\r\n");
	}
	
	
	/**
	 * Displays the search result from the user's input
	 * @param request
	 * @param response
	 */
    private void showSearchResults(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
    		String departure = request.getParameter("departure");
    		System.out.println(departure);
    		System.out.println(request.toString());
    		System.out.println(response.toString());

    		//Hard code values until we implement real values 
	    	PrintWriter writer = writeHeader(response);
	    	writer.append("<p>1) Boston -> Washing DC 03/18/2018 # seats 5</p>");    
	    	writer.append("<p>2) Washing DC -> Manchester 03/20/2018 # seats 6</p>"); 
			writeFooter(writer);
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    /**
	 * Displays an error
	 * @param request
	 * @param response
	 */
    private void error(HttpServletResponse response) {
    	
    	try {
	    	PrintWriter writer = writeHeader(response);
	    	writer.append("<p>Error during request!</p>");
			writeFooter(writer);
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
	

}
