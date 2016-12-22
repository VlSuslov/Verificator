import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Verificator implements IVerificator {
	
	String firstName;
	String lastName;
	String email;
	String phone;
	String department;
	String nameEN;
	String fieldsPath;
	String domensPath;
	ArrayList<String> domens;
	
	 Verificator() throws ParserConfigurationException, SAXException, IOException{
		domens=new ArrayList<String>();
		fieldsPath="D://specification.xml";
		domensPath="D://domens.xml";
		fillFilds();
		fillDomens();
	}
	
	void fillFilds() 
	{
		DocumentBuilder documentBuilder;

			try {
				documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = documentBuilder.parse(fieldsPath);
			    Element root = document.getDocumentElement();
			    Element element=(Element) root.getElementsByTagName("firstName").item(0);
			    firstName=element.getTextContent();
			     element=(Element) root.getElementsByTagName("lastName").item(0);
			    lastName=element.getTextContent();
			    element=(Element) root.getElementsByTagName("email").item(0);
			    email=element.getTextContent();
			    element=(Element) root.getElementsByTagName("phone").item(0);
			    phone=element.getTextContent();
			    element=(Element) root.getElementsByTagName("department").item(0);
			    department=element.getTextContent();
			    element=(Element) root.getElementsByTagName("nameEN").item(0);
			    nameEN=element.getTextContent();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}
	
	void fillDomens()
	{
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(domensPath);
		    Element root = document.getDocumentElement();
		    NodeList element= root.getElementsByTagName("domens");
		    for(int i=0;i<element.getLength();i++)
		    {
		     domens.add(element.item(i).getNodeValue());
		    }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	    
	}
	
	public ArrayList<String> Verificate(Account account)
	{

		ArrayList<String> Errors=new ArrayList<String>(); 
        if(!Pattern.matches(firstName, account.firstName))
        	Errors.add("Некорректное поле First Name");
        if(!Pattern.matches(lastName, account.lastName))
        	Errors.add("Некорректное поле Last Name");
        if(!Pattern.matches(email, account.email))
        	Errors.add("Некорректное поле Secondary Email");
        else
        {
        	boolean fit=false;
        	int position=account.email.indexOf("@");
        	String domen=account.email.substring(position, account.email.length()-1);
        	for(String dom:domens)
        	{
        		if(domen.equals(dom))
        		{
        			fit=true;
        			break;
        		}
        		if(!fit)
        			Errors.add("Не корректный домен email в поле Secondary Email");
        	}
        }
        if(!Pattern.matches(phone, account.phone))
        	Errors.add("Некорректное поле Mobile Phone 1");
        if(!Pattern.matches(department, account.department))
        	Errors.add("Некорректное поле Mobile Department");
        if(!Pattern.matches(nameEN, account.firstNameEN))
        	Errors.add("Некорректное поле First Name EN");
        if(!Pattern.matches(nameEN, account.lastNameEN))
        	Errors.add("Некорректное поле Last Name EN");
		return Errors;		
	}
	
	
}
