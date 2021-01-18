package co.soaint.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.soaint.contingencia.operaciones.GeneradorRotulo;


@WebServlet({"/PlantillaTYS"})
public class PlantillaTYS
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("application/pdf");
    
    response.setHeader("Content-disposition", "inline; filename=automatic_start.pdf");
    
    String tramite = request.getParameter("Tramite");
    String numRadicado = request.getParameter("numRadicado");
    
    ByteArrayOutputStream baos = getByteArrayOutputStream(numRadicado, tramite);
    
    response.setContentLength(baos.size());


    
    ServletOutputStream sos = response.getOutputStream();
    
    baos.writeTo(sos);
    
    sos.flush();
  }

  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }


  
  private ByteArrayOutputStream getByteArrayOutputStream(String numRadicado, String tramite) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    try {
      byte[] archivo;
      if (tramite.equals("nnaentr")) {
        archivo = GeneradorRotulo.getEntrevista(numRadicado);
      } else if (tramite.equals("nnavisita")) {
        archivo = GeneradorRotulo.getVisita(numRadicado);
      } else if (tramite.equals("empservtempo")) {
        archivo = GeneradorRotulo.getEmpresaServTemporal(numRadicado);
      }
      else if (tramite.equals("archivoSind")) {
        archivo = GeneradorRotulo.getCertificados(numRadicado);
      } else {
        
        return bos;
      } 
      bos.write(archivo);
    } catch (Exception e) {

      e.printStackTrace();
    } 
    
    return bos;
  }
}
