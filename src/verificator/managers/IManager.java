package verificator.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * 
 * »нтерфейс модул€ отвечающего за считывани€ данных аккаунтов из файла
 *
 */
public interface IManager {
	/**
	 * —оздает экземпл€р словар€ из соответсвующих значений аккаунтов по каждому полю из заданного файла
	 * 
	 * @param file
	 *            ѕуть к файлу в формате XLS содержащему требуемые данные
	 * @return —ловарь из соответсвующих значений аккаунтов по каждому полю
	 * @throws IOException-если
	 *             требуемое поле пусто
	 */
	public Dictionary<String, ArrayList<String>> getAccountsData(String file) throws IOException;
}
