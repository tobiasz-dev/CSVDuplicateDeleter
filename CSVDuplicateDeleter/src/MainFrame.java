import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class MainFrame extends JFrame {
	private JFrame frame; 
	private JFileChooser chooser;
	private JPanel contentPane;
	private Controller controller;
	private JLabel lblUsunitoDuplikatw;
	private JComboBox<String> comboBox;
	private int counter;

	/**
	 * Create the frame.
	 */
	public MainFrame(Controller aController) {
		frame = this;
		controller = aController;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 288, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("CSV files (csv)", "csv");
		chooser.setFileFilter(imageFilter);
		
		JButton btnWybierzPlik = new JButton("Wybierz plik CSV");
		btnWybierzPlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
					
					File file = chooser.getSelectedFile();
					
					try {
						controller.fileToCSV(file);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, "B³¹d odczytu pliku: " + e1.toString());
					}
					
					String[] columns = controller.getColumns();
					
					if(columns != null){
						for(String itemName : columns){
							comboBox.addItem(itemName);
						}
						comboBox.repaint();
					}
				}
				

			}
		});
		GridBagConstraints gbc_btnWybierzPlik = new GridBagConstraints();
		gbc_btnWybierzPlik.insets = new Insets(0, 0, 5, 5);
		gbc_btnWybierzPlik.gridx = 1;
		gbc_btnWybierzPlik.gridy = 1;
		contentPane.add(btnWybierzPlik, gbc_btnWybierzPlik);
		
		JLabel lblNewLabel_1 = new JLabel("Kolumna z kt\u00F3rej usuni\u0119te zostan\u0105 duplikaty");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 4;
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setFilter(e.getActionCommand());
			}
			
		});
		contentPane.add(comboBox, gbc_comboBox);
		
		JButton btnUsuDuplikaty = new JButton("Usu\u0144 duplikaty");
		GridBagConstraints gbc_btnUsuDuplikaty = new GridBagConstraints();
		gbc_btnUsuDuplikaty.insets = new Insets(0, 0, 5, 5);
		gbc_btnUsuDuplikaty.gridx = 1;
		gbc_btnUsuDuplikaty.gridy = 5;
		btnUsuDuplikaty.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setFilter((String) comboBox.getSelectedItem());
				controller.deleteDuplicates();
				updateCounter();
				controller.writeToFile();
			}
			
		});
		contentPane.add(btnUsuDuplikaty, gbc_btnUsuDuplikaty);
		
		lblUsunitoDuplikatw = new JLabel("Usuni\u0119to duplikat\u00F3w:" + counter);
		GridBagConstraints gbc_lblUsunitoDuplikatw = new GridBagConstraints();
		gbc_lblUsunitoDuplikatw.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsunitoDuplikatw.gridx = 1;
		gbc_lblUsunitoDuplikatw.gridy = 7;
		contentPane.add(lblUsunitoDuplikatw, gbc_lblUsunitoDuplikatw);
	}
	
	private void updateCounter(){
		lblUsunitoDuplikatw.setText("Usuni\u0119to duplikat\u00F3w:" + this.controller.getduplicateNumber());
		
	}
}
