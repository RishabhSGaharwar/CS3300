
class SampleTest {
    public static void main(String[] a){
        System.out.println(new Test().Start(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16));
    }
}

class Test {
    int aux;
    public int Start(int p0, int p1, int p2, int p3 , int p4, int p5, int p6, int p7, int p8, int p9, int p10,int p11, int p12, int p13, int p14, int p15, int p16){
        System.out.println(p0);
        aux = p0 + p1;
        return aux;
    }
}
