import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSManager implements IManager{

	
	public Account CreateAccount(String file) throws IOException
	{
		File excel = new File(file);
	    FileInputStream fis;
	    Account account=new Account();
			fis = new FileInputStream(excel);

			HSSFWorkbook workbook = new HSSFWorkbook(fis);
		    HSSFSheet sheet = workbook.getSheet("Лист1");
		    HSSFRow row=sheet.getRow(0);
		    account.firstName=row.getCell(0).getStringCellValue();
		    account.lastName=row.getCell(1).getStringCellValue();
		    account.email=row.getCell(4).getStringCellValue();
		    account.phone=row.getCell(5).getStringCellValue();
		    account.department=row.getCell(6).getStringCellValue();
		    account.firstNameEN=row.getCell(7).getStringCellValue();
		    account.lastNameEN=row.getCell(8).getStringCellValue();

       return account;      
	}

}
