class T3{
    public static void main(String[] a){
	System.out.println(new BS().Start());
    }
}
// This class contains an array of integers and
// methods to initialize, print and search the array
// using Binary Search

class BS
{
    int[] number ;
    int size ;

    // Invoke methods to initialize, print and search
    // for elements on the array
    public int Start()
	{
		int x;
		number = new int[5];
		number[0] = 20;
		System.out.println(number.length);
		x = this.foo();
		x = this.check();
		x = this.goo();
		x = this.check();
		return 1;
    }

	public int foo()
	{
		int[] number;
		number = new int[6];
		return 2;
	}

	public int goo()
	{
		number = new int[10];
		return 1;
	}

	public int check()
	{
		System.out.println(number.length);
		return 1;
	}




}