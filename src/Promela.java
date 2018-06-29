import java.util.ArrayList;

public class Promela {

	String DefSignalnames;
	String DefTransition;
	String DefTransitionRuleIn;
	String DefTransitionRuleOut;
	String DefInitialMarking;
	String DefSTGPath;
	String All;
	STGOperation u = new STGOperation();
	public Promela(STGData [] Data, int maxin, int maxout,String [] Marking, int type)
	{
		GenerateSignalnames(Data,type);		
		GenerateTransitionRuleIn(maxin);
		GenerateTransitionRuleOut(maxout);
		GenerateMarking(Marking,type);
		GenerateSTGPath(Data,type);
		GetAllString();
	}
	private void GenerateSignalnames(STGData [] Data, int type)
	{
		DefSignalnames = "byte ";
		for(int i = 0; i < Data.length; i++)
		{
			int s = Data[i].outputnum;
			for(int j = 0; j < s; j++)
			{
				DefSignalnames += u.ConvertSignalname(Data[i].Signalname,Data[i].OutputSignalnames[j],type) + ", ";
			}
		}
		DefSignalnames = DefSignalnames.substring(0, DefSignalnames.length()-2);
		DefSignalnames += ";";
//		System.out.println(DefSignalnames);		
	}
	private void GenerateTransition(int k)
	{
		DefTransition = "byte ";
		for(int i = 1; i <= k; i++)
		{
			DefTransition += "t" + i + ",";
		}
		DefTransition = DefTransition.substring(0, DefTransition.length()-1);
		DefTransition += ";";
	}
	private void GenerateTransitionRuleIn(int MaxInputArc)
	{
		DefTransitionRuleIn = "";
		String [] parameters = new String[MaxInputArc];
		String [] transitionparameters = new String[MaxInputArc];
		String [] saverule = new String[MaxInputArc];
		for(int i = 1; i <= MaxInputArc; i++)
		{
			parameters[i-1] = "x" + i;
			transitionparameters[i-1] = "t" + i;
		}		
		for(int i = 1; i <= MaxInputArc; i++)
		{
			String i1 = "#define inp";			
			i1 += i + "(";
			String i2 = "";
			String i4 = "";
			String i6 = "";
			for(int j = 1; j <= i; j++)
			{
				i2 += parameters[j-1];
				i2 += ",";								
			}
			for(int j = 1; j <= i; j++)
			{
				i2 += transitionparameters[j-1];
				if(j!=i)
				{
					i2 += ",";
				}				
			}
			String i3 = ")	(";
			for(int k= 1; k <= i; k++)
			{
				i4 += parameters[k-1] + ">0";
				if(k!=i)
				{
					i4 += " && ";
				}				
			}
			String i5 = ") -> ";
			for(int l = 1; l <= i; l++)
			{
				i6 += parameters[l-1] + " = " + parameters[l-1] + "-1";
				i6+="; ";
			}
			for(int l = 1; l <= i; l++)
			{
				i6 += transitionparameters[l-1] + " = " + transitionparameters[l-1] + "+1";
				if(l!=i){i6+="; ";}
			}			
			saverule[i-1] = i1+i2+i3+i4+i5+i6;
		}
		for(int i = 0; i < MaxInputArc; i++)
		{
			DefTransitionRuleIn += saverule[i];
			if(i+1 != MaxInputArc)
			{DefTransitionRuleIn += "\n";}
		}		
//		System.out.println(DefTransitionRuleIn);		
	}
	private void GenerateTransitionRuleOut(int MaxOutputArc)
	{
		DefTransitionRuleOut = "";
		String [] parameters = new String[MaxOutputArc];
		String [] saverule = new String[MaxOutputArc];
		for(int i = 1; i <= MaxOutputArc; i++)
		{
			parameters[i-1] = "x" + i;
		}		
		for(int i = 1; i <= MaxOutputArc; i++)
		{
			String i1 = "#define outp";			
			i1 += i + "(";
			String i2 = "";
			String i4 = "";
			String i6 = "";
			for(int j = 1; j <= i; j++)
			{
				i2 += parameters[j-1];
				if(j!=i)
				{
					i2 += ",";
				}				
			}
			String i3 = ")	";			
			for(int l = 1; l <= i; l++)
			{
				i6 += parameters[l-1] + " = " + parameters[l-1] + "+1";
				if(l!=i){i6+="; ";}
			}
			
			saverule[i-1] = i1+i2+i3+i4+i6;
		}
		for(int i = 0; i < MaxOutputArc; i++)
		{
			DefTransitionRuleOut += saverule[i];
			if(i+1 != MaxOutputArc)
			{DefTransitionRuleOut += "\n";}
		}		
//		System.out.println(DefTransitionRuleOut);		
	}
	private void GenerateMarking(String [] Marking,int type)
	{
		DefInitialMarking = "init {\n";
		int m = Marking.length;
		for(int i = 0; i < m; i++)
		{
			String [] a = Marking[i].split(",");
			DefInitialMarking += u.ConvertSignalname(a[0],a[1],type) + "=1;";
		}
//		int snum = s.length;
//		for(int i = 0; i < m; i++)
//		{
//			String mm = Marking[i];
//			for(int j = 0; j < snum; j++)
//			{
//				String ss = s[j].Signalname;
//				if(mm.equals(ss))
//				{
//					int ss1 = s[j].outputnum;
//					for(int k = 0; k < ss1; k++)
//					{
//						DefInitialMarking += u.ConvertSignalname(mm,s[j].OutputSignalnames[k]) + "=1;";
//					}
//					break;
//				}					
//			}
//		}
//		System.out.println(DefInitialMarking);		
	}
	private void GenerateSTGPath(STGData [] s,int type)
	{
		DefSTGPath = "do\n";
		int k = 1;		
		for(int i = 0; i < s.length; i++)
		{
			String atomic = "\t::atomic{ inp";
			String linker = " -> ";
			String outer = "outp";
			String end = "}\n";
			int ilong = s[i].inputnum;
			int olong = s[i].outputnum;
			DefSTGPath += atomic + ilong + "(";
			String [] ip = s[i].InputSignalnames;
			String [] op = s[i].OutputSignalnames;
			String ipath = "";
			for(int j = 0; j < ilong; j++)
			{
				ipath += u.ConvertSignalname(ip[j], s[i].Signalname,type);
				ipath += ",";
			}
			for(int j = 0; j < ilong; j++)
			{
				ipath += "t" + k;
				if(j != ilong-1){ipath += ",";}
				k++;
			}					
			ipath += ")";
			DefSTGPath += ipath + linker + outer + olong + "(";
			String opath = "";
			for(int j = 0; j < olong; j++)
			{
				opath += u.ConvertSignalname(s[i].Signalname,op[j],type);
				if(j != olong-1){opath += ",";}
			}
			opath += ")";
			DefSTGPath += opath +end;			
		}		
		DefSTGPath += "od\n}";
		System.out.println(k);
		GenerateTransition((k-1));
//		System.out.println(DefSTGPath);
	}
	private void GetAllString()
	{
		All = DefSignalnames + "\n" + DefTransition + "\n" + DefTransitionRuleIn + "\n" + DefTransitionRuleOut + "\n" + DefInitialMarking + "\n" + DefSTGPath;
//		System.out.println("testAll : \n" + All);
	}
}
