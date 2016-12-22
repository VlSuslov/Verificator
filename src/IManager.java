import java.io.FileNotFoundException;
import java.io.IOException;

public interface IManager {
	public Account CreateAccount(String file) throws FileNotFoundException, IOException;
}
