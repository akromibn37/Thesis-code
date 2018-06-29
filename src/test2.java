import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;

public class test2 {
	
//	public static void main(String [] args)
//	{	
//		boolean full_lock,semilock;
//		String xy,xz,xa,yz,za; 
//		String fs1,fs2;		
//		fs1 = "C:\\Users\\Administrator.RUEPYZRTR9ZLHID\\Desktop\\csctext\\testtexts1_1.txt";
//		fs2 = "C:\\Users\\Administrator.RUEPYZRTR9ZLHID\\Desktop\\csctext\\testtexts2_1.txt";		
//		
//		ArrayList<String> x = getString(fs1);
//		full_lock = checkfulllock("x","y",x);
//		xy = full_lock? "full-lock":"not full-lock";
//		full_lock = checkfulllock("x","z",x);
//		xz = full_lock? "full-lock":"not full-lock";	 
//		full_lock = checkfulllock("x","a",x);
//		xa = full_lock? "full-lock":"not full-lock";	 
//		full_lock = checkfulllock("y","z",x);
//		yz = full_lock? "full-lock":"not full-lock";	 
//		full_lock = checkfulllock("z","a",x);
//		za = full_lock? "full-lock":"not full-lock";
//		
//		x = getString(fs2);
//		full_lock = checkfulllock("x","y",x);
//		xy = xy.equals("full-lock")? xy:(full_lock?"full-lock":"not full-lock");
//		full_lock = checkfulllock("x","z",x);
//		xz = xy.equals("full-lock")? xz:(full_lock?"full-lock":"not full-lock");	 
//		full_lock = checkfulllock("x","a",x);
//		xa = xy.equals("full-lock")? xa:(full_lock?"full-lock":"not full-lock");	 
//		full_lock = checkfulllock("y","z",x);
//		yz = xy.equals("full-lock")? yz:(full_lock?"full-lock":"not full-lock");	 
//		full_lock = checkfulllock("z","a",x);
//		za = xy.equals("full-lock")? za:(full_lock?"full-lock":"not full-lock");
//		
//		x = getString(fs1);
//		semilock = checksemilock("x","y",x);
//		if(!semilock){x = getString(fs2); semilock = checksemilock("x","y",x);}
//		xy += semilock? ",semi-lock":",not semilock";
//		x = getString(fs1);
//		semilock = checksemilock("x","z",x);
//		if(!semilock){x = getString(fs2); semilock = checksemilock("x","z",x);}
//		xz += semilock? ",semi-lock":",not semilock";
//		x = getString(fs1);
//		semilock = checksemilock("x","a",x);
//		if(!semilock){x = getString(fs2); semilock = checksemilock("x","a",x);}
//		xa += semilock? ",semi-lock":",not semilock";
//		x = getString(fs1);
//		semilock = checksemilock("y","z",x);
//		if(!semilock){x = getString(fs2); semilock = checksemilock("y","z",x);}
//		yz += semilock? ",semi-lock":",not semilock";
//		x = getString(fs1);
//		semilock = checksemilock("z","a",x);
//		if(!semilock){x = getString(fs2); semilock = checksemilock("z","a",x);}
//		za += semilock? ",semi-lock":",not semilock";
//				
//		System.out.println("xy : " + xy + "\nxz : " + xz + "\nxa : " + xa + "\nyz : " + yz + "\nza : " + za);
//	}
	public static ArrayList<String> getString(String filename)
	{
		ArrayList<String> x = new ArrayList<String>();
		File file;
		file = new File(filename);
		
        //This is where a real application would open the file.
        ReadFile a = new ReadFile();        
		ArrayList<String> xx = a.ReadFile(file);
		System.out.println(xx.size());
		for(int i = 0; i < xx.size();i++)
		{
			if(xx.get(i).contains("printf"))
			{
				String z = xx.get(i).substring(0, xx.get(i).indexOf(" "));
				String j = z.substring(z.indexOf("(")+1,z.length()-3);
//				System.out.println(j);
				x.add(j);
			}				
		}
		System.out.println(x);
		return x;
	}
	public static boolean checkfulllock(String signal1,String signal2,ArrayList<String> signalvalue)
	{
		String s1p = signal1 + 'p';
		String s1m = signal1 + 'm';
		String s2p = signal2 + 'p';
		String s2m = signal2 + 'm';
		int rs1p = 0;
		int rs1m = 0;
		int rs2p = 0;
		int rs2m = 0;
		
		int rank = 1;
		
		System.out.println(s1p + " , " + s1m + " , " + s2p + " , " + s2m);
		for(int i = 0; i < signalvalue.size(); i++)
		{
			if(signalvalue.get(i).equals(s1p)){rs1p = rank; rank++; }
			else if(signalvalue.get(i).equals(s1m)){rs1m = rank; rank++; }
			else if(signalvalue.get(i).equals(s2p)){rs2p = rank; rank++; }
			else if(signalvalue.get(i).equals(s2m)){rs2m = rank; rank++; }
		}
		System.out.println(rs1p + " , " + rs1m + " , " + rs2p + " , " + rs2m);
		if((Math.abs(rs1p-rs1m)==2) && (rs1p !=0) && (rs1m!=0) && (rs2p!=0) && (rs2m!=0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean checksemilock(String signal1,String signal2,ArrayList<String> signalvalue)
	{
		String s1p = signal1 + 'p';
		String s1m = signal1 + 'm';
		String s2p = signal2 + 'p';
		String s2m = signal2 + 'm';
		int rs1p = 0;
		int rs1m = 0;
		int rs2p = 0;
		int rs2m = 0;
		
		int rank = 1;
		
		System.out.println(s1p + " , " + s1m + " , " + s2p + " , " + s2m);
		for(int i = 0; i < signalvalue.size(); i++)
		{
			if(signalvalue.get(i).equals(s1p)){rs1p = rank; rank++; }
			else if(signalvalue.get(i).equals(s1m)){rs1m = rank; rank++; }
			else if(signalvalue.get(i).equals(s2p)){rs2p = rank; rank++; }
			else if(signalvalue.get(i).equals(s2m)){rs2m = rank; rank++; }
			else{rank++;}
		}
		System.out.println(rs1p + " , " + rs1m + " , " + rs2p + " , " + rs2m);
		if(((Math.abs(rs1p-rs1m)>1) && (rs1p !=0) && (rs1m!=0)) || ((Math.abs(rs2p-rs2m)>1) && (rs2p !=0) && (rs2m!=0)))
		{
			if((Math.abs(rs1p-rs1m)==1) || (Math.abs(rs2p-rs2m)==1)){return false;}
			else{return true;}
		}
		else
		{
			return false;
		}
	}
	public static boolean checkassolock(String signal1,String full1,String full2,ArrayList<String> signalvalue)
	{
		String s1p = signal1 + 'p';
		String s1m = signal1 + 'm';
		String f1p = full1 + 'p';
		String f1m = full1 + 'm';
		String f2p = full2 + 'p';
		String f2m = full2 + 'm';
		int rs1p = 0;
		int rs1m = 0;
		int rf1p = 0;
		int rf1m = 0;
		int rf2p = 0;
		int rf2m = 0;
		
		int rank = 1;
		
		System.out.println(s1p + " , " + s1m + " , " + f1p + " , " + f1m + " , " + f2p + " , " + f2m);
		for(int i = 0; i < signalvalue.size(); i++)
		{
			if(signalvalue.get(i).equals(s1p)){rs1p = rank; rank++; }
			else if(signalvalue.get(i).equals(s1m)){rs1m = rank; rank++; }
			else if(signalvalue.get(i).equals(f1p)){rf1p = rank; rank++; }
			else if(signalvalue.get(i).equals(f1m)){rf1m = rank; rank++; }
			else if(signalvalue.get(i).equals(f2p)){rf2p = rank; rank++; }
			else if(signalvalue.get(i).equals(f2m)){rf2m = rank; rank++; }
			else{rank++;}
		}
		System.out.println(rs1p + " , " + rs1m + " , " + rf1p + " , " + rf1m + " , " + rf2p + " , " + rf2m);
		if(Math.abs(rs1p-rs1m)>1 && rs1p!=0 && rs1m !=0)
		{
			int max = Math.max(rs1p, rs1m);
			int min = Math.min(rs1m, rs1p);
			return true;
		}
		else
		{
			return false;
		}
	}
}
