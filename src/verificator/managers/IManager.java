package verificator.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * 
 * ��������� ������ ����������� �� ���������� ������ ��������� �� �����
 *
 */
public interface IManager {
	/**
	 * ������� ��������� ������� �� �������������� �������� ��������� �� ������� ���� �� ��������� �����
	 * 
	 * @param file
	 *            ���� � ����� � ������� XLS ����������� ��������� ������
	 * @return ������� �� �������������� �������� ��������� �� ������� ����
	 * @throws IOException-����
	 *             ��������� ���� �����
	 */
	public Dictionary<String, ArrayList<String>> getAccountsData(String file) throws IOException;
}
