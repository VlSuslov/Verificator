package verificator.verificators;
/**
 * 
 * Представляет ошибки найденные в файле спецификации specification.xml
 *
 */
public class SpecificationException extends Exception {

	/**
	 * 
	 * @param message Описание ошибки
	 */
	public SpecificationException(String message) {
		super(message);
	}
}
