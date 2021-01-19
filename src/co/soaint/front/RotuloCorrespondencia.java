package co.soaint.front;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.soaint.contingencia.operaciones.GeneradorRotulo;

/**
 * Servlet implementation class RotuloCorrespondencia
 */
@WebServlet("/StickerPDF")
public class RotuloCorrespondencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RotuloCorrespondencia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
		
    	response.setContentType("application/pdf");

    	response.setHeader("Content-disposition","inline; filename=automatic_start.pdf" );

    	String numeroRadicado = request.getParameter("numero");
    	String documento = "COR";
    	String qrCode = getFullURL(request);
    	//900009

    	ByteArrayOutputStream baos = getByteArrayOutputStream(numeroRadicado,documento,qrCode);

    	response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();
		} catch (Exception e) {
			System.out.println("StickerPDF : "+e.toString());
		}
		
		finally {
			GeneradorRotulo.pasarGarbageCollector();
		}

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private ByteArrayOutputStream getByteArrayOutputStream(String numeroRadicado, String documento, String qrCode ) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    GeneradorRotulo ops = new GeneradorRotulo();
	    try {
			byte[] archivo =  ops.getDocumentoCompleto(numeroRadicado, documento, qrCode);
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}
	
	  /**
	   * Obtener URL Donde estamos ubicados.
	   * @param request
	   * @return
	   */
	  private String getFullURL(HttpServletRequest request) { 
	    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString()); 
	    String queryString = request.getQueryString(); 
	    if (queryString == null) { 
	      return requestURL.toString(); 
	      } else { 
	        return requestURL.append('?').append(queryString).toString(); } 
	    }

}
