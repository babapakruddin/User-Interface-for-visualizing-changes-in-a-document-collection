package dataset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;



public class xml_Parse {
	
	
	
	public static ArrayList<String> getallXMLFiles(String pathName)
	{	
		ArrayList<String> fileList = new ArrayList<String>();
		File fld = new File(pathName); 
		File[] file_List = fld.listFiles();
		
		for (int f = 0; f < file_List.length; f++) {
		      if (file_List[f].isFile()) {
		        fileList.add(file_List[f].getName());
		      } else if (file_List[f].isDirectory()) {
		        System.out.println("Directory " + file_List[f].getName());
		      } 
		
	}
		return fileList;
	}


	
	public static ArrayList<parse_Law> parseXMl(String xmlFilename) throws ParserConfigurationException, SAXException, IOException, NullPointerException, DOMException
	{
		File xml_File = new File(xmlFilename);
		ArrayList<parse_Law> lawData_1 = new ArrayList<parse_Law>();
		DocumentBuilderFactory db_Factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder d_Builder = db_Factory.newDocumentBuilder();
		Document doc = d_Builder.parse(xml_File);

		doc.getDocumentElement().normalize();
		
		NodeList dokumente = doc.getElementsByTagName("dokumente");
		String doknr = dokumente.item(0).getAttributes().getNamedItem("doknr").getNodeValue();
		String builddate = dokumente.item(0).getAttributes().getNamedItem("builddate").getNodeValue();
		
	     FileInputStream inp = new FileInputStream(xml_File);
         byte[] bf = new byte[(int)xml_File.length()];
         inp.read(bf);
         String norm = new String(bf, "UTF-8");
				parse_Law  ld = new parse_Law(
						doknr,
						builddate,
						norm				
					);
				lawData_1.add(ld);	
		
		
		return lawData_1;
		
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,  SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String systemPath = System.getProperty("user.dir");
		System.out.println(systemPath+"/src/main/java/data/");
		
		ArrayList<String> filenameList = getallXMLFiles(systemPath+"/src/main/java/data/");
		ArrayList<parse_Law> lawData = new ArrayList<parse_Law>();
		for (String xmlFilename : filenameList)
		{
			ArrayList<parse_Law> lawData_l = parseXMl(systemPath + "/src/main/java/data/" + xmlFilename);
			lawData.addAll(lawData_l);
		}
		insert2ElasticSearch ins = new insert2ElasticSearch();
		ins.insertES(lawData);
		
	}
	


}
