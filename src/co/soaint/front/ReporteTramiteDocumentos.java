package co.soaint.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import co.soaint.contingencia.operaciones.GeneradorRotulo;

@WebServlet("/ReporteTramiteDocumentos")
public class ReporteTramiteDocumentos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	   /**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    	try {
	    	Date date = new Date();
	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        String formato = request.getParameter("formato");
	    	          
	        if(formato.equals("PDF")){
	        	response.setContentType("application/pdf");
		    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"TramiteDocumentos.pdf\"" );
	        }else{    
	        	response.setContentType("application/xls");
		        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"TramiteDocumentos.xls\"");
	        }	
	        
	    	String numRadicado = request.getParameter("NoRadicado");
	    	System.out.println("# de radicado ingresado: "+numRadicado);
	    	System.out.println("formato ingresado: "+formato);
	    	

	    	ByteArrayOutputStream baos = getByteArrayOutputStream(numRadicado,formato);

	    	response.setContentLength(baos.size());

	    	ServletOutputStream sos;

	    	sos = response.getOutputStream();

	    	baos.writeTo(sos);

	    	sos.flush();
	    	} catch (Exception e) {
	    		System.out.println("ReporteTramiteDocumentos : "+e.toString());
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
	    
	    
	    
	
	    @SuppressWarnings("static-access")
		private ByteArrayOutputStream getByteArrayOutputStream(String numRadicado, String formato) throws IOException {

		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    GeneradorRotulo ops = new GeneradorRotulo();
		    try {
		    	
				byte[] archivo = null;
				if(formato.equals("PDF")) {
					archivo = ops.obtenerReporteTramiteDocumentosPDF(numRadicado);
				}else {
			      archivo = ops.obtenerReporteTramiteDocumentosXLS(numRadicado);
				}
				
				bos.write(archivo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    return bos;
		}
	
	

}
