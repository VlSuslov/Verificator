package verificator.verificators;
import java.util.ArrayList;

/**
  * 
  * ��������� ������ ����������� �� ����������� ��������
  *
  */
public interface IVerificator {
	/**
	 * 
	 * @param column ����������� ���� ���������
	 * @param data �������� ������������ ����
	 * @return c����� �������� ������
	 */
	public ArrayList<String> Verificate(String column,ArrayList<String> data);
}
