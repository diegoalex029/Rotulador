package co.soaint.contingencia.operaciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class GeneradorRotulo {

	public static byte[] getDocumentoCompleto (String numeroRadicado, String documento) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerrotuloCompleto(numeroRadicado ,documento));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] getReporteOperacion (String numRadicado, String fechaInicio, String fechaFin, String tipoTramite, String direccion, String medioRecepcion, String planta) throws Exception
	{
		
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteOperacionPDF(numRadicado,fechaInicio,fechaFin,tipoTramite,direccion,medioRecepcion, planta));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

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

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	@SuppressWarnings("deprecation")
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"Grupo_archivo_sindical.jasper");

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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"Empresa_Servicios_Temporales.jasper");

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
		finally {
			alias.closeConnection(conexion);
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"Visita_NNA.jasper");

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
		finally {
			alias.closeConnection(conexion);
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"Entrevista_NNA.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteOperacionPDF (String numRadicado, String fechaInicio, String fechaFin, String tipoTramite, String direccion, String medioRecepcion, String planta) throws Exception
	{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteOperacionPDF(numRadicado,fechaInicio,fechaFin,tipoTramite,direccion,medioRecepcion, planta));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteOperacionPDF (String numRadicado, String fechaInicio, String fechaFin, String tipoTramite, String direccion, String medioRecepcion, String planta) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReportesOperación1.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteDireccionGeneralPDF (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin) throws Exception{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionGeneralPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionGeneralPDF (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteDirectivos1.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteDireccionGeneralGPDF (String tipoTramite, String fechaInicio, String fechaFin, String direccion) throws Exception{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionGeneralGPDF(tipoTramite,fechaInicio,fechaFin,direccion));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionGeneralGPDF (String tipoTramite, String fechaInicio, String fechaFin, String direccion) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteD1Grafica.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] getReporteDireccionNNAPDF (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception{
		List<byte[]> pdfBytes = new ArrayList<byte[]>();
		pdfBytes.add(obtenerReporteDireccionNNAPDF(tipoTramite,estadoTramite,subFondo,canal,fechaInicio,fechaFin,sexo));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteDireccionNNAPDF (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReportesDirectivo2.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionNNAXLS (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin, String sexo) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteD1Grafica.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionGeneralGXLS (String tipoTramite, String fechaInicio, String fechaFin, String direccion) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteD1Grafica.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteDireccionGeneralXLS (String tipoTramite, String estadoTramite, String subFondo, String canal, String fechaInicio, String fechaFin) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteDirectivos1.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteOperacionXLS (String numRadicado, String fechaInicio, String fechaFin, String tipoTramite, String direccion, String medioRecepcion, String planta) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReportesOperación1.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	//Reporte Tramites por sustanciador
	public static byte[] getReporteTramitesPorSustaciadorPDF (String dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, String tipoTramite) throws Exception{
		List<byte[]> pdfBytes = new ArrayList();
		pdfBytes.add(obtenerReporteTramitesPorSustaciadorPDF(dirTerritorial,fechaInicio,fechaFin,idPlanta,estado,tipoTramite));

		byte[] bytesFinal = concatenarPdf(pdfBytes);

		File file = new File (DataConfigProperties.getPropietat("pathJasper")+"pdfmuestra.pdf");

		FileOutputStream out = new FileOutputStream(file);
		out.write(bytesFinal);
		out.close();

		return bytesFinal;
	}
	
	public static byte[] obtenerReporteTramitesPorSustaciadorPDF (String dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, String tipoTramite) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("namejndi")+"ReporteSustanciadores.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	public static byte[] obtenerReporteTramitesPorSustaciadorXLS (String dirTerritorial, String fechaInicio, String fechaFin, String idPlanta, String estado, String tipoTramite) throws Exception
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"ReporteSustanciadores.jasper");

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
		finally {
			alias.closeConnection(conexion);
		}

		byte[] bytes = ba.toByteArray();
		return bytes;
	}
	
	
	public static byte[] obtenerrotuloCompleto (String numero, String documento) throws Exception
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
			JasperReport reporte = null;
			if(buscarTipologia(numero)){
				reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"sticker.jasper");
			}else{
				reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"stickerSalida.jasper");
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
		finally {
			alias.closeConnection(conexion);
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
			parameters.put("NUMEROPLANILLA",numPlanilla);
			JasperReport reporte = null;
			
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"planillaInterna.jasper");
			
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
		finally {
			alias.closeConnection(conexion);
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"PlanillaInterna.jasper");

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
		finally {
			alias.closeConnection(conexion);
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"PlanillaSalida.jasper");

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
		finally {
			alias.closeConnection(conexion);
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
			reporte = (JasperReport) JRLoader.loadObject(DataConfigProperties.getPropietat("pathJasper")+"PlanillaSalida.jasper");

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
		finally {
			alias.closeConnection(conexion);
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
			String query = "SELECT IDE_DOCUMENTO FROM SGD.COR_DOC_CORRESPONDENCIA"
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
		finally {
			alias.closeConnection(conn);
		}
		
		return usuariosEncontrado;
	}
}
