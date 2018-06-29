import java.util.ArrayList;
import java.util.Arrays;

public class STGData {
	String Signalname;
	String [] InputSignalnames;
	String [] OutputSignalnames;
	int inputnum;
	int outputnum;	
	
	public STGData(ArrayList<String> PathInformation, int i)
	{
		SeparateData(PathInformation.get(i).toString());
		SetInputSignal(PathInformation,i);	
		SetInOutnum();		
	}
	private void SeparateData(String text)
	{
		String [] x = text.split(" ");
		String y = x[0];
		SetSignalname(y);
		SetOutputSignal(x,x.length-1);
	}
	private void SetSignalname(String name)
	{
		Signalname = name;
	}
	private void SetInputSignal(ArrayList<String> PathInformation, int i)
	{
		int a = PathInformation.size();
		ArrayList<String> b = new ArrayList<String>();
		for(int j = 0; j < a; j++)
		{
			if(j != i)
			{
				String [] x = PathInformation.get(j).toString().split(" ");
				for(int k = 1; k <x.length; k++)
				{
					if(x[k].equals(Signalname)){b.add(x[0]);break;}
				}
			}
		}
		InputSignalnames = new String[b.size()];
		for(int j = 0; j < b.size();j++)
		{
			InputSignalnames[j] = b.get(j);
		}
	}
	private void SetOutputSignal(String [] out, int i)
	{
		OutputSignalnames = new String[i];
		for(int j = 0; j < i; j++)
		{
			OutputSignalnames[j] = out[j+1];
		}
	}
	private void SetInOutnum()
	{
		inputnum = InputSignalnames.length;
		outputnum = OutputSignalnames.length;
	}	
	public void PrintData()
	{
		System.out.println("In : " + Arrays.toString(InputSignalnames) + "\t S : " + Signalname + "\t Out : " + Arrays.toString(OutputSignalnames) + "\t, Innum = " + inputnum + ", Outnum = " + outputnum );
//		System.out.println("Innum = " + inputnum + ", Outnum = " + outputnum);
	}
}
