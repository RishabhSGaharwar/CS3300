//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   boolean printLabel = false;
   Integer RegOrInt = -1;
   Integer Operator = -1;
   boolean ExpIsSimple = false;
   Integer thirdARG = -1;
   Integer firstARG = -1;
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   
   public void printPrologue(String name, Integer StackSpace, boolean isMain)
   {
      System.out.println("\t.text");
      System.out.println("\t.globl " + name);
      System.out.println(name+":");
      if(!isMain)
      {
         System.out.println("\tsw $fp, -8($sp)");
      }
      System.out.println("\tmove $fp, $sp");
      System.out.println("\tsw $ra, -4($fp)");
      System.out.println("\tsubu $sp, $sp, " + ((StackSpace+2)*4));
   }
   
   public void printEpilogue(boolean isMain, Integer StackSpace)
   {
      System.out.println("\taddu $sp, $sp, " + ((StackSpace+2)*4));
      System.out.println("\tlw $ra, -4($fp)");
      if(!isMain)
      {
         System.out.println("\tlw $fp, -8($sp)");
      }
      System.out.println("\tj $ra");
      System.out.println();
   }
   /**
    * f0 -> "MAIN"
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    * f12 -> ( SpillInfo() )?
    * f13 -> ( Procedure() )*
    * f14 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      Integer r1 = null, r2 = null, r3 = null;
      String sr1 = null, sr2 = null, sr3 = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      sr1 = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      sr2 = (String)n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      sr3 = (String)n.f8.accept(this, argu);
      r1 = Integer.parseInt(sr1);
      r2 = Integer.parseInt(sr2);
      r3 = Integer.parseInt(sr3);
      r3 -= 4;
      if(r3<0)
      {
         r3 = 0;
      }
      thirdARG = r3;
      firstARG = r1;
      printPrologue("main", r2+r3-1, true);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      printEpilogue(true, r2+r3-1);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    * f12 -> ( SpillInfo() )?
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      Integer r1 = null, r2 = null, r3 = null;
      String sr1 = null, sr2 = null, sr3 = null;
      String fname = null;
      printLabel = false;
      fname = (String)n.f0.accept(this, argu);
      printLabel = true;
      n.f1.accept(this, argu);
      sr1 = (String)n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      sr2 = (String)n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      sr3 = (String)n.f8.accept(this, argu);
      r1 = Integer.parseInt(sr1);
      r2 = Integer.parseInt(sr2);
      r3 = Integer.parseInt(sr3);
      r3 -= 4;
      if(r3<0)
      {
         r3 = 0;
      }
      thirdARG = r3;
      firstARG = r1;
      printPrologue(fname, r2+r3, false);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      printEpilogue(false, r2+r3);
      printLabel = false;
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    *       | ALoadStmt()
    *       | AStoreStmt()
    *       | PassArgStmt()
    *       | CallStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      System.out.println("\tnop");
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      System.out.println("\tla $a0, str_er");
      System.out.println("\tsyscall");
      System.out.println("\tli $v0, 10");
      System.out.println("\tsyscall");
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Reg()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      String reg = null, lbl = null;
      n.f0.accept(this, argu);
      printLabel = false;
      reg = (String) n.f1.accept(this, argu);
      lbl = (String) n.f2.accept(this, argu);
      System.out.println("\tbeqz " + reg + " " + lbl);
      printLabel = true;
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      String lbl = null;
      printLabel = false;
      n.f0.accept(this, argu);
      lbl = (String) n.f1.accept(this, argu);
      System.out.println("\tb " + lbl);
      printLabel = true;
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Reg()
    * f2 -> IntegerLiteral()
    * f3 -> Reg()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      String reg1 = null, reg2 = null, offset = null;
      n.f0.accept(this, argu);
      reg1 = (String)n.f1.accept(this, argu);
      offset = (String) n.f2.accept(this, argu);
      Integer blah = Integer.parseInt(offset);
      reg2 = (String)n.f3.accept(this, argu);
      System.out.println("\tsw " + reg2 + ", " + blah + "(" + reg1 + ")");
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Reg()
    * f2 -> Reg()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      String reg1 = null, reg2 = null, offset = null;
      n.f0.accept(this, argu);
      reg1 = (String) n.f1.accept(this, argu);
      reg2 = (String)n.f2.accept(this, argu);
      offset = (String)n.f3.accept(this, argu);
      Integer blah = Integer.parseInt(offset);
      System.out.println("\tlw " + reg1 + ", " + blah + "(" + reg2 + ")");
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Reg()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      String reg = null, exp = null;
      n.f0.accept(this, argu);
      reg  = (String)n.f1.accept(this, argu);
      ExpIsSimple = false;
      printLabel = false;
      exp = (String)n.f2.accept(this, (A) reg);
      printLabel = true;
      if(ExpIsSimple)
      {
         if(RegOrInt.equals(2))
         {
            System.out.println("\tli " + reg + " " + exp);
         }
         else if(RegOrInt.equals(1))
         {
            System.out.println("\tmove " + reg + " " + exp);
         }
         else if(RegOrInt.equals(0))
         {
            System.out.println("\tla " + reg + " " + exp);
         }
      }
      else
      {
         System.out.println(exp);
      }
      ExpIsSimple = false;
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      String reg = null;
      n.f0.accept(this, argu);
      reg = (String)n.f1.accept(this, argu);
      if(RegOrInt.equals(1))
      {
         System.out.println("\tmove $a0 " + reg);
      }
      else if(RegOrInt.equals(2))
      {
         System.out.println("\tli $a0 " + reg);
      }
      else if(RegOrInt.equals(0))
      {
         System.out.println("\tla $a0 " + reg);
      }
      System.out.println("\tjal _print");
      return _ret;
   }

   /**
    * f0 -> "ALOAD"
    * f1 -> Reg()
    * f2 -> SpilledArg()
    */
   public R visit(ALoadStmt n, A argu) {
      R _ret=null;
      String reg = null;
      String offset = null;
      n.f0.accept(this, argu);
      reg = (String) n.f1.accept(this, argu);
      offset = (String)n.f2.accept(this, argu);
      Integer blah = Integer.parseInt(offset);
      if(blah < (firstARG-4))
      {
         System.out.println("\tlw " + reg + ", " + (blah*4) + "($fp)");
         return _ret;
      }
      blah += thirdARG;
      blah *= 4;
      System.out.println("\tlw " + reg + ", " + blah + "($sp)");
      return _ret;      
   }

   /**
    * f0 -> "ASTORE"
    * f1 -> SpilledArg()
    * f2 -> Reg()
    */
   public R visit(AStoreStmt n, A argu) {
      R _ret=null;
      String reg = null;
      String offset = null;
      n.f0.accept(this, argu);
      offset = (String)n.f1.accept(this, argu);
      reg = (String)n.f2.accept(this, argu);
      Integer blah = Integer.parseInt(offset);
      if(blah < (firstARG-4))
      {
         System.out.println("\tsw " + reg + ", " + (blah*4) + "($fp)");
         return _ret;
      }
      blah += thirdARG;
      blah *= 4;
      System.out.println("\tsw " + reg + ", " + blah + "($sp)");
      return _ret;
   }

   /**
    * f0 -> "PASSARG"
    * f1 -> IntegerLiteral()
    * f2 -> Reg()
    */
   public R visit(PassArgStmt n, A argu) {
      R _ret=null;
      String reg = null, offset = null;
      n.f0.accept(this, argu);
      reg = (String)n.f2.accept(this, argu);
      offset = (String)n.f1.accept(this, argu);
      Integer blah = Integer.parseInt(offset);
      blah--;
      System.out.println("\tsw " + reg + ", " + ((blah)*4) + "($sp)");
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    */
   public R visit(CallStmt n, A argu) {
      R _ret=null;
      String reg = null;
      n.f0.accept(this, argu);
      printLabel = false;
      reg = (String)n.f1.accept(this, argu);
      System.out.println("\tjalr " + reg);
      printLabel = true;
      return _ret;
   }

   /**
    * f0 -> HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n, A argu) {
      R _ret=null;
      if(n.f0.which == 2)
      {
         ExpIsSimple = true;
      }
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      String arg = (String) argu, fin = null, reg = null;
      n.f0.accept(this, argu);
      reg = (String)n.f1.accept(this, argu);
      if(RegOrInt.equals(2))
      {
         System.out.println("\tli $a0 " + reg);
      }
      else if(RegOrInt.equals(1))
      {
         System.out.println("\tmove $a0 " + reg);
      }
      else if(RegOrInt.equals(0))
      {
         System.out.println("\tla $a0 " + reg);
      }
      System.out.println("\tjal _halloc");
      fin = "\tmove " + arg + " $v0"; 
      _ret = (R)fin;
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Reg()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      String reg = null, exp = null, ret = (String) argu, fin = null;
      n.f0.accept(this, argu);
      reg = (String) n.f1.accept(this, null);
      exp = (String) n.f2.accept(this, null);
      switch(Operator){
         case 0:
            fin = ("\tsle " +  ret + ", " + reg + ", " + exp);
            break;
         case 1:
            fin = ("\tsne " +  ret + ", " + reg + ", " + exp);
            break;
         case 2:
            fin = ("\tadd " +  ret + ", " + reg + ", " + exp);
            break;
         case 3:
            fin = ("\tsub " +  ret + ", " + reg + ", " + exp);
            break;
         case 4:
            fin = ("\tmul " +  ret + ", " + reg + ", " + exp);
            break;
         case 5:
            fin = ("\tdiv " +  ret + ", " + reg + ", " + exp);
            break;
      }
      _ret = (R)fin;
      return _ret;
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n, A argu) {
      R _ret=null;
      Operator = n.f0.which;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "SPILLEDARG"
    * f1 -> IntegerLiteral()
    */
   public R visit(SpilledArg n, A argu) {
      R _ret=null;
      String offset = null;
      n.f0.accept(this, argu);
      offset = (String)n.f1.accept(this, argu);
      _ret = (R)offset;
      return _ret;
   }

   /**
    * f0 -> Reg()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret=null;
      printLabel = false;
      String temp = (String) n.f0.accept(this, null);
      _ret = (R)temp;
      printLabel = true;   
      return _ret;
   }

   /**
    * f0 -> "a0"    0
    *       | "a1"1
    *       | "a2"2
    *       | "a3"3
    *       | "t0"4
    *       | "t1"5
    *       | "t2"6
    *       | "t3"7
    *       | "t4"8
    *       | "t5"9
    *       | "t6"10
    *       | "t7"11
    *       | "s0"12
    *       | "s1"13
    *       | "s2"14
    *       | "s3"15
    *       | "s4"16
    *       | "s5"17
    *       | "s6"18
    *       | "s7"19
    *       | "t8"20
    *       | "t9"21
    *       | "v0"22
    *       | "v1"23
    */
   public R visit(Reg n, A argu) {
      R _ret=null;
      String reg = null;
      RegOrInt = 1;
      n.f0.accept(this, argu);
      if(n.f0.which<4)
      {
         reg = "a" + n.f0.which;
      }
      else if(n.f0.which<12)
      {
         reg = "t" + (n.f0.which-4);
      }
      else if(n.f0.which<20)
      {
         reg = "s" + (n.f0.which-12);
      }
      else if(n.f0.which<22)
      {
         reg = "t" + (n.f0.which-20+8);
      }
      else
      {
         reg = "v" + (n.f0.which-22);
      }
      _ret = (R)("$" + reg);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      RegOrInt = 2;
      n.f0.accept(this, argu);
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      RegOrInt = 0;
      if(printLabel)
      {
         System.out.println(n.f0.tokenImage+":");
      }
      _ret = (R)n.f0.tokenImage;
      return _ret;
   }

   /**
    * f0 -> "//"
    * f1 -> SpillStatus()
    */
   public R visit(SpillInfo n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <SPILLED>
    *       | <NOTSPILLED>
    */
   public R visit(SpillStatus n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

}
