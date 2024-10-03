class T5{
    public static void main(String[] a)
    {
	    System.out.println((new TV().init()));
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
    public int init()
    {
        int x;
        cnter = 0;
        f = new Fac();
        x = this.goo(this);
        return x;
    }
    public int goo(TV t)
    {
        int x;
        x = t.set();
        return 0;
    }
    public int stop()
    {
        return 0;
    }
    public int set()
    {
        cnter = cnter + 10;
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