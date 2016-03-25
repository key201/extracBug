

import java.io.File;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;


public class RDFReading {
	
	
	public static String inputFileName = "resources.rdf";
	
	public static void main(String [] args){
	
		String strpath = System.getProperty("user.dir");
		System.out.print(strpath);
		   
		inputFileName = strpath  + File.separator + inputFileName ;
		
		// ʹ��FileManager�����ļ�
		InputStream in = FileManager.get().open(inputFileName);
		if(in==null){
			throw new IllegalArgumentException("File" + inputFileName + "not found!");
		}
		
		Model model = ModelFactory.createDefaultModel();
		//��ȡRDF/XML�ļ�
		//Model��read�������Զ�ȡRDF���뵽model�С��ڶ�����������ָ����ʽ
		model.read(in, null);
		model.write(System.out);
		
		}
}
