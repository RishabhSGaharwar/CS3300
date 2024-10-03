import syntaxtree.*;
import visitor.*;

public class P4 {
   public static void main(String [] args) {
      try {
         External object = new External();
         Node root = new microIRParser(System.in).Goal();
         FirstPass fp = new FirstPass();
         fp.ext = object;
         root.accept(fp, null);
         // object.printAllFunctions();
         SecondPass sp = new SecondPass();
         sp.ext = object;
         root.accept(sp, null);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 