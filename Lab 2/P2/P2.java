import syntaxtree.*;
import visitor.*;

public class P2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         // System.out.println("Program parsed successfully");
         // root.accept(new DepthFirstVisitor()); // Your assignment part is invoked here.
         DepthFirstVisitor Parser = new DepthFirstVisitor(1);
         root.accept(Parser);
         Parser.setPassIndex(2);
         root.accept(Parser);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 