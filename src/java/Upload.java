import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * File upload 
 * 
 * @author tomo
 */
@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig
public class Upload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 保存先のディレクターを取得
        String destination = request.getParameter("destination");
        Part part = request.getPart("file");
        String fileName = getFielName(part);
        Path filePath = Paths.get(destination + File.separator + fileName);
        // アップしたファイルを取得して、保存
        InputStream in = part.getInputStream();
        Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        // 画面遷移先で保存したファイルパスを表示
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Upload</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>File upload result</h1>");
            out.println("<div>");
            out.println("upload succeed[file path:" + filePath + "]"); 
            out.println("<div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getFielName(Part part) {
        String header = part.getHeader("content-disposition");
        System.out.println("***" + header);
        String[] split = header.split(";");
        // headerは、以下の内容になっているので、ここからfilenameである「fileupload.png」を取得
        // form-data; name="file"; filename="fileupload.png"
        String fileName =
                Arrays.asList(split).stream()
                        .filter(s -> s.trim().startsWith("filename"))
                        .collect(Collectors.joining());
        return fileName.substring(fileName.indexOf("=") + 1).replace("\"", "");
    }

}
