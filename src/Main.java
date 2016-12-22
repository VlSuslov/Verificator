import java.io.*;
import java.util.ArrayList;

import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;


public class Main {
	
	static IVerificator verificator;
	static IManager manager;

	public static void main (String[] args) throws IOException, ParserConfigurationException, SAXException 
	{
		verificator=new Verificator();
		manager=new XLSManager();
		Window window=new Window();
		window.setVisible(true);
		window.pack();
		Account account=manager.CreateAccount("D://Downloads//1 (2).xls");
        ArrayList<String> Errors=verificator.Verificate(account);
        for(String str:Errors)
        {
        	System.out.println(str);
        }
	    }
	

}
