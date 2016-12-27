import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Verificator implements IVerificator {

	Element firstName;
	Element lastName;
	Element emailProviders;
	Element phones;
	Element departments;
	Element firstNameEN;
	Element lastNameEN;
	String fieldsPath;
	String domensPath;
	ArrayList<Element> domens;

	Verificator() throws ParserConfigurationException, SAXException, IOException {
		fieldsPath = "D://specification.xml";
		domensPath = "D://domens.xml";
		fillFilds();
		fillDomens();
	}

	void fillFilds() {
		DocumentBuilder documentBuilder;

		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(fieldsPath);
			Element root = document.getDocumentElement();
			firstName = (Element) root.getElementsByTagName("firstName").item(0);
			lastName = (Element) root.getElementsByTagName("lastName").item(0);
			emailProviders = (Element) root.getElementsByTagName("email-providers");
			phones = (Element) root.getElementsByTagName("phones");
			departments = (Element) root.getElementsByTagName("departmens");
			firstNameEN = (Element) root.getElementsByTagName("firstNameEN").item(0);
			lastNameEN = (Element) root.getElementsByTagName("lastNameEN").item(0);
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

	void fillDomens() {
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(domensPath);
			Element root = document.getDocumentElement();
			NodeList element = root.getElementsByTagName("domens");
			for (int i = 0; i < element.getLength(); i++) {
				// domens.add(element.item(i).getNodeValue());
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
			throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> Errors = new ArrayList<String>();
		DocumentBuilder documentBuilder;
		documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(fieldsPath);
		Element root = document.getDocumentElement();
		if (!Pattern.matches(firstName.getNodeValue(), account.firstName))
			Errors.add("Некорректное поле " + firstName.getAttribute("name"));
		if (!Pattern.matches(lastName.getNodeValue(), account.lastName))
			Errors.add("Некорректное поле " + lastName.getAttribute("name"));
		String defaultEmail = ((Element) emailProviders.getElementsByTagName("default").item(0)).getNodeValue();
		if (!Pattern.matches(defaultEmail, account.email))
			Errors.add("Некорректное поле Secondary Email");
		else {

			Element provider;
			String domen;
			int position = account.email.indexOf("@");
			if (position > 0 && position < account.email.length() - 2) {
				String accountDomen = account.email.substring(position + 1, account.email.length() - 1);
				boolean fit = false;
				int count = emailProviders.getElementsByTagName("email-provider").getLength();
				for (int i = 0; i < count; i++) {
					provider = (Element) emailProviders.getElementsByTagName("email-provider").item(i);
					domen = provider.getAttribute("domen");
					if (domen.equals(accountDomen)) {
						if (Pattern.matches(provider.getNodeValue(), account.email)) {
							fit = true;
							break;
						}
					}
				}
				if (!fit) 
					Errors.add("Email-provider не относиться к рекомендуемым или проверенным");
			}
		}
		{
			Element phone;
			boolean fit = false;
			int count = phones.getElementsByTagName("phone").getLength();
			for (int i = 0; i < count; i++) {
				phone = (Element) phones.getElementsByTagName("phone").item(i);
				if (Pattern.matches(phone.getNodeValue(), account.phone)) {
					fit = true;
					break;
				}
			}
			if(!fit)
				Errors.add("Некорректное поле "+phones.getAttribute("name"));
		}
		{
			Element department;
			boolean fit = false;
			int count = departments.getElementsByTagName("department").getLength();
			for (int i = 0; i < count; i++) {
				department = (Element) departments.getElementsByTagName("department").item(i);
				if (Pattern.matches(department.getNodeValue(), account.department)) {
					fit = true;
					break;
				}
			}
			if(!fit)
			{
				department=(Element) departments.getElementsByTagName("default").item(0);
				if (!Pattern.matches(department.getNodeValue(), account.department)) 
				{
					Errors.add("Некорректное поле "+departments.getAttribute("name"));
				}
				else
				{
					count=departments.getElementsByTagName("department").getLength();
					String list="";
							for(int i=0;i<count;i++)
							{
								department=(Element)departments.getElementsByTagName("department").item(i);
								list+=department.getAttribute("name")+" ,";
							}
					Errors.add("Поле "+departments.getAttribute("name")+" не относиться к "+list);
				}
			}
				Errors.add("Некорректное поле"+departments.getAttribute("name"));
		}		
		if (!Pattern.matches(firstNameEN.getNodeValue(), account.firstNameEN))
			Errors.add("Некорректное поле"+firstNameEN.getAttribute("name"));
		if (!Pattern.matches(lastNameEN.getNodeValue(), account.lastNameEN))
			Errors.add("Некорректное поле "+lastNameEN.getAttribute("name"));
		return Errors;
	}

}
