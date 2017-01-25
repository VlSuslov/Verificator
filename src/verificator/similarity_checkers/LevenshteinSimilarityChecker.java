package verificator.similarity_checkers;
/**
 * 
 *Класс сравниващий строки на схожесть путем нахождения растояния Левенштейна
 *
 */
public class LevenshteinSimilarityChecker implements ISimilarityChecker {

	/**
	 * Находит растояние Левенштейна для двух строк
	 * @param source - Строка с которой идет сравнение
	 * @param target - Сравниваемая строка
	 * @return показатель различия строк
	 */
	public int compare(String source, String target) {
		int m = source.length(), n = target.length();
		int[] D1;
		int[] D2 = new int[n + 1];

		for (int i = 0; i <= n; i++)
			D2[i] = i;

		for (int i = 1; i <= m; i++) {
			D1 = D2;
			D2 = new int[n + 1];
			for (int j = 0; j <= n; j++) {
				if (j == 0)
					D2[j] = i;
				else {
					int cost = (source.charAt(i - 1) != target.charAt(j - 1)) ? 1 : 0;
					if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
						D2[j] = D2[j - 1] + 1;
					else if (D1[j] < D1[j - 1] + cost)
						D2[j] = D1[j] + 1;
					else
						D2[j] = D1[j - 1] + cost;
				}
			}
		}
		return D2[n];
	}
}
