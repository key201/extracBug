

import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

//Modelͬ��������ͼʾ����һ�������ǿ���ͨ��Model��Write��������model������д��һ���������
//�������Ϊ��
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
		
		//���Կ�����model.write(OutputStream),model(OutputStream, "RDF/XML-ABBREV"),
		//model.write(OutputStream, "N-TRIPLE")�ֱ�����˲�ͬ��ʽ�����ݡ�
		//model.write(OutputStream):Ҳ������model.write(OutputStream, null);���档Ĭ�ϵ������ʽ
		//model.write(outputStream,��RDF/XML-ABBREV��);ʹ��XML�����﷨���RDF��
		//model.write(outputStream,"N-TRIPLE");���nԪ��ĸ�ʽ
		
		//Model write
		model.write(System.out);
		System.out.println();
		model.write(System.out, "RDF/XML-ABBREV");
		System.out.println();
		model.write(System.out, "N-TRIPLE");
		
		
	}
}
