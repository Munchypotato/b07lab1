public class Polynomial {
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
			}	
}