package es.uvigo.esei.dai.hybridserver;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class Launcher {
	public static void main(String[] args) throws Exception{
		HybridServer server= null;
		File xml= new File("../configuration.xml");
		File validar=new File ("../configuracion.xsd");
		if(args.length==0) {
			if(validateXMLSchema(validar,xml)) {
				XMLConfigurationLoader loadxml = new XMLConfigurationLoader();
				loadxml.load(xml);
			}
		}
		
		server.start();
	}

	private static boolean validateXMLSchema(File validar, File xml) throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory
			    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);	
		  try{
			  Schema schema = schemaFactory.newSchema(validar);
			  Validator validator = schema.newValidator();
			  Source xmlFile = new StreamSource(xml);
			  validator.validate(xmlFile);
			  return true;
			
		} catch (Exception e) {
			HTTPResponse name = new HTTPResponse();
			name.setStatus(HTTPResponseStatus.S400);
			return false;
		}
		
	}
	
}
