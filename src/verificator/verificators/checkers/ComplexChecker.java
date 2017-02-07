package verificator.verificators.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import verificator.verificators.SpecificationException;
/**
 * 
 * класс для проверки  данных комплексного типа верификаторами аккаунтов данных 
 * 
 */
public class ComplexChecker implements IChecker {
	
	private HashMap<String, IChecker> checkers;

	public ComplexChecker() {
		checkers = new HashMap<String, IChecker>();
		checkers.put("regexp", new RegexpChecker());
		checkers.put("email-provider", new EmailProviderChecker());
	}
	/**
	 * проверяет комплексные данные
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
		ArrayList<String[]> Errors = new ArrayList<String[]>();
		if (element.hasAttribute("mode")) {
			String mode = element.getAttribute("mode");
			ArrayList<String[]> innerErrors = new ArrayList<String[]>();
			ArrayList<String[]> innerWarnings = new ArrayList<String[]>();
			NodeList nodes = element.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element bufElement = (Element) nodes.item(i);
					if (bufElement.hasAttribute("name")) {
						if (bufElement.hasAttribute("type")) {
							String type = bufElement.getAttribute("type");
							IChecker checker = checkers.get(type);
							if (checker != null) {
								if (type.equals("regexp")) {
									if (bufElement.getAttribute("name").equals("default")) {
										ArrayList<String[]> out = checker.check(data, position, bufElement, fieldName);
										String[] result = out.get(0);
										if (result[1] != null)
											Errors.add(result);
										return Errors;
									} else
										innerErrors.addAll(checker.check(data, position, bufElement, fieldName));
								} else {
									if (type.equals("email-provider")) {
										if (bufElement.getAttribute("name").equals("default")) {
											ArrayList<String[]> out = checker.check(data, position, bufElement,
													fieldName);
											String[] result = out.get(0);
											if (result[0].equals("error") && result[1] != null) {
												Errors.add(result);
												return Errors;
											} else {
												if (result[0].equals("warning"))
													innerWarnings.add(result);
												else
													innerErrors.add(result);
											}
										} else {
											ArrayList<String[]> out = checker.check(data, position, bufElement,
													fieldName);
											String[] result = out.get(0);
											if (result[0].equals("warning"))
												innerWarnings.add(result);
											else
												innerErrors.add(result);
										}
									} else
										innerErrors.addAll(checker.check(data, position, bufElement, fieldName));
								}
							} else
								throw new SpecificationException("В файле specification.xml тэг "
										+ bufElement.getAttribute("name") + " содержит недопустимый тэг type");
						} else
							throw new SpecificationException("В файле specification.xml тэг "
									+ bufElement.getAttribute("name") + " не содержит атрибут type");
					} else
						throw new SpecificationException(
								"В файле specification.xml присутствуют теги без атрибута name");
				}
			}
			if (mode.equals("one")) {
				if (innerErrors.size() > 0) {
					boolean contain = false;
					for (String[] error : innerErrors) {
						if (error[1] == null) {
							contain = true;
							break;
						}
					}
					if (contain)
						return innerWarnings;
					else {
						String[] error = { "error",
								"Строка  " + (position + 2) + ": Некорректное поле " + element.getAttribute("name") };
						Errors.add(error);
						return Errors;
					}
				}
				else
					return innerWarnings;
			} else {
				innerErrors.addAll(innerWarnings);
				return innerErrors;
			}
		} else
			throw new SpecificationException(
					"В файле specification.xml тэг " + element.getAttribute("name") + " не содержит атрибут mode");
	}

}
