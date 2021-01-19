package co.soaint.contingencia.operaciones;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfCopyFields;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.json.JSONException;

public class GeneradorRotulo {

	public static byte[] getDocumentoCompleto (String numeroRadicado, String documento, String qrCode) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerrotuloCompleto(numeroRadicado,documento,qrCode));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getPlanillaInterna (String numPlanilla) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerPlanillaInternaPDF(numPlanilla));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getPlanillaSalida (String numPlanilla) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerPlanillaSalidaPDF(numPlanilla));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getPlanillaInternaXLS (String numPlanilla) throws Exception
	{
		return obtenerPlanillaInternaXLS(numPlanilla);
	}
	
	public static byte[] getPlanillaSalidaXLS (String numPlanilla) throws Exception
	{
		return obtenerPlanillaSalidaXLS(numPlanilla);
	}
	
	public static byte[] getEntrevista (String numRadicado) throws Exception
	{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerEntrevista(numRadicado));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getVisita (String numRadicado) throws Exception
	{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerVisita(numRadicado));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getEmpresaServTemporal (String numRadicado) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerEmpresaServTemporal(numRadicado));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getReporteOperacion (String numRadicado, String fechaInicio, String fechaFin, List<?> tipoTramite, List<String> direccion, String medioRecepcion, String planta) throws Exception
	{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteOperacionPDF(numRadicado,fechaInicio,fechaFin,tipoTramite,direccion,medioRecepcion, planta));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getCertificados (String numRadicado) throws Exception
	{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerCertificados(numRadicado));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerCertificados (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NRO_RADICADO",numRadicado);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Grupo_archivo_sindical.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerEmpresaServTemporal (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NRO_RADICADO",numRadicado);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Empresa_Servicios_Temporales.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerVisita (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NRO_RADICADO",numRadicado);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Visita_NNA.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerEntrevista (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NRO_RADICADO",numRadicado);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Entrevista_NNA.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteOperacionPDF (String numRadicado, String fechaInicio, String fechaFin, List<?> tipoTramite, List<String> direccion, String medioRecepcion, String planta) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteOperacionPDF(numRadicado,fechaInicio,fechaFin,tipoTramite,direccion,medioRecepcion, planta));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	/*
	 * Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteTramiteDocumentosPDF
	 */
	public static byte[] obtenerReporteTramiteDocumentosPDF (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUM_RADICADO",numRadicado);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");		
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Tramite_Documento.jasper"));
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	/*
	 * Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteTramiteDocumentosXLS
	 */
	public static byte[] obtenerReporteTramiteDocumentosXLS (String numRadicado) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUM_RADICADO",numRadicado);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");		
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Tramite_Documento.jasper"));
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
		  
	/**
	 * Fecha creacion 2020/01/14
	 * @param JSONSource  JSON desde URL
	 * @return Bytes del PDF
	 * @throws Exception
	 * Basado en el metodo obtenerReporteTramiteDocumentosPDF
	 * Metodo que carga un JSON para previsualizar el reporte
	 * JSON desde archivo
	 * fuente:https://java.dokry.com/10125/jasper-reports-json-datasource-obtiene-valores-nulos.html
	 */
	public static byte []obtenerReporteGestionPracticaNNAPDF(String JSONSource) throws Exception{
		

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		 
		try{

			JasperReport reporte = null;			
			ByteArrayInputStream jsonStream = new ByteArrayInputStream(JSONSource.getBytes("utf-8"));  
			System.out.println("Inicio carga objeto jasper OK");
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Entrevista_PracticasNNA.jasper"));//Compilado de RporteNacionalTyS
			System.out.println("Cargo objeto en jasper  OK");

			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM,jsonStream);
			
			System.out.println("Parametros : "+ parameters);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters);  
			net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(jasperPrint);
			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e ){
			
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		byte[] bytes = ba.toByteArray();
		return bytes;
	}

	/* Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteOperacionPDF
	 * */
public static byte []obtenerReporteGestionPracticaNNACompletoPDF(String JSONSource) throws Exception{
		

		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		 
		try{

			JasperReport reporte = null;			
			ByteArrayInputStream jsonStream = new ByteArrayInputStream(JSONSource.getBytes("utf-8"));  
			System.out.println("Inicio carga objeto jasper OK");
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Entrevista_PracticasNNACompleta.jasper"));//Compilado de RporteNacionalTyS
			System.out.println("Cargo objeto en jasper  OK");

			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM,jsonStream);
			
			System.out.println("Parametros : "+ parameters);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters);  
			net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(jasperPrint);
			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e ){
			
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	/*
	 * Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteOperacionPDF
	 */

	public static byte[]obtenerReporteGestionNacionalTySPDF(String fechaInicio, String fechaFin, String tipoTramite, String direccion, String estadoTramite,String nroRadicado,String funResponsable, String canalRadica) throws Exception{
		
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		JasperReport reporte = null;
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("FECHAINICIO",fechaInicio);//Parametro 4
			parameters.put("FECHAFIN",fechaFin);//Parametro 5
			parameters.put("TIPOTRAMITE",tipoTramite);//Parametro 6
			parameters.put("DIRETERRITO",direccion);//Parametro 3
			parameters.put("ESTADOTRAMITE",estadoTramite);//Parametro 1
			parameters.put("NRORADICADO",nroRadicado);//Parametro
			parameters.put("CANALRADICA",canalRadica);//Parametro
			
			if(funResponsable != "") {
				parameters.put("FUNRESPONSABLE",funResponsable);//Parametroparameters.put("FUNRESPONSABLE",funResponsable);//Parametro
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteGestionNacionalTyS.jasper"));//Compilado de RporteNacionalTyS
			}else {
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteGestionNacionalTyS_SINPLANTA.jasper"));//Compilado de RporteNacionalTyS
			}
												
			
			System.out.println("Inicio carga objeto jasper OK");
			System.out.println(parameters);
			
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  
			net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint,ba);
			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	/*
	 * Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteOperacionXLS
	 */
	public static byte[]obtenerReporteGestionNacionalTySXLS(String fechaInicio, String fechaFin, String tipoTramite, String direccion, String estadoTramite,String nroRadicado,String funResponsable, String canalRadica) throws Exception{
		
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		JasperReport reporte = null;
		
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("FECHAINICIO",fechaInicio);//Parametro 4
			parameters.put("FECHAFIN",fechaFin);//Parametro 5
			parameters.put("TIPOTRAMITE",tipoTramite);//Parametro 6
			parameters.put("DIRETERRITO",direccion);//Parametro 3
			parameters.put("ESTADOTRAMITE",estadoTramite);//Parametro 1
			parameters.put("NRORADICADO",nroRadicado);//Parametro
			parameters.put("CANALRADICA",canalRadica);//Parametro		
						
			if(funResponsable != null) {
				parameters.put("FUNRESPONSABLE",funResponsable);//Parametroparameters.put("FUNRESPONSABLE",funResponsable);//Parametro
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteGestionNacionalTyS.jasper"));//Compilado de RporteNacionalTyS
			}else {
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteGestionNacionalTyS_SINPLANTA.jasper"));//Compilado de RporteNacionalTyS
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRXlsExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		}catch (JRException e ){
			
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
			
		}catch (Exception   ee ){
			System.out.println("EncontroErrores "+ee.getMessage());
			ee.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	
	/*
	 * Reporte nuevo 2019/12/10
	 * Basado en el m�todo obtenerReporteEncuestaPDF
	 */
	public static byte[] obtenerReporteEncuestaPDF(String numRadicado, String formato, String numRadicadoExt,
			String tipoRegistro, String fechaInicioRadicado, String fechaFinRadicado, String fechaInicioExt,
			String fechaFinExt, String fechaInicioEncuesta, String fechaFinEncuesta, String funcionario,
			String dependencia) throws Exception {
		
		
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUM_RADICADO",numRadicado);
			parameters.put("NUM_RADICADO_EXT",numRadicadoExt);
			parameters.put("FECHA_INI_RADICADO",fechaInicioRadicado);
			parameters.put("FECHA_FIN_RADICADO",fechaFinRadicado);
			parameters.put("FECHA_INI_ENCUESTA",fechaInicioEncuesta);
			parameters.put("FECHA_FIN_ENCUESTA",fechaFinEncuesta);
			parameters.put("FECHA_INI_EXT",fechaInicioExt);
			parameters.put("FECHA_FIN_EXT",fechaFinExt);
			parameters.put("FUNCIONARIO",funcionario);
			parameters.put("DEPENDENCIA",dependencia);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");	
			
			if (tipoRegistro.equalsIgnoreCase("PQR")) {
				
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteEncuestaPQR.jasper"));
				System.out.println("FIN carga objeto jasper PQR  OK");
				
			} else if (tipoRegistro.equalsIgnoreCase("TRAMITE")) {
				
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteEncuestaTramite.jasper"));
				System.out.println("FIN carga objeto jasper TREMITE  OK");
				
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	/*
	 * Reporte nuevo 2019/11/29
	 * Basado en el m�todo obtenerReporteTramiteDocumentosXLS
	 */
	public static byte[] obtenerReporteEncuestaXSL (String numRadicado,String  formato,String numRadicadoExt,String tipoRegistro,String fechaInicioRadicado,String fechaFinRadicado,String fechaInicioExt,String fechaFinExt,String fechaInicioEncuesta,String fechaFinEncuesta,String funcionario,String dependencia) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUM_RADICADO",numRadicado);
			parameters.put("NUM_RADICADO_EXT",numRadicadoExt);
			parameters.put("FECHA_INI_RADICADO",fechaInicioRadicado);
			parameters.put("FECHA_FIN_RADICADO",fechaFinRadicado);
			parameters.put("FECHA_INI_ENCUESTA",fechaInicioEncuesta);
			parameters.put("FECHA_FIN_ENCUESTA",fechaFinEncuesta);
			parameters.put("FECHA_INI_EXT",fechaInicioExt);
			parameters.put("FECHA_FIN_EXT",fechaFinExt);
			parameters.put("FUNCIONARIO",funcionario);
			parameters.put("DEPENDENCIA",dependencia);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");	
			
			if (tipoRegistro.equalsIgnoreCase("PQR")) {
				
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteEncuestaPQR.jasper"));
				System.out.println("FIN carga objeto jasper PQR XLST  OK");
				
			} else if (tipoRegistro.equalsIgnoreCase("TRAMITE")) {
				
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteEncuestaTramite.jasper"));
				System.out.println("FIN carga objeto jasper TREMITE XLST  OK");
				
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteOperacionPDF (String numRadicado, String fechaInicio, String fechaFin, List<?> tipoTramite, List<String> direccion, String medioRecepcion, String planta) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NoRadicado",numRadicado);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("TIPOTRAMITE",tipoTramite);
			parameters.put("DIRETERRITO",direccion);
			parameters.put("MEDIORECEP",medioRecepcion);
			parameters.put("IDE_PLANTA",planta);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");		
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReportesOperacion1.jasper"));
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		

		byte[] bytes = ba.toByteArray();
		return bytes;
	}

	
	/*
	 * Reporte nuevo 2019/12/31
	 * Basado en el m�todo obtenerReportesEmpresaServiciosPDF
	 */
	
	public static byte[] obtenerReporteEmpresasServiciosPDF (String NIT, String tipoReporte) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NIT",NIT);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");	
			System.out.println(parameters);
			
			if(tipoReporte.equalsIgnoreCase("detalle")) {
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Reporte_Empresa_Servicios_Detalle.jasper"));
				System.out.println("Con detalle");
			}else {			
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Reporte_Empresa_Servicios.jasper"));
				System.out.println("Sin detalle");
			}
		
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	
	public static byte[] obtenerReporteEmpresasServiciosXLS (String NIT, String tipoReporte) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NIT",NIT);
						
			JasperReport reporte = null;
			
			System.out.println("Inicio carga objeto jasper OK");	
			
			if(tipoReporte.equalsIgnoreCase("detalle")) {
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Reporte_Empresa_Servicios_Detalle.jasper"));
			}else {			
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "Reporte_Empresa_Servicios.jasper"));
			}
		
			System.out.println("Cargo objeto en jasper  OK");
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		
		}catch (Exception e )
		{
			System.out.println("EncontroErrores JASPER OPERACION "+e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	
	public static byte[] getReporteDireccionGeneralPDF (List<?> tipoTramite, String estadoTramite, List<?> subFondo, String canal, String fechaInicio, String fechaFin) throws Exception{
        List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionGeneralPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionGeneralPDF (List<?>  tipoTramite, String estadoTramite, List<?>  subFondo, String canal, String fechaInicio, String fechaFin) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Id_Tramite",tipoTramite);
			parameters.put("Estado_Tramite",estadoTramite);
			parameters.put("Subfondo",subFondo);
			parameters.put("Canal",canal);
			parameters.put("FechaInicio",fechaInicio);
			parameters.put("FechaFin",fechaFin);
			
			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteDirectivosGeneral.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteDireccionGeneralGPDF (String tipoTramite, String fechaInicio, String fechaFin, List<?> direccion) throws Exception{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionGeneralGPDF(tipoTramite,fechaInicio,fechaFin,direccion));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionGeneralGPDF (String tipoTramite, String fechaInicio, String fechaFin, List<?> direccion) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("TIPOTRAMITE",tipoTramite);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("DIRECCION",direccion);
			
			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteD1Grafica.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteDireccionNNAPDF (List<?> tipoTramite, String estadoTramite, List<?> subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionNNAPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin,sexo));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionNNAPDF (List<?> tipoTramite, String estadoTramite, List<?> subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Id_Tramite",tipoTramite);
			parameters.put("EstadoTramite",estadoTramite);
			parameters.put("Subfondo",subFondo);
			parameters.put("CanalRecepcion",canal);
			parameters.put("FechaInicio",fechaInicio);
			parameters.put("FechaFin",fechaFin);
			parameters.put("Sexo",sexo);
			
			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReportesDirectivosNNA.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
			
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionNNAXLS (List<?> tipoTramite, String estadoTramite, List<?> subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Id_Tramite",tipoTramite);
			parameters.put("EstadoTramite",estadoTramite);
			parameters.put("Subfondo",subFondo);
			parameters.put("CanalRecepcion",canal);
			parameters.put("FechaInicio",fechaInicio);
			parameters.put("FechaFin",fechaFin);
			parameters.put("Sexo",sexo);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReportesDirectivosNNA.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionGeneralGXLS (String tipoTramite, String fechaInicio, String fechaFin, List<?> direccion) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("TIPOTRAMITE",tipoTramite);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("DIRECCION",direccion);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteD1Grafica.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionGeneralXLS (List<?>  tipoTramite, String estadoTramite, List<?> subFondo, String canal, String fechaInicio, String fechaFin) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Id_Tramite",tipoTramite);
			parameters.put("Estado_Tramite",estadoTramite);
			parameters.put("Subfondo",subFondo);
			parameters.put("Canal",canal);
			parameters.put("FechaInicio",fechaInicio);
			parameters.put("FechaFin",fechaFin);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteDirectivosGeneral.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteOperacionXLS (String numRadicado, String fechaInicio, String fechaFin, List<String> tipoTramite, List<String> direccion, String medioRecepcion, String planta) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NoRadicado",numRadicado);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("TIPOTRAMITE",tipoTramite);
			parameters.put("DIRETERRITO",direccion);
			parameters.put("MEDIORECEP",medioRecepcion);
			parameters.put("IDE_PLANTA",planta);
			

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReportesOperacion1.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	//Reporte Tramites por sustanciador
	public static byte[] getReporteTramitesPorSustaciadorPDF (List<String> dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, List<String> tipoTramite) throws Exception{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteTramitesPorSustaciadorPDF(dirTerritorial,fechaInicio,fechaFin,idPlanta,estado,tipoTramite));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper") + "pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteTramitesPorSustaciadorPDF (List<String> dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, List<String> tipoTramite) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("DIRTERRITORIAL",dirTerritorial);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("IDE_PLANTA",idPlanta);
			parameters.put("ESTADO",estado);
			parameters.put("TIPOTRAMITE",tipoTramite);

			JasperReport reporte = null;
			
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteSustanciadores.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteTramitesPorSustaciadorXLS (List<String> dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, List<String> tipoTramite) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();			
			parameters.put("DIRTERRITORIAL",dirTerritorial);
			parameters.put("FECHAINICIO",fechaInicio);
			parameters.put("FECHAFIN",fechaFin);
			parameters.put("IDE_PLANTA",idPlanta);
			parameters.put("ESTADO",estado);
			parameters.put("TIPOTRAMITE",tipoTramite);
			

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "ReporteSustanciadores.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  
			

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	
	public static byte[] obtenerrotuloCompleto (String numero, String documento,String qrCode) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		
		try
		{

			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("NumerRadicado",numero); 
			parameters.put("PrefijoCodigo",documento);
			parameters.put("qrCode",qrCode);
			JasperReport reporte = null;
			//File reporte = null;
			if(buscarTipologia(numero)){
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "sticker.jasper"));
				//reporte = new File("C:\\Configuracion\\sticker.jasper");
			}else{
				reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "stickerSalida.jasper"));
				//reporte = new File("C:\\Configuracion\\stickerSalida.jasper");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);	

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 
		    exporter.exportReport();	
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerPlanillaInternaPDF (String numPlanilla) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{

			Map<String, Object> parameters = new HashMap<String, Object>();

			/*parameters.put("IDEINSTANCIABPM",ideInstancia);
			parameters.put("VALMASIVO",valMasivo); 
			parameters.put("IDEUSUARIO",ideUsuario);*/
			
			parameters.put("NUMEROPLANILLA",numPlanilla);
			JasperReport reporte = null;
			
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "planillaInterna.jasper"));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}
		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerPlanillaInternaXLS (String numPlanilla) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUMEROPLANILLA",numPlanilla);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "PlanillaInterna.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}

	public static byte[] obtenerPlanillaSalidaPDF (String numPlanilla) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUMEROPLANILLA",numPlanilla);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "PlanillaSalida.jasper"));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 

			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerPlanillaSalidaXLS (String numPlanilla) throws Exception
	{
		GetConnectionDB alias = new GetConnectionDB();
		Connection conexion; 
		conexion = alias.getConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NUMEROPLANILLA",numPlanilla);

			JasperReport reporte = null;
			reporte = (JasperReport) JRLoader.loadObject(new File(DataConfigProperties.getPropietat("pathJasper") + "PlanillaSalida.jasper"));
			

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);  

			JRExporter exporter = new JRXlsExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ba); 
			
			exporter.exportReport(); 
			ba.close();
		}catch (Exception e )
		{
			System.out.println("EncontroErrores "+e.getMessage());
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	private static byte[] concatenarPdf(List<byte[]> pdfBytes) throws
	Exception
	{


		FileOutputStream file = new FileOutputStream("1");
		PdfReader reader = null;
		PdfCopyFields copy = new PdfCopyFields(file);
		int tam = 0;

		for (byte[] pdfByte : pdfBytes)
		{
			tam += pdfByte.length;
			reader = new PdfReader(pdfByte);
			copy.addDocument(reader);
		}

		copy.close();
		FileInputStream archivo = new FileInputStream("1");
		byte[] bytesFinal = new byte[tam];

		int c = 0;
		int i = 0;
		while ((c = archivo.read()) != -1)
		{
			bytesFinal[i] = (byte) c;
			i++;
		}
		archivo.close();
		file.close();
		File f = new File("1");
		if (f.exists())
		{
			f.delete();
		}


		return bytesFinal;
	}
	
	public static boolean buscarTipologia(String filtroBusqueda) throws Exception{
		boolean usuariosEncontrado = false;		
		ResultSet resultSet = null;		
		GetConnectionDB alias = new GetConnectionDB();
		Connection conn; 
		conn = alias.getConnection();	
		
		try{
			CallableStatement callableStatement = null;
			String query = "SELECT IDE_DOCUMENTO FROM COR_DOC_CORRESPONDENCIA"
					+" WHERE NRO_RADICADO = '"+filtroBusqueda+"' "
					+ "AND COD_TIPO_CMC IN (1,4)";
			
			callableStatement = conn.prepareCall(query);
			callableStatement.executeQuery();
			resultSet = callableStatement.getResultSet();
			
			if(resultSet.next())
			{
				if(resultSet.getInt("IDE_DOCUMENTO") != 0){
					usuariosEncontrado = true;
				}else{
					usuariosEncontrado = false;
				}
			}
			conn.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return usuariosEncontrado;
	}
	
	private static DefaultTableModel getDefaultData() {
        return  new DefaultTableModel(
        			new Object[][]{{"10/12/2015", "06EE2015100300100004001", new BigDecimal(123),new BigDecimal(1223),"pruebadependencia","destinatarioSedeAdminP","remitente"}},
        			new String[]{"fecha", "numero", "folios", "anexos","destinatarioDependencia","destinatarioSedeAdmin","remitente"});
    }
	
	public static void pasarGarbageCollector(){
   	 /**
   	   https://ayddup.wordpress.com/2011/08/08/la-memoria-en-java-garbage-collector-y-el-metodo-finalize/
   	  */
       Runtime garbage = Runtime.getRuntime();
       System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory());
       
       garbage.gc();

       System.out.println("Memoria libre tras la limpieza: "+ garbage.freeMemory());
   }
}
