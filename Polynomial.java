import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
public class Polynomial {
	/*
	double[] coef = new double[100];
			public Polynomial()
			{
				for(int i = 0; i < 100; i++)
				{
					coef[i] = 0;
				}	
			}
			public Polynomial(double[] d)
			{
				for(int i = 0; i < d.length; i++)
				{
					coef[i] = d[i];
				}
			}
			public Polynomial add(Polynomial p)
			{
				Polynomial newpoly = new Polynomial();
				for(int i = 0; i < 100; i++)
				{
					newpoly.coef[i] = coef[i] + p.coef[i];
				}	
				return newpoly;
			}
			public double evaluate(double x)
			{
				double ans = 0;
				for(int i = 0; i < 100; i++)
				{
					ans += Math.pow(x, i) * coef[i];
				}
				return ans;
			}
			public boolean hasRoot(double x)
			{
				double ans = 0;
				for(int i = 0; i < 100; i++)
				{
					ans += Math.pow(x, i) * coef[i];
				}
				if(ans != 0)
					return false;
				return true;
			}*/
	double[] coef;
	int[] exp;
	public Polynomial()
	{
		coef = new double[1];
		exp = new int[1];
		coef[0] = 0;
		exp[0] = 0;
	}
	public Polynomial(double[] d, int[] e)
	{
		coef = new double[d.length];
		exp = new int[d.length];
		for(int i = 0; i < d.length; i++)
		{
			coef[i] = d[i];
			exp[i] = e[i];
		}
	}
	public Polynomial(File f) throws Exception
	{
		Scanner input = new Scanner(f);
		String s = input.next();
		input.close();
		for(int i = 0; i<s.length(); i++)
		{
			if(s.charAt(i) == '-' && i != 0)
			{
				s = s.substring(0, i) + '+' + s.substring(i, s.length());
				i++;
			}
		}
		String[] arr = s.split("\\+");
		coef = new double[arr.length];
		exp = new int[arr.length];
		for(int i = 0; i<arr.length; i++)
		{
			int x = arr[i].indexOf('x');
			if(x!=-1)
			{
				if(x == 0)
					coef[i] = 1;
				else
					coef[i] = Double.parseDouble(arr[i].substring(0, x));
				if(arr[i].substring(x, arr[i].length()).length() == 1)
					exp[i] = 1;
				else
					exp[i] = Integer.parseInt(arr[i].substring(x+1, arr[i].length()));
			}
			else
			{
				coef[i] = Double.parseDouble(arr[i]);
				exp[i] = 0;
			}
		}		
	}
	
	public Polynomial add(Polynomial p)
	{
		/*
		 * alright i just realized i did a lot of stuff to 
		 * make the arrays as small as possible for any polynomial's given size 
		 * while doing addition and multiplication and i just realized that was 
		 * completely unnecessary given how i defined the field however i've committed 
		 * and it shouldn't make any differences other than visually looking better
		 * if you print them
		 * edit: nvm i just modified my polynomial so it isnt defaulting to an array of size 100
		*/
		
		//setup and duplicate removal
		int[] newarray = new int[exp.length+p.exp.length]; //new array with all exponents including duplicates
		int d = 0; //counts duplicates
		for(int i = 0; i < exp.length; i++)
			newarray[i] = exp[i]; //adds exponents of array 1
		for(int i = exp.length; i < exp.length+p.exp.length; i++)
			newarray[i] = p.exp[i-exp.length];	 //exponents of array 2
		for(int i = 0; i < exp.length; i++)
		{
			for(int j = 0; j < p.exp.length; j++)
			{
				if(exp[i] == p.exp[j])
					d++; //how many duplicates are there
			}
		}
		int[] newexp = new int[exp.length+p.exp.length-d]; //new array with size same as number of unique exponents for new polynomial exponents
		double[] newcoef = new double[exp.length+p.exp.length-d]; //same as above but for coefficients
		int counter = 0;
		for(int i = 0; i < newarray.length; i++) //adds unique exponents into new exponent array
		//never need to consider index overflow because anything that goes over should be a duplicate
		//note: exp 0 always at the end, but since order doesn't
		{
			boolean check = false;
			for(int j = 0; j < newexp.length; j++)
			{
				if(newarray[i] == newexp[j])
					check = true; //if the exponent is a duplicate, this becomes true
			}
			if(check != true) //^so it knows not to add it to the new array
			{
				newexp[counter] = newarray[i];
				counter++;
			}
		}
		//actual adding
		for(int i = 0; i < newexp.length; i++) //adds coefficients to appropriate spot, for every element in exponent array
		{
			for(int j = 0; j < exp.length; j++) //check og array 1 for common exponent, if there is, add the coefficient from there to the associated place from the new exponent array
			{
				if(exp[j] == newexp[i])
					newcoef[i] += coef[j];
			}
			for(int j = 0; j < p.exp.length; j++) //same for array 2
			{
				if(p.exp[j] == newexp[i])
					newcoef[i] += p.coef[j];
			}
		}
		Polynomial finallydone = new Polynomial(newcoef, newexp);
		return finallydone;
	}
	
	public double evaluate(double x)
	{
		double ans = 0;
		for(int i = 0; i < coef.length; i++)
		{
			ans += Math.pow(x, exp[i]) * coef[i];
		}
		return ans;
	}
	
	public boolean hasRoot(double x)
	{
		double ans = 0;
		for(int i = 0; i < coef.length; i++)
		{
			ans += Math.pow(x, exp[i]) * coef[i];
		}
		if(ans != 0)
			return false;
		return true;
	}
	
	public Polynomial multiply(Polynomial p)
	{
		int totalsize = exp.length * p.exp.length; //how many terms are there after first expanding a polynomial multiplication
		double[] multcoef = new double[totalsize]; //make an array of that size for coefs
		int[] multexp = new int[totalsize]; //and exps
		int counter = 0; //make a separate iterator
		for(int i = 0; i<exp.length; i++)
		{
			for(int j = 0; j<p.exp.length; j++)
			{
				multcoef[counter] = coef[i]*p.coef[j]; //multiply coef of polynomial a with every element of b
				multexp[counter] = exp[i]+p.exp[j]; //add the coefs for each
				counter++; //make sure they go in a new slot in the new array
			}
		}
		if(counter!=totalsize)
			System.out.println("uh oh"); //if every slot in the new array isnt filled then im in pain but this is an assignment so i wont use profanity
		//yes this next part is almost the exact same duplicate removal process as in addition, yes i was too lazy to figure out how to make a separate function for this for them to both leech from
		int d = 0;
		for(int i = 0; i < multexp.length-1; i++)
		{
			for(int j = i+1; j < multexp.length; j++)
			{
				if(multexp[i] == multexp[j])
					d++; //how many duplicates are there
			}
		}
		int[] finalexp = new int[multexp.length-d]; //new array with size same as number of unique exponents for new polynomial exponents
		double[] finalcoef = new double[multexp.length-d]; //same as above but for coefficients
		counter = 0; //need a separate iterator again
		for(int i = 0; i < multexp.length; i++) //adds unique exponents into new exponent array
		//never need to consider index overflow because anything that goes over should be a duplicate
		{
			boolean check = false;
			for(int j = 0; j < finalexp.length; j++)
			{
				if(multexp[i] == finalexp[j])
					check = true; //if the exponent is a duplicate, this becomes true
			}
			if(check != true) //^so it knows not to add it to the new array
			{
				finalexp[counter] = multexp[i];
				counter++; //iterates separately to ensure new terms go in new slots
			}
		}
		for(int i = 0; i < finalexp.length; i++) //adds coefficients to appropriate spot, for every element in exponent array
		{
			for(int j = 0; j < multexp.length; j++) //check og array 1 for common exponent, if there is, add the coefficient from there to the associated place from the new exponent array
			{
				if(multexp[j] == finalexp[i]) //wow look a duplicate
					finalcoef[i] += multcoef[j]; //oops no more duplicate
			}
		}
		Polynomial finallydone = new Polynomial(finalcoef, finalexp);
		return finallydone;
	}
	
	public void saveToFile(String s) throws Exception
	{
		String poly = ""; //what the printed polynomial is saved to
		for(int i = 0; i<exp.length; i++)
		{
			//I just learned we can ignore these weird cases with 1 so crap, but its all here so whatever
			if(exp[i] == 0) //case for if exponent is 0
				poly += Double.toString(coef[i]);
			else if(coef[i] == 0) //if coef is 0
				poly += '0';
			/*this stuff deals with all the 1, -1 cases, but i never added it to the file constructor, so if you uncomment this it might not work both ways yet
			else if(coef[i] == 1 && exp[i] != 1) //if coef is 1 with any >1 exponent
				poly += 'x' + Integer.toString(exp[i]);
			else if(coef[i] == -1 && exp[i] != 1) //if coef is -1 with any >1 exponent
				poly += "-x" + Integer.toString(exp[i]);
			else if(coef[i] == 1 && exp[i] == 1) //if coef and exp are 1
				poly += 'x';
			else if(coef[i] == -1 && exp[i] == 1) //if coef is -1 and exp is 1o
				poly += "-x";
			else if(exp[i] == 1) //if exp is 1
				poly += Double.toString(coef[i]) + 'x';
			*/
			else
				poly += Double.toString(coef[i]) + 'x' + Integer.toString(exp[i]);
			poly += '+'; //add + between each term
		}
		poly = poly.substring(0, poly.length()-1); //remove last + from the last term
		for(int i = 1; i<poly.length(); i++)
		{
			if(poly.charAt(i) == '-')
				poly = poly.substring(0, i-1) + poly.substring(i, poly.length()); //if theres a negative coef, remove the +
		}
		PrintStream output = new PrintStream(s); //print to file s
		output.print(poly);
		output.close();
	}
}