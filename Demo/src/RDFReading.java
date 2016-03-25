

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
		
		// 使用FileManager查找文件
		InputStream in = FileManager.get().open(inputFileName);
		if(in==null){
			throw new IllegalArgumentException("File" + inputFileName + "not found!");
		}
		
		Model model = ModelFactory.createDefaultModel();
		//读取RDF/XML文件
		//Model的read方法可以读取RDF输入到model中。第二个参数可以指定格式
		model.read(in, null);
		model.write(System.out);
		
		}
}
