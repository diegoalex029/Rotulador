package co.soaint.contingencia.operaciones;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class DataConfigProperties {

	private final Configuration configuration;

	// init singleton threatsafe
	protected DataConfigProperties(){
		Configuration config = null;
		try {
			config = new PropertiesConfiguration("DataConfigProperties.prop");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Exception(e);
		}
		this.configuration = config;
	}

	private static class PropertiesHolder {
		private final static DataConfigProperties INSTANCE = new DataConfigProperties();
	}

	private static DataConfigProperties getInstance() {
		return PropertiesHolder.INSTANCE;
	}

	// final singleton

	// implementación lazy
	/**
	 * Recuperamos una propiedad concreta de un entorno
	 */
	public static String getPropietat(String prop) {
		return DataConfigProperties.getInstance().configuration.getString(prop);
	}
	
	public static Configuration getConfig(){
		return DataConfigProperties.getInstance().configuration;
	}

}
