package verificator.verificators;
import java.util.ArrayList;
import verificator.Account;

/**
  * 
  * ��������� ������ ����������� �� ����������� ��������
  *
  */
public interface IVerificator {
	/**
	 * 
	 * @param account ����������� ������ Account
	 * @return c����� �������� ������
	 */
	public ArrayList<String> Verificate(Account account);

}
