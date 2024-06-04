import java.io.*;
import java.util.*;

public class application {
    public static void getAllFile(Set<String> out){
        List<File> dre=new LinkedList<>();
        BasicOperation.getAllFile(new File(BasicOperation.getRunPath()+"/data"),dre);
        for (File fd:dre){
            out.add(fd.toString());
        }
    }
    public static HashSet<String>forceSearch(String wordf) {
        File as = new File(BasicOperation.getRunPath() + "/data");
        List<File> alldatas = new LinkedList<>();
        HashSet<String> out=new HashSet<>();
        BasicOperation.getAllFile(as, alldatas);
        for (File filename : alldatas) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> Word = new LinkedList<>();
                    dataOperation.WordSplit(line, Word);
                    for (String word : Word) {
                        if (word.equals(wordf)){
                            out.add(filename.toString());
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return out;
    }
    public static void createIndex() {
        File as = new File(BasicOperation.getRunPath() + "/data");
        List<File> alldatas = new LinkedList<>();
        BasicOperation.getAllFile(as, alldatas);
        HashMap<String, index> dex = new HashMap<>();
        HashMap<String,rankhelp> tf=new HashMap<>();
        rankhelp help;
        index exm;
        for (File filename : alldatas) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    List<String> Word = new LinkedList<>();
                    dataOperation.WordSplit(line, Word);
                    for (String word : Word) {
                        if ((tf.get(word))!=null){
                            help=tf.get(word);
                            help.setTf(filename.toString());
                            tf.put(word,help);
                        }else{
                            help=new rankhelp();
                            help.getTf().put(filename.toString(),1);
                            tf.put(word,help);
                        }
                        if ((exm = dex.get(word)) != null) {
                            exm.setFreq();
                            exm.setDocIDAppend(filename.toString());
                        } else {
                            exm = new index();
                            exm.setDocIDAppend(filename.toString());
                            dex.put(word, exm);
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            //dataOperation.showAllPath(dex);
            dataOperation.writeIndex(dex);
            dataOperation.writetf(tf);

        }
    }

    public static void getIndex(List<index> out){
        List<List<String>> index=new LinkedList<>();
        BasicOperation.OutFile(BasicOperation.getRunPath()+"/out.txt",index);
        List<String>ou=new LinkedList<>();
        HashMap<String,String> dict= (HashMap<String, String>) dataOperation.getDict2(dataOperation.getDict1(ou));

        for (List<String> inn :index){
            //System.out.println(inn);
            index jin=new index();
            jin.setWord(inn.get(0));
            for (String docid:inn.subList(1,inn.size())){
                jin.setDocIDAppend(dict.get(docid));
            }
            out.add(jin);
        }
    }

    public static void getRank(HashMap<String,rankhelp> outrank){
        List<List<String>> index=new LinkedList<>();
        BasicOperation.OutRank(BasicOperation.getRunPath()+"/rank.txt",index);
        for (List<String> inn :index){
            rankhelp jin=new rankhelp();
            //System.out.println(inn);
            for (String docid:inn.subList(1,inn.size())) {
                //System.out.println(docid);
                if (docid==null)
                    continue;

                //System.out.println(docid.substring(docid.indexOf("&")+1 ));
                jin.getTf().put(docid.substring(0, docid.indexOf("&")), Integer.parseInt(docid.substring(docid.indexOf("&")+1 )));
            }
            outrank.put(inn.get(0),jin);
            }
        }

    public static HashSet<String> findFile(List<index> index, String find){
        for (index inlist:index){
            if (inlist.getWord()!=null&&inlist.getWord().equals(find)){
                return (HashSet<String>) inlist.getDocID();
            }
        }
        return new HashSet<String>();
    }




}
