import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class test {

//	public static void main(String [] args)
//	{
////		int ln1=0,ln2=0,ln3=0,ln4;
////		int ln5=0,ln6=0,ln7=0,ln8;
////		int ln9=0,ln10=0,ln11=0,ln12=0;
////		
////		ln4=1;
////		ln8=1;
////		ln12=1;
//		
//		ArrayList<String> x = new ArrayList<String>();
//		int i =1;
////		int j=1;
////		while(true)
////		{
////			if(ln4==1)
////			{
////				ln4=0;ln1=1;
////				x.add("ln1");
////				i++;
////			}
////			if(ln1==1 && ln12==1)
////			{
////				ln1=0;ln12=0;ln2=1;ln9=1;
////				x.add("ln2 con ln9");
////				i++;
////			}			
////			if(ln2==1)
////			{
////				ln2=0;ln3=1;
////				x.add("ln3");
////				i++;
////			}
////			if(ln3==1 && ln10==1)
////			{
////				ln3=0;ln10=0;ln4=1;ln11=1;
////				x.add("ln4 con ln11");
////				i++;
////			}
////			if(ln8==1 && ln9==1)
////			{
////				ln8=0;ln9=0;ln10=1;ln5=1;
////				x.add("ln10 con ln5");
////				i++;
////			}
////			if(ln5==1)
////			{
////				ln5=0;ln6=1;
////				x.add("ln6");
////				i++;
////			}
////			if(ln6==1 && ln11==1)
////			{
////				ln6=0;ln11=0;ln7=1;ln12=1;
////				x.add("ln7 con ln12");
////				i++;
////			}
////			if(ln7==1)
////			{
////				ln7=0;ln8=1;
////				x.add("ln8");
////				i++;
////			}	
////			if(i>=20)
////			{
////				System.out.println(x);
////				break;
////			}
////		}	
////		Random r = new Random();
////		int n=r.nextInt(2)+1;
////		int k=1;
////		while(i<=3)
////		{
////			String y = "";
////			if(i==1)
////			{
////				y+= "4->1->";
////				n=r.nextInt(2)+1;
////				if(n==1)
////				{
////					y+="2->3->4->";
////				}
////				else
////				{
////					y+="9->10->4->";							
////				}
////				if(!x.contains(y))	x.add(y);
////			}
////			k++;
////			if(k>=10) break;
////		}
////		System.out.println(x);
//		
//		String a ="AomRip=1;RomAop=1;AimRop=1;"
//			+"::atomic{ inp1(AomRip) -> outp1(RipAop)}"
//			+"::atomic{ inp2(RipAop,RomAop) -> outp2(AopRop,AopRim)}"
//			+"::atomic{ inp1(AopRim) -> outp1(RimAom)}"
//			+"::atomic{ inp2(RimAom,RopAom) -> outp2(AomRip,AomRom)}"
//			+"::atomic{ inp2(AopRop,AimRop) -> outp2(RopAip,RopAom)}"
//			+"::atomic{ inp1(RopAip) -> outp1(AipRom)}"
//			+"::atomic{ inp2(AomRom,AipRom) -> outp2(RomAim,RomAop)}"
//			+"::atomic{ inp1(RomAim) -> outp1(AimRop)}";
//		String initial = a.substring(0,a.indexOf(":"));
//		String [] init = initial.split(";");		
//		System.out.println(initial);
//		for(int j =0 ;j<init.length;j++){init[j] = init[j].substring(0, init[j].length()-2);}
//		System.out.println(Arrays.toString(init));
//		ArrayList<String[]> in = new ArrayList<String[]>();
//		ArrayList<String[]> out = new ArrayList<String[]>();
//		a=a.substring(a.indexOf(":"));
//		System.out.println(a);
//		String [] t = a.split("::atomic\\{");
//		System.out.println(Arrays.toString(t));
//		String [] t1 = new String[t.length-1];
//		for(int j =0; j < t.length-1;j++)
//		{
//			t1[j]=t[j+1];
//		}		
//		System.out.println(Arrays.toString(t1));
//		for(int j=0;j<t1.length;j++)
//		{
//			String [] rest = t1[j].split("->");
//			String inp = rest[0].substring(5, rest[0].length()-1);
//			if(inp.contains(",")){
//				String [] inp1 = inp.split(",");
//				in.add(inp1);
//			}
//			else{ String [] inp1 = new String[1];
//				inp1[0]=inp;
//				in.add(inp1);
//			}
//			String outp = rest[1].substring(6, rest[1].length()-1);
//			if(outp.contains(",")){
//				String [] outp1 = outp.split(",");
//				out.add(outp1);
//			}
//			else{ String [] outp1 = new String[1];
//				outp1[0]=outp;
//				out.add(outp1);
//			}
//			System.out.println("in" + (j+1) + ": "+Arrays.toString(in.get(j)));
//			System.out.println("out" + (j+1) + ": "+Arrays.toString(out.get(j)));			
//		}		
//	}
////	public static ArrayList<String> getSimpleCycle(ArrayList<String[]> in,ArrayList<String[]> out,String []init)
////	{
////		ArrayList<String> x = new ArrayList<String>();
////		for(int i=0;i<init.length;i++)
////		{
////			String ref = init[i];
////			for(int j =0 ;j < in.size();j++)
////			{
////				if(Arrays.asList(in.get(j)).contains(ref))
////				{
////										
////				}
////			}
////		}
////	}
}
