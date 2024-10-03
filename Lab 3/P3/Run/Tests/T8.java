class T8{
    public static void main(String[] a)
    {
	    System.out.println((new Clazz().init()));
    }
}

class Clazz
{
    public int init()
    {
       if((this.foo()) || (this.foo())) System.out.println(1);
       if((this.foo()) && (this.goo())) System.out.println(2);
       if((this.foo()) || (this.foo())) System.out.println(3);
       if((this.foo()) && (this.goo())) System.out.println(4);
       if((this.goo()) || (this.foo())) System.out.println(5);
       if((this.goo()) && (this.goo())) System.out.println(6);
       if((this.goo()) || (this.foo())) System.out.println(7);
       if((this.goo()) && (this.goo())) System.out.println(8);

       return 999;
    }

    public boolean foo()
    {
        System.out.println(100);
        System.out.println(110);
        return false;
    }

    public boolean goo()
    {
        System.out.println(10);
        System.out.println(11);
        return true;
    }
}

class B
{
    int x;
    int y;

    
}