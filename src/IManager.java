import java.io.IOException;
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
