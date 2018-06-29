import java.util.Arrays;
import java.util.stream.Stream;

public class Promela2 {
	String DefArcnames;
	String DefSignalnames;
	String DefTransitionRuleIn;
	String DefTransitionRuleOut;
	String DefInitialMarking;
	String DefSTGPath;
	String DefInit;
	String All;
	STGOperation u = new STGOperation();
	public Promela2(STGData [] Data, int maxin, int maxout,String [] Marking, int type,String [] Input,String [] Output)
	{
		GenerateArcnames(Data,type);	
		GenerateSignalnames(Input,Output);		
		GenerateTransitionRuleIn();
		GenerateTransitionRuleOut(maxout);
		GenerateMarking(Marking,type,Data,Input,Output);
		GenerateSTGPath(Data,type);
		GenerateInit(Marking);
		GetAllString();
	}
	private void GenerateArcnames(STGData [] Data,int type)
	{
		DefArcnames = "byte ";
		for(int i = 0; i < Data.length; i++)
		{
			int s = Data[i].outputnum;
			for(int j = 0; j < s; j++)
			{
				DefArcnames += u.ConvertSignalname(Data[i].Signalname,Data[i].OutputSignalnames[j],type) + ", ";
			}
		}
		DefArcnames = DefArcnames.substring(0, DefArcnames.length()-2);
		DefArcnames += ";";
	}
//	private void GenerateSignalnames(STGData [] Data,int type)
//	{
//		DefSignalnames = "byte ";
//		for(int i = 0; i < Data.length; i++)
//		{
//			DefSignalnames += u.ConvertNameforSignal(Data[i].Signalname,type) + ", ";
////			System.out.println(DefSignalnames);
//		}
//		DefSignalnames = DefSignalnames.substring(0, DefSignalnames.length()-2);
//		DefSignalnames += ";";
////		System.out.println(DefSignalnames);		
//	}
	private void GenerateSignalnames(String [] Input,String [] Output)
	{
		DefSignalnames = "byte ";
		String [] both = Stream.concat(Arrays.stream(Input), Arrays.stream(Output)).toArray(String[]::new);
		for(int i = 0; i < both.length; i++)
		{
			DefSignalnames += u.ConvertNameforSignal((both[i]+"+"),1) + ", " + u.ConvertNameforSignal((both[i]+"-"),1) + ", ";
		}		
		DefSignalnames = DefSignalnames.substring(0, DefSignalnames.length()-2);
		DefSignalnames += ";";
//		System.out.println(DefSignalnames);		
	}
	private void GenerateTransitionRuleIn()
	{
		DefTransitionRuleIn = "#define inp(x,y) if ::(x==1) -> x=10;y++; ::(x==2) -> x++;y++; fi";		
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
			for(int j = 1; j <= i; j++)
			{
				i2 += parameters[j-1];
				if(j!=i)
				{
					i2 += ",";
				}				
			}
			String i3 = ")	if :: (";
			String i4 = "";
			for(int l = 1; l <= i; l++)
			{
				i4 += parameters[l-1] + " != 10) -> " + parameters[l-1] + "++; :: ";
				if(l!=i){i4+="( ";}
			}
			String i5 = "else -> break; fi";
			
			saverule[i-1] = i1+i2+i3+i4+i5;
		}
		for(int i = 0; i < MaxOutputArc; i++)
		{
			DefTransitionRuleOut += saverule[i];
			if(i+1 != MaxOutputArc)
			{DefTransitionRuleOut += "\n";}
		}		
//		System.out.println(DefTransitionRuleOut);		
	}
	private void GenerateMarking(String [] Marking,int type,STGData [] Data,String [] Input,String [] Output)
	{
		DefInitialMarking = "proctype P3 (byte k){\n atomic{\n  if\n";
		int m = Marking.length;
		String [] InitMarking = new String[m];
		String [] InitMarking1 = new String[m];
		for(int k = 0; k < m; k++)
		{
			String [] a = Marking[k].split(",");
			InitMarking[k] = u.ConvertSignalname(a[0],a[1],type);			
		}
//		System.out.println(Arrays.toString(InitMarking));
		for(int k = 0; k < m; k++)
		{
			InitMarking1[k] = InitMarking[k] + "=2;";
			for(int l = 0; l < m;l++)
			{
				if(l!=k)
				{
					InitMarking1[k] += InitMarking[l] + "=10;";
				}
			}
		}
//		System.out.println(Arrays.toString(InitMarking1));
		for(int k = 0; k < m; k++)
		{
			DefInitialMarking += "   :: k=="+ (k+1) + " -> " + InitMarking1[k];
			for(int i = 0; i < Data.length; i++)
			{
				int s = Data[i].outputnum;
				for(int j = 0; j < s; j++)
				{
					String Arcname = u.ConvertSignalname(Data[i].Signalname,Data[i].OutputSignalnames[j],type);
					DefInitialMarking += Arrays.asList(InitMarking).contains(Arcname)? "":(Arcname+"=0;");
				}
			}
			String [] both = Stream.concat(Arrays.stream(Input), Arrays.stream(Output)).toArray(String[]::new);
			for(int i = 0; i < both.length; i++)
			{
				DefInitialMarking += u.ConvertNameforSignal((both[i]+"+"),1) + "=0;" + u.ConvertNameforSignal((both[i]+"-"),1) + "=0;";
			}	
			DefInitialMarking += "\n";
		}
		DefInitialMarking += "  fi";
//		System.out.println(DefInitialMarking);		
	}
	private void GenerateSTGPath(STGData [] s,int type)
	{
		DefSTGPath = "  do\n";	
		for(int i = 0; i < s.length; i++)
		{
			String atomic = "\t::atomic{ inp";
			int ilong = s[i].inputnum;
			int olong = s[i].outputnum;
			String [] ip = s[i].InputSignalnames;
			String [] op = s[i].OutputSignalnames;			
			for(int j = 0; j < ilong; j++)
			{
				String ipath = "";
				ipath += atomic + "(";
				String sname = ip[j].indexOf("/")!=-1? ip[j].substring(0,ip[j].indexOf("/")):ip[j];
//				System.out.println(sname);
				ipath += u.ConvertSignalname(ip[j], s[i].Signalname,type) + "," + u.ConvertNameforSignal(sname,type) + ");printf(\"printf(" + u.ConvertNameforSignal(sname,type) + "=%d)\"," + u.ConvertNameforSignal(sname,type) + ") -> outp";
				ipath += olong + "(";
				for(int l = 0; l < olong; l++)
				{
					ipath += u.ConvertSignalname(s[i].Signalname,op[l],type);
					if(l != olong-1){ipath += ",";}
				}
				ipath += ")}\n";
				DefSTGPath += ipath;
//				System.out.println(DefSTGPath);
			}	
		}		
		DefSTGPath += "  od\n}}";
//		System.out.println(DefSTGPath);
	}
	private void GenerateInit(String [] Marking)
	{
		DefInit = "init{\n";
		int Markingnum = Marking.length;
		for(int i = 1; i <= Markingnum; i++)
		{
			DefInit += "  run P3(" + i + ");\n";
		}
		DefInit += "}";
	}
	private void GetAllString()
	{
		All = DefArcnames + "\n" + DefSignalnames + "\n" + DefTransitionRuleIn + "\n" + DefTransitionRuleOut + "\n" + DefInitialMarking + "\n" + DefSTGPath + "\n" + DefInit;
//		System.out.println("testAll : \n" + All);
	}	
}
