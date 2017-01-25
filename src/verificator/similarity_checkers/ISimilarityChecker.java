package verificator.similarity_checkers;
/**
 * 
 * »нтерфейс модул€ отвечающего за сравнение строк на схожесть
 *
 */
public interface ISimilarityChecker {

	/**
	 * Ќаходит показатель различи€ строк друг от друга
	 * @param source - —трока с которой идет сравнение
	 * @param target - —равниваема€ строка
	 * @return показатель различи€ строк
	 */
	public int compare(String source, String target);
}
