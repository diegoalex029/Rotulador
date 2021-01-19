package co.soaint.front;

import java.io.BufferedReader;
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

/**
 * Servlet implementation class ReporteGestionNacionalTyS
 */
@WebServlet("/ReportePracticaNNA")

public class ReportePracticaNNA extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportePracticaNNA() {
       super();
    // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		request.setCharacterEncoding("UTF-8");
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //String formato = request.getParameter("formato");//Parametro 2 Formato del reporte
		
        response.setContentType("application/pdf;charset=UTF-8");
        //response.setHeader("Access-Control-Allow-Origin", "*");
    	//response.setHeader("Access-Control-Allow-Methods", "POST");
    	//response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_PracticaLaboral.pdf\"" );
            
        
        
	    BufferedReader reader = request.getReader();
	    String jsonPost = reader.readLine();
	    System.out.println(jsonPost);
        //String JSONSource = request.getParameter("JSONDATA");//Parametro 1
        //Procesar Lista para trámites
         
        ByteArrayOutputStream baos = getByteArrayOutputStream("PDF",jsonPost);
    	
        response.setContentLength(baos.size());

    	ServletOutputStream sos;

    	sos = response.getOutputStream();

    	baos.writeTo(sos);

    	sos.flush();
		} catch (Exception e) {
			System.out.println("ReportePracticaNNA : "+e.toString());
		}
		
		finally {
    	  GeneradorRotulo.pasarGarbageCollector();
		}

    	
	}
	
	@SuppressWarnings("static-access")
	private ByteArrayOutputStream getByteArrayOutputStream(String formato,String JSONSource) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    GeneradorRotulo ops = new GeneradorRotulo();
	    try {
	    	
			byte[] archivo = null;
			if(formato.equals("PDF")) {
				
				archivo = ops.obtenerReporteGestionPracticaNNAPDF(JSONSource);
			}
			
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}


}
