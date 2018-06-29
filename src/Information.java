import java.util.ArrayList;
import java.util.Arrays;

public class Information {
	String Modelname;
	String [] Keywords = {".model",".input",".output",".graph",".marking",".end"};
	String [] Input;
	String [] Output;
	ArrayList<String> ModelInformation = new ArrayList<String>();
	ArrayList<String> PathInformation = new ArrayList<String>();
	String [] Marking;	
	
	public void GetInformation(ArrayList<String> text)
	{		
		boolean syntax = Checksyntax(text);
		if(syntax == true)
		{			
			SetInformation(text);
			Getmodelname();
			GetInput();
			GetOutput();
			GetMarking(text);
		}
	}
	private boolean Checksyntax(ArrayList<String> text)
	{
		boolean check = true;
		for(int i = 0; i < 4; i++)
		{
			String[] x = text.get(i).toString().split(" ");
			String y = x[0];
			if(!y.equals(Keywords[i])){check = false; System.out.println("Syntax error");break;}
		}	
		int j = 1;
		for(int i = text.size(); i > text.size()-2; i--)
		{
			String[] x = text.get(i-1).toString().split(" ");
			String y = x[0];
			int n = Keywords.length;
			if(!y.equals(Keywords[n-j])){check = false; System.out.println("Syntax error");break;}
			j++;
		}		
		
		return check;
	}
	private void SetInformation(ArrayList<String> text)
	{
		for(int i = 0; i < 3; i++){	ModelInformation.add(text.get(i).toString());}
		for(int i = 4; i < text.size()-2; i++) {PathInformation.add(text.get(i).toString());}			
	}
	private void Getmodelname()
	{
		String []x = ModelInformation.get(0).toString().split(" ");
		Modelname = x[1];
	}
	private void GetInput()
	{
		Input = cutter(ModelInformation,1);
	}
	private void GetOutput()
	{
		Output = cutter(ModelInformation,2);
	}
	private void GetMarking(ArrayList<String> text)
	{
		String [] x = cutter(text,text.size()-2);
		String y = x[0];
		y = y.substring(1, y.length()-1);
		String [] z = y.split(">");
		for(int i = 0; i < z.length; i++)
		{			
			if(i == 0)
				z[i] = z[i].substring(1, z[i].length());
			else
				z[i] = z[i].substring(2, z[i].length());
		}
		Marking = z;
	}
	private String[] cutter(ArrayList<String> x, int i)
	{		
		String[] y = x.get(i).toString().split(" ");
		String [] z = new String[y.length-1];
		for(int j = 0; j <z.length; j++)
		{
			z[j] = y[j+1];
		}
		return z;		
	}
	public void PrintData()
	{
		System.out.println("Modelname : " + Modelname);
		System.out.println("Input : " + Arrays.toString(Input));
		System.out.println("Output : " + Arrays.toString(Output));
		System.out.println("Marking : " + Arrays.toString(Marking));
//		System.out.println("ModelInformation : " + ModelInformation.toString());
		System.out.println("PathInformation : " + PathInformation.toString());
	}
}
