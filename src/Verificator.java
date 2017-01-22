import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * 
 * ����� ��� ����������� ������� Account
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
	 * ������� ��������� ������� � ��������� �� ������������ ����
	 * specification.xml
	 * 
	 * @throws SpecificationException-����
	 *             ���� ��������� � ����������� � ��������� �����
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
			throw new SpecificationException("� ����� specification.xml ����������� ��� firstName");
		if (firstName.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� firstName �� �������� ��������");
		lastName = (Element) root.getElementsByTagName("lastName").item(0);
		if (lastName == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� lastName");
		if (lastName.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� lastName �� �������� ��������");
		bufElement = (Element) root.getElementsByTagName("email-providers").item(0);
		if (bufElement == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� email-providers");
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("� ����� specification.xml ��� email-providers �� ����� ��������� �����");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("� ����� specification.xml ��� " + bufElement.getNodeName()
							+ " c ������ " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " �� �������� ��������");
				else
					emailProviders.add((Element) nodes.item(i));
			}
		}
		bufElement = (Element) root.getElementsByTagName("phones").item(0);
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("� ����� specification.xml ��� phones �� ����� ��������� �����");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("� ����� specification.xml ��� " + bufElement.getNodeName()
							+ " c ������ " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " �� �������� ��������");
				else
					phones.add((Element) nodes.item(i));
			}
		}
		bufElement = (Element) root.getElementsByTagName("departments").item(0);
		nodes = bufElement.getChildNodes();
		if (nodes == null)
			throw new SpecificationException("� ����� specification.xml ��� departments �� ����� ��������� �����");
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.getTextContent().trim() == "")
					throw new SpecificationException("� ����� specification.xml ��� " + bufElement.getNodeName()
							+ " c ������ " + bufElement.getAttributes().getNamedItem("name").getNodeValue()
							+ " �� �������� ��������");
				else
					departments.add((Element) nodes.item(i));
			}
		}
		firstNameEN = root.getElementsByTagName("firstNameEN").item(0);
		if (firstNameEN == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� firstNameEN");
		if (firstNameEN.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� firstNameEN �� �������� ��������");
		lastNameEN = root.getElementsByTagName("lastNameEN").item(0);
		if (lastNameEN == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� lastNameEN");
		if (lastNameEN.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� lastNameEN �� �������� ��������");
	}

	/**
	 * ��������� ������ Account �� ������������ �� �������� ��������� � �����
	 * specification.xml
	 * 
	 * @param account ����������� ������ Account
	 * @return c����� �������� ������
	 */
	public ArrayList<String> Verificate(Account account) {
		ArrayList<String> Errors = new ArrayList<String>();
		String str = firstName.getTextContent().trim();
		if (!Pattern.matches(str, account.firstName))
			Errors.add("������������ ���� First Name");
		if (!Pattern.matches(lastName.getTextContent().trim(), account.lastName))
			Errors.add("������������ ���� Last Name");
		String defaultEmail = emailProviders.get(0).getTextContent().trim();
		if (!Pattern.matches(defaultEmail, account.email))
			Errors.add("������������ ���� Secondary Email");
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
					Errors.add("Email-provider �� ���������� � ������������� ��� �����������");
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
				Errors.add("������������ ���� Phones");
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
						Errors.add("���� Department �� ���������� � ����� ������������� �������� ���: " + list);
					}
				} else
					Errors.add("������������ ���� Department");
			}
		}
		if (!Pattern.matches(firstNameEN.getTextContent().trim(), account.firstNameEN))
			Errors.add("������������ ���� First Name EN");
		if (!Pattern.matches(lastNameEN.getTextContent().trim(), account.lastNameEN))
			Errors.add("������������ ���� Last Name EN");
		return Errors;
	}
}
