import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class CSCoperation {
	//1.GenPair -> get pair save in ArrayList
	//2.GenFullockCSC -> LTL for full-lock
	//3.GenFullockResult(after get sim text) -> see result by fullock_pair_result
	STGOperation o = new STGOperation();	
	
	public CSCoperation()
	{
		
	}
	//For full-lock LTL
	public String GenFulllockCSC(String [] Input,String [] Output,String [] Marking,int type)
	{
		ArrayList<String> pair = PairSignal(Input,Output);
		String fulllock = "";
		String x ="";
		String y = "";
		for(int i =0;i<pair.size();i++)
		{
			String [] z = pair.get(i).split(" ");
			x = z[0];y = z[1];
//			System.out.println("Signal: "+x + " " + y);
			fulllock += GetfulllockLTL(x,y,Marking,type,(i+1));
		}
		return fulllock;
	}
	public ArrayList<String> PairSignal(String [] Input,String [] Output)
	{	
		ArrayList<String> pair = new ArrayList<String>();
		String [] both = Stream.concat(Arrays.stream(Input), Arrays.stream(Output)).toArray(String[]::new);
//		System.out.println(Arrays.toString(both));
		for(int i =0;i<both.length-1;i++)
		{
			String x = "";
			for(int j = i+1;j<both.length;j++)
			{
				x=both[i]+" "+both[j];
				pair.add(x);
			}
		}
//		System.out.println(pair);
		return pair;
	}
	//For full-lock LTL
	private String GetfulllockLTL(String x,String y,String [] Marking,int type,int rank)
	{
		String m = "";
		for(int k = 0; k < Marking.length; k++)
		{
			String [] a = Marking[k].split(",");
			m += o.ConvertSignalname(a[0],a[1],type) + "==4 || ";			
		}
		m = m.substring(0, m.length()-4);
		String full = "";
		String a = "";
		String b = "";
		a = "#define csc_full_"+x+y+" ("+x+"p"+"==1 && "+x+"m"+"==1 && "+y+"p"+"==1 && "+y+"m"+"==1 && (" + m + "))";
		b = "\nltl c"+ rank +" {!<> csc_full_"+ x + y + "}\n";
		full = a+b;
		return full;
	}
	//For full-lock result
	public ArrayList<String> GetFullockResult(String txt,ArrayList<String> pair,ArrayList<String> fulllock_pair_result)
	{
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> sim = Getsimplecycle(txt);
		if(fulllock_pair_result.isEmpty())
		{
			for(int i = 0;i<pair.size();i++)
			{
				fulllock_pair_result.add("false");
			}
		}
		if(result.isEmpty())
		{
			for(int i = 0;i<pair.size();i++)
			{
				result.add("false");
			}
		}
//		System.out.println(fulllock_pair_result);
		for(int i = 0; i < pair.size();i++)
		{
			String [] z = pair.get(i).split(" ");
			String x = z[0];String y = z[1];
//			System.out.println(z[0]+ ","+ z[1]);
			if(fulllock_pair_result.get(i).toString().equals("false"))result.set(i, checkfull(sim,x,y));
			else result.set(i, "true");
//			System.out.println("Signal: "+x + "," + y + " result : " + result.get(i));
		}		
//		System.out.println(result);
		return result;
	}
	public String PrintFulllock_result(ArrayList<String> pair,ArrayList<String> fulllock_pair_result)
	{
		String result = "";
		for(int i =0;i<pair.size();i++)
		{
			String [] z = pair.get(i).split(" "); 
			result += z[0] + "," + z[1] + " : " + fulllock_pair_result.get(i) + "\n";
		}
		return result;
	}
	public String Savesimplecycle(String txt)
	{
		String simplecycle = "";
		ArrayList<String> sim = Getsimplecycle(txt);
		for(int i = 0; i < sim.size(); i++)
		{
			simplecycle += sim.get(i) + "->";
		}
		simplecycle = simplecycle.substring(0, simplecycle.length()-2);
		System.out.println(simplecycle);
		return simplecycle;
	}
	private ArrayList<String> Getsimplecycle(String txt)
	{
		ArrayList<String> sim = new ArrayList<String>();
		String prockey = "proc ";
		String error = "assertion violated";
		if(txt.contains(error))
		{
			String[] txt1 = txt.split("\n");
//			System.out.println(Arrays.toString(txt1));
			int num = 0;
			String p = "";
			for(int i = txt1.length-1; i>= 0; i--)
			{
				if(txt1[i].contains(error))
				{
					num=i;
					p = txt1[i-3];
					break;
				}
			}
			num = p.indexOf("proc");
			prockey += " " + p.charAt(num+6);
//			System.out.println("p : " + p + ";num : " + num + ";prockey :" +prockey);			
			
			for(int i = 0; i < txt1.length;i++)
			{
//				System.out.println(txt1.length);
				if(txt1[i].contains("printf") && txt1[i].contains(prockey))
				{
					String z = txt1[i].substring(0, txt1[i].indexOf(" "));
					String j = z.substring(z.indexOf("(")+1,z.indexOf("="));
//					System.out.println(txt1[i]);
//					System.out.println("z : " + z + ",j : " + j);
					sim.add(j);
				}				
			}					
		}
//		System.out.println(sim);	
		return sim;
	}
	private String checkfull(ArrayList<String> sim,String s1,String s2)
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
//		System.out.println(Arrays.toString(x));
		
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
	//check fullock, is it pair make CSC or not?
	public boolean checkfulllockpair(ArrayList<String> Pair,ArrayList<String> fulllock_pair_result,String [] Output)
	{
		if(fulllock_pair_result.contains("false"))
		{
//			System.out.println("Full-lock pair contain false.");
			if(fulllock_pair_result.contains("true"))
			{
				ArrayList<String> x = new ArrayList<String>();
				for(int i =0; i<Output.length;i++)
				{
					for(int j = i+1;j<Output.length;j++)
					{
						x.add(Output[i]+" " + Output[j]);
					}
				}
//				System.out.println(x);
				for(int i = 0; i < Pair.size();i++)
				{
					for(int j = 0; j < x.size();j++)
					{
						if(Pair.get(i).equals(x.get(j)))
						{
//							System.out.println(Pair.get(i)+ "," + x.get(j));
							if(fulllock_pair_result.get(i).equals("false"))
							{
								return false;
							}
						}
					}
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	//check transitive in simplecycle 
	public boolean checksimplecycle(ArrayList<String> sim,ArrayList<String> pair,ArrayList<String> fulllock_pair_result,String [] Output)
	{
		for(int i = 0;i< pair.size();i++)
		{
			if(fulllock_pair_result.get(i).equals("true"))
			{
				//get signal in full-lock and non-input signal in Arraylist 
				ArrayList<String> x = new ArrayList<String>();
				x.addAll(Arrays.asList(Output));
				String [] y = pair.get(i).split(" ");
				if(x.contains(y[0]))x.remove(y[0]);x.add(y[0]);
				if(x.contains(y[1]))x.remove(y[1]);x.add(y[1]);
				System.out.println("Output : "+ Arrays.toString(Output) + ", Array x : " + x);
				
				//check if have 1 output and find in fulllock
				if(x.size()==2) return true;
				String [] z = new String[x.size()*2];
//				System.out.println(z.length);
				
				//get all signal with p,m
				for(int j = 0;j<x.size();j++)
				{
//					System.out.println(j);
					z[j*2] = x.get(j)+ "p";
					z[(j*2)+1] = x.get(j) + "m";
				}
				System.out.println(Arrays.toString(z));
				
				//Array for get simplecycle
				for(int k = 0;k < sim.size();k++)
				{
					//array to mem rank of signal
					ArrayList<Integer> r = new ArrayList<Integer>();
					String [] s = sim.get(k).split("->");
					ArrayList<String> si = new ArrayList<String>();
					si.addAll(Arrays.asList(s));
					
					//check rank of all signal in transitive 
					for(int l = 0;l<z.length;l++)
					{
						r.add(si.indexOf(z[l])+1);
					}
					System.out.println(si);
					System.out.println(r);
					boolean check = checktransitiverank(r);
					if(check == true)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	//check rank from arraylist and answer is it CSC?
	private boolean checktransitiverank(ArrayList<Integer> r)
	{	
		int ap = r.get(r.size()-4);
		int am = r.get(r.size()-3);
		int bp = r.get(r.size()-2);
		int bm = r.get(r.size()-1);		
		System.out.println("f1p : " + ap + ",f1m : " + am + ",f2p : " + bp + ",f2m : " + bm);
		for(int i = 0; i < r.size()-4; i+=2)
		{
			int min = Math.min(r.get(i),r.get(i+1));
			int max = Math.max(r.get(i),r.get(i+1));
			if(Math.abs(max-min)==1) return false;
			if(min==0 || max==0) return false;
			System.out.println("i : " + i + ",min : " + min + ",max : " + max);
			if((ap<max && ap!= 0)&&(am<max && am!=0)&&(bp<max && bp!=0)&&(bm<max && bm!=0))
			{
				if(((ap > min && ap!=0) || (am > min && am!=0)) && ((bp > min && bp!=0) || (bm > min && bm!=0))) 
				{
					if((ap < min && ap!=0) || (am < min && am!=0) || (bp < min && bp!=0) || (bm < min && bm!=0)) continue; 
					else return false;
				}
				else if((ap > min && ap!=0) || (am > min && am!=0))  
				{
					if((bp < min && bp!=0) || (bm < min && bm!=0)) continue; 
					else return false;
				}
				else if((bp > min && bp!=0) || (bm > min && bm!=0))  
				{
					if((ap < min && am!=0) || (am < min && am!=0)) continue; 
					else return false;
				}
			}
			else if((ap>max && ap!=0)||(am>max && am!=0)||(bp>max && bp!=0)||(bm>max && bm!=0))
			{
				if(((ap > max && ap!=0) || (am > max && am!=0)) && ((bp > max && bp!=0) || (bm > max && bm!=0))) 
				{
//					if(ap > min || am > min || bp > min || bm > min) continue; 
					if(((ap > min && ap!=0) || (am > min && am!=0)) && ((bp > min && bp!=0) || (bm > min && bm!=0))) continue;
					else return false;
				}
				else if((ap > max && ap!=0) || (am > max && am!=0))  
				{
					if((bp > min && bp!=0) || (bm > min && bm!=0)) continue; 
					else return false;
				}
				else if((bp > max && bp!=0) || (bm > max && bm!=0))  
				{
					if((ap > min && ap!=0) || (am > min && am!=0)) continue; 
					else return false;
				}
			}
		}
		return true;
	}
	//Generate transitive LTL
	public String GenTransitiveLTL(ArrayList<String> pair,ArrayList<String> fulllock_pair_result,String [] Output,String [] Marking,int type)
	{
		String m = "";
		for(int k = 0; k < Marking.length; k++)
		{
			String [] a = Marking[k].split(",");
			m += o.ConvertSignalname(a[0],a[1],type) + "==4 || ";			
		}
		m = m.substring(0, m.length()-4);
		int a = 1;
		String LTL = "";
		for(int i = 0; i < fulllock_pair_result.size();i++)
		{
			if(fulllock_pair_result.get(i)=="true")
			{
				String [] x = pair.get(i).split(" ");
				LTL += "#define transitive"+a +" ((" + x[0] + "p==1 || " + x[0] + "m==1) && ("+ x[1] + "p==1 || " + x[1] + "m==1) && ("  ;
				for(int j=0;j<Output.length;j++)
				{
					if(!(Arrays.asList(x).contains(Output[j])))
					{
						LTL += Output[j] + "p==1 && " +Output[j] + "m==1 && ";
					}
				}
				LTL = LTL.substring(0, LTL.length()-4) + ") && (" + m + "))\n";	
				a++;
			}					
		}
		String t = "";
		for(int k = 1; k < a; k++)
		{
			t+= "ltl t"+k+ " {!<>transitive"+k+"}\n";
		}
		LTL += t;
		return LTL;		
	}
}
