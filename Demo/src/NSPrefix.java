import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class NSPrefix {
	public static void main(String [] args)
	{
		//�ó������ȵ���Model��createProperty��createResource����������Դ��Ȼ��
		//����Model add����model����������Statement��add�����������ֱ�����Ԫ��
		//�����ν��Ϳ��塣��Model����������ʵ���Ͼ���������Ԫ��
		Model m = ModelFactory.createDefaultModel();
		String nsA = "http://somewhere/else#";
		String nsB = "http://nowhere/else";
		
		//����Resource��Property
		Resource root = m.createResource(nsA + "root");
		Property P    = m.createProperty(nsA + "P");
		Property Q    = m.createProperty(nsA + "Q");
		Resource x 	  = m.createResource(nsA + "x");
		Resource y	  = m.createResource(nsA + "y");
		Resource z    = m.createResource(nsA + "z");
		
		//�����������Statement
		m.add(root, P, x).add(root, P, y).add(y, Q, z);
		System.out.println("#--- no special prefixes defined");
		m.write(System.out);
		System.out.println("#--- nsA defined");
		
		
		//����namespace nsBǰ׺Ϊ"cat"
		m.setNsPrefix("cat", nsB);
		m.write(System.out);
	}

}
