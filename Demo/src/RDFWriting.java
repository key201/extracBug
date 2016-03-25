

import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

//Model同第三部分图示所述一样。我们可以通过Model的Write方法将其model中内容写入一个输出流。
//本例输出为：
public class RDFWriting {
	public static void main(String [] args)
	{
		// Introduction
		String personURI = "http://somewhere/JohSmith";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;
		Model model = ModelFactory.createDefaultModel();
		
		Resource johnSmith = model.createResource(personURI);
		johnSmith.addProperty(VCARD.FN, fullName);
		johnSmith.addProperty(VCARD.N, 
				model.createResource()
				.addProperty(VCARD.Given, givenName)
				.addProperty(VCARD.Family, familyName));
		
		//可以看出，model.write(OutputStream),model(OutputStream, "RDF/XML-ABBREV"),
		//model.write(OutputStream, "N-TRIPLE")分别输出了不同格式的内容。
		//model.write(OutputStream):也可以用model.write(OutputStream, null);代替。默认的输出格式
		//model.write(outputStream,“RDF/XML-ABBREV”);使用XML缩略语法输出RDF。
		//model.write(outputStream,"N-TRIPLE");输出n元组的格式
		
		//Model write
		model.write(System.out);
		System.out.println();
		model.write(System.out, "RDF/XML-ABBREV");
		System.out.println();
		model.write(System.out, "N-TRIPLE");
		
		
	}
}
