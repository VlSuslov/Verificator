package verificator.managers;

import java.io.IOException;
import verificator.Account;
/**
 * 
 * Интерфейс модуля отвечающего за создание объекта аккаунта из файла
 *
 */
public interface IManager {
	/**
	 * 
	 * @param file Путь к файлу в формате XLS содержащему требуемые данные
	 * @return заполненный из файла объект Account
	 * @throws IOException-если требуемое поле пусто
	 */
	public Account CreateAccount(String file) throws IOException;
}
