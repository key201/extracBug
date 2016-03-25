import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class NSPrefix {
	public static void main(String [] args)
	{
		//该程序首先调用Model的createProperty和createResource生成属性资源。然后
		//调用Model add想用model中增加三个Statement。add的三个参数分别是三元组
		//的主语、谓语和客体。想Model中增加内容实际上就是增加三元组
		Model m = ModelFactory.createDefaultModel();
		String nsA = "http://somewhere/else#";
		String nsB = "http://nowhere/else";
		
		//创建Resource和Property
		Resource root = m.createResource(nsA + "root");
		Property P    = m.createProperty(nsA + "P");
		Property Q    = m.createProperty(nsA + "Q");
		Resource x 	  = m.createResource(nsA + "x");
		Resource y	  = m.createResource(nsA + "y");
		Resource z    = m.createResource(nsA + "z");
		
		//层叠增加三个Statement
		m.add(root, P, x).add(root, P, y).add(y, Q, z);
		System.out.println("#--- no special prefixes defined");
		m.write(System.out);
		System.out.println("#--- nsA defined");
		
		
		//设置namespace nsB前缀为"cat"
		m.setNsPrefix("cat", nsB);
		m.write(System.out);
	}

}
