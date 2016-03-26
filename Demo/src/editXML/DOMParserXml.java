package editXML;

import java.util.regex.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	 
	 //�޲ι��캯��
	 DOMParserXml() {		
		 ReadFromFile rFile = new ReadFromFile();
		 wholeFileTxt = rFile.readFileByLines(bugsFileName);
		 mapBugInfos = rFile.getBugIdMap();
	 }
	 
	 //���������캯��
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
	
	//����logentry�������ӽڵ�	
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
    				 
    				 //������־��Ϣ
    				 indvalInfo.setmodifyMessage(msg.replace("\r|\n", ""));
    				 
    				 //��ȡ��־��Ϣ�е�bugid
    				 String msgBugs = RegExPatern("id=[0-9]{5}", msg);
    				 if((msgBugs.length()!=0))
    				 {
	        			   String bugId = msgBugs.substring(3);//ɾ����id=�������ַ�  			   
	        			   
	        			   //��ȡbug����
	        			   String strBugInfo = getBugInformation(bugId.trim());
	        			   //����bugID��bug��������Ϣ
	        			   indvalInfo.setBugInformation(strBugInfo);
	        			   indvalInfo.setBugId(bugId.trim());	        			 
	        		   }
    			 }
    		 }
    		 if (subtextnode.getNodeName().equals("paths"))
	           {
	        	   //�������е�path	        	   
	        	   Node pathnode = subtextnode.getFirstChild();
	        	   //for(int k=0; k<pathnodes.getLength();k++)
	        	   while(pathnode!=null)
	        	   {
	        		   
	        		   //Node tmpNode= pathnodes.item(k);//�����ע�ͻ�Ҫȥ����
	        		   
	        		   Node tmpNode = pathnode.getFirstChild();
	        		   while(tmpNode!=null){
	        		   List<codeModInfos> listPaths = new ArrayList<codeModInfos>();
	        		   
	        		   if (pathnode.getNodeType() == Node.ELEMENT_NODE)
	        		   {
	        			   codeModInfos modifyInfo = new codeModInfos();
	        			   modifyInfo.setModifyAction(((Element)pathnode).getAttribute("action"));
	        			   modifyInfo.setFileType(((Element)pathnode).getAttribute("kind"));
	        			   modifyInfo.setProp_mods(((Element)pathnode).getAttribute("prop-mods"));
	        			   modifyInfo.setText_mods(((Element)pathnode).getAttribute("text-mods"));
	        			   modifyInfo.setModifyMsg(((Element)pathnode).getTextContent());
	        			   String strpathText = ((Element)pathnode).getTextContent();
	        			   if (!strpathText.contains("changelog.xml"))     				   
	        			   listPaths.add(modifyInfo);
	        		   }
	        		   indvalInfo.setModifyPath(listPaths);
	        		   tmpNode = tmpNode.getNextSibling();
	        		   }
	        		   pathnode = pathnode.getNextSibling();
	        	   }
	           }
    	 }    	 
     }
     return volidateCount;
 }
 
 //����logentry�ĸ���
 public long paraCommitInfo(String xmlFileName) {

     Document document = parse(xmlFileName); 
     //get root element 
     Element rootElement = document.getDocumentElement(); 

     NodeList nodeList = rootElement.getElementsByTagName("logentry"); 
     if(nodeList != null) 
     { 
    	 //�������е�logentry
        for (int i = 0 ; i < nodeList.getLength(); i++) 
        {
           modifyInfo indvalInfo = new modifyInfo(); 
        	 
           Element element = (Element)nodeList.item(i); 
           String revisionId = element.getAttribute("revision"); 
           
           indvalInfo.setmodifyRevision(revisionId);
           
           NodeList subnodes = element.getChildNodes();
           //����logentry�������ӽڵ�
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
 
 //�ж��ύ��־�е�id�Ƿ���bugzilla�д��ڵ�
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
 
 //δ��ɵĺ���
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
	 
	 //logentry�е�revision��Ψһ��
	 for(String revisionInfo: mapModiInfos.keySet())
	 {
     	modifyInfo infoKeyValue = mapModiInfos.get(revisionInfo);
     	if(infoKeyValue.getBugId()!=null){
     		System.out.println(infoKeyValue.getBugId()+"----->"+infoKeyValue.getmodifyRevision());
     		bugIdCount++;
     		List<codeModInfos> listPath= infoKeyValue.getmodifyPathList();
     		Iterator<codeModInfos> iter = listPath.iterator();
     		while (iter.hasNext())
    		{
     			codeModInfos codeInfo = iter.next();
    			System.out.println(codeInfo.getModifyMsg());
    		}
     	}
     	else
     	{
     		//nonBugInfos.put();
     	}
     }
	 nonBugIdCount = allInfoCount - bugIdCount;
	 System.out.println("��ȡ���������޸���Ϣ�汾��:"+allInfoCount);
	 System.out.println("�ܹ���ȡ�ĵ�bugId�ĸ���: "+bugIdCount);
	 System.out.println("���ܹ���ȡ�ĵ�bugId�ĸ���:"+nonBugIdCount);
 }
}

