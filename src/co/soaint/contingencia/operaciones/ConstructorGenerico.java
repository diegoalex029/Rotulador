package co.soaint.contingencia.operaciones;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ConstructorGenerico {	
	
	public static byte[] generarReporte(String jasperFile,Map parameters,String format) throws Exception{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try{
			JasperReport reporte = (JasperReport) JRLoader.loadObject(jasperFile);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter =null;
			if(format.toLowerCase().equals("xls")){
				exporter = new JRXlsExporter();
			}else{
				exporter = new JRPdfExporter();
			}	
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba);
			exporter.exportReport(); 
			ba.close();
		}catch (Exception e ){
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
}
