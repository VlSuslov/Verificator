import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import verificator.Account;
import verificator.managers.*;
import verificator.verificators.*;


public class Window extends JFrame {

	private static JButton selectFile = new JButton("SelectFile");
	private static JButton verificate = new JButton("Verificate");
	private static JTextArea textArea = new JTextArea(20, 20);
	/**
	 * 
	 */
	private static JLabel pathLabel = new JLabel("");
	private String filePath;
	private IVerificator verificator;
	private IManager manager;
	private JScrollPane textField = new JScrollPane(textArea);

	//GUI проекта
	public Window() {
		super("Verificator");
		setBounds(800, 400, 600, 420);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel buttonsPanel = new JPanel();
		selectFile.addActionListener(new SelectFileActionListener());
		verificate.addActionListener(new VerificateActionListener());
		buttonsPanel.add(selectFile);
		buttonsPanel.add(verificate);
		pathLabel.setBounds(10, 0, 500, 20);
		add(pathLabel);
		textField.setBounds(10, 30, 550, 300);
		add(textField);
		buttonsPanel.setBounds(150, 330, 200, 50);
		add(buttonsPanel);
	}

	public class SelectFileActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser openFile = new JFileChooser();
			int returnVal = openFile.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = openFile.getSelectedFile();
				filePath = file.getPath();
				pathLabel.setText(filePath);
			}
		}
	}

	public class VerificateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (filePath != null) {
				try {
					verificator = new Verificator();
					manager = new XLSManager();
					Account account;
					account = manager.CreateAccount(filePath);
					ArrayList<String> Errors = verificator.Verificate(account);
					for (String str : Errors)
						try {
							textArea.getDocument().insertString(textArea.getDocument().getLength(), str + "\n", null);
						} catch (BadLocationException e1) {
							textArea.setText(e1.getMessage());
						}
				} catch (ParserConfigurationException e2) {
					textArea.setText(e2.getMessage());
				} catch (SAXException e2) {
					textArea.setText(e2.getMessage());
				} catch (IOException e2) {
					textArea.setText(e2.getMessage());
				} catch (SpecificationException e2) {
					textArea.setText(e2.getMessage());
				}
			}
		}
	}

}