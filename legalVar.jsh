import static java.lang.System.out;

/* Yuck! */
class Gen { }
class Gen1 <Gen> { }
class Gen2 <Gen1, String> { }
class Gen3 <Gen1, Gen2> { }

public class VAR {
	public static void main (String[] args) {

		//Example #1
		var one = "Type Inference!"; //Inferred to java.lang.String;
		out.println("\nOur first use of var!:\t\"" + one + "\"");
		out.println("The variable is of/can cast to type " +
			((Object)one).getClass().getName() + ".\n");

		//Example #2
		var two = 9; //Inferred to int
		out.println("\nOur second use of var!:\t\"" + two + "\"");
		out.println("The variable is of/can cast to type " +
			((Object)two).getClass().getName() + ".\n");

		//Example #2
		var three = 9.9; //Inferred to double
		out.println("\nOur second use of var!:\t\"" + three + "\"");
		out.println("The variable is of/can cast to type " +
			((Object)three).getClass().getName() + ".\n");

		//Example #4 - The evolution of messy generic instantiation
		Gen3 <Gen1<Gen>, Gen2<Gen1,String>> gen3_1 = new Gen3 <Gen1<Gen>, Gen2<Gen1,String>> (); //88 Characters
		Gen3 <Gen1<Gen>, Gen2<Gen1,String>> gen3_2 = new Gen3 <> (); //60 Characters
		var gen3_3 = new Gen3 <Gen1<Gen>, Gen2<Gen1,String>> (); // 56 Characters
		var four = new Gen3 <> (); //26 Characters
		out.println("\nOur fourth use of var!:\t\"" + four + "\"");
		out.println("The variable is of/can cast to type " +
			((Object)four).getClass().getName() + ".\n");
		out.println("In Java 5-6 this generic code was 88 characters long.");
		out.println("In Java 7-9 this generic code is 60 characters long.");
		out.println("In Java 10 this generic code can be 26 characters long.\n");

		// #Example #5 - Common Collections
		var five = new java.util.ArrayList<String> ();
		five.add("Hello!");
		five.add("Hiya!");
		out.println("\nOur fifth use of var!:\t\"" + five + "\"");
		out.println("The variable is of/can cast to type " +
			((Object)five).getClass().getName() + ".\n");
	}
}



