package verificator.similarity_checkers;
/**
 * 
 * ��������� ������ ����������� �� ��������� ����� �� ��������
 *
 */
public interface ISimilarityChecker {

	/**
	 * ������� ���������� �������� ����� ���� �� �����
	 * @param source - ������ � ������� ���� ���������
	 * @param target - ������������ ������
	 * @return ���������� �������� �����
	 */
	public int compare(String source, String target);
}
