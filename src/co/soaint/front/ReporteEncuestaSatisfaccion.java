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

@WebServlet("/ReporteEncuestaSatisfaccion")
public class ReporteEncuestaSatisfaccion extends HttpServlet  {
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
			    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"ReporteEncuesta.pdf\"" );
		        }else{    
		        	response.setContentType("application/xls");
			        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"ReporteEncuesta.xls\"");
		        }	
		        
		        //Parametros Reporte
		    	String numRadicado = request.getParameter("NoRadicado");
		    	String numRadicadoExt = request.getParameter("NoRadicadoExt");
		    	String tipoRegistro= request.getParameter("TipoRegistro");
		    	String fechaInicioRadicado= request.getParameter("FechaInicioRadicado");
		    	String fechaFinRadicado= request.getParameter("FechaFinRadicado");
		    	String fechaInicioExt= request.getParameter("FechaInicioExt");
		    	String fechaFinExt= request.getParameter("FechaFinExt");
		    	String fechaInicioEncuesta= request.getParameter("FechaInicioEncuesta");
		    	String fechaFinEncuesta= request.getParameter("FechaFinEncuesta");
		    	String funcionario= request.getParameter("Funcionario");
		    	String dependencia= request.getParameter("Dependencia");
		    	
		    	
		    	System.out.println("# de radicado ingresado: "+numRadicado);
		    	System.out.println("formato ingresado: "+formato);
		    	

		    	ByteArrayOutputStream baos = getByteArrayOutputStream(numRadicado,formato,numRadicadoExt,tipoRegistro,fechaInicioRadicado,fechaFinRadicado,fechaInicioExt,fechaFinExt,fechaInicioEncuesta,fechaFinEncuesta,funcionario,dependencia);

		    	response.setContentLength(baos.size());

		    	ServletOutputStream sos;

		    	sos = response.getOutputStream();

		    	baos.writeTo(sos);

		    	sos.flush();
		    	} catch (Exception e) {
		    		System.out.println("ReporteEncuestaSatisfaccion : "+e.toString());
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
			private ByteArrayOutputStream getByteArrayOutputStream(String numRadicado,String  formato,String numRadicadoExt,String tipoRegistro,String fechaInicioRadicado,String fechaFinRadicado,String fechaInicioExt,String fechaFinExt,String fechaInicioEncuesta,String fechaFinEncuesta,String funcionario,String dependencia) throws IOException {

			    ByteArrayOutputStream bos = new ByteArrayOutputStream();
			    GeneradorRotulo ops = new GeneradorRotulo();
			    try {
			    	
					byte[] archivo = null;
					if(formato.equals("PDF")) {
						archivo = ops.obtenerReporteEncuestaPDF(numRadicado,formato,numRadicadoExt,tipoRegistro,fechaInicioRadicado,fechaFinRadicado,fechaInicioExt,fechaFinExt,fechaInicioEncuesta,fechaFinEncuesta,funcionario,dependencia);
					}else {
				      archivo = ops.obtenerReporteEncuestaXSL(numRadicado,formato,numRadicadoExt,tipoRegistro,fechaInicioRadicado,fechaFinRadicado,fechaInicioExt,fechaFinExt,fechaInicioEncuesta,fechaFinEncuesta,funcionario,dependencia);
					}
					
					bos.write(archivo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    return bos;
			}
		
		


}
