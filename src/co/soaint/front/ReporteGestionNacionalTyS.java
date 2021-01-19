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

/**
 * Servlet implementation class ReporteGestionNacionalTyS
 */
@WebServlet("/ReporteGestionNacionalTyS")
public class ReporteGestionNacionalTyS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteGestionNacionalTyS() {
       super();
    // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formato = request.getParameter("formato");//Parametro 2 Formato del reporte
		
        if(formato.equals("PDF")){
        	response.setContentType("application/pdf");
	    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_GestionNacionalTyS.pdf\"" );
        }else{    
        	response.setContentType("application/xls");
	        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"_GestionNacionalTyS.xls\"");
        }
        
        String estadoTramite = request.getParameter("ESTADOTRAMITE");//Parametro 1
    	String direccionListService = request.getParameter("DIRETERRITO");//Parametro 3 (List)
      	String fechaInicio = request.getParameter("FECHAINICIO");//Paranmetro 4
    	String fechaFin = request.getParameter("FECHAFIN");//Paranmetro 5
    	String tipoTramiteListService = request.getParameter("TIPOTRAMITE");//Parametro 6 (List)
    	String nroRadicadado = request.getParameter("NRORADICADO");//Parametro 7
    	String funResponsable = request.getParameter("FUNRESPONSABLE");//Parametro 8
    	String canalRadica = request.getParameter("CANALRADICA");//Parametro 9
    	//Procesar Lista para trámites
    	
    	List<String> tipoTramiteListJasper = new ArrayList<String>();
    	StringTokenizer tokensTramite=new StringTokenizer(tipoTramiteListService, "-");
    	
    	while(tokensTramite.hasMoreTokens()){    		
    		String token=tokensTramite.nextToken();
    		System.out.println("Token Tramite ingresado: "+token);
    		tipoTramiteListJasper.add(token);
        }
    	
    	
    	//Procesar Lista para direcciones
    	
    	List<String> direccionesListJasper = new ArrayList<String>();
    	StringTokenizer tokensDirecciones=new StringTokenizer(direccionListService, "-");
    	
    	while(tokensDirecciones.hasMoreTokens()){    		
    		String token2=tokensDirecciones.nextToken();
    		System.out.println("Token Dirección ingresado: "+token2);
    		direccionesListJasper.add(token2);
        }
    	
         
        ByteArrayOutputStream baos = getByteArrayOutputStream(fechaInicio,fechaFin,tipoTramiteListService,direccionListService,estadoTramite,nroRadicadado,funResponsable,canalRadica,formato);
    	
        response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();
		} catch (Exception e) {
			System.out.println("ReporteGestionNacionalTyS : "+e.toString());
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
	private ByteArrayOutputStream getByteArrayOutputStream(String fechaInicio, String fechaFin, String tipoTramite, String direccion, String estadoTramite,String nroRadicado,String funResponsable, String canalRadica, String formato) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    GeneradorRotulo ops = new GeneradorRotulo();
	    try {
	    	
			byte[] archivo = null;
			if(formato.equals("PDF")) {
				archivo = ops.obtenerReporteGestionNacionalTySPDF(fechaInicio,fechaFin,tipoTramite,direccion,estadoTramite,nroRadicado,funResponsable,canalRadica);
			}else {
				archivo = ops.obtenerReporteGestionNacionalTySXLS(fechaInicio,fechaFin,tipoTramite,direccion,estadoTramite,nroRadicado,funResponsable,canalRadica);
			}
			
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}


}
