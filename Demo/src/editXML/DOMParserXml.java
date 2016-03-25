package editXML;

import java.util.regex.*;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

public class DOMParserXml {

	private String bugsFileName = "";
	private String wholeFileTxt = "";

	 //private String xmlFileName  = "";
	private Map<String, modifyInfo>mapModifyInfos  = new HashMap<String, modifyInfo>();
	Map<String, String> mapBugInfos = new HashMap<String, String>();
	private DocumentBuilderFactory builderFactory  = DocumentBuilderFactory.newInstance(); 
	 
	 //无参构造函数
	 DOMParserXml() {		
		 ReadFromFile rFile = new ReadFromFile();
		 wholeFileTxt = rFile.readFileByLines(bugsFileName);
		 mapBugInfos = rFile.getBugIdMap();
	 }
	 
	 //带参数构造函数
	 DOMParserXml(String bugsFileName) {
		 this.bugsFileName = bugsFileName;
		 ReadFromFile rFile = new ReadFromFile();
		 wholeFileTxt = rFile.readFileByLines(bugsFileName);
		 mapBugInfos = rFile.getBugIdMap();
	 }	 
	 Map<String, modifyInfo>getMapModifyInfos(){
		 return mapModifyInfos;
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
	
	//遍历logentry的所有子节点	
 public int travelLogentry(NodeList subnodes, modifyInfo indvalInfo){
	 int volidateCount = 0;
     if(subnodes != null) 
     {    	 
    	 for(int j=0;j<subnodes.getLength();j++)
    	 {
    		 Node subtextnode = subnodes.item(j);
    		 if (subtextnode.getNodeType() == Node.ELEMENT_NODE)
    		 {
    			 String subNodeName = subtextnode.getNodeName();
    			 
    			 if (subNodeName.equals("date"))
    			 {
    				 String modifyTime = subtextnode.getTextContent();
    				 indvalInfo.setCommitTime(modifyTime);
    			 }
    			 
    			 if (subNodeName.equals("msg")) 
    			 {    				 
    				 String msg = subtextnode.getTextContent();
    				 
    				 //设置日志信息
    				 indvalInfo.setmodifyMessage(msg.replace("\r|\n", ""));
    				 
    				 //提取日志信息中的bugid
    				 String msgBugs = RegExPatern("id=[0-9]{5}", msg);
    				 if((msgBugs.length()!=0))
    				 {
	        			   String bugId = msgBugs.substring(3);//删除掉id=这三个字符  			   
	        			   
	        			   //获取bug描述
	        			   String strBugInfo = getBugInformation(bugId.trim());
	        			   //设置bugID和bug的描述信息
	        			   indvalInfo.setBugInformation(strBugInfo);
	        			   indvalInfo.setBugId(bugId.trim());	        			 
	        		   }
    			 }
    		 }
    		 if (subtextnode.getNodeName().equals("paths"))
	           {
	        	   //遍历所有的path	        	   
	        	   NodeList pathnodes = subtextnode.getChildNodes();
	        	   for(int k=0; k<pathnodes.getLength();k++){
	        		   
	        		   Node tmpNode= pathnodes.item(k);//这儿的注释还要去掉的
	        		   if (tmpNode.getNodeType() == Node.ELEMENT_NODE)
	        		   {
	        			   codeModInfos modifyInfo = new codeModInfos();
	        			   modifyInfo.setModifyAction(((Element)tmpNode).getAttribute("action"));
	        			   modifyInfo.setFileType(((Element)tmpNode).getAttribute("kind"));
	        			   modifyInfo.setProp_mods(((Element)tmpNode).getAttribute("prop-mods"));
	        			   modifyInfo.setText_mods(((Element)tmpNode).getAttribute("text-mods"));
	        			   modifyInfo.setModifyMsg(((Element)tmpNode).getTextContent());	        			   
	        		   }
	        	   }
	           }
    	 }    	 
     }
     return volidateCount;
 }
 
 //返回logentry的个数
 public long paraCommitInfo(String xmlFileName) {

     Document document = parse(xmlFileName); 
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
           
           indvalInfo.setmodifyRevision(revisionId);
           
           NodeList subnodes = element.getChildNodes();
           //遍历logentry的所有子节点
           travelLogentry(subnodes, indvalInfo);
           mapModifyInfos.put(revisionId, indvalInfo);
        }
      } 
     return mapModifyInfos.size();
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
 public boolean IsExistBug(String bugID){ 	
	 
	 return wholeFileTxt.contains(bugID)? true:false;
 }
 
 public String getBugInformation(String bugID)
 {	 
	 //return wholeFileTxt.contains(bugID)? mapBugInfos.get(bugID):"";
	 bugID.trim();
	 //System.out.println(wholeFileTxt.contains(bugID));
	 //System.out.println(mapBugInfos.get(bugID));
	 return mapBugInfos.get(bugID);
 }
 
 //未完成的函数
 public void extractBugs() {
	 DOMParserXml domxml = new DOMParserXml("C:/temp/Tomcat7.txt");
	 domxml.paraCommitInfo("f:/books.xml");
	 
 }
 public static void main(String[] args) {
	 
	 DOMParserXml domxml = new DOMParserXml("C:/temp/Tomcat7bugs_Info.txt"); 
	 domxml.paraCommitInfo("C:/temp/commit_Info.xml");
	 
	 Map<String, modifyInfo>mapModiInfos = domxml.getMapModifyInfos();
	 Map<String, modifyInfo>nonBugInfos = new HashMap<String, modifyInfo>();
	 int bugIdCount   = 0;
	 int nonBugIdCount = 0;
	 int allInfoCount = mapModiInfos.size();
	 
	 //logentry中的revision是唯一的
	 for(String revisionInfo: mapModiInfos.keySet())
	 {
     	modifyInfo infoKeyValue = mapModiInfos.get(revisionInfo);
     	if(infoKeyValue.getBugId()!=null){
     		System.out.println(infoKeyValue.getBugId()+"----->"+infoKeyValue.getmodifyRevision());
     		bugIdCount++;
     	}
     	else
     	{
     		//nonBugInfos.put();
     	}
     }
	 nonBugIdCount = allInfoCount - bugIdCount;
	 System.out.println("提取到的所有修改信息版本数:"+allInfoCount);
	 System.out.println("能够提取的到bugId的个数: "+bugIdCount);
	 System.out.println("不能够提取的到bugId的个数:"+nonBugIdCount);
 }
}

