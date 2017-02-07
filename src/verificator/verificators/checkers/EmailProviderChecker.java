package verificator.verificators.checkers;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.w3c.dom.Element;
import verificator.similarity_checkers.*;
import verificator.verificators.SpecificationException;

/**
 * 
 * класс для проверки данных типа email адреса верификаторами аккаунтов данных
 * 
 */
public class EmailProviderChecker implements IChecker {
	private ISimilarityChecker checker;

	public EmailProviderChecker() {
		checker = new LevenshteinSimilarityChecker();
	}

	/**
	 * проверяет данные типа email адреса
	 * 
	  @param data
	 *            проверяемое поле
	 * @param position
	 *            номер проверяемого поля
	 * @param element
	 *            объект с данными для проверки
	 * @param fieldName
	 * 			 название проверяемого поля
	 * @return cписок найденых ошибок
	 * 
	 * @throws SpecificationException-Если
	 *             есть нарушение в требованиях к структуре файла
	 *             specification.xml
	 */
	public ArrayList<String[]> check(String data, int position, Element element, String fieldName)
			throws SpecificationException {
		String error = null;
		ArrayList<String[]> errors = new ArrayList<String[]>();
		String type = "error";
		boolean fit = true;
		String regexp = element.getTextContent().trim();

		if (!Pattern.matches(regexp, data))
			if (element.hasAttribute("name")) {
				String name = element.getAttribute("name");
				if (name.equals("default"))
					error = "Строка  " + (position + 2) + ": Некорректное поле " + fieldName;
				else {
					if (element.hasAttribute("domen")) {
						if (element.hasAttribute("similarity")) {
							String domen = element.getAttribute("domen");
							String rawCoefficient = element.getAttribute("similarity");
							int dog = data.indexOf("@");
							if (dog > 0 && dog < data.length() - 2) {
								String emailDomen = data.substring(dog + 1, data.length());
								int similarityCoefficient;
								if (rawCoefficient != null) {
									if (rawCoefficient.endsWith("%") && rawCoefficient.length() > 1) {
										similarityCoefficient = domen.length() * Integer
												.parseInt(rawCoefficient.substring(0, rawCoefficient.length() - 1))
												/ 100;
									} else
										similarityCoefficient = Integer.parseInt(rawCoefficient);
									int similarity = checker.compare(emailDomen, domen);
									if (similarity <= similarityCoefficient) {
										type = "warning";
										error = "Строка  " + (position + 2)
												+ ": Email-provider не относиться к рекомендуемым или проверенным"
												+ "Возможно вы имели в виду " + data.substring(0, dog) + "@" + domen
												+ " ?";
									}
								} else
									fit = false;
							} else
								fit = false;
						} else
							throw new SpecificationException("В файле specification.xml тэг "
									+ element.getAttribute("name") + " не содержит атрибут domen");
					} else
						fit = false;
				}
			} else
				throw new SpecificationException("В файле specification.xml присутствуют теги без атрибута name");
		if (fit = false)
			error = "Строка  " + (position + 2) + ": Некорректное поле " + fieldName;
		String[] output = { type, error };
		errors.add(output);
		return errors;
	}
}
