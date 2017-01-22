import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * класс для создания экземпляра Account из файла
 */
public class XLSManager implements IManager {

	/**
	 * Создает экземпляр Account из заданного файла
	 * @param 
	 * file Путь к файлу в формате XLS содержащему требуемые данные
	 * @return заполненный из файла объект Account
	 * @throws IOException-если требуемое поле пусто
	 */
	public Account CreateAccount(String file) throws IOException {
		File excel = new File(file);
		FileInputStream fis;
		Account account = new Account();
		fis = new FileInputStream(excel);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		String buf = row.getCell(0).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле First Name во входном файле");
		else
			account.firstName = buf;
		buf = row.getCell(1).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Last Name во входном файле");
		else
			account.lastName = buf;
		buf = row.getCell(2).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Email во входном файле");
		buf = row.getCell(3).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Phone во входном файле");
		else
			account.phone = row.getCell(3).getStringCellValue().trim();
		buf = row.getCell(4).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Password во входном файле");
		buf = row.getCell(5).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Secondary Email во входном файле");
		else
			account.email = row.getCell(5).getStringCellValue().trim();
		buf = row.getCell(6).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Department во входном файле");
		else
			account.department = row.getCell(6).getStringCellValue().trim();
		buf = row.getCell(7).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле First Name EN во входном файле");
		else
			account.firstNameEN = row.getCell(7).getStringCellValue().trim();
		buf = row.getCell(8).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("Пустое поле Last Name EN во входном файле");
		else
			account.lastNameEN = row.getCell(8).getStringCellValue().trim();
		return account;
	}

}
