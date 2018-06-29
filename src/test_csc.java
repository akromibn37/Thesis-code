import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class test_csc extends JFrame {

	private JPanel contentPane;
	private JTextField txt_signal1;
	private JTextField txt_signal2;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					test_csc frame = new test_csc();
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
	public test_csc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 269, 410);
		contentPane.add(scrollPane);
		
		JTextArea textArea_sim = new JTextArea();
		scrollPane.setViewportView(textArea_sim);
		
		txt_signal1 = new JTextField();
		txt_signal1.setBounds(289, 52, 90, 20);
		contentPane.add(txt_signal1);
		txt_signal1.setColumns(10);
		
		txt_signal2 = new JTextField();
		txt_signal2.setColumns(10);
		txt_signal2.setBounds(289, 94, 90, 20);
		contentPane.add(txt_signal2);
		
		JLabel lblSignal = new JLabel("Signal1");
		lblSignal.setBounds(290, 37, 46, 14);
		contentPane.add(lblSignal);
		
		JLabel lblSignal_1 = new JLabel("Signal2");
		lblSignal_1.setBounds(290, 79, 46, 14);
		contentPane.add(lblSignal_1);
		
		JLabel lblSimulationText = new JLabel("Simulation text");
		lblSimulationText.setBounds(10, 11, 76, 14);
		contentPane.add(lblSimulationText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(389, 28, 231, 129);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_full = new JTextArea();
		scrollPane_1.setViewportView(textArea_full);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(390, 337, 230, 99);
		contentPane.add(scrollPane_2);
		
		JTextArea textArea_csc = new JTextArea();
		scrollPane_2.setViewportView(textArea_csc);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(390, 186, 230, 117);
		contentPane.add(scrollPane_3);
		
		JTextArea textArea_ltl = new JTextArea();
		scrollPane_3.setViewportView(textArea_ltl);
		
		JLabel lblFulllockResult = new JLabel("Full-lock result");
		lblFulllockResult.setBounds(389, 11, 76, 14);
		contentPane.add(lblFulllockResult);
		
		JLabel lblTransitivelockCheck = new JLabel("Transitive-lock check(LTL)");
		lblTransitivelockCheck.setBounds(389, 168, 136, 14);
		contentPane.add(lblTransitivelockCheck);
		
		JLabel lblCscResult = new JLabel("CSC result");
		lblCscResult.setBounds(389, 312, 64, 14);
		contentPane.add(lblCscResult);
		
		JButton btnFulllockCheck = new JButton("Full-lock");
		btnFulllockCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = textArea_sim.getText();
				ArrayList<String> sim = csc_class.getsimplecycle(txt);				
				String [] signal = {"x","y","z","a"};				
				ArrayList<String> pfull = new ArrayList<String>();
				for(int i = 0;i<signal.length;i++)
				{
					for(int j = i+1;j<signal.length;j++)
					{
						pfull.add(signal[i]+signal[j]);
					}
				}
				System.out.println(pfull);
				String [] boopfull = new String[pfull.size()];
				if(boopfull[0] == null){Arrays.fill(boopfull, "false");}
				for(int i =0; i<pfull.size();i++)
				{
					if(boopfull[i].equals("false"))
					{
						boopfull[i] = csc_class.checkfull(sim, Character.toString(pfull.get(i).toString().charAt(0)), Character.toString(pfull.get(i).toString().charAt(1)));
						System.out.println(boopfull[i]);
					}
				}
				String x = "";
				for(int i = 0;i<pfull.size();i++)
				{
					x+=pfull.get(i)+ " : " + boopfull[i]+"\n";
				}
				textArea_full.setText(x);
			}
		});
		btnFulllockCheck.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFulllockCheck.setBounds(290, 134, 89, 30);
		contentPane.add(btnFulllockCheck);
		
		JButton btnGetLtl = new JButton("Get LTL");
		btnGetLtl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String csc_ltl = "#define transitive ((xm==1 || xp==1) && (ym==1 || yp==1) && ((zp==1 && zm==1) || (am==1 && ap==1)))\n + ltl t1 {!<>transitive}";
				textArea_ltl.setText(csc_ltl);				
			}
		});
		btnGetLtl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGetLtl.setBounds(289, 177, 89, 30);
		contentPane.add(btnGetLtl);
		
		JButton btnCheckCsc = new JButton("Check CSC");
		btnCheckCsc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = textArea_sim.getText();
				ArrayList<String> sim = csc_class.getsimplecycle(txt);				
				String [] signal = {"x","y","z","a"};	
				String [] full = {"xp","xm","yp","ym"};
//				boolean csc = csc_class.check_csc(sim, signal,full);
//				System.out.println(csc);
			}
		});
		btnCheckCsc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCheckCsc.setBounds(289, 218, 89, 30);
		contentPane.add(btnCheckCsc);
	}
}
