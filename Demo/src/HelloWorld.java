
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
	
	//它的每个箭头都代表一个Statement。如资源http://.../JohnSmith有一个vCard.FN属性，其值是文本
	//"John Smith"。这个资源还有一个vCard:N属性，这个属性的值是一个无名资源。该无名资源有两个属性
	//分别是vCard:Given和vCard:Family。其值分别是文本的文本的"John"和"Smith"
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
	//Model类的listStatements将返回一个Statement的Iterator。Statement有主语、谓语、客体分别用
	//getSubject、getPedicate、getObect来返回。其类型分别是Resource、property和RDFNode。
	//其中客体Object类型可以是Resource或者文本。


}
