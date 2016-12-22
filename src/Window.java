import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class Window extends JFrame {

	private static JButton select = new JButton("");
	private static JButton update = new JButton("Update");
	private static JButton storedProcedure = new JButton("Stored Procedure");
	private static JTextArea textArea = new JTextArea(20, 20);
	private JScrollPane textField = new JScrollPane(textArea);

	public Window() {
		super("My First Window");
		setBounds(800, 400, 200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		select.addActionListener(new SelectActionListener());
		update.addActionListener(new UpdateActionListener());
		storedProcedure.addActionListener(new ProcedureActionListener());
		buttonsPanel.add(select);
		buttonsPanel.add(update);
		buttonsPanel.add(storedProcedure);
		textField.setSize(100, 100);
		add(buttonsPanel, BorderLayout.SOUTH);
		add(textField, BorderLayout.NORTH);
	}

	public class SelectActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		}
	}

	public class UpdateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}

	public class ProcedureActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
}