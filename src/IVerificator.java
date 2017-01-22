import java.util.ArrayList;

/**
  * 
  * Интерфейс модуля отвечающего за верификацию аккаунта
  *
  */
public interface IVerificator {
	/**
	 * 
	 * @param account проверяемый объект Account
	 * @return cписок найденых ошибок
	 */
	public ArrayList<String> Verificate(Account account);

}
