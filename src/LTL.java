import java.util.Arrays;
import java.util.stream.Stream;

public class LTL {
	STGOperation o = new STGOperation();
	
	public LTL()
	{}
	//to do next time.Change return to String.
	public String GenPersistency(STGData [] Signal,String [] con,int type)
	{
		String p = "#define persist (";
		int n = con.length;		
		for(int i = 0; i < n; i++)
		{
			p += "(";
			String op = con[i].substring(0, con[i].length()-1);
			char sym = con[i].charAt(con[i].length()-1);
			op += ((sym == '+') ? "-" : "+");
			int k = find(Signal,op);
			String [] a = Signal[k].OutputSignalnames;
			if(a.length > 1) p += "(";
			for(int j = 0; j < a.length; j++)
			{
				p += "(" + o.ConvertSignalname(op, a[j],type) + " == 1)";
				if(!(j == a.length-1))
					p += " || ";
			}
			if(a.length > 1) p += ")";
			p += " -> ";
			k = find(Signal,con[i]);
			a = Signal[k].OutputSignalnames;
			if(a.length > 1) p += "(";
			for(int j = 0; j < a.length; j++)
			{
				p += "(" + o.ConvertSignalname(con[i], a[j],type) + " == 0)";
				if(!(j == a.length-1))
					p += " && ";
			}	
			if(a.length > 1) p += ")";
			if(!(i==n-1))
				p += ") && ";
			else{ p+= ")";}
		}	
		p+= ")";
		String q = "\nltl p3 { [] persist }";
		p += q;
		System.out.println(p);
		return p;
	}
	public String GenSafety(STGData [] Signal,int type)
	{
		String s = "#define safe (";
		for(int i = 0; i < Signal.length; i++)
		{
			int out = Signal[i].outputnum;
			for(int j = 0; j < out; j++)
			{
				String a = "(" + o.ConvertSignalname(Signal[i].Signalname, Signal[i].OutputSignalnames[j],type);
				s += a + " <= 1) ";
				if(!((i == Signal.length-1) && (j == out-1)))
					s += "&& ";
			}
		}
		s += ")\n";
//		s += "/*safety check*/\n";
		s += "ltl p1 { [] safe }";
		System.out.println(s);
		return s;		
	}
	public String GenLiveness(STGData []Signal,int type)
	{
		String l = "#define live (";
		int k = 0;
		for(int i = 0; i < Signal.length; i++)
		{
			k += Signal[i].outputnum;			
		}
//		int out = Signal[i].outputnum;
		for(int j = 1; j <= k; j++)
		{
//			String a = "(" + o.ConvertSignalname(Signal[i].Signalname, Signal[i].OutputSignalnames[j],type);
//			l += a + " > 0) ";
//			if(!((i == Signal.length-1) && (j == out-1)))
//				l += "|| ";
			String a = "(t"+j + ">1)";
			a+= j!=k ? " && " : "";
			l+=a;
		}
		l += ")\n";
//		l += "/*liveness check*/\n";
		l += "ltl p2 { <> live }";
		System.out.println(l);
		return l;
	}
	public String GenConsistency(String [] Input,String [] Output,int type,String [] Marking)
	{
		String m = "";
		for(int k = 0; k < Marking.length; k++)
		{
			String [] a = Marking[k].split(",");
			m += o.ConvertSignalname(a[0],a[1],type) + "==4 || ";			
		}
		m = m.substring(0, m.length()-4);
//		System.out.println(m);
		String [] both = Stream.concat(Arrays.stream(Input), Arrays.stream(Output)).toArray(String[]::new);
//		System.out.println(Arrays.toString(both));
		String c = "";
		String d = "";
		for(int i = 0; i < both.length; i++)
		{
			c += "#define consist"+ (i+1) + " (" + o.ConvertNameforSignal((both[i]+"+"),type) + ">=1 && " + o.ConvertNameforSignal((both[i]+"-"),type) + ">=1 && ("+ m +"))\n";
			d += "ltl p" + (i+4) + "{!<>consist" + (i+1) + "}\n";
		}
		c += d; 
		return c;
	}
	public String GenCSCFulllock(String [] Input,String [] Output,String [] Marking, int type)
	{
		CSCoperation c = new CSCoperation();
		String fulllock = c.GenFulllockCSC(Input, Output, Marking, type);
		return fulllock;
	}
	private int find(STGData [] a,String b)
	{
		for(int i = 0; i < a.length; i++)
		{
			if(a[i].Signalname.equals(b))
				return i;
		}
		return -1;
	}
}
