package verificator.verificators.checkers;

import java.util.ArrayList;
import org.w3c.dom.Element;
import verificator.verificators.SpecificationException;

/**
 * 
 * Интерфейс для проверки данных верификаторами аккаунтов конкретного типа
 * 
 */
public interface IChecker {
	
	/**
	 * проверяет данные rонкретного типа
	 * 
	 * @param data
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
	public ArrayList<String[]> check(String data, int position, Element element, String fieldName) throws SpecificationException;
	
	}
