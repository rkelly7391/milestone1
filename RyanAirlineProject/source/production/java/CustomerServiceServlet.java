

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(
        name = "customerServlet",
        urlPatterns = {"/customerSupport"},
        loadOnStartup = 1
)
@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB
        maxFileSize = 20_971_520L, //20MB
        maxRequestSize = 41_943_040L //40MB
)
public class CustomerServiceServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private volatile int COMMENT_ID_SEQUENCE = 1;

    private Map<Integer, CustomerService> customerSupportDatabase = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                this.showCommentForm(response);
                break;
            case "view":
                this.viewComment(request, response);
                break;
            case "download":
                this.downloadAttachment(request, response);
                break;
            case "list":
            default:
                this.listComment(response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                this.createComment(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("customerSupport");
                break;
        }
    }

    private void showCommentForm(HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Submit a request</h2>\r\n");
        writer.append("<form method=\"POST\" action=\"customerSupport\" ")
              .append("enctype=\"multipart/form-data\">\r\n");
        writer.append("<input type=\"hidden\" name=\"action\" ")
              .append("value=\"create\"/>\r\n");
        writer.append("Your Name<br/>\r\n");
        writer.append("<input type=\"text\" name=\"customerName\"/><br/><br/>\r\n");
        writer.append("Subject<br/>\r\n");
        writer.append("<input type=\"text\" name=\"subject\"/><br/><br/>\r\n");
        writer.append("Body<br/>\r\n");
        writer.append("<textarea name=\"body\" rows=\"5\" cols=\"30\">")
              .append("</textarea><br/><br/>\r\n");
        writer.append("<b>Attachments</b><br/>\r\n");
        writer.append("<input type=\"file\" name=\"file1\"/><br/><br/>\r\n");
        writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
        writer.append("</form>\r\n");

        this.writeFooter(writer);
    }

    private void viewComment(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException
    {
        String idString = request.getParameter("commentId");
        CustomerService comment = this.getComment(idString, response);
        if(comment == null)
            return;

        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Comment #").append(idString)
              .append(": ").append(comment.getSubject()).append("</h2>\r\n");
        writer.append("<i>Customer Name - ").append(comment.getCustomerName())
              .append("</i><br/><br/>\r\n");
        writer.append(comment.getBody()).append("<br/><br/>\r\n");

        if(comment.getNumberOfAttachments() > 0)
        {
            writer.append("Attachments: ");
            int i = 0;
            for(Attachment attachment : comment.getAttachments())
            {
                if(i++ > 0)
                    writer.append(", ");
                writer.append("<a href=\"customerSupport?action=download&commentId=")
                      .append(idString).append("&attachment=")
                      .append(attachment.getName()).append("\">")
                      .append(attachment.getName()).append("</a>");
            }
            writer.append("<br/><br/>\r\n");
        }

        writer.append("<a href=\"customerSupport\">Return to list comments</a>\r\n");

        this.writeFooter(writer);
    }

    private void downloadAttachment(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException
    {
        String idString = request.getParameter("commentId");
        CustomerService comment = this.getComment(idString, response);
        if(comment == null)
            return;

        String name = request.getParameter("attachment");
        if(name == null)
        {
            response.sendRedirect("customerSupport?action=view&commentId=" + idString);
            return;
        }

        Attachment attachment = comment.getAttachment(name);
        if(attachment == null)
        {
            response.sendRedirect("customerSupport?action=view&commentId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition",
                "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContents());
    }

    private void listComment(HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter writer = this.writeHeader(response);

        writer.append("<h2>Comments</h2>\r\n");
        writer.append("<a href=\"customerSupport?action=create\">Create Comment")
              .append("</a><br/><br/>\r\n");

        if(this.customerSupportDatabase.size() == 0)
            writer.append("<i>There are no Comments in the system.</i>\r\n");
        else
        {
            for(int id : this.customerSupportDatabase.keySet())
            {
                String idString = Integer.toString(id);
                CustomerService comment = this.customerSupportDatabase.get(id);
                writer.append("Comment #").append(idString)
                      .append(": <a href=\"customerSupport?action=view&commentId=")
                      .append(idString).append("\">").append(comment.getSubject())
                      .append("</a> (customer: ").append(comment.getCustomerName())
                      .append(")<br/>\r\n");
            }
        }

        this.writeFooter(writer);
    }

    private void createComment(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException
    {
        CustomerService comment = new CustomerService();
        comment.setCustomerName(request.getParameter("customerName"));
        comment.setSubject(request.getParameter("subject"));
        comment.setBody(request.getParameter("body"));

        Part filePart = request.getPart("file1");
        if(filePart != null && filePart.getSize() > 0)
        {
            Attachment attachment = this.processAttachment(filePart);
            if(attachment != null)
                comment.addAttachment(attachment);
        }

        int id;
        synchronized(this)
        {
            id = this.COMMENT_ID_SEQUENCE++;
            this.customerSupportDatabase.put(id, comment);
        }

        response.sendRedirect("customerSupport?action=view&commentId=" + id);
    }

    private Attachment processAttachment(Part filePart)
            throws IOException
    {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while((read = inputStream.read(bytes)) != -1)
        {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    private CustomerService getComment(String idString, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(idString == null || idString.length() == 0)
        {
            response.sendRedirect("customerSupport");
            return null;
        }

        try
        {
            CustomerService comment = this.customerSupportDatabase.get(Integer.parseInt(idString));
            if(comment == null)
            {
                response.sendRedirect("customerSupport");
                return null;
            }
            return comment;
        }
        catch(Exception e)
        {
            response.sendRedirect("customerSupport");
            return null;
        }
    }

    private PrintWriter writeHeader(HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("<link rel=\"stylesheet\" href=\"styles.css\">\r\n")
              .append("        <title>Comment Help</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n");

        return writer;
    }

    private void writeFooter(PrintWriter writer)
    {
        writer.append("    </body>\r\n").append("</html>\r\n");
    }
}
