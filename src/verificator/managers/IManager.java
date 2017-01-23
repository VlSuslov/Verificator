package verificator.managers;

import java.io.IOException;
import verificator.Account;
/**
 * 
 * ��������� ������ ����������� �� �������� ������� �������� �� �����
 *
 */
public interface IManager {
	/**
	 * 
	 * @param file ���� � ����� � ������� XLS ����������� ��������� ������
	 * @return ����������� �� ����� ������ Account
	 * @throws IOException-���� ��������� ���� �����
	 */
	public Account CreateAccount(String file) throws IOException;
}
