class T7{
    public static void main(String[] a)
    {
	    System.out.println((new TV().init()));
    }
}

class TV
{
    public int init()
    {
        B b1;
        B b2;
        int dump;
        b1 = new B();
        b2 = b1;
        dump = b1.init();
        dump = b1.add((b1.This()),(b2.changey()),(b2.This()),(b1.changey()));
        System.out.println(dump);
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
        return y;
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

    public B This()
    {
        return this;
    }

    public int add(B dum1, int x1, B dum2, int x2)
    {
        int dump;
        dump = dum2.goo();
        dump = dum1.foo();
        System.out.println(dum1.disp());
        System.out.println(dum2.disp());
        return ((dum1.inc()) + (x1 + ((dum2.inc()) + x2)));
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