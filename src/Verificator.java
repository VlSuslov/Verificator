import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * 
 * класс для верификации объекта Account
 * 
 */
public class Verificator implements IVerificator {

	private Element firstName;
	private Element lastName;
	private ArrayList<Element> emailProviders;
	private ArrayList<Element> phones;
	private ArrayList<Element> departments;
	private Node firstNameEN;
	private Node lastNameEN;
	private String fieldsPath;

	/**
	 * 
	 * Создает экземпляр объекта и проверяет на корректность файл
	 * specification.xml
	 * 
	 * @throws SpecificationException-Если
	 *             есть нарушение в требованиях к структуре файла
	 *             specification.xml
	 * @throws ParserConfigurationException-if
	 *             a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 * @throws SAXException-If
	 *             any parse errors occur.
	 * @throws IOException-If
	 *             any IO errors occur.
	 */
	Verificator() throws ParserConfigurationException, SAXException, IOException, SpecificationException {
		emailProviders = new ArrayList<Element>();
		phones = new ArrayList<Element>();
		departments = new ArrayList<Element>();
		fieldsPath = "specification.xml";
		fillFilds();
	}

	private void fillFilds() throws ParserConfigurationException, SAXException, IOException, SpecificationException {
		DocumentBuilder documentBuilder;
		NodeList nodes;
		Element bufElement;
		documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(fieldsPath);
		Element root = document.getDocumentElement();
		firstName = (Element) root.getElementsByTagName("firstName").item(0);
		if (firstName == null)
			throw new SpecificationException("В файле specification.xml отсутствует тэг firstName");
		if (firstName.getTextContent().trim() == "")
			throw new SpecificationException("В файле specification.xml тэг firstName не содержит значение");
		lastName = (Element) root.getElementsByTagName("lastName").item(0);
		if (lastName == null)
			throw new SpecificationException("В файле specification.xml отсутствует тэг lastName");
		if (lastName.getTextContent().trim() == "")
			throw new SpecificationException("В файле specification.xml тэг lastName не содержит значение");
		bufElement = (Element) root.getElementsByTagName("email-providers").item(0);
		if (bufElement == null)
			throw new SpecificationException("В файле specification.xml отсутствует тэг email-providers");
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("В файле specification.xml тэг email-providers не имеет вложенных тэгов");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("В файле specification.xml тэг " + bufElement.getNodeName()
							+ " c именем " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " не содержит значение");
				else
					emailProviders.add((Element) nodes.item(i));
			}
		}
		bufElement = (Element) root.getElementsByTagName("phones").item(0);
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("В файле specification.xml тэг phones не имеет вложенных тэгов");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("В файле specification.xml тэг " + bufElement.getNodeName()
							+ " c именем " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " не содержит значение");
				else
					phones.add((Element) nodes.item(i));
			}
		}
		bufElement = (Element) root.getElementsByTagName("departments").item(0);
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("В файле specification.xml тэг departments не имеет вложенных тэгов");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("В файле specification.xml тэг " + bufElement.getNodeName()
							+ " c именем " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " не содержит значение");
				else
					departments.add((Element) nodes.item(i));
			}
		}
		firstNameEN = root.getElementsByTagName("firstNameEN").item(0);
		if (firstNameEN == null)
			throw new SpecificationException("В файле specification.xml отсутствует тэг firstNameEN");
		if (firstNameEN.getTextContent().trim() == "")
			throw new SpecificationException("В файле specification.xml тэг firstNameEN не содержит значение");
		lastNameEN = root.getElementsByTagName("lastNameEN").item(0);
		if (lastNameEN == null)
			throw new SpecificationException("В файле specification.xml отсутствует тэг lastNameEN");
		if (lastNameEN.getTextContent().trim() == "")
			throw new SpecificationException("В файле specification.xml тэг lastNameEN не содержит значение");
	}

	/**
	 * Проверяет объект Account на корректность по правилам описанным в файле
	 * specification.xml
	 * 
	 * @param account проверяемый объект Account
	 * @return cписок найденых ошибок
	 */
	public ArrayList<String> Verificate(Account account) {
		ArrayList<String> Errors = new ArrayList<String>();
		String str = firstName.getTextContent().trim();
		if (!Pattern.matches(str, account.firstName))
			Errors.add("Некорректное поле First Name");
		if (!Pattern.matches(lastName.getTextContent().trim(), account.lastName))
			Errors.add("Некорректное поле Last Name");
		String defaultEmail = emailProviders.get(0).getTextContent().trim();
		if (!Pattern.matches(defaultEmail, account.email))
			Errors.add("Некорректное поле Secondary Email");
		else {
			Element provider;
			String domen;
			int position = account.email.indexOf("@");
			if (position > 0 && position < account.email.length() - 2) {
				String accountDomen = account.email.substring(position + 1, account.email.length() - 1);
				boolean fit = false;
				for (int i = 1; i < emailProviders.size(); i++) {
					provider = emailProviders.get(i);
					domen = provider.getAttribute("domen");
					if (domen.equals(accountDomen)) {
						if (Pattern.matches(provider.getTextContent().trim(), account.email)) {
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
			boolean fit = false;
			int count = phones.size();
			for (int i = 0; i < count; i++) {
				if (Pattern.matches(phones.get(i).getTextContent().trim(), account.phone)) {
					fit = true;
					break;
				}
			}
			if (!fit)
				Errors.add("Некорректное поле Phones");
		}
		{
			boolean fit = false;
			int count = departments.size();
			for (int i = 1; i < count; i++) {
				if (Pattern.matches(departments.get(i).getTextContent().trim(), account.department)) {
					fit = true;
					break;
				}
			}
			if (!fit) {
				if (Pattern.matches(departments.get(0).getTextContent().trim(), account.department)) {
					count = departments.size();
					String list = "";
					if (count > 1) {
						list += departments.get(1).getAttribute("name");
						if (count > 2) {
							for (int i = 2; i < count; i++) {
								list += "," + departments.get(i).getAttribute("name");
							}
						}
						Errors.add("Поле Department не относиться к таким рекомендуемым шаблонам как: " + list);
					}
				} else
					Errors.add("Некорректное поле Department");
			}
		}
		if (!Pattern.matches(firstNameEN.getTextContent().trim(), account.firstNameEN))
			Errors.add("Некорректное поле First Name EN");
		if (!Pattern.matches(lastNameEN.getTextContent().trim(), account.lastNameEN))
			Errors.add("Некорректное поле Last Name EN");
		return Errors;
	}
}
