package co.soaint.front;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.soaint.contingencia.operaciones.GeneradorRotulo;

@WebServlet("/ReportePracticaNNACompleto")
public class ReportePracticaNNACompleto  extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReportePracticaNNACompleto() {
        super();
     // TODO Auto-generated constructor stub
     }


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
    		
            //response.setContentType("application/pdf;charset=UTF-8");
            //response.setHeader("Access-Control-Allow-Origin", "*");
        	//response.setHeader("Access-Control-Allow-Methods", "POST");
        	//response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    	    response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_PracticaLaboral.pdf\"" );
                
            
            
    	    BufferedReader reader = request.getReader();
    	    String jsonPost = reader.readLine();
    	    System.out.println(jsonPost);
        
             
            ByteArrayOutputStream baos = getByteArrayOutputStream("PDF",jsonPost);
            
            byte[] pdfByte = baos.toByteArray();
            String base64 = java.util.Base64.getEncoder().encodeToString(pdfByte);
            //System.out.println("Imprimendo base64 de ReportePracticaNNACompleto: "+ base64);                    
            
            //response.setContentLength(baos.size());  
            
            PrintWriter out = response.getWriter();
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out.print(base64);
            out.flush();
    		} catch (Exception e) {
    			System.out.println("ReportePracticaNNACompleto : "+e.toString());
			}
    		
    		finally {
              GeneradorRotulo.pasarGarbageCollector();
    		}

        	//ServletOutputStream sos;

        	//sos = response.getOutputStream();
        	/*for (int i = 0; i < base64.length(); ++i) {
        		baos.write(base64.charAt(i));
        	}*/

        	//baos.writeTo(sos);

        	//sos.flush();

        	
    	}
    	
    	@SuppressWarnings("static-access")
    	private ByteArrayOutputStream getByteArrayOutputStream(String formato,String JSONSource) throws IOException {

    	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	    GeneradorRotulo ops = new GeneradorRotulo();
    	    try {
    	    	
    			byte[] archivo = null;
    			if(formato.equals("PDF")) {
    				
    				archivo = ops.obtenerReporteGestionPracticaNNACompletoPDF(JSONSource);
    			}
    			
    			bos.write(archivo);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    	    return bos;
    	}

    
    
}



