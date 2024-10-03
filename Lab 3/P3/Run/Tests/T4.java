class T4{
    public static void main(String[] a)
    {
	    System.out.println(((((((new TV().init()).foo()).foo()).foo()).foo()).foo()).stop());
    }
}

class TV
{
    int cnter;
    Fac f;
    public TV foo()
    {
        cnter = f.ComputeFac(cnter);
        System.out.println(cnter);
        return this;
    }
    public TV init()
    {
        cnter = 0;
        f = new Fac();
        return this;
    }
    public int stop()
    {
        return 0;
    }
}

class Fac {
    public int ComputeFac(int num){
        int num_aux ;
        int ans;

        ans = 1;
        num_aux = 1;
        while(num_aux <= num)
        {
            ans = ans * num_aux;
            num_aux = num_aux + 1;
        }
        return ans;
    }
}