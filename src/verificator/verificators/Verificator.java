package verificator.verificators;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import verificator.similarity_checkers.*;
import verificator.verificators.checkers.*;

/**
 * 
 * класс для верификации данных аккаунтов
 * 
 */
public class Verificator implements IVerificator {

	private String fieldsPath;
	private ISimilarityChecker checker;
	Dictionary<String, Element> rules;
	HashMap<String, IChecker> checkers;

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
	public Verificator() throws ParserConfigurationException, SAXException, IOException, SpecificationException {
		checker = new LevenshteinSimilarityChecker();
		fieldsPath = "specification.xml";
		checkers = new HashMap<String, IChecker>();
		checkers.put("regexp", new RegexpChecker());
		checkers.put("complex", new ComplexChecker());
		CreateRules();
	}

	private void CreateRules() throws ParserConfigurationException, SAXException, IOException, SpecificationException {
		DocumentBuilder documentBuilder;
		NodeList nodes;
		Element bufElement;
		documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(fieldsPath);
		Element root = document.getDocumentElement();
		rules = new Hashtable<String, Element>();
		nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				bufElement = (Element) nodes.item(i);
				if (bufElement.hasAttribute("name")) {
					if (bufElement.hasAttribute("type")) {
						rules.put(bufElement.getAttribute("name"), bufElement);
					} else
						throw new SpecificationException("В файле specification.xml тэг "
								+ bufElement.getAttribute("name") + " не содержит атрибут type");
				} else
					throw new SpecificationException("В файле specification.xml присутствуют теги без атрибута name");
			}
		}
	}

	/**
	 * Проверяет набор данных на корректность по правилам описанным в файле
	 * specification.xml
	 * 
	 * @param column
	 *            проверяемые поля аккаунтов
	 * @param data
	 *            название проверяемого поля
	 * @return cписок найденых ошибок
	 * 
	 * @throws SpecificationException-Если
	 *             есть нарушение в требованиях к структуре файла
	 *             specification.xml
	 */

	public ArrayList<String[]> Verificate(String column, ArrayList<String> data) throws SpecificationException {
		ArrayList<String[]> Errors = new ArrayList<String[]>();
		String selected = null;
		Enumeration<String> keys = rules.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equals(column)) {
				selected = key;
				break;
			}
		}
		if (selected == null) {
			String[] error={"error","Отсутствуют правила для заданного поля"};
			Errors.add(error);
			return Errors;
		} else {
			Element element = rules.get(selected);
			String type = element.getAttribute("type");
				IChecker checker = checkers.get(type);
				if (checker!=null) {
				for (int i = 0; i < data.size(); i++) {
					Errors.addAll(checker.check(data.get(i), i, element, element.getAttribute("name")));
				}
			} else {
				String[] error={"error","Неразрешенный тип корневого элемента " + element.getAttribute("name")};
				Errors.add(error);
			}
		}
		return Errors;
	}
	
}
