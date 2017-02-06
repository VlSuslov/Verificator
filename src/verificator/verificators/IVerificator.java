package verificator.verificators;
import java.util.ArrayList;

/**
  * 
  * Интерфейс модуля отвечающего за верификацию аккаунта
  *
  */
public interface IVerificator {
	/**
	 * 
	 * @param column проверяемые поля аккаунтов
	 * @param data название проверяемого поля
	 * @return cписок найденых ошибок
	 */
	public ArrayList<String> Verificate(String column,ArrayList<String> data);
}
