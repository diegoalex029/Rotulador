package co.soaint.front;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.soaint.contingencia.operaciones.ConstructorGenerico;
import co.soaint.contingencia.operaciones.DataConfigProperties;

/**
 * Servlet implementation class PlanillaInternaEnvio
 */
@WebServlet("/PlanillaInternaEnvio")
public class PlanillaInternaEnvio extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlanillaInternaEnvio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");    	
    	
    	String format = "pdf";
    	String baseDir=DataConfigProperties.getPropietat("pathJasper");
    	String reportName="PlanillaInternaEnvio";    	
        
    	Map<String, String> parameters=new HashMap<String, String>();        
        Enumeration<String> en=request.getParameterNames();
        if(en!=null){
	        String key=null,value=null;boolean enc=false;
	        while(en.hasMoreElements()){
	        	enc=false;
	        	key=en.nextElement();
	        	value=request.getParameter(key);
	        	if(key.equals("formato")){            	
	            	format=value.toLowerCase();
	            	enc=true;
	            }
	            if(key.equals("reporte")){            	
	            	reportName=value;
	            	enc=true;
	            }
	            if(!enc){
	            	parameters.put(key,value);
	            }
	        }
        }
        
        if(!format.equals("xls")){
        	format="pdf";
        }
        
        String jasperFile=reportName+".jasper";
    	String fileNameOut=dateFormat.format(date)+"_"+reportName+"."+format;    	
    	
    	response.setContentType("application/"+format);
        response.setHeader("Content-Disposition", "inline; filename=\""+fileNameOut+"\"");  	
        
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try{
    		baos.write(ConstructorGenerico.generarReporte(jasperFile, parameters, format));	
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	

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
		doGet(request, response);
	}
}
