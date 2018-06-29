import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextArea;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class UI2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Runner r;
	private ArrayList<String> x = new ArrayList<String>();
	private JTextArea textArea_ModelData;
	private JTextArea textArea_list;
	private JTextArea textArea_Promela;
	private JTextArea textArea_LTL;
	private JButton btnClear;
	private JButton btnGenPromela;
	private JButton btnGenLTL;
	private JButton btnOpen;
	private String Promelacode;
	private String Promelacode2;
	private JButton btnGenPromela2;
	private JButton btnGenCLTL;
	private JButton btnCSCCheck;
	private JTextArea textArea_Promela2;
	private JTextArea textArea_Simtext;
	private JTextArea textArea_CLTL;
	private JTextArea textArea_Result;
	private JRadioButton rdbtnCSC;
	private JRadioButton rdbtnConsistency;
	private JButton btnCheckFullock;
	private JTextArea textArea_Full_lock;
	private JTextArea textArea_Transitive_LTL;
	private JTextArea textArea_Simtext_CSC;
	private JButton btnResult;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UI2 frame = new UI2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 739);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Promela1", null, panel, null);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(73, 12, 195, 20);
		panel.add(textField);
		
		JLabel label = new JLabel("File :");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(17, 15, 46, 14);
		panel.add(label);
		
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(UI2.this);
				 
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
	                btnGenPromela.setEnabled(true);
	                Promelacode2 = r.RunPromelaCode2();
	                btnGenPromela2.setEnabled(true);
	                
	            } else {
	                System.out.println("Open command cancelled by user." + "\n");
	            }
			}
		});
		btnOpen.setBounds(278, 11, 89, 23);
		panel.add(btnOpen);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 275, 341, 377);
		panel.add(scrollPane);
		
		textArea_list = new JTextArea();
		scrollPane.setViewportView(textArea_list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(476, 64, 516, 387);
		panel.add(scrollPane_1);
		
		textArea_Promela = new JTextArea();
		scrollPane_1.setViewportView(textArea_Promela);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea_Promela.setText("");
				textArea_list.setText("");
				textArea_LTL.setText("");
				textArea_ModelData.setText("");
				x.clear();
				btnGenLTL.setEnabled(false);
				btnGenPromela.setEnabled(false);
				btnOpen.setEnabled(true);
				
				textArea_Promela2.setText("");
				textArea_CLTL.setText("");
				textArea_Result.setText("");
				textArea_Simtext.setText("");
				btnGenCLTL.setEnabled(false);
				btnGenPromela2.setEnabled(false);
				btnCSCCheck.setEnabled(false);
				btnResult.setEnabled(false);
				textArea_Simtext_CSC.setText("");
				textArea_Full_lock.setText("");				
				textArea_Transitive_LTL.setText("");
			}
		});
		btnClear.setBounds(377, 239, 89, 23);
		panel.add(btnClear);
		
		btnGenPromela = new JButton("GenPromela");
		btnGenPromela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Promela.append(Promelacode);
				btnGenLTL.setEnabled(true);
			}
		});
		btnGenPromela.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon icon = new ImageIcon("C:\\Users\\Administrator.RUEPYZRTR9ZLHID\\Documents\\Eclipse_code\\Eclipse_Thesis\\Thesis_tool\\right-arrow-icon-1543.png"); 
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(65, 50, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		btnGenPromela.setIcon(icon);		
		btnGenPromela.setBackground(Color.LIGHT_GRAY);
		btnGenPromela.setEnabled(false);		
		btnGenPromela.setBounds(377, 273, 89, 59);
		panel.add(btnGenPromela);
		
		JRadioButton rdbtnPersistency = new JRadioButton("Persistency");
		rdbtnPersistency.setSelected(true);
		rdbtnPersistency.setBounds(377, 523, 89, 23);
		panel.add(rdbtnPersistency);
		
		JRadioButton rdbtnSafety = new JRadioButton("Safety");
		rdbtnSafety.setBounds(377, 501, 82, 23);
		panel.add(rdbtnSafety);
		
		JRadioButton rdbtnLiveness = new JRadioButton("Liveness");
		rdbtnLiveness.setBounds(377, 480, 89, 23);
		panel.add(rdbtnLiveness);
		
		btnGenLTL = new JButton("GenLTL");
		btnGenLTL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnGenLTL.setEnabled(false);
		btnGenLTL.setBounds(377, 572, 89, 50);
		panel.add(btnGenLTL);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(377, 11, 89, 23);
		panel.add(btnSave);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(476, 480, 516, 172);
		panel.add(scrollPane_2);
		
		textArea_LTL = new JTextArea();
		scrollPane_2.setViewportView(textArea_LTL);
		
		JLabel label_1 = new JLabel("Promela :");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(476, 43, 46, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("LTL :");
		label_2.setBounds(476, 462, 46, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("NetList :");
		label_3.setBounds(26, 255, 46, 14);
		panel.add(label_3);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(27, 64, 341, 180);
		panel.add(scrollPane_3);
		
		textArea_ModelData = new JTextArea();
		scrollPane_3.setViewportView(textArea_ModelData);
		
		JLabel label_4 = new JLabel("Model Data :");
		label_4.setBounds(27, 43, 65, 14);
		panel.add(label_4);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnPersistency);
		group.add(rdbtnSafety);
		group.add(rdbtnLiveness);		
		rdbtnPersistency.setSelected(true);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Promela2", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 28, 524, 319);
		panel_1.add(scrollPane_4);
		
		textArea_Promela2 = new JTextArea();
		scrollPane_4.setViewportView(textArea_Promela2);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(674, 28, 318, 189);
		panel_1.add(scrollPane_5);
		
		textArea_CLTL = new JTextArea();
		scrollPane_5.setViewportView(textArea_CLTL);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(674, 543, 318, 108);
		panel_1.add(scrollPane_6);
		
		textArea_Result = new JTextArea();
		scrollPane_6.setViewportView(textArea_Result);
		
		JLabel lblLtl = new JLabel("LTL");
		lblLtl.setBounds(674, 11, 76, 14);
		panel_1.add(lblLtl);
		
		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(674, 518, 64, 14);
		panel_1.add(lblResult);
		
		btnGenPromela2 = new JButton("GenPromela2");
		btnGenPromela2.setEnabled(false);
		btnGenPromela2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_Promela2.append(Promelacode2);
				btnGenCLTL.setEnabled(true);
			}
		});
		btnGenPromela2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGenPromela2.setBounds(544, 157, 120, 30);
		panel_1.add(btnGenPromela2);
		
		btnGenCLTL = new JButton("GenLTL");
		btnGenCLTL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num;
				if(rdbtnConsistency.isSelected())
					num = 1;
				else if(rdbtnCSC.isSelected())
					num = 2;				
				else num = 4;
				String ltl = r.RunConLTL(num);
				textArea_CLTL.append(ltl + "\n");
			}
		});
		btnGenCLTL.setEnabled(false);
		btnGenCLTL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGenCLTL.setBounds(544, 241, 120, 30);
		panel_1.add(btnGenCLTL);
		
		btnCSCCheck = new JButton("CSC Check");
		btnCSCCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCheckFullock.setEnabled(false);
				boolean csccheck = r.Simplecycle_CSCcheck();
				if(csccheck == true)
				{
					textArea_Result.setText("This STG has CSC property.");
				}
				else
				{
					String transitive_LTL = r.GenTransitiveLTL();
					textArea_Transitive_LTL.setText(transitive_LTL);
					textArea_Result.setText("Please use LTL in transitive LTL block and get simulation result here again.\nIf not have any text appear,The STG has non-CSC.");
				}
			}
		});
		btnCSCCheck.setEnabled(false);
		btnCSCCheck.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCSCCheck.setBounds(544, 326, 120, 30);
		panel_1.add(btnCSCCheck);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(10, 383, 251, 268);
		panel_1.add(scrollPane_8);
		
		textArea_Simtext = new JTextArea();
		textArea_Simtext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!(textArea_Simtext.getText().trim().equals(""))) btnCheckFullock.setEnabled(true);
				else btnCheckFullock.setEnabled(false);
			}
		});
		scrollPane_8.setViewportView(textArea_Simtext);
		
		JLabel label_5 = new JLabel("Promela2");
		label_5.setBounds(10, 11, 136, 14);
		panel_1.add(label_5);
		
		JLabel lblSimulationTextFor = new JLabel("Simulation text for Full-lock");
		lblSimulationTextFor.setBounds(10, 358, 136, 14);
		panel_1.add(lblSimulationTextFor);		
		
		rdbtnConsistency = new JRadioButton("Consistency");
		rdbtnConsistency.setBounds(559, 194, 109, 23);
		panel_1.add(rdbtnConsistency);
		
		rdbtnCSC = new JRadioButton("CSC");
		rdbtnCSC.setBounds(559, 216, 109, 23);
		panel_1.add(rdbtnCSC);
		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollPane_4, textArea_Promela2, scrollPane_5, textArea_CLTL, scrollPane_6, textArea_Result, lblLtl, lblResult, btnGenPromela2, btnGenCLTL, btnCSCCheck, scrollPane_8, textArea_Simtext, label_5, lblSimulationTextFor}));
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnConsistency);
		group1.add(rdbtnCSC);			
		rdbtnConsistency.setSelected(true);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(674, 249, 318, 124);
		panel_1.add(scrollPane_7);
		
		textArea_Full_lock = new JTextArea();		
		textArea_Full_lock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!(textArea_Full_lock.getText().trim().equals(""))) btnCSCCheck.setEnabled(true);
				else btnCSCCheck.setEnabled(false);
			}
		});
		scrollPane_7.setViewportView(textArea_Full_lock);
		
		JLabel lblFulllockResult = new JLabel("Full-lock Result");
		lblFulllockResult.setBounds(674, 224, 76, 14);
		panel_1.add(lblFulllockResult);
		
		btnCheckFullock = new JButton("Check Full-lock");
//		btnCheckFullock.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				btnCheckFullock.setEnabled(true);
//			}
//		});
		btnCheckFullock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = textArea_Simtext.getText();
				textArea_Full_lock.setText(r.checkfull(txt));
				textArea_Simtext.setText("");
			}
		});
		btnCheckFullock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCheckFullock.setEnabled(false);
		btnCheckFullock.setBounds(544, 282, 120, 30);
		panel_1.add(btnCheckFullock);
		
		JLabel lblTransitiveLtl = new JLabel("Transitive LTL");
		lblTransitiveLtl.setBounds(674, 383, 76, 14);
		panel_1.add(lblTransitiveLtl);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(674, 407, 318, 99);
		panel_1.add(scrollPane_9);
		
		textArea_Transitive_LTL = new JTextArea();
		scrollPane_9.setViewportView(textArea_Transitive_LTL);
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(283, 383, 251, 268);
		panel_1.add(scrollPane_10);
		
		textArea_Simtext_CSC = new JTextArea();
		textArea_Simtext_CSC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!(textArea_Simtext_CSC.getText().trim().equals(""))) btnResult.setEnabled(true);
				else btnResult.setEnabled(false);
			}
		});
		scrollPane_10.setViewportView(textArea_Simtext_CSC);
		
		JLabel lblSimulationTextFrom = new JLabel("Simulation Text from CSC");
		lblSimulationTextFrom.setBounds(283, 358, 136, 14);
		panel_1.add(lblSimulationTextFrom);
		
		btnResult = new JButton("Result");
		btnResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = textArea_Simtext_CSC.getText();
				boolean t = r.ChecktransitivefromCSCLTL(txt);
				if(t == true)
				{
					textArea_Result.setText("This STG has CSC property.");
					textArea_Simtext_CSC.setEnabled(false);
					textArea_Simtext_CSC.setText("");
					btnResult.setEnabled(false);					
				}
				else textArea_Result.setText("Is there anymore transitive LTL for simulation in SPIN?\nIf yes, please simulate next LTL and check again.\nIf not,The STG has non-CSC.");
			}
		});
		btnResult.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnResult.setEnabled(false);
		btnResult.setBounds(544, 367, 120, 30);
		panel_1.add(btnResult);
	}
}
