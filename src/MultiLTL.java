import java.util.ArrayList;
import java.util.Arrays;

public class MultiLTL extends LTL{	
	
	public MultiLTL()
	{}
	public String GenPersistency(STGData [] Signal,String [] con,int type,MultiSTG [] repeatSignal)
	{
		String p = "#define persist (";
		ArrayList<String> d = new ArrayList<String>();
//		String [] d = new String[con.length];
		for(int i = 0; i < con.length; i++)
		{
			if(ismulti(con[i]))
				d.add(GetmultiLTL(Signal, con[i], type,repeatSignal));
			else 
				d.add(GetLTL(Signal, con[i], type)); 
			System.out.println(d);
		}	
		d = CheckltlRepeat(d);
		p += addLTLString(d);
		if(d.size()>1)p += ")";
		System.out.println(p);
		String q = "\nltl p3 { [] persist }"; 
		p += q;
		return p;
	}
	private ArrayList<String> CheckltlRepeat(ArrayList<String> d)
	{
		for(int i = 0; i < d.size(); i++)
		{
			for(int j = 0; j < d.size(); j++)
			{
				if((i!=j) && (d.get(i).equals(d.get(j))))
//					d[j] = "";
					d.remove(j);
			}
		}
		return d;
	}
	private String addLTLString(ArrayList<String> d)
	{
		String p = "";
		for(int i = 0; i < d.size(); i++)
		{
			p += d.get(i);
			p += ") && ";
//			if(i != d.length-1)
//				p += " && ";
		}
		p = p.substring(0,p.length()-3);
		return p;
		
	}
	private boolean ismulti(String s)
	{
		s = s.substring(s.length()-1);
		return (isNumeric(s))? true : false;
	}
	private boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	private int getposition(MultiSTG [] Signal, String b)
	{
		int position = 0;
		for(int i = 0; i < Signal.length; i++)
		{
			if(Signal[i].MultiSignalname.equals(b))
				position = i;
		}
		return position;
	}
	private String GetmultiLTL(STGData [] Signal,String con,int type,MultiSTG [] repeatSignal)
	{
		String p = "";
		p += "(";
		String [] b = con.split("/");
		int pos = getposition(repeatSignal,b[0]);
		int l = repeatSignal[pos].repeatnum;
		int start = repeatSignal[pos].start;
		char sym = b[0].charAt(b[0].length()-1);
		String z = b[0].substring(0,b[0].length()-1);
		z += ((sym == '+') ? "-" : "+");
		l = (start == 1) ? l+1 : l;
		
		p += "(";
		for(int i = start; i < l; i++)
		{
			String c = z + "/" + i;
			int k = find(Signal,c);
			String [] a = Signal[k].OutputSignalnames;
			
			if(a.length > 1) 
				p += "(";
			
			for(int j = 0; j < a.length; j++)
			{
				p += "(" + o.ConvertSignalname(c, a[j], type) + " == 1)";
				if(!(j == a.length-1))
					p += " || ";
			}
			if(a.length > 1) 
				p += ")";
			if(!(i == l-1))
				p += " || ";
		}
		p += ") -> (";
		
		for(int i = start; i < l; i++)
		{
			String c = b[0] + "/" + i;
			int k = find(Signal,c);
			String [] a = Signal[k].OutputSignalnames;
			
			if(a.length > 1) p += "(";
			
			for(int j = 0; j < a.length; j++)
			{
				p += "(" + o.ConvertSignalname(c, a[j], type) + " == 0)";
				if(!(j == a.length-1))
					p += " && ";
			}
			if(a.length > 1) p += ")";		
			if(!(i == l-1))
				p += " && ";
		}	
		p += ")";
		p += ")";
		return p;		
	}
	private String GetLTL(STGData [] Signal,String con,int type)
	{
		String p = "";
		p += "(";
		String op = con.substring(0, con.length()-1);
		char sym = con.charAt(con.length()-1);
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
		k = find(Signal,con);
		a = Signal[k].OutputSignalnames;
		
		if(a.length > 1) p += "(";
		
		for(int j = 0; j < a.length; j++)
		{
			p += "(" + o.ConvertSignalname(con, a[j],type) + " == 0)";
			if(!(j == a.length-1))
				p += " && ";
		}	
		
		if(a.length > 1) p += ")";
		
		return p;
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
