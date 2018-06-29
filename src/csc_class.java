import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class csc_class {

//	public static void main(String [] args)
//	{
//		String f1 = "C:\\Users\\Administrator.RUEPYZRTR9ZLHID\\Desktop\\csctext\\sim.txt";
//		
//		ArrayList<String> x = getString(f1);
//		String sim = "am->";
//		String start = "am";
//		ArrayList<String> simplecycle = new ArrayList<String>();
//		//get simplecycle from arraylist x
//		for(int i = 1; i< x.size();i++)
//		{
//			//memory in simplecycle if not find in list
//			if(x.get(i).equals(start))
//			{
//				if(!simplecycle.contains(sim))
//				{
//					simplecycle.add(sim);
//				}
//				sim = "am->";
//			}
//			else
//			{
//				sim+=x.get(i)+"->";				
//			}
//		}
//		System.out.println(simplecycle);		
//	}
	public static ArrayList<String> getString(String filename)
	{
		ArrayList<String> x = new ArrayList<String>();
		File file;
		file = new File(filename);
		
        //This is where a real application would open the file.
        ReadFile a = new ReadFile();        
		ArrayList<String> xx = a.ReadFile(file);
//		System.out.println(xx.size());
		for(int i = 0; i < xx.size();i++)
		{
			if(xx.get(i).contains("printf"))
			{
				String z = xx.get(i).substring(0, xx.get(i).indexOf(" "));
				String j = z.substring(z.indexOf("(")+1,z.indexOf("="));
//				System.out.println(j);
				x.add(j);
			}				
		}
		System.out.println(x);
		return x;
	}
	public static ArrayList<String> getsimplecycle(String txt)
	{
		String[] txt1 = txt.split("\n");
		System.out.println(Arrays.toString(txt1));
		ArrayList<String> x = new ArrayList<String>();
		for(int i = 0; i < txt1.length;i++)
		{
//			System.out.println(txt1.length);
			if(txt1[i].contains("printf"))
			{
				String z = txt1[i].substring(0, txt1[i].indexOf(" "));
				String j = z.substring(z.indexOf("(")+1,z.indexOf("="));
//				System.out.println(j);
				x.add(j);
			}				
		}
		System.out.println(x);		
		return x;
	}
	public static String checkfull(ArrayList<String> sim,String s1,String s2)
	{
		String s1p = s1 + "p";
		String s1m = s1 + "m";
		String s2p = s2 + "p";
		String s2m = s2 + "m";
		
		int [] x = new int[4];
		x[0] = sim.indexOf(s1p)+1;
		x[1] = sim.indexOf(s1m)+1;
		x[2] = sim.indexOf(s2p)+1;
		x[3] = sim.indexOf(s2m)+1;
		System.out.println(Arrays.toString(x));
		
		if(x[0]==0||x[1]==0||x[2]==0||x[3]==0){return "false";}
		else{
			if(((Math.abs(x[0]-x[1])>1) && (x[0] !=0) && (x[1]!=0)) || ((Math.abs(x[2]-x[3])>1) && (x[2] !=0) && (x[3]!=0)))
			{
				if((Math.abs(x[0]-x[1])==1) || (Math.abs(x[2]-x[3])==1)){return "false";}
				else{return "true";}
			}
			else
			{
				return "false";
			}
		}
	}
	public static boolean check_csc(ArrayList<String> sim,String [] notin_full,String [][] full)
	{
		boolean csc = false;		
//		String [] signalnames = new String[signal.length*2];
//		ArrayList<String> notin_full = new ArrayList<String>();
		 
//		for(int i = 0; i < signal.length;i++)
//		{
//			signalnames[i] = signal[i] + "p";
//			signalnames[i+1] = signal[i] + "m";
//		}
//		System.out.println(Arrays.toString(signalnames));
//		int [] x = new int[8];
//		for(int i = 0; i < signalnames.length; i++)
//		{
//			x[i] = sim.indexOf(signalnames[i]);
//			if(!(Arrays.asList(full).contains(signalnames[i])))
//			{
//				notin_full.add(signalnames[i]);
//			}
//		}
//		System.out.println(Arrays.toString(x));
//		System.out.println(notin_full);
		int [] rnotin_full = new int[notin_full.length];
		int [][] rfull = new int[full.length][4];
		for(int i = 0; i < full.length;i++)
		{
			rfull[i][0] = sim.indexOf(full[i][0])+1;
			rfull[i][1] = sim.indexOf(full[i][1])+1;
			rfull[i][2] = sim.indexOf(full[i][2])+1;
			rfull[i][3] = sim.indexOf(full[i][3])+1;
		}
		for(int i = 0; i < notin_full.length;i++)
		{
			rnotin_full[i] = sim.indexOf(notin_full[i])+1;
		}
		System.out.println(Arrays.toString(full));
		System.out.println(Arrays.toString(rfull));
		System.out.println(Arrays.toString(notin_full));
		System.out.println(Arrays.toString(rnotin_full));
		
		for(int j = 0;j<rfull.length;j++)
		{
			//find min and max of full lock pair(each pair)
			int min_full1 = Math.min(rfull[j][0],rfull[j][1]);
			int max_full1 = Math.max(rfull[j][0],rfull[j][1]);
			int min_full2 = Math.min(rfull[j][2],rfull[j][3]);
			int max_full2 = Math.max(rfull[j][2],rfull[j][3]);
			int count = 0;
			for(int i= 0;i<notin_full.length;i+=2)
			{
				int min_notin = Math.min(rnotin_full[i], rnotin_full[i+1]);
				int max_notin = Math.min(rnotin_full[i], rnotin_full[i+1]);
				if(Math.abs(rnotin_full[i]-rnotin_full[i+1])!=1)
				{
					if(min_notin>min_full1)
					{
						if((min_full2<max_notin)||(min_full2<max_notin)){count++; }
					}
					else if(min_notin>min_full2)
					{
						if((min_full1<max_notin)||(min_full1<max_notin)){count++; }
					}
					else
					{
						
					}
				}
			}
			if(count == notin_full.length/2){csc = true;break;}
		}
		return csc;
	}	
}
