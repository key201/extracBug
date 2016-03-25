package editXML;
import java.util.regex.*;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

 

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DOMParserXml {

	private String bugsFileName = "";
	 //private String xmlFileName  = "";
	private Map<String, modifyInfo>mapModifyInfos  = new HashMap<String, modifyInfo>();
	private DocumentBuilderFactory builderFactory  = DocumentBuilderFactory.newInstance(); 
	 
	 //无参构造函数
	 DOMParserXml() {		 
	 }
	 
	 //带参数构造函数
	 DOMParserXml(String bugsFileName) {
		 this.bugsFileName = bugsFileName;
	 }	 

	 //Load and parse XML file into DOM 
	public Document parse(String filePath) { 
	    Document document = null; 
	    try { 
	       //DOM parser instance 
	       DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	       //parse an XML file into a DOM tree 
	       document = builder.parse(new File(filePath)); 
	    } catch (ParserConfigurationException e) { 
	       e.printStackTrace();  
	    } catch (SAXException e) { 
	       e.printStackTrace(); 
	    } catch (IOException e) { 
	       e.printStackTrace(); 
	    } 
	    return document; 
	 } 
	
 public void paraCommitInfo(String xmlFileName) {
	 
	 int volidateCount = 0;
	 DOMParserXml parser = new DOMParserXml(); 
     Document document = parser.parse(xmlFileName); 
     //get root element 
     Element rootElement = document.getDocumentElement(); 

     NodeList nodeList = rootElement.getElementsByTagName("logentry"); 
     if(nodeList != null) 
     { 
    	 //遍历所有的logentry
        for (int i = 0 ; i < nodeList.getLength(); i++) 
        {
           modifyInfo indvalInfo = new modifyInfo(); 
        	 
           Element element = (Element)nodeList.item(i); 
           String revisionId = element.getAttribute("revision"); 
           NodeList subnodes = element.getChildNodes();
           //遍历logentry的所有子节点
           for(int j=0;j<subnodes.getLength();j++)
           {
        	   
        	   Node subtextnode = subnodes.item(j);
	           if (subtextnode.getNodeType() == Node.ELEMENT_NODE)
	           {
	        	   String subNodeName = subtextnode.getNodeName();
	        	   if (subNodeName.equals("msg"))
	        	   {
	        		   String msg = subtextnode.getTextContent();
	        		   String msgBugs = RegExPatern("id=[0-9]{5}", msg);
	        		   if((msgBugs.length()!=0))
	        		   {
	        			   String strTestSub = msgBugs.substring(3);
	        			   if ((IsExistBug(strTestSub)==1)){
	        			   //说明找到bugid
	        				   System.out.println("bug is volidate" + msgBugs);
	        				   //通过键获取值
	        				   volidateCount++;
	        			   }
	        		   }
	        			   
	        		   //System.out.println(msg);
	        	   }
	        	   //System.out.println(subtextnode.getNodeName());
	           }
	           
	           if (subtextnode.getNodeName().equals("paths"))
	           {
	        	   //遍历所有的path	        	   
	        	   NodeList pathnodes = subtextnode.getChildNodes();
	        	   for(int k=0; k<pathnodes.getLength();k++)
	        	   {
	        		   //
	        		   Node tmpNode = pathnodes.item(k);//这儿的注释还要去掉的
	        		   //if (tmpNode.getNodeType() == Node.ELEMENT_NODE)
	        			//   System.out.println(tmpNode.getNodeName());
	        	   }
	           }
           }
           //System.out.println(id);
        }
        //mapModifyInfos.put(revisionId, indvalInfo);
     } 
     System.out.println("volidate count is:" + volidateCount);
       //System.out.println("Total logentry is:"+ncount);
 } 
  

 public String RegExPatern(String regExpression, String piContent) 
 {
	   //StringBuffer piPeiAll = new StringBuffer(""); 
	 String tmpContent = "";
       Pattern p = Pattern.compile(regExpression);
       Matcher m = p.matcher(piContent);
       while(m.find()) {
    	  
    	   tmpContent = m.group();
    	   //piPeiAll.append(tmpContent);
         //System.out.println(tmpContent);
       }
       
       //return piPeiAll.toString();
       return tmpContent;
 }
 
 //判断提交日志中的id是否在bugzilla中存在的
 public int IsExistBug(String bugID){ 	
	 
	 ReadFromFile rFile = new ReadFromFile();
	 String wholeFileTxt = rFile.readFileByLines(bugsFileName);
	 return wholeFileTxt.contains(bugID)? 1:0;
 }
 
 //未完成的函数
 public void extractBugs() {
	 DOMParserXml domxml = new DOMParserXml("C:/temp/Tomcat7.txt");
	 domxml.paraCommitInfo("f:/books.xml");
	 
 }
 public static void main(String[] args) {
	 DOMParserXml domxml = new DOMParserXml("C:/temp/Tomcat7.txt");
	 domxml.paraCommitInfo("f:/books.xml");
 }
}

