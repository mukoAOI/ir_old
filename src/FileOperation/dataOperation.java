import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dataOperation {
     public static void WordSplit(String line ,List<String> outword)
     {
         line =line.strip();
         Matcher m = Pattern.compile("([A-Za-z]+[-\\']*[A-Za-z]*|(\\d+,\\d+)|\\d+|[\\u4e00-\\u9fa5]+)").matcher(line);
         while (m.find()){
             outword.add(m.group());
         }
     }
    public static void showPath(Set<String> exm){
        for (String sd:exm){
            System.out.print(sd+"\t");
        }
        System.out.println(" ");
    }
    public static  HashMap<String,String>getDict1(List <String> ou){
        File as=new File(BasicOperation.getRunPath()+"/data");
        List<File> alldatas=new LinkedList<>();
        BasicOperation.getAllFile(as,alldatas);
        HashMap<String,String>out=new HashMap<>();
        int num=0;
        for (File file:alldatas){
            out.put(file.toString(),Integer.toString(num));
            ou.add(file.toString()+"&"+num);
            num++;
        }
        return out;
    }
    public static Map<String,String>getDict2(Map<String,String> in){
         Map<String,String> out=new HashMap<>();
         for (String key:in.keySet()){
             out.put(in.get(key),key);
         }
         return out;
    }

    public static void showAllPath(HashMap<String, index> exm){
        Set<String> li;
        for (String word :exm.keySet()){
            li= exm.get(word).getDocID();
            System.out.print(word+"\t"+exm.get(word).getFreq()+"\t");
            showPath(exm.get(word).getDocID());
        }
    }

    public static  void writetf(HashMap<String,rankhelp> rank){
        String path=BasicOperation.getRunPath();
        BasicOperation.Infile(path+"rank.txt","");
        for (String key:rank.keySet()){
            rankhelp help=rank.get(key);
            String in=key+"%";
            for (String filename: help.getTf().keySet()){
                in+=filename+"&"+help.getTf().get(filename)+"%";
            }
            BasicOperation.InFileAppend(path+"/rank.txt",in+"\n");
        }


    }
    public static void writeIndex(Map<String, index> in){
        UserComparator comparator = new UserComparator(in);
        Map<String, index> out=new TreeMap<>(comparator);
        String path=BasicOperation.getRunPath();
        BasicOperation.Infile(path+"/out.txt","");
        List <String> ou=new LinkedList<>();
        Map<String ,String> checks=getDict1(ou);
        out.putAll(in);
        out.forEach((k, v) -> {
            String inline = k + "%";
            for (String id : ou) {
                //inline+=id+"%";
                if (v.getDocID().contains(id.substring(0,id.indexOf("&"))))
                    inline+=id.substring(id.indexOf("&")+1)+"%";
            }
            BasicOperation.InFileAppend(path+"/out.txt",inline+"\n");
        }
        );
    }
}