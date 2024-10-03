package visitor;
import java.util.*;
import java.util.concurrent.TimeUnit;

import syntaxtree.Temp;

public class External {
    public class BlockInfo
    {
        HashSet<Integer> use;
        HashSet<Integer> in;
        HashSet<Integer> out;
        HashSet<Integer> def;
        ArrayList<Integer> successors;
    
        public BlockInfo()
        {
            use = new HashSet<Integer>();
            in = new HashSet<Integer>();
            out = new HashSet<Integer>();
            def = new HashSet<Integer>();
            successors = new ArrayList<Integer>();
        }
        public void printBlock()
        {
            System.out.println("\t\tUse: " + use);
            System.out.println("\t\tDef: " + def);
            System.out.println("\t\tIn: " + in);
            System.out.println("\t\tOut: " + out);
            System.out.println("\t\tSucc: " + successors);
        }
    }
    
    public class TempInfo{
        int start;
        int end;
        int tempNo;
        String parentFunction;
        
        public TempInfo()
        {
            start = -1;
            end = -1;
            tempNo = -1;
            parentFunction = null;
        }

        public TempInfo(int tempNum)
        {
            start = -1;
            end = -1;
            parentFunction = null;
            this.tempNo = tempNum;
        }

        public TempInfo(String name, int tempNum)
        {
            start = -1;
            end = -1;
            parentFunction = name;
            this.tempNo = tempNum;
        }
        public void printTemp()
        {
            System.out.println("TEMP"+tempNo + " start: " + start + " end: " + end);
            System.out.flush();
        }
    }

    public class FunctionInfo{
        String name;
        int numArgs;
        int StackSpace;
        int maxArgs;
        int sp;
        Boolean leaf;
        HashSet<String> ChildFunctions;
        ArrayList<BlockInfo> blocks;
        HashMap<Integer, String> jumps;
        HashMap<String, Integer> labels;
        HashMap<Integer, TempInfo> temps;
        HashMap<TempInfo, String> TempRegMap;
        HashMap<TempInfo, Integer> TempStackMap;

        public FunctionInfo(String FuncName)
        {
            this.name = FuncName;
            numArgs = 0;
            StackSpace = 0;
            maxArgs = 0;
            sp = 0;
            leaf = true;
            ChildFunctions = new HashSet<String>();
            blocks = new ArrayList<BlockInfo>();
            jumps = new HashMap<Integer, String>();
            labels = new HashMap<String, Integer>();
            temps = new HashMap<Integer, TempInfo>();
            TempRegMap = new HashMap<TempInfo, String>();
            TempStackMap = new HashMap<TempInfo, Integer>();
        }

        public void printFunc()
        {
            System.out.println("Name: " + this.name);
            System.out.println("Args Number: " + numArgs);
            System.out.println("MaxArgs Number: " + maxArgs);
            System.out.println("Is a leaf? " + leaf);
            System.out.println("Number of blocks: " + blocks.size());
            System.out.println(ChildFunctions);
            System.out.println(jumps);
            System.out.println(labels);
            for(TempInfo temp : temps.values())
            {
                temp.printTemp();
            }
            for(int i=0; i<blocks.size(); i++)
            {
                System.out.print("\t" + i+ "\n");
                blocks.get(i).printBlock();
            }
            for(TempInfo elem : TempRegMap.keySet())
            {
                System.out.println("TEMP" + elem.tempNo + " -> " + TempRegMap.get(elem));
            }
            System.out.println("SPILLS:");
            for(TempInfo tt : TempStackMap.keySet())
            {
                System.out.print("\t");
                tt.printTemp();
            }
        }
        public void manageJumps()
        {
            for(Integer i : jumps.keySet())
            {
                BlockInfo blockWithJump = blocks.get(i);
                String jumpTargetLabel = jumps.get(i);
                Integer targetLabelBlock = labels.get(jumpTargetLabel);
                blockWithJump.successors.add(targetLabelBlock);
            }
        }
        public void setInOut()
        {
            Boolean flag = true;
            while(flag)
            {
                flag = false;
                for(int i=this.blocks.size()-1; i>=0; i--)
                {
                    BlockInfo thisBlock = this.blocks.get(i);
                    HashSet<Integer> in$ = new HashSet<Integer>(thisBlock.in);
                    HashSet<Integer> out$ = new HashSet<Integer>(thisBlock.out);
                    thisBlock.in.clear();
                    thisBlock.in.addAll(thisBlock.use);
                    thisBlock.out.removeAll(thisBlock.def);
                    thisBlock.in.addAll(thisBlock.out);
                    thisBlock.out.clear();
                    for(int j=0; j< thisBlock.successors.size(); j++)
                    {
                        thisBlock.out.addAll(this.blocks.get(thisBlock.successors.get(j)).in);
                    }
                    if(!(thisBlock.in.equals(in$) && thisBlock.out.equals(out$)))
                    {
                        flag = true;
                    }
                }
            }
        }
        public void LivenessAnalysis()
        {
            for(Integer tempNum : this.blocks.get(0).in)
            {
                if(!temps.containsKey(tempNum))
                {
                    temps.put(tempNum, new TempInfo(this.name, tempNum));
                }
                temps.get(tempNum).start = 0;
                temps.get(tempNum).end = 0;
            }    
            for(int i=0; i<this.blocks.size(); i++)
            {
                for(Integer tempNum : this.blocks.get(i).out)
                {
                    if(!temps.containsKey(tempNum))
                    {
                        temps.put(tempNum, new TempInfo(this.name, tempNum));
                        temps.get(tempNum).start = i;
                        temps.get(tempNum).end = i;
                    }
                }
                for(Integer tempNum : this.blocks.get(i).in)
                {
                    temps.get(tempNum).end = i;
                }
            }   
        }
        public void LinearScan()
        {
            TreeSet<String> Registers = new TreeSet<String>();
            for(int i=0; i<8; i++)
            {
                Registers.add("s"+i);
            }
            for(int i=0; i<10; i++)
            {
                Registers.add("t"+i);
            }
            TreeSet<TempInfo> Active = new TreeSet<TempInfo>(new Comparator<TempInfo>(){
                @Override
                public int compare(TempInfo t1, TempInfo t2)
                {
                    if(t1.end < t2.end)
                    {
                        return -1;
                    }
                    if(t1.end > t2.end)
                    {
                        return 1;
                    }
                    if(t1.tempNo != t2.tempNo)
                    {
                        return 1;
                    }
                    return 0;
                }
            });  
            TreeSet<TempInfo> Intervals = new TreeSet<TempInfo>(new Comparator<TempInfo>(){
                @Override
                public int compare(TempInfo t1, TempInfo t2)
                {
                    if(t1.start < t2.start)
                    {
                        return -1;
                    }
                    if(t1.start > t2.start)
                    {
                        return 1;
                    }
                    if(t1.tempNo != t2.tempNo)
                    {
                        return 1;
                    }
                    return 0;
                }
            });  
            Integer location = 0;
            for(Integer t : temps.keySet())
            {
                Intervals.add(this.temps.get(t));
            }
            for(TempInfo i : Intervals)
            {
                while(!Active.isEmpty())
                {
                    ArrayList<TempInfo> active = new ArrayList<TempInfo>(Active);
                    Active.clear();
                    TempInfo first = active.get(0);
                    if(first.end >= i.start)
                    {
                        Active.addAll(active);
                        break;
                    }
                    Registers.add(TempRegMap.get(first));
                    active.remove(0);
                    Active.addAll(active);
                }
                if(Active.size()>=18)
                {
                    TempInfo spill = Active.last();
                    if(spill.end > i.end)
                    {
                        String reg = TempRegMap.get(spill);
                        TempRegMap.put(i, reg);
                        TempStackMap.put(spill, location);
                        Active.remove(spill);
                        Active.add(i);
                    }
                    else
                    {
                        TempStackMap.put(i, location);
                    }
                    location++;
                }
                else
                {
                    String reg = Registers.first();
                    TempRegMap.put(i, reg);
                    Registers.remove(reg);
                    Active.add(i);
                }
            }
            sp = TempStackMap.size();
            if(numArgs>4)
            {
                sp += numArgs-4;
            }        
            StackSpace = sp;
            if(!name.equals("MAIN"))
            {
                StackSpace += 8;
            }
            if(!leaf)
            {
                StackSpace += 10;
            }
        }            
    }   
    HashMap<String, FunctionInfo> SymbolTable = new HashMap<String, FunctionInfo>();
    public void printAllFunctions()
    {
        for(FunctionInfo elem : SymbolTable.values())
        {
            elem.printFunc();
        }
    }
    public void processAllFunctions()
    {
        for(FunctionInfo elem : SymbolTable.values())
        {
            elem.manageJumps();
            elem.setInOut();
            elem.LivenessAnalysis();
            elem.LinearScan();
        }
    }
}
