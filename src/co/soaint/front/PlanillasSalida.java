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

@WebServlet("/PlanillasSalida")
public class PlanillasSalida extends HttpServlet{

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlanillasSalida() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        String formato = request.getParameter("FormatoPlanilla");
        
        if(formato.equals("xls")){
	    	response.setContentType("application/xls");
	        response.setHeader("Content-Disposition", "inline; filename=\""+dateFormat.format(date)+"_PlanillaSalida.xls\"");
        }else{    
	    	response.setContentType("application/pdf");
	    	response.setHeader("Content-disposition","inline; filename=\""+dateFormat.format(date)+"_PlanillaSalida.pdf\"" );
        }	
        
    	String numPlanilla = request.getParameter("numPlanilla");

    	ByteArrayOutputStream baos = getByteArrayOutputStream(numPlanilla,formato);

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
	
	private ByteArrayOutputStream getByteArrayOutputStream(String numPlanilla, String formato) throws IOException {

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();

	    try {
	    	
			byte[] archivo ;
			if(formato.equals("xls")){
				archivo = GeneradorRotulo.getPlanillaSalidaXLS(numPlanilla);
			}else{
				archivo = GeneradorRotulo.getPlanillaSalida(numPlanilla);
			}	
			bos.write(archivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return bos;
	}



}
