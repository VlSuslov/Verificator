import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ����� ��� �������� ���������� Account �� �����
 */
public class XLSManager implements IManager {

	/**
	 * ������� ��������� Account �� ��������� �����
	 * @param 
	 * file ���� � ����� � ������� XLS ����������� ��������� ������
	 * @return ����������� �� ����� ������ Account
	 * @throws IOException-���� ��������� ���� �����
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
			throw new IOException("������ ���� First Name �� ������� �����");
		else
			account.firstName = buf;
		buf = row.getCell(1).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Last Name �� ������� �����");
		else
			account.lastName = buf;
		buf = row.getCell(2).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Email �� ������� �����");
		buf = row.getCell(3).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Phone �� ������� �����");
		else
			account.phone = row.getCell(3).getStringCellValue().trim();
		buf = row.getCell(4).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Password �� ������� �����");
		buf = row.getCell(5).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Secondary Email �� ������� �����");
		else
			account.email = row.getCell(5).getStringCellValue().trim();
		buf = row.getCell(6).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Department �� ������� �����");
		else
			account.department = row.getCell(6).getStringCellValue().trim();
		buf = row.getCell(7).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� First Name EN �� ������� �����");
		else
			account.firstNameEN = row.getCell(7).getStringCellValue().trim();
		buf = row.getCell(8).getStringCellValue().trim();
		if (buf == "")
			throw new IOException("������ ���� Last Name EN �� ������� �����");
		else
			account.lastNameEN = row.getCell(8).getStringCellValue().trim();
		return account;
	}

}
