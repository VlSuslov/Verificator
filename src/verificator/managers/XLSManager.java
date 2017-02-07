package verificator.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * класс дл€ работы c XLS файлом
 */
public class XLSManager implements IManager {
	/**
	 * —оздает экземпл€р словар€ из соответсвующих значений аккаунтов по каждому
	 * полю из заданного файла
	 * 
	 * @param file
	 *            ѕуть к файлу в формате XLS содержащему требуемые данные
	 * @return —ловарь из соответсвующих значений аккаунтов по каждому полю
	 * @throws IOException-если
	 *             требуемое поле пусто
	 */
	public Dictionary<String, ArrayList<String>> getAccountsData(String file) throws IOException {
		File excel = new File(file);
		FileInputStream fis;
		Dictionary<String, ArrayList<String>> Accounts = new Hashtable<String, ArrayList<String>>();
		fis = new FileInputStream(excel);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		for (int i = 0; i < header.getLastCellNum(); i++) {
			String cell = header.getCell(i).getStringCellValue();
			FillDictionary(Accounts, sheet, cell, i);
		}
	return Accounts;

}

	private void FillDictionary(Dictionary<String, ArrayList<String>> dictionary, HSSFSheet sheet, String Name,
			int index) {
		ArrayList<String> cells = new ArrayList<String>();
		for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
			Cell c = sheet.getRow(j).getCell(index);
			if (c != null) {
				cells.add(c.getStringCellValue());
			}
		}
		dictionary.put(Name, cells);
	}
}
