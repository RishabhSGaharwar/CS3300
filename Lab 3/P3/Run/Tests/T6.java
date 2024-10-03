class T6{
    public static void main(String[] a)
    {
	    System.out.println((new TV().init()));
    }
}

class TV
{
    public int init()
    {
        B b;
        D d;
        B b1;
        int dump;
        b = new B();
        d = new D();
        b1 = new B();
        dump = b.foo();
        System.out.println(b.disp());
        dump = b.goo();
        System.out.println(b.disp());
        dump = d.foo();
        System.out.println(d.disp());
        dump = d.goo();
        System.out.println(d.disp());
        dump = b1.init();
        System.out.println((b1.changey())*((b1.changey())/((b1.gety()) + (b1.changey()))));
        System.out.println(b1.lol());
        return 0;
    }
}

class B
{
    int x;
    int y;

    public int foo()
    {
        x = 10;
        return 0;
    }

    public int goo()
    {
        x = 20;
        return 0;
    }

    public int disp()
    {
        return x;
    }

    public int init()
    {
        y = 10;
        return 0;
    }

    public int changey()
    {
        y = y*10;
        return 0;
    }

    public int gety()
    {
        return y;
    }

    public int inc()
    {
        y = y+1;
        return y;
    }

    public int lol()
    {
        int ret;
        if(1000000 <= y){ret = 1000000;}
        else
        {
            y = (this.inc()) * y;
            System.out.println(y);
            y = y + (this.inc());
            System.out.println(y);
            y = (this.inc()) + y;
            System.out.println(y);
            y = y * (this.lol());
            System.out.println(y);
            y = (this.inc()) + y;
            System.out.println(y);
            ret = y;
        }
       
        ret = y;
        return ret;
    }
}

class D extends B
{
    public int goo()
    {
        x = 30;
        return 0;
    }

    public int disp()
    {
        return x;
    }
}