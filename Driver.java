import java.io.File;
import java.util.Arrays;

public class Driver { 
	 public static void main(String [] args) throws Exception { 
	  Polynomial p = new Polynomial(); 
	  System.out.println(p.evaluate(3)); //0.0
	  
	  
	  double [] c1 = {6,5}; 
	  int[] e1 = {0, 3};
	  Polynomial p1 = new Polynomial(c1, e1); 
	  double [] c2 = {3, 5, 1, 67, 3}; 
	  int [] e2 = {4, 6, 1, 7, 3};
	  Polynomial p2 = new Polynomial(c2, e2); 
	  double [] c3 = {1};
	  int [] e3 = {0};
	  Polynomial p3 = new Polynomial(c3, e3);
	  double [] c4 = {1};
	  int [] e4 = {1};
	  Polynomial p4 = new Polynomial(c4, e4);
	  
	  System.out.println("Base constructor tests");
	  System.out.println(Arrays.toString(p1.coef)); //[6.0, 5.0]
	  System.out.println(Arrays.toString(p2.exp)); //[4, 6, 1, 7, 3]
	  System.out.println(p2.evaluate(2)); //8970.0
	  System.out.println(p1.hasRoot(-1.063)); //false
	  System.out.println(p4.hasRoot(0)); //true
	  
	  System.out.println(); //gap in output
	  
	  System.out.println("Addition test");
	  Polynomial added = p1.add(p2);
	  System.out.println(Arrays.toString(added.coef)); //[8.0, 3.0, 5.0, 1.0, 67.0, 6.0]
	  System.out.println(Arrays.toString(added.exp)); //[3, 4, 6, 1, 7, 0] *this and ^ can be any order as long as the exp and coefs align correctly (i think)
	  
	  System.out.println();
	  
	  System.out.println("Multiplication test");
	  Polynomial mult = p1.multiply(p2);
	  System.out.println(Arrays.toString(mult.coef)); //[23.0, 45.0, 6.0, 417.0, 18.0, 25.0, 335.0]
	  System.out.println(Arrays.toString(mult.exp)); //[4, 6, 1, 7, 3, 9, 10] *same note as with addition
	  System.out.println(mult.evaluate(4)); //3.64850072E8 -> i checked this on desmos, this is true
	  System.out.println(mult.evaluate(2)); //412620.0 i wanted a nicer number
	  System.out.println(mult.hasRoot(0)); //true
	  System.out.println(mult.hasRoot(1)); //false
	  
	  System.out.println();
	  
	  System.out.println("Multiplication test on 1");
	  Polynomial mult1 = p1.multiply(p3);
	  System.out.println(Arrays.toString(mult1.coef)); //[5.0, 6.0]
	  System.out.println(Arrays.toString(mult1.exp)); //[3, 0] again order doesn't matter as long as they align correctly
	  
	  System.out.println("Multiplication test on x");
	  Polynomial multx = p1.multiply(p4);
	  System.out.println(Arrays.toString(multx.coef)); //[6.0, 5.0]
	  System.out.println(Arrays.toString(multx.exp)); //[1, 4] again order doesn't matter as long as they align correctly
	  
	  System.out.println();
	  
	  System.out.println("Multiplication test on 0");
	  Polynomial mult0 = mult.multiply(p);
	  System.out.println(Arrays.toString(mult0.coef)); //[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
	  System.out.println(Arrays.toString(mult0.exp)); //[4, 6, 1, 7, 3, 9, 10] 
	  //^honestly idk how we should treat this case and i guess output depends on how your method works, but at least for me i dont care enough and this is technically a 0 polynomial
	  
	  System.out.println();
	  
	  System.out.println("Addition test on x and 0");
	  Polynomial add1 = p1.add(p4);
	  System.out.println(Arrays.toString(add1.coef)); //[5.0, 1.0, 6.0]
	  System.out.println(Arrays.toString(add1.exp)); //[3, 1, 0]
	  Polynomial add0 = p1.add(p);
	  System.out.println(Arrays.toString(add0.coef)); //[5.0, 6.0]
	  System.out.println(Arrays.toString(add0.exp)); //[3, 0]

	  mult.saveToFile("pleasework.txt");
	  System.out.println();
	  System.out.println("Reminder for what this should be");
	  System.out.println(Arrays.toString(mult.coef)); //[23.0, 45.0, 6.0, 417.0, 18.0, 25.0, 335.0]
	  System.out.println(Arrays.toString(mult.exp)); //[4, 6, 1, 7, 3, 9, 10] *same note as with addition
	  
	  //**************************YOU NEED TO ADD THESE FILES FOR THE NEXT 2 TESTS*****************************
	  File file1 = new File("C:\\Users\\rzhao\\eclipse-workspace\\CSCB07\\pleasework.txt"); //change file names as needed
	  Polynomial filed = new Polynomial(file1);
	  System.out.println();
	  System.out.println("Read file constructor test"); //FILE CONTAINS LINE: 23.0x4+45.0x6+6.0x1+417.0x7+18.0x3+25.0x9+335.0x10
	  System.out.println(Arrays.toString(filed.coef)); //should be same as above reminder
	  System.out.println(Arrays.toString(filed.exp)); //same here
	  
	  File file2 = new File("C:\\Users\\rzhao\\eclipse-workspace\\CSCB07\\godareyoureal.txt");
	  Polynomial filed2 = new Polynomial(file2);
	  System.out.println();
	  System.out.println("Read file constructor test 2"); //FILE CONTAINS LINE: 5-3x2+7x8
	  System.out.println(Arrays.toString(filed2.coef)); //[5.0, -3.0, 7.0]
	  System.out.println(Arrays.toString(filed2.exp)); //[0, 2, 8]
	  //check that the reverse holds
	  filed2.saveToFile("unoreversecheckmategod.txt"); //5.0-3.0x2+7.0x8 or some variation
	  
	  System.out.println();
	  double [] neg = {-1}; 
	  int [] nege = {1};
	  Polynomial bully = new Polynomial(neg, nege); 
	  bully.saveToFile("negative.txt");
	  System.out.println("Check file named \"negative\""); //-x -> ok i just learned we can ignore all these weird 1x or 3x1 cases so kill me cause i dealt with them, just think about it ig and see if youre output is right
	  													  //ok file should be -1.0x1
	  double [] c5 = {-4, -2, 1, -1, 3}; 
	  int [] e5 = {1, 3, 5, 6, 9};
	  Polynomial p5 = new Polynomial(c5, e5); 
	  p5.saveToFile("funky.txt");
	  System.out.println("Check file named \"funky\""); //-4.0x-2.0x3+x5-x6+3.0x9 -> same here. pain.
	  													//should be -4.0x1-2.0x3+1.0x5-1.0x6+3.0x9
	  
	  //im suffering
	  File file3 = new File("C:\\Users\\rzhao\\eclipse-workspace\\CSCB07\\negative.txt");
	  Polynomial filed3 = new Polynomial(file3);
	  System.out.println();
	  System.out.println("Read file constructor test 3"); //file from save to file 3
	  System.out.println(Arrays.toString(filed3.coef)); //[-1.0]
	  System.out.println(Arrays.toString(filed3.exp)); //[1]
	  
	  File file4 = new File("C:\\Users\\rzhao\\eclipse-workspace\\CSCB07\\funky.txt");
	  Polynomial filed4 = new Polynomial(file4);
	  System.out.println();
	  System.out.println("Read file constructor test 4"); //file from save to file 4
	  System.out.println(Arrays.toString(filed4.coef)); //[-4.0, -2.0, 1.0, -1.0, 3.0]
	  System.out.println(Arrays.toString(filed4.exp)); //[1, 3, 5, 6, 9]
	  
	  //extra tests
	  System.out.println();
	  double[] c6 = {2, 3};
	  int[] e6 = {1, 0};
	  double[] c7 = {4};
	  int[] e7 = {2};
	  Polynomial p6 = new Polynomial(c6, e6);
	  Polynomial p7 = new Polynomial(c7, e7);
	  Polynomial p6p7 = p6.multiply(p7);
	  Polynomial p6plusp7 = p6.add(p7);
	  p6p7.saveToFile("p6p7.txt"); //8.0x3+12.0x2 in file
	  p6plusp7.saveToFile("p6plusp7.txt"); //2.0x1+4.0x2+3.0 in file
	  
	  
	  /*
	  Polynomial s = p1.add(p2); 
	  System.out.println("s(0.1) = " + s.evaluate(0.1)); 
	  if(s.hasRoot(1)) 
	   System.out.println("1 is a root of s"); 
	  else 
	   System.out.println("1 is not a root of s"); */
	 } 
	} 