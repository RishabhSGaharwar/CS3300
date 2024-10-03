package visitor;
import java.util.*;

public class External {
    public HashMap<String, String> inheritance  = new HashMap<String, String>();
    public class Var
    {
        public String VarID;
        public String retType;
        public String parentClass;
        public Var(String Name, String Type)
        {
            this.retType = Type;
            this.VarID = Name;
        }
        public String getID()
        {
            return this.VarID;
        }
        public String getType()
        {
            return this.retType;
        }
        public void PrintVar()
        {
            System.out.print("Type : " + retType);
            System.out.println(" ID : " + VarID);
        }
    }
    public class Method
    {
        public String MethodID;
        public String retType;
        public String parentClass;
        public HashMap<String, Var> variables = new HashMap<String, Var>();
        public ArrayList<Var> parameters = new ArrayList<Var>();

        public Method(String Name, String Type)
        {
            this.retType = Type;
            this.MethodID = Name;
        }

        public String getID()
        {
            return this.MethodID;
        }
        public String getType()
        {
            return this.retType;
        }
        public String getParentClass()
        {
            return this.parentClass;
        }
        public HashMap<String, Var> getAllVariables()
        {
            return this.variables;
        }
        public Var getVariable(String Name)
        {
            return this.variables.get(Name);
        }
        public ArrayList<Var> getAllParameters()
        {
            return this.parameters;
        }
        public Var getnParameter(int n)
        {
            if(n>=0 && n<this.parameters.size())
            {
            return parameters.get(n);
            }
            return null;
        }
        public void addParameter(Var x)
        {
            this.parameters.add(x);
        }
        public void addVariable(String Name, Var x)
        {
            this.variables.put(Name, x);
        }
        public void PrintMethod()
        {
            ArrayList<Var> thisParams = new ArrayList<Var> (parameters);
            ArrayList<Var> thisVars = new ArrayList<Var> (variables.values());
            System.out.println("Method : " + MethodID + " with return type : " + retType + " in Class : " + parentClass);
            System.out.println("Parameters: ");
            for(int i=0; i<thisParams.size(); i++)
            {
                thisParams.get(i).PrintVar();
            }
            System.out.println("Parameter Printing Done");
            System.out.println("Variables: ");
            for(int i=0; i<thisVars.size(); i++)
            {
                thisVars.get(i).PrintVar();
            }
            System.out.println("Variables Printing Done");
        }
    }
    public class ClassDetail
    {
        public String ClassID;
        public ArrayList<Method> methods = new ArrayList<Method>();
        public ArrayList<Var> fields = new ArrayList<Var>();
        
        public ClassDetail(String Name)
        {
            this.ClassID = Name;
        }
        public String getID()
        {
            return this.ClassID;
        }
        public ArrayList<Var> getAllFields()
        {
            return this.fields;
        } 
        public ArrayList<Method> getAllMethod()
        {
            return this.methods;
        }
        public Method getMethod(String ID) //Returns first occurence of method
        {
            for(int i=0; i< this.methods.size(); i++)
            {
                if(this.methods.get(i).getID() == ID)
                {
                    return this.methods.get(i);
                }
            }
            return null;
        }
        public Var getField(String Name) //Returns first occurence of field
        {
            for(int i=0; i< this.fields.size(); i++)
            {
                if(this.fields.get(i).getID() == Name)
                {
                    return this.fields.get(i);
                }
            }
            return null;
        }
        public void addField(String Name, Var x)
        {
            this.fields.add(x);
        }
        public void addMethod(String Name, Method x)
        {
            for(int i=0; i<this.methods.size(); i++)
            {
                if(this.methods.get(i).getID() == Name)
                {
                    this.methods.remove(i);
                    this.methods.add(i, x);
                    return;
                }
            }
            this.methods.add(x);
        }
        //CopyClass method
        public void CopyClass(ClassDetail parent)
        {
            for(int i=0; i<parent.getAllFields().size(); i++)
            {
                this.fields.add(parent.getAllFields().get(i));
            }
            for(int i=0; i<parent.getAllMethod().size(); i++)
            {
                Method temp = parent.getAllMethod().get(i);
                
                this.addMethod(temp.getID(), temp);
            }
        }
        public void PrintClass()
        {
            System.out.println("Class: " + ClassID);
            for(int i=0; i<methods.size(); i++)
            {
                methods.get(i).PrintMethod();
            }
            System.out.println("Fields: ");
            for(int i=0; i<fields.size(); i++)
            {
                fields.get(i).PrintVar();
            }
            System.out.println("Fields Printing Done");
            System.out.println();
        }
    }
    public class SymbolTable
    {
        public HashMap<String, ClassDetail> classes = new HashMap<String, ClassDetail>();
        public HashMap<String, String> inheritance = new HashMap<String, String>(); //Child, Parent pair
        public HashMap<String, ArrayList<String>> tree = new HashMap<String, ArrayList<String>>(); 

        public ClassDetail getClass(String Name)
        {
            if(this.classes.containsKey(Name))
            {
                return this.classes.get(Name);
            }
            return null;
        }
        public void addClass(String Name, ClassDetail x)
        {
            this.classes.put(Name, x);
        }
        public String getSuperClass(String DerivedClass)
        {
            if(this.inheritance.containsKey(DerivedClass))
            {
                return this.inheritance.get(DerivedClass);
            }
            return null;
        }
        public void addInheritanceRelation(String DerivedClass, String SuperClass)
        {
            this.inheritance.put(DerivedClass, SuperClass);
        }
        public boolean isAncestor(String AncestorClass, String DerivedClass)
        {
            if(this.inheritance.get(DerivedClass)==null)
            {
                return false;
            }
            if(this.inheritance.get(DerivedClass)==AncestorClass)
            {
                return true;
            }
            return isAncestor(AncestorClass, this.inheritance.get(DerivedClass));
        }
        public void addEdge(String AncestorClass, String DerivedClass)
        {
            if(!this.tree.containsKey(AncestorClass))
            {
                this.tree.put(AncestorClass, new ArrayList<String>());   
            }
            this.tree.get(AncestorClass).add(DerivedClass);
        }
    }
    public class ObjectStructure
    {
        public String ObjectStructureID;
        public ArrayList<Method> methods;
        public ArrayList<Var> fields;
        public ObjectStructure(String Name)
        {
            ObjectStructureID = Name;
        }
        public String getID()
        {
            return this.ObjectStructureID;
        }
        public ArrayList<Var> getAllFields()
        {
            return this.fields;
        } 
        public ArrayList<Method> getAllMethod()
        {
            return this.methods;
        }
        public void AssignField(ArrayList<Var> inFields)
        {
            this.fields = new ArrayList<Var>(inFields);
        }
        public void AssignMethods(ArrayList<Method> inMethods)
        {
            this.methods = new ArrayList<Method>(inMethods);
        }
    }
    public class VarTypeDeclaration
    {
        public String VarID;
        public String retType;
        public String ScopeClass;
        public String DerivedClass;
        public String ScopeMethod;
        public VarTypeDeclaration(String Name, String Type, String SClass, String SMethod, String DervClass)
        {
            VarID = Name;
            retType = Type;
            ScopeClass = SClass;
            ScopeMethod = SMethod;
            DerivedClass = DervClass;
        }
        public VarTypeDeclaration(String Name, String Type, String SClass, String SMethod)
        {
            VarID = Name;
            retType = Type;
            ScopeClass = SClass;
            ScopeMethod = SMethod;
            DerivedClass = null;
        }
        public VarTypeDeclaration(String Name, String Type, String SClass)
        {
            VarID = Name;
            retType = Type;
            ScopeClass = SClass;
            ScopeMethod = null;
            DerivedClass = null;
        }
    }
    SymbolTable ST = new SymbolTable();
    HashMap<String, ObjectStructure> ClassToObjectStructure = new HashMap<String, ObjectStructure>();
    void DFS(String Source)
    {
        if(!ST.tree.containsKey(Source))
        {
            return;
        }
        for(int i=0; i<ST.tree.get(Source).size(); i++)
        {
            ClassDetail temp = ST.classes.get(ST.tree.get(Source).get(i));
            ClassDetail newClass = new ClassDetail(temp.getID());
            newClass.CopyClass(ST.classes.get(Source));
            newClass.CopyClass(temp);
            ST.classes.remove(temp.getID());
            ST.classes.put(temp.getID(), newClass);
            DFS(newClass.getID());
        }
    }
    void DoAllDFS()
    {
        ArrayList<String> allRoots = new ArrayList<String>(ST.classes.keySet());
        ArrayList<String> removalList = new ArrayList<String>();
        for(int i=0; i<allRoots.size(); i++)
        {
            if(ST.inheritance.containsKey(allRoots.get(i)))
            {
                removalList.add(allRoots.get(i));
            }
        }
        allRoots.removeAll(removalList);
        for(int i=0; i<allRoots.size(); i++)
        {
            DFS(allRoots.get(i));
        }
    }
    void PrintAllClass()
    {
        ArrayList<ClassDetail> allClasses = new ArrayList<ClassDetail>(ST.classes.values());
        for(int i=0; i<allClasses.size(); i++)
        {
            allClasses.get(i).PrintClass();
        }
        System.out.println("CLASS PRINTING TERMINATED");
    }
    void fillMap()
    {
        ArrayList<ClassDetail> allClasses = new ArrayList<ClassDetail>(ST.classes.values());
        for(int i=0; i<allClasses.size(); i++)
        {
            ObjectStructure thisObjectStructure = new ObjectStructure(allClasses.get(i).ClassID);
            thisObjectStructure.AssignField(allClasses.get(i).getAllFields());
            thisObjectStructure.AssignMethods(allClasses.get(i).getAllMethod());
            ClassToObjectStructure.put(allClasses.get(i).ClassID, thisObjectStructure);
        }
    }
}
