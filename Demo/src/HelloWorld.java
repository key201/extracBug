
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;
import com.*;

public class HelloWorld {
	
	static String personURI = "http://somewhere/JohnSmith";
	static String fullname  = "John Smith";
	
	static String giveName = "john";
	static String familyName = "Smith";
	
	//����ÿ����ͷ������һ��Statement������Դhttp://.../JohnSmith��һ��vCard.FN���ԣ���ֵ���ı�
	//"John Smith"�������Դ����һ��vCard:N���ԣ�������Ե�ֵ��һ��������Դ����������Դ����������
	//�ֱ���vCard:Given��vCard:Family����ֵ�ֱ����ı����ı���"John"��"Smith"
	//
	
	public static void main(String []args)
	{
		//System.out.println("HelloWorld!");

		// Create an empty model
		Model model = ModelFactory.createDefaultModel();
		
		// create the resource
		Resource johnSmith = model.createResource(personURI);
		
		// add the property
		johnSmith.addProperty(VCARD.FN, fullname);
		johnSmith.addProperty(VCARD.N, 
					model.createResource()
					.addProperty(VCARD.Given, giveName)
					.addProperty(VCARD.Family, familyName));
		
		//Statement
		StmtIterator iter = model.listStatements();
		while(iter.hasNext()){
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();
			
			System.out.println(subject.toString());
			System.out.println(" "+predicate.toString());
			if(object instanceof Resource)
				System.out.println(object.toString());
			else
				System.out.println("\"" + object.toString() + "\"");
		}
		
		System.out.print(".");
	}
	//Model���listStatements������һ��Statement��Iterator��Statement�����ν�����ֱ���
	//getSubject��getPedicate��getObect�����ء������ͷֱ���Resource��property��RDFNode��
	//���п���Object���Ϳ�����Resource�����ı���


}
