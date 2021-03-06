
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import verificator.managers.*;
import verificator.verificators.*;

public class Window extends JFrame {

	private static JButton selectFile = new JButton("SelectFile");
	private static JButton verificate = new JButton("Verificate");
	private static JTextArea textArea = new JTextArea(20, 20);
	private static JComboBox comboBox;
	/**
	 * 
	 */
	private static JLabel pathLabel = new JLabel("");
	private String filePath;
	private IVerificator verificator;
	private IManager manager;
	private JScrollPane textField = new JScrollPane(textArea);

	// GUI проекта
	public Window() {
		super("Verificator");
		setBounds(800, 400, 700, 420);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel buttonsPanel = new JPanel();
		selectFile.addActionListener(new SelectFileActionListener());
		verificate.addActionListener(new VerificateActionListener());
		buttonsPanel.add(selectFile);
		buttonsPanel.add(verificate);
		pathLabel.setBounds(10, 0, 500, 20);
		add(pathLabel);
		textField.setBounds(10, 30, 650, 300);
		add(textField);
		buttonsPanel.setBounds(230, 330, 200, 50);
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
				manager = new XLSManager();
				Dictionary<String, ArrayList<String>> AccountsData;
				try {
					AccountsData = manager.getAccountsData(filePath);
					Enumeration<String> keys = AccountsData.keys();
			        ArrayList<String> arraykeys = Collections.list(keys);
			        String[] stockArr = new String[arraykeys.size()];
			        stockArr = arraykeys.toArray(stockArr);
					comboBox=new JComboBox(stockArr);
					comboBox.setBounds(460, 10, 200, 20);
					add(comboBox);
				} catch (IOException e1) {
					textArea.setText(e1.getMessage());
				}				
			}
		}
	}

	public class VerificateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (filePath != null) {
				try {
					verificator = new Verificator();
					manager = new XLSManager();
					Dictionary<String, ArrayList<String>> AccountsData = manager.getAccountsData(filePath);
					String selected = (String) comboBox.getSelectedItem();	
					Enumeration<String> keys = AccountsData.keys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();
						if (key.equals(selected)) {
							selected = key;
							break;
						}
					}
					ArrayList<String> column = AccountsData.get(selected);
					if (column != null) {
						ArrayList<String[]> Errors = new ArrayList<String[]>();
						Errors = verificator.Verificate(selected, column);
						if (Errors.size() != 0) {
							textArea.setText("");
							for (String[] str : Errors)
								try {									
									textArea.getDocument().insertString(textArea.getDocument().getLength(), str[1] + "\n",
											null);
								} catch (BadLocationException e1) {
									textArea.setText(e1.getMessage());
								}
						} else {
							textArea.setText("");
							textArea.setText("Ошибки не обнаруженны");
						}
					} else {
						textArea.setText("В файле отсутствует слобец " + selected);
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
