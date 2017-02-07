package verificator.verificators.checkers;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.w3c.dom.Element;

import verificator.verificators.SpecificationException;

/**
 * 
 * класс для проверки данных верификаторами аккаунтов данных методом регулярных выражений
 * 
 */
public class RegexpChecker implements IChecker {

	/**
	 * проверяет данные c помощью регулярных выражений
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
	 
	 */
	public ArrayList<String[]> check(String data, int position, Element element, String fieldName) {
		ArrayList<String[]> errors = new ArrayList<String[]>();
		String regexp = element.getTextContent().trim();
		if (!Pattern.matches(regexp, data)) {
			String[] error = { "error", "Строка  " + (position + 2) + ": Некорректное поле " + fieldName };
			errors.add(error);
		}
		return errors;
	}

}
