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

@WebServlet("/ReporteTramitesPorSustanciador")

public class ReporteTramitesPorSustanciador extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteTramitesPorSustanciador(){
        super();
        // TODO Auto-generated constructor stub
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
	    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_TramitesPorSustanciador.pdf\"" );
        }else{    
        	response.setContentType("application/xls");
	        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"_TramitesPorSustanciador.xls\"");
        }	
        
        String dirTerritorial = request.getParameter("DIRTERRITORIAL");
    	String fechaInicio = request.getParameter("FECHAINICIO");
    	String fechaFin = request.getParameter("FECHAFIN");
    	String idPlanta = request.getParameter("IDE_PLANTA");
    	String estado = request.getParameter("ESTADO");
    	String tipoTramite = request.getParameter("TIPOTRAMITE");
    	
    	
    	List<String> tipoTramiteListJasper = new ArrayList<String>();
    	StringTokenizer tokensTramite=new StringTokenizer(tipoTramite, "-");
    	
    	while(tokensTramite.hasMoreTokens()){    		
    		String token=tokensTramite.nextToken();
    		System.out.println("Token Tramite ingresado: "+token);
    		tipoTramiteListJasper.add(token);
        }
    	
    	
    	//Procesar Lista para direcciones
    	
    	List<String> direccionesListJasper = new ArrayList<String>();
    	StringTokenizer tokensDirecciones=new StringTokenizer(dirTerritorial, "-");
    	
    	while(tokensDirecciones.hasMoreTokens()){    		
    		String token2=tokensDirecciones.nextToken();
    		System.out.println("Token Dirección ingresado: "+token2);
    		direccionesListJasper.add(token2);
        }
    	
    	
    	

    	ByteArrayOutputStream baos = getByteArrayOutputStream(direccionesListJasper,fechaInicio,fechaFin,idPlanta,estado,tipoTramiteListJasper,formato);

    	response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();
    	} catch (Exception e) {
    		System.out.println("ReporteTramitesPorSustanciador : "+e.toString());
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
    
    private ByteArrayOutputStream getByteArrayOutputStream(List<String> dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, List<String> tipoTramite, String formato) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    GeneradorRotulo ops = new GeneradorRotulo();
	    try {
	    	
			byte[] archivo;
			if(formato.equals("PDF")) {
				archivo = ops.obtenerReporteTramitesPorSustaciadorPDF(dirTerritorial,fechaInicio,fechaFin,idPlanta,estado,tipoTramite);
			}else {
				archivo = ops.obtenerReporteTramitesPorSustaciadorXLS(dirTerritorial,fechaInicio,fechaFin,idPlanta,estado,tipoTramite);
			}
			
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}
	

}
