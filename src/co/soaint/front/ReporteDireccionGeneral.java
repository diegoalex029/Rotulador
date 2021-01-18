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

@WebServlet("/ReporteDireccionGeneral")
public class ReporteDireccionGeneral extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteDireccionGeneral() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formato = request.getParameter("formato");
    	          
        if(formato.equals("PDF")){
        	response.setContentType("application/pdf");
	    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_DireccionGeneral.pdf\"" );
        }else{    
        	response.setContentType("application/xls");
	        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"_DireccionGeneral.xls\"");
        }	
    	
    	String tipoTramite = request.getParameter("Id_Tramite");
    	String estadoTramite = request.getParameter("Estado_Tramite");
    	String subFondo = request.getParameter("Subfondo");
    	String canal = request.getParameter("Canal");
    	String fechaInicio = request.getParameter("FechaInicio");
    	String fechaFin = request.getParameter("FechaFin");
    	
    	ByteArrayOutputStream baos = getByteArrayOutputStream(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin,formato);

    	response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();

    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    
    private ByteArrayOutputStream getByteArrayOutputStream(String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin, String formato) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
	    	
			byte[] archivo ;
			if(formato.equals("PDF")) {
				archivo = GeneradorRotulo.getReporteDireccionGeneralPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin);
			}
			else {
				archivo = GeneradorRotulo.obtenerReporteDireccionGeneralXLS(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin);
			}
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}

}
