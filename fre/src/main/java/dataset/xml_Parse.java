package com.masterthesis.ovgu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;




public class XmlParse {
	
	
	public static ArrayList<ArrayList<parse_Law>> order_ParseLaw(ArrayList<parse_Law> lawdata, Set<String> unqDoknr)
	{
		ArrayList<ArrayList<parse_Law>> sParseLists = new ArrayList<ArrayList<parse_Law>>(); 
		for (String donkr : unqDoknr)
		{
			ArrayList<parse_Law> spl = new ArrayList<parse_Law>();
			for (parse_Law pl : lawdata)
			{
				if (donkr.equals(pl.getDoknr()))
				{
					spl.add(pl);
				}
			}
			sParseLists.add(spl);
		}
		return sParseLists;
	}
	
	
	public static ArrayList<ArrayList<parse_Law>> setpFalg(ArrayList<ArrayList<parse_Law>> sParseLists) throws IOException
	{
		ArrayList<ArrayList<parse_Law>> finalwDif = new ArrayList<ArrayList<parse_Law>>(); 
		LineComparsion linC = new LineComparsion();
		
		for (ArrayList<parse_Law> spList : sParseLists)
		{
			int cBdate =  0;
			for (parse_Law pl : spList)
			{	
				int bDate = Integer.parseInt((pl.getBuilddate()).substring(0,8));
				if ( bDate > cBdate )
				{
					cBdate = bDate;
				}
			}

			for (parse_Law pl : spList)
			{
				int LBdate = Integer.parseInt((pl.getBuilddate()).substring(0,8));
				
				 if (cBdate == LBdate)
				 {
					 pl.setfplag("N");
					 
				 }
				 else 
					 pl.setfplag("Y");
			}
			
			finalwDif.add(linC.linComp(spList));
			
			}
			return sParseLists;
		}
		
		

	
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


	
	public static parse_Law parseXMl(String xmlFilename) throws ParserConfigurationException, SAXException, IOException, NullPointerException, DOMException
	{
		File xml_File = new File(xmlFilename);
		DocumentBuilderFactory db_Factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder d_Builder = db_Factory.newDocumentBuilder();
		Document doc = d_Builder.parse(xml_File);
		

		doc.getDocumentElement().normalize();
		
		NodeList dokumente = doc.getElementsByTagName("dokumente");
		String doknr = dokumente.item(0).getAttributes().getNamedItem("doknr").getNodeValue();
		String builddate = (dokumente.item(0).getAttributes().getNamedItem("builddate").getNodeValue());
		NodeList xmlnodeList = doc.getElementsByTagName("norm");
		
		Node xmlNode = xmlnodeList.item(0);
		
		Element eElement = (Element) xmlNode;
		
		String fplag = "N";
		
	     FileInputStream inp = new FileInputStream(xml_File);
         byte[] bf = new byte[(int)xml_File.length()];
         inp.read(bf);
         String norm = new String(bf, "UTF-8");
				parse_Law  ld = new parse_Law(
						builddate,
						fplag,
						doknr,
						norm,
						"Test",
						norm);
		return ld;
		
	}
	
	public static void main(String[] args) throws ParserConfigurationException, IOException,  SQLException, ClassNotFoundException, NullPointerException, DOMException, SAXException {
		// TODO Auto-generated method stub
		String systemPath = System.getProperty("user.dir");
		//System.out.println(systemPath+"/src/main/resources/data/");
		List<String> doknrList = new ArrayList<String>();
		
		LineComparsion lComp = new LineComparsion();
		
		ArrayList<String> filenameList = getallXMLFiles(systemPath+"/src/main/resources/data/");
		ArrayList<parse_Law> lawData = new ArrayList<parse_Law>();
		ArrayList<parse_Law> fList1 = new ArrayList<parse_Law>();
		for (String xmlFilename : filenameList)
		{
			parse_Law pl  = parseXMl(systemPath + "/src/main/resources/data/" + xmlFilename);
			lawData.add(pl);
			doknrList.add(pl.getDoknr());
		}
		Set<String> unqDoknr = new HashSet<String>(doknrList);
		ArrayList<ArrayList<parse_Law>> sParseLists = order_ParseLaw(lawData, unqDoknr);
		
		ArrayList<ArrayList<parse_Law>> pfList = setpFalg(sParseLists);
		for(ArrayList<parse_Law> pf :pfList)
		{
			if (pf.size() == 1)
			{
				fList1.add(pf.get(0));
			}
			else 
			{
				fList1.addAll(lComp.linComp(pf));
			}
		}
		
		insert2ElasticSearch ins = new insert2ElasticSearch();
		ins.insertES(fList1);
		
	}

}
