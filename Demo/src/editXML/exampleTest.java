package editXML;
import java.util.List;
import java.util.regex.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class exampleTest {
	
//����Set��ʹ��	
public static void testSet()
{
	Set set = new HashSet();
	String s1 = new String("Hello");
	String s2=s1;
	String s3 = new String("world");
	
	set.add(s1);
	set.add(s2);
	set.add(s3);
	
	System.out.println("�����ж������Ŀ:"+set.size());//��ӡ�����ж������Ŀ
	
	//Set��add()����������ж϶����Ƿ��Ѿ�����ڼ����У�
	String newStr = "hel";
	boolean isExists=false;
	
	Iterator<String> it = set.iterator();
	while(it.hasNext())
	{
		String oldStr = (String)it.next();
		if(newStr.equals(oldStr))
		{
			isExists=true;
		}
		System.out.println(oldStr);
	}
	System.out.println(isExists);
}


public static void main(String[] args) {
	
	//���ϵĲ���
	testSet();
	
	List lsBugId = new ArrayList();
	
	
	Pattern p = Pattern.compile("[0-9]{5}");
	Matcher m = p.matcher("18462	Tomcat 7	Catalin");
	while(m.find()) {
		String bugid = m.group() + m.start() +(m.end() - 1);
		lsBugId.add(bugid);
		System.out.println("Match " + m.group() +" at positions " + m.start() + "-" + (m.end() - 1));
		}
	}
}
	

