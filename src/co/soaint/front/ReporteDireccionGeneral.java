package co.soaint.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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
	 * Se agrega 05-12-2019
	 */
	 public static void main (String [ ] args) {

	    	String tipoTramiteListService="9-10-12";
	    	
	    	List<String> tipoTramiteListJasper = new ArrayList<String>();

	    	StringTokenizer tokensTramite=new StringTokenizer(tipoTramiteListService, "-");
	    	
	    	while(tokensTramite.hasMoreTokens()){
	    		
	    		String token=tokensTramite.nextToken();
	    		tipoTramiteListJasper.add(token);
	        }
	    	
	    	
	    	for(String tramite:tipoTramiteListJasper) {
	    	 	System.out.println("Tramite en lista: "+tramite);
	    		
	    		
	    	}
	}
    
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
	    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_DireccionGeneral.pdf\"" );
        }else{    
        	response.setContentType("application/xls");
	        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"_DireccionGeneral.xls\"");
        }	
    	
    	String tipoTramiteListService = request.getParameter("Id_Tramite");
    	String estadoTramite = request.getParameter("Estado_Tramite");
    	String direccionListService = request.getParameter("Subfondo");
    	String canal = request.getParameter("Canal");
    	String fechaInicio = request.getParameter("FechaInicio");
    	String fechaFin = request.getParameter("FechaFin");
    	
    	//Procesar Lista para trámites
    	/**
    	* Se agrega 02-12-2019
    	*/
    	List<String> tipoTramiteListJasper = new ArrayList<String>();
    	 	StringTokenizer tokensTramite=new StringTokenizer(tipoTramiteListService, "-");
    	    	
    	 while(tokensTramite.hasMoreTokens()){    		
    	 	String token=tokensTramite.nextToken();
    	 	System.out.println("Token Tramite ingresado: "+token);
    	 	tipoTramiteListJasper.add(token);
    	 }
    	    	
    	    	
    	 /**
    	 * Se agrega 02-12-2019
    	 */
    	 //Procesar Lista para direcciones
    	    	
    	 List<String> direccionesListJasper = new ArrayList<String>();
    	 StringTokenizer tokensDirecciones=new StringTokenizer(direccionListService, "-");
    	    	
    	 while(tokensDirecciones.hasMoreTokens()){    		
    	   	String token2=tokensDirecciones.nextToken();
    	   	System.out.println("Token Dirección ingresado: "+token2);
    	   	direccionesListJasper.add(token2);
    	 }
    	
    	
    	ByteArrayOutputStream baos = getByteArrayOutputStream(tipoTramiteListJasper,estadoTramite,direccionesListJasper,canal,fechaInicio,fechaFin,formato);

    	response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();
    	} catch (Exception e) {
    		System.out.println("ReporteDireccionGeneral : "+e.toString());
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
    
    private ByteArrayOutputStream getByteArrayOutputStream(List<String> tipoTramite, String estadoTramite, List<String> subFondo, String canal, String fechaInicio, String fechaFin, String formato) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    GeneradorRotulo ops = new GeneradorRotulo();
	    try {
	    	
			byte[] archivo ;
			if(formato.equals("PDF")) {
				archivo = ops.getReporteDireccionGeneralPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin);
			}
			else {
				archivo = ops.obtenerReporteDireccionGeneralXLS(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin);
			}
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}

}
