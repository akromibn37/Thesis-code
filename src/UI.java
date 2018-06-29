import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLayeredPane;

public class UI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private ArrayList<String> x = new ArrayList<String>();
	private JTextArea textArea_list;
	private JTextArea textArea_Promela;
	private JTextArea textArea_LTL;
	private String Promelacode;
	private JTextArea textArea_ModelData;
	Runner r;	
	private JButton btnGenltl;
	private JButton btnGenpromela;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					UI frame = new UI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(74, 12, 195, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFile = new JLabel("File :");
		lblFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblFile.setBounds(18, 15, 46, 14);
		contentPane.add(lblFile);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(UI.this);
				 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                //This is where a real application would open the file.
	                System.out.println("Path: " + file);
	                System.out.println("Opening: " + file.getName() + "." + "\n"); 
	                textField.setText(file.getPath());	
	                ReadFile a = new ReadFile();
	                x = a.ReadFile(file);
	                for(int i = 0; i < x.size(); i++)
	                {
	                	textArea_list.append(x.get(i).toString() + "\n");
	                }
	                btnOpen.setEnabled(false);
	                r = new Runner(x);
					r.Operating();
					Promelacode = r.RunPromelaCode();
					textArea_ModelData.append("Model : " + r.init.Modelname + "\n");
					textArea_ModelData.append("InputSignal : " + Arrays.toString(r.init.Input) + "\n");
					textArea_ModelData.append("OutputSignal : " + Arrays.toString(r.init.Output) + "\n");
					textArea_ModelData.append("Marking : " + Arrays.toString(r.init.Marking) + "\n");
					textArea_ModelData.append("TotalArc : " + r.amountpath + "\n");
					textArea_ModelData.append("STGType : " + (r.STGtype==1 ? "Singlecycle" : "Multicycle") + "\n");
					textArea_ModelData.append("Maxrepeat : " + r.Maxrepeat + "\n");
					textArea_ModelData.append("ConcurrentSignalnames : " + Arrays.toString(r.Conpath) + "\n");	
	                btnGenpromela.setEnabled(true);
	                
	            } else {
	                System.out.println("Open command cancelled by user." + "\n");
	            }
	            
			}
		});
		btnOpen.setBounds(279, 11, 89, 23);
		contentPane.add(btnOpen);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 275, 341, 402);
		contentPane.add(scrollPane);
		
		textArea_list = new JTextArea();
		textArea_list.setEditable(false);
		scrollPane.setViewportView(textArea_list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(477, 64, 516, 387);
		contentPane.add(scrollPane_1);
		
		textArea_Promela = new JTextArea();
		scrollPane_1.setViewportView(textArea_Promela);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea_Promela.setText("");
				textArea_list.setText("");
				textArea_LTL.setText("");
				textArea_ModelData.setText("");
				x.clear();
				btnGenltl.setEnabled(false);
				btnGenpromela.setEnabled(false);
				btnOpen.setEnabled(true);
			}
		});
		btnClear.setBounds(378, 239, 89, 23);
		contentPane.add(btnClear);
		
		btnGenpromela = new JButton("GenPromela");
		btnGenpromela.setEnabled(false);
		btnGenpromela.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon icon = new ImageIcon("C:\\Users\\Administrator.RUEPYZRTR9ZLHID\\Documents\\Eclipse_code\\Eclipse_Thesis\\Thesis_tool\\right-arrow-icon-1543.png"); 
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(65, 50, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		btnGenpromela.setIcon(icon);		
		btnGenpromela.setBackground(Color.LIGHT_GRAY);
		btnGenpromela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {							
				textArea_Promela.append(Promelacode);
				btnGenltl.setEnabled(true);
			}
		});
		btnGenpromela.setBounds(378, 273, 89, 59);
		contentPane.add(btnGenpromela);
		
		JRadioButton rdbtnPersistency = new JRadioButton("Persistency");
		rdbtnPersistency.setBounds(378, 523, 89, 23);
		contentPane.add(rdbtnPersistency);
		
		JRadioButton rdbtnSafety = new JRadioButton("Safety");
		rdbtnSafety.setBounds(378, 501, 82, 23);
		contentPane.add(rdbtnSafety);
		
		JRadioButton rdbtnLiveness = new JRadioButton("Liveness");
		rdbtnLiveness.setBounds(378, 480, 89, 23);
		contentPane.add(rdbtnLiveness);
		
		JRadioButton rdbtnConsistency = new JRadioButton("Consistency");
		rdbtnConsistency.setBounds(378, 542, 89, 23);
		contentPane.add(rdbtnConsistency);
		
		btnGenltl = new JButton("GenLTL");
		btnGenltl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int num;
				if(rdbtnPersistency.isSelected())
					num = 1;
				else if(rdbtnSafety.isSelected())
					num = 2;
				else if(rdbtnLiveness.isSelected())
					num = 3;
				else num = 4;
				String ltl = r.RunLTL(num);
				textArea_LTL.append(ltl + "\n");				
			}
		});
		btnGenltl.setEnabled(false);
		btnGenltl.setBounds(378, 572, 89, 50);
		contentPane.add(btnGenltl);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnPersistency);
		group.add(rdbtnSafety);
		group.add(rdbtnLiveness);
		group.add(rdbtnConsistency);
		rdbtnPersistency.setSelected(true);
				
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(378, 11, 89, 23);
		contentPane.add(btnSave);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(477, 480, 516, 197);
		contentPane.add(scrollPane_2);
		
		textArea_LTL = new JTextArea();
		scrollPane_2.setViewportView(textArea_LTL);
		
		JLabel lblPromela = new JLabel("Promela :");
		lblPromela.setHorizontalAlignment(SwingConstants.CENTER);
		lblPromela.setBounds(477, 43, 46, 14);
		contentPane.add(lblPromela);
		
		JLabel lblLtl = new JLabel("LTL :");
		lblLtl.setBounds(477, 462, 46, 14);
		contentPane.add(lblLtl);
		
		JLabel lblNetlist = new JLabel("NetList :");
		lblNetlist.setBounds(27, 255, 46, 14);
		contentPane.add(lblNetlist);	
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(28, 64, 341, 180);
		contentPane.add(scrollPane_3);
		
		textArea_ModelData = new JTextArea();
		scrollPane_3.setViewportView(textArea_ModelData);
		
		JLabel lblModelData = new JLabel("Model Data :");
		lblModelData.setBounds(28, 43, 65, 14);
		contentPane.add(lblModelData);
		
	}
}
