import java.util.ArrayList;

public class STGOperation {
		
	public STGOperation()
	{}
	public STGData[] AddSTGData(ArrayList<String> PathInformation)
	{
		int x = PathInformation.size();
		STGData[] a = new STGData[x];
		for(int i = 0; i < x; i++)
		{
			a[i] = new STGData(PathInformation, i);
			a[i].PrintData();
		}
		return a;
	}	
	public int Checktype(ArrayList<String> text)
	{
		int type = 1;		
		for(int i = 4; i < text.size()-2; i++) 
		{
			String[] x = text.get(i).toString().split(" ");
			String y = x[0].substring(x[0].length()-1);
			boolean z = isNumeric(y);
			if(z == true)
				return type = 2;
		}
		return type;		
	}
	private boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	//to do checking more in multicycle
	public int CheckRepeat(MultiSTG [] a, int type)
	{
		if(type == 1){return 1;}
		else
		{
			int max = 0;
			for(int i = 0; i < a.length; i++)
			{
				max = (a[i].repeatnum > max) ? a[i].repeatnum : max;
			}
			return max;
		}
		
	}
	//Find multicycle signal and keep in MultiSTG
	public MultiSTG [] FindrepeatSignal(ArrayList<String> Pathinformation)
	{
		ArrayList<String> a = new ArrayList<String>();	
		ArrayList<Integer> e = new ArrayList<Integer>();
		for(int i = 0; i < Pathinformation.size(); i++)
		{
			int count = 1;
			String [] b = Pathinformation.get(i).toString().split(" ");
			String c = b[0];
			if((isNumeric(c.substring(c.length()-1))))
			{
				String [] g = c.split("/");
				c = g[0];
				if(!findrepeat(a,c))
				{
					a.add(c);
					for(int j = 0; j < Pathinformation.size(); j++)
					{
						String [] h = Pathinformation.get(j).toString().split(" ");
						String d = h[0];
						if((i!=j) && (isNumeric(d.substring(d.length()-1))))
						{
							String [] m = d.split("/");
							d = m[0];
							if(c.equals(d))
								count++;
						}
					}
					e.add(count);
				}
			}
		}
		int [] start = CheckStart(Pathinformation,a,e);
		MultiSTG [] k = new MultiSTG[a.size()];
		for(int i = 0; i < k.length; i++)
		{
			MultiSTG f = new MultiSTG();
			f.MultiSignalname = a.get(i).toString();
			f.repeatnum = e.get(i);
			f.start = start[i];
			k[i] = f;
			System.out.println("repeatSignal : " + k[i].MultiSignalname + "," + k[i].repeatnum + "," + k[i].start);
		}
		return k;
	}
	private boolean findrepeat(ArrayList<String> a,String b)
	{
		boolean c = false;
		for(int i = 0; i < a.size(); i++)
		{
			if(b.equals(a.get(i).toString()))
			{
				c = true;
				return c;
			}
		}
		return c;
	}
	private int[] CheckStart(ArrayList<String> Pathinformation,ArrayList<String> a,ArrayList<Integer> e)
	{
		int [] x = new int[a.size()];
		for(int i = 0; i < a.size(); i++)
		{
			x[i] = 0;
			String b = a.get(i).toString();
			b += "/";
			b += e.get(i);
			for(int j = 0; j < Pathinformation.size(); j++)
			{
				String [] c = Pathinformation.get(j).toString().split(" ");
				String d = c[0];
				if(b.equals(d))
				{	x[i] = 1; break;}
			}			
		}	
		return x;
	}
	public int GetMaxInput(STGData [] a)
	{
		int max = 0;
		for(int i = 0; i <a.length; i++)
		{
			if(a[i].inputnum > max) max = a[i].inputnum;
		}
		return max;
	}
	public int GetMaxOutput(STGData [] a)
	{
		int max = 0;
		for(int i = 0; i <a.length; i++)
		{
			if(a[i].outputnum > max) max = a[i].outputnum;
		}
		return max;
	}
	public String ConvertSignalname(String x,String y,int type)
	{
		if(type == 1)
		{
			x = ConvertName(x);
			y = ConvertName(y);
		}
		else
		{
			x = isNumeric(x.substring(x.length()-1)) ? MultiConvertName(x) : ConvertName(x);
			y = isNumeric(y.substring(y.length()-1)) ? MultiConvertName(y) : ConvertName(y);
		}
		String name = x + y;
		return name;
	}
	public String ConvertNameforSignal(String x,int type)
	{
		return type==1? ConvertName(x):(isNumeric(x.substring(x.length()-1)) ? MultiConvertName(x) : ConvertName(x));
	}
	private String ConvertName(String x)
	{
		String symbol = "";
		String name = x.substring(0, x.length()-1);
		if(x.charAt(x.length()-1) == '+')	{symbol = "p";}
		else{symbol = "m";}		
		name = name + symbol;
//		System.out.println(name);
		return name;
	}
	private String MultiConvertName(String x)
	{
		String symbol = "";
		String [] a = x.split("/");
		String name = a[0].substring(0, a[0].length()-1);
		if(a[0].charAt(a[0].length()-1) == '+')	{symbol = "p";}
		else{symbol = "m";}		
		name = name + symbol+a[1];
		return name;
		
	}
	public String GeneratePromela(STGData [] Data, int maxin, int maxout,String [] Marking,int type)
	{
		Promela a = new Promela(Data,maxin,maxout,Marking,type);
		String x = a.All;
		return x;
	}
	public String GeneratePromela2(STGData [] Data, int maxin, int maxout,String [] Marking,int type,String [] Input,String [] Output)
	{
		Promela2 a = new Promela2(Data,maxin,maxout,Marking,type,Input,Output);
		String x = a.All;
		return x;
	}
	public String[] GetConcurrentPath(STGData [] Signal)
	{
		String [] b;
		ArrayList<String> a = new ArrayList<String>();
		int k = Signal.length;
		for(int i = 0; i < k; i++)
		{
			if(Signal[i].outputnum>1)
				a.add(Signal[i].Signalname);
		}
		b = new String[a.size()];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = a.get(i);
		}
		return b;
	}
}
