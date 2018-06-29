import java.util.ArrayList;

public class Runner {
	ArrayList<String> text = new ArrayList<String>();	
	Information init = new Information();;
	STGOperation AData = new STGOperation();
	STGData [] SignalData;	
	int STGtype;
	int Maxrepeat;
	int amountpath;
	int maxinput;
	int maxoutput;	
	String [] Conpath;
	MultiSTG [] repeatSignal;
	private String Promelacode;
	private String Promelacode2;
	//for CSCoperation
	CSCoperation c = new CSCoperation();
	ArrayList<String> pair = new ArrayList<String>();
	ArrayList<String> simplecycle = new ArrayList<String>();
	ArrayList<String> fulllock_pair_result = new ArrayList<String>();
	//------------------
	
	public Runner(ArrayList<String> t)
	{
		text = t;
	}
	public void Operating()
	{
		STGtype = AData.Checktype(text);
		init.GetInformation(text);
		init.PrintData();
		amountpath = init.PathInformation.size();
		repeatSignal = !(STGtype==1) ? AData.FindrepeatSignal(init.PathInformation) : null;
		Maxrepeat = AData.CheckRepeat(repeatSignal, STGtype);			
	}
	public String RunPromelaCode()
	{
		SignalData = new STGData[amountpath];
		SignalData = AData.AddSTGData(init.PathInformation);
		maxinput = AData.GetMaxInput(SignalData);
		maxoutput = AData.GetMaxOutput(SignalData);
		Promelacode = AData.GeneratePromela(SignalData, maxinput, maxoutput, init.Marking,STGtype);
		Conpath = AData.GetConcurrentPath(SignalData);
				
		return Promelacode;
	}	
	public String RunPromelaCode2()
	{
		Promelacode2 = AData.GeneratePromela2(SignalData, maxinput, maxoutput, init.Marking, STGtype,init.Input,init.Output);
		return Promelacode2;
	}
	//Promela1 page UI2(safe,live,persist)
	public String RunLTL(int num)
	{
		if(STGtype == 1)
		{
			LTL a = new LTL();
			if(num == 1)
			{
				return a.GenPersistency(SignalData, Conpath, STGtype);
			}
			else if(num == 2)
			{
				return a.GenSafety(SignalData, STGtype);
			}
			else if(num == 3)
			{
				return a.GenLiveness(SignalData, STGtype);
			}
			else
			{
				return "";
			}
		}
		else
		{
			MultiLTL b = new MultiLTL();
			if(num == 1)
			{
				return b.GenPersistency(SignalData, Conpath, STGtype, repeatSignal);
			}
			else if(num == 2)
			{
				return b.GenSafety(SignalData, STGtype);
			}
			else if(num == 3)
			{
				return b.GenLiveness(SignalData, STGtype);
			}
			else
			{
				return "";
			}
		}		
	}
	//Promela2 page UI2(consistency,csc)
	public String RunConLTL(int num)
	{
		if(STGtype == 1)
		{
			LTL a = new LTL();
			if(num == 1)
			{
				return a.GenConsistency(init.Input,init.Output,STGtype,init.Marking);
			}
			else if(num == 2)
			{
				return a.GenCSCFulllock(init.Input, init.Output, init.Marking, STGtype);
			}			
			else
			{
				return "";
			}
		}
		else
		{
			MultiLTL b = new MultiLTL();
			if(num == 1)
			{
				return b.GenConsistency(init.Input,init.Output,STGtype,init.Marking);
			}
			else if(num == 2)
			{
				return b.GenCSCFulllock(init.Input, init.Output, init.Marking, STGtype);
			}
			else
			{
				return "";
			}
		}		
	}
	//Check full-lock is true or not.
	public String checkfull(String txt)
	{
		if(!txt.isEmpty())
		{
			if(pair.isEmpty())pair = c.PairSignal(init.Input, init.Output);
			fulllock_pair_result = c.GetFullockResult(txt, pair, fulllock_pair_result);
			String result = c.PrintFulllock_result(pair, fulllock_pair_result);
			simplecycle.add(c.Savesimplecycle(txt));
			System.out.println("Sim : " + simplecycle);
			System.out.println(pair);
			System.out.println(fulllock_pair_result);
			return result;
		}
		else
		{
			if(pair.isEmpty())pair = c.PairSignal(init.Input, init.Output);
			if(fulllock_pair_result.isEmpty())
			{
				for(int i = 0; i < pair.size();i++)
				{
					fulllock_pair_result.add("false");
				}
			}
			return c.PrintFulllock_result(pair, fulllock_pair_result);
		}
		
	}
	public boolean Simplecycle_CSCcheck()
	{
		boolean t = c.checkfulllockpair(pair, fulllock_pair_result, init.Output);
		if(t == false)
		{
			return c.checksimplecycle(simplecycle, pair, fulllock_pair_result, init.Output);
		}
		return true;
	}
	public String GenTransitiveLTL()
	{
		String transitive_LTL = c.GenTransitiveLTL(pair, fulllock_pair_result, init.Output,init.Marking,STGtype);
		return transitive_LTL;		
	}
	public boolean ChecktransitivefromCSCLTL(String txt)
	{
		ArrayList<String> sim = new ArrayList<String>();
		sim.add(c.Savesimplecycle(txt));
		boolean t = c.checksimplecycle(sim, pair, fulllock_pair_result, init.Output);
		return t;		
	}
}
 