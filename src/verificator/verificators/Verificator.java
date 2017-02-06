package verificator.verificators;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import verificator.similarity_checkers.*;

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
	private Element firstNameEN;
	private Element lastNameEN;
	private String fieldsPath;
	private ISimilarityChecker checker;

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
	public Verificator() throws ParserConfigurationException, SAXException, IOException, SpecificationException {
		emailProviders = new ArrayList<Element>();
		phones = new ArrayList<Element>();
		departments = new ArrayList<Element>();
		checker = new LevenshteinSimilarityChecker();
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
				else {
					if (!bufElement.getAttribute("name").equals("default")) {
						String rawCoefficient = bufElement.getAttribute("similarity");
						if (rawCoefficient != null) {
							if (!Pattern.matches("^[0-9]{1,2}[0-9%]?$", rawCoefficient))
								throw new SpecificationException(
										"� ����� specification.xml ��� " + bufElement.getNodeName() + " c ������ "
												+ bufElement.getAttributes().getNamedItem("name").getNodeValue()
												+ " ����� ������������ ������� similarity");
						}
					}
					emailProviders.add((Element) nodes.item(i));
				}
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
					phones.add(bufElement );

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
		firstNameEN = (Element) root.getElementsByTagName("firstNameEN").item(0);
		if (firstNameEN == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� firstNameEN");
		if (firstNameEN.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� firstNameEN �� �������� ��������");
		lastNameEN = (Element) root.getElementsByTagName("lastNameEN").item(0);
		if (lastNameEN == null)
			throw new SpecificationException("� ����� specification.xml ����������� ��� lastNameEN");
		if (lastNameEN.getTextContent().trim() == "")
			throw new SpecificationException("� ����� specification.xml ��� lastNameEN �� �������� ��������");
	}

	/**
	 * ��������� ������ Account �� ������������ �� �������� ��������� � �����
	 * specification.xml
	 * 
	 * @param column ����������� ���� ���������
	 * @param data �������� ������������ ����
	 * @return c����� �������� ������
	 */
	public ArrayList<String> Verificate(String column, ArrayList<String> data) {
		ArrayList<String> Errors = new ArrayList<String>();
		if (column.equals("First Name"))
			Errors = VerificateFirstNames(data);
		if (column.equals("Last Name"))
			Errors = VerificateLastNames(data);
		if (column.equals("Secondary Email"))
			Errors = VerificateEmails(data);
		if (column.equals("Mobile Phone"))
			Errors = VerificatePhones(data);
		if (column.equals("Department"))
			Errors = VerificateDepartments(data);
		if (column.equals("First Name EN"))
			Errors = VerificateFirstNamesEN(data);
		if (column.equals("Last Name EN"))
			Errors = VerificateLastNamesEN(data);
		return Errors;
	}
	

	private ArrayList<String> VerificateFirstNames(ArrayList<String> firstNames) {
		ArrayList<String> Errors = new ArrayList<String>();
		String str = firstName.getTextContent().trim();
		for (int i = 0; i < firstNames.size(); i++) {
			if (!Pattern.matches(str, firstNames.get(i)))
				Errors.add("������  " + (i + 1) + ": ������������ ���� First Name");
		}
		return Errors;
	}

	private ArrayList<String> VerificateLastNames(ArrayList<String> lastNames) {
		ArrayList<String> Errors = new ArrayList<String>();
		String str = lastName.getTextContent().trim();
		for (int i = 0; i < lastNames.size(); i++) {
			if (!Pattern.matches(str, lastNames.get(i)))
				Errors.add("������  " + (i + 1) + ": ������������ ���� Last Name");
		}
		return Errors;
	}

	private ArrayList<String> VerificateEmails(ArrayList<String> emails) {
		ArrayList<String> Errors = new ArrayList<String>();
		String defaultEmail = emailProviders.get(0).getTextContent().trim();
		for (int i = 0; i < emails.size(); i++) {
			String email = emails.get(i);
			if (!Pattern.matches(defaultEmail, email))
				Errors.add("������  " + (i + 1) + ": ������������ ���� Secondary Email");
			else {
				Element provider;
				String domen;
				String similar = null;
				int position = email.indexOf("@");
				if (position > 0 && position < email.length() - 2) {
					String accountDomen = email.substring(position + 1, email.length());
					boolean fit = false;
					for (int j = 1; j < emailProviders.size(); j++) {
						provider = emailProviders.get(j);
						domen = provider.getAttribute("domen");
						if (domen.equals(accountDomen)) {
							if (Pattern.matches(provider.getTextContent().trim(), email)) {
								fit = true;
								break;
							}
						} else {
							String rawCoefficient = provider.getAttribute("similarity");
							int similarityCoefficient;
							if (rawCoefficient != null) {
								if (rawCoefficient.endsWith("%") && rawCoefficient.length() > 1) {
									similarityCoefficient = domen.length()
											* Integer.parseInt(rawCoefficient.substring(0, rawCoefficient.length() - 1))
											/ 100;
								} else
									similarityCoefficient = Integer.parseInt(rawCoefficient);
								int similarity = checker.compare(domen, accountDomen);
								if (similarity <= similarityCoefficient)
									if (similar != null)
										similar += "," + email.substring(0, position) + domen;
									else
										similar = email.substring(0, position + 1) + domen;
							}
						}
					}
						if (!fit) {
							Errors.add("������  " + (i + 1)
									+ ": Email-provider �� ���������� � ������������� ��� �����������");
							if (similar != null)
								Errors.add("�������� �� ����� � ���� " + similar + " ?");
						
					}
				}
			}
		}
		return Errors;
	}

	private ArrayList<String> VerificatePhones(ArrayList<String> mobilePhones) {
		ArrayList<String> Errors = new ArrayList<String>();
		int count = phones.size();
		for (int i = 0; i < mobilePhones.size(); i++) {
			boolean fit = false;
			for (int j = 0; j < count; j++) {
				if (Pattern.matches(phones.get(j).getTextContent().trim(), mobilePhones.get(i))) {
					fit = true;
					break;
				}
			}
			if (!fit)
				Errors.add("������  " + (i + 1) + ": ������������ ���� Phones");
		}
		return Errors;
	}

	private ArrayList<String> VerificateDepartments(ArrayList<String> deps) {
		ArrayList<String> Errors = new ArrayList<String>();
		int count = departments.size();
		for (int i = 0; i < deps.size(); i++) {
			boolean fit = false;
			for (int j = 1; j < count; j++) {
				if (Pattern.matches(departments.get(j).getTextContent().trim(), deps.get(i))) {
					fit = true;
					break;
				}
			}
			if (!fit) {
				if (Pattern.matches(departments.get(0).getTextContent().trim(), deps.get(i))) {
					count = departments.size();
					String list = "";
					if (count > 1) {
						list += departments.get(1).getAttribute("name");
						if (count > 2) {
							for (int k = 2; k < count; i++) {
								list += "," + departments.get(k).getAttribute("name");
							}
						}
						Errors.add("������  " + (i + 1)
								+ ": ���� Department �� ���������� � ����� ������������� �������� ���: " + list);
					}
				} else
					Errors.add("������  " + (i + 1) + ": ������������ ���� Department");
			}
		}
		return Errors;
	}

	private ArrayList<String> VerificateFirstNamesEN(ArrayList<String> firstNamesEN) {
		ArrayList<String> Errors = new ArrayList<String>();
		for (int i = 0; i < firstNamesEN.size(); i++) {
			if (!Pattern.matches(firstNameEN.getTextContent().trim(), firstNamesEN.get(i)))
				Errors.add("������  " + (i + 1) + ": ������������ ���� First Name EN");
		}
		return Errors;
	}

	private ArrayList<String> VerificateLastNamesEN(ArrayList<String> lastNamesEN) {
		ArrayList<String> Errors = new ArrayList<String>();
		for (int i = 0; i < lastNamesEN.size(); i++) {
			if (!Pattern.matches(lastNameEN.getTextContent().trim(), lastNamesEN.get(i)))
				Errors.add("������  " + (i + 1) + ": ������������ ���� First Name EN");
		}
		return Errors;
	}

	private ArrayList<String> getDomens() {
		Element provider;
		ArrayList<String> domens = new ArrayList<String>();
		for (int i = 1; i < emailProviders.size(); i++) {
			provider = emailProviders.get(i);
			domens.add(provider.getAttribute("domen"));
		}
		return domens;
	}
}
