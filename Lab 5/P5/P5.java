import syntaxtree.*;
import visitor.*;

public class P5 {
   public static void main(String [] args) {
      try {
        Node root = new MiniRAParser(System.in).Goal();
        GJDepthFirst visit = new GJDepthFirst<>();
        root.accept(visit, null);
        System.out.println("\t .text");
        System.out.println("\t .globl _halloc");
        System.out.println("_halloc:");
        System.out.println("\t li $v0, 9");
        System.out.println("\t syscall");
        System.out.println("\t j $ra");
        System.out.println();
        System.out.println("\t .text");
        System.out.println("\t .globl _print");
        System.out.println("_print:");
        System.out.println("\t li $v0, 1");
        System.out.println("\t syscall");
        System.out.println("\t la $a0, newl");
        System.out.println("\t li $v0, 4");
        System.out.println("\t syscall");
        System.out.println("\t j $ra");
        System.out.println();
        System.out.println("\t .data");
        System.out.println("\t .align 0");
        System.out.println("newl:	.asciiz \"\\n\"");
        System.out.println("\t .data");
        System.out.println("\t .align 0");
        System.out.println("str_er:\t.asciiz \"ERROR: abnormal termination\\n" + //
                    "\"");
      }
      catch (ParseException e) {
        System.out.println(e.toString());
      }
   }
} 