import java.util.*;

public class BoolStack {
    private Stack<String> symbol;
    private Stack<HashSet<String>> nowsetlist;
    private static HashSet<String> allset;
    private static List<index> index;
    public BoolStack()
    {
        allset=new HashSet<>();
        application.getAllFile(allset);
        symbol=new Stack<>();
        nowsetlist=new Stack<>();
        index=new ArrayList<>();
        application.getIndex(index);
    }
    public static HashSet<String> Bool_and(HashSet<String> s1,HashSet<String> s2){
        HashSet<String> s3 = new HashSet<>(s1);
         s3.retainAll(s2);
         return s3;
    }
    public static HashSet<String> Bool_or(HashSet<String> s1,HashSet<String> s2){
        HashSet<String> s3 = new HashSet<>(s1);
        s3.addAll(s2);
        return s3;
    }
    public static HashSet<String> Bool_not(HashSet<String> s1){
        HashSet<String> s2 = new HashSet<>(allset);
        s2.removeAll(s1);
        return s2;
    }

    public void Sentence(String query,List<String> outlist){
        String[] out=query.strip().split(" ");
        for (String spt:out){
            if (spt=="")
                continue;
            outlist.add(spt);
        }
    }
    public HashSet<String> query(String query,int modle){
        List<String> out=new ArrayList<>();
        Sentence(query,out);
        String help1;
        for (String qindex:out){
            if (qindex.equals("AND")||qindex.equals("OR")||qindex.equals("NOT")||qindex.equals("(")||qindex.equals(")")){
                if (qindex.equals(")")){
                    if(symbol.size()>0&symbol.peek().equals("(")) {
                        symbol.pop();
                    }
                    else {
                        System.out.println("括号不匹配请重新输入");
                        return null;
                    }
                }else{
                    symbol.push(qindex);
                }
            }
            else{
                HashSet<String> linshi;
                if (modle==1) {
                    linshi = application.findFile(index, qindex);
                }
                else{
                    linshi=application.forceSearch(qindex);
                }
                if (symbol.empty()){
                    nowsetlist.push(linshi);
                }
                else{
                    while (symbol.size()>0&&!(symbol.peek().equals("("))) {
                        if (symbol.peek().equals("NOT")) {
                            linshi = Bool_not(linshi);
                            symbol.pop();
                            continue;
                        }
                        if (symbol.peek().equals("AND")){
                            if(nowsetlist.size()==0){
                                System.out.println("查询语句错误");
                                return null;
                            }
                            linshi=Bool_and(linshi,nowsetlist.pop());
                            symbol.pop();
                            continue;
                        }
                        if (symbol.peek().equals("OR")){
                            if(nowsetlist.size()==0){
                                System.out.println("查询语句错误");
                                return null;
                            }
                            linshi=Bool_or(linshi,nowsetlist.pop());
                            symbol.pop();
                            continue;
                        }
                    }
                    nowsetlist.push(linshi);
                }
            }
        }
        HashSet<String> linshi = nowsetlist.pop();
        while (symbol.size()>0&&nowsetlist.size()>0) {
            if (symbol.peek().equals("(")) {
                System.out.println("括号不匹配请重新输入");
                return null;
            }
            if (symbol.peek().equals("NOT")) {
                linshi = Bool_not(linshi);
                symbol.pop();
                continue;
            }
            if (symbol.peek().equals("AND")){
                if(nowsetlist.size()==0){
                    System.out.println("查询语句错误");
                    return null;
                }
                linshi=Bool_and(linshi,nowsetlist.pop());
                symbol.pop();
                continue;
            }
            if (symbol.peek().equals("OR")){
                if(nowsetlist.size()==0){
                    System.out.println("查询语句错误");
                    return null;
                }
                linshi=Bool_or(linshi,nowsetlist.pop());
                symbol.pop();
                continue;
            }
        }
        if (symbol.size()!=0||nowsetlist.size()!=0){
            System.out.println("查询语句错误1");
            return null;
        }
        return linshi;
    }
}
