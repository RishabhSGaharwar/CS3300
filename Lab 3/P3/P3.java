import syntaxtree.*;
import visitor.*;

public class P3 {
   public static void main(String [] args) {
      try {
         External table = new External();
         Node root = new MiniJavaParser(System.in).Goal();
         GJDepthFirst Visitor = new GJDepthFirst(table);
         Visitor.passNum = 1;
         root.accept(Visitor, table); 
         Visitor.passNum = 2;
         root.accept(Visitor, table);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 

