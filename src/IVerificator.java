import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IVerificator {
	public ArrayList<String> Verificate(Account account) throws ParserConfigurationException, SAXException, IOException;

}
