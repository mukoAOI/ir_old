import java.io.File;
import java.io.IOException;
import java.util.*;
public class tfidf {
    int docnum;
    HashMap<String,Integer> tf;
    HashMap<String,Integer>df;
    private static List<index> index;

    public tfidf(HashMap<String,rankhelp>in){
        tf=new HashMap<>();
        List<File> lis=new LinkedList<>();
        BasicOperation.getAllFile(new File(BasicOperation.getRunPath()+"/data"),lis);
        docnum=lis.size();
        df=new HashMap<>();
        index=new ArrayList<>();
        application.getIndex(index);
        for (String word:in.keySet()){
            int sum=0;
            int sum2=0;
            for (String filename:in.get(word).getTf().keySet()){
                sum+=in.get(word).getTf().get(filename);
                for (String file:in.get(word).getTf().keySet()){
                    sum2+=in.get(word).getTf().get(file);
                }
            }
            df.put(word,sum);
            tf.put(word,sum2);
        }

    }

    public static void orderSetBytfidf(List<String> ot , HashMap<String,Double> refernce){
        ot.addAll(refernce.keySet());
        Collections.sort(ot, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return refernce.get(o2).compareTo(refernce.get(o1));
            }
        });
    }

    public static void orderBytf(Set<String> out , HashMap<String,Integer> refernce){
        Set<String> sortSet = new TreeSet<String>((o1, o2) -> refernce.get(o2)>=refernce.get(o1)?1:0);
        sortSet.addAll(out);
        out=sortSet;
    }

    public void querybytfidf(List<String> out,String query,HashMap<String,rankhelp>in){

        HashMap<String,Double> tfdf=new HashMap<>();
        List<String> order=new ArrayList<>();
         for (String he:query.strip().split(" ")){
            if (he==null || he=="")
                continue;
            else {
                if (tf.get(he)==null || df.get(he)==null)
                    tfdf.put(he,0d);
                else
                    tfdf.put(he,(Math.log10(tf.get(he))+1)*Math.log10(docnum*1.0/df.get(he)));
            }
        }
        orderSetBytfidf(order ,tfdf);
        for (String i : order){
            HashSet <String> exm=new HashSet<>();
            exm=application.findFile(index,i);
            if (exm==null || exm.size()==0)
                continue;

            orderBytf(exm,in.get(i).getTf());
            for (String aa: exm){
                if (out==null||out.contains(aa))
                    continue;
                else
                    out.add(aa);
            }
        }

    }





    public static void orderBypage(Set<String> out , HashMap<String,Double> refernce){
        Set<String> sortSet = new TreeSet<String>((o1, o2) -> refernce.get(o2)>=refernce.get(o1)?1:0);
        sortSet.addAll(out);
        out=sortSet;
    }

    public void querybypagerank(List<String> out,String query,HashMap<String,rankhelp>in){
        List<String>ou=new LinkedList<>();
        HashMap<String,String> dict= (HashMap<String, String>) dataOperation.getDict2(dataOperation.getDict1(ou));
        HashMap<Integer,Double>pagerankod=pagerank.out_pagerank();
        HashMap<String ,Double> rankrenf=new HashMap<>();
        for (int i:pagerankod.keySet()){
            rankrenf.put(dict.get(Integer.toString(i)),pagerankod.get(i));
        }
        HashMap<String,Double> tfdf=new HashMap<>();
        List<String> order=new ArrayList<>();
        for (String he:query.strip().split(" ")){
            //System.out.println(he);
            if (he==null || he=="")
                continue;
            else {
                if (tf.get(he)==null || df.get(he)==null)
                    tfdf.put(he,0d);
                else
                    tfdf.put(he,(Math.log10(tf.get(he))+1)*Math.log10(docnum*1.0/df.get(he)));
            }
        }
        //System.out.println(tfdf);
        orderSetBytfidf(order ,tfdf);
        //System.out.println(order);
        for (String i : order){
            HashSet <String> exm;
            exm=application.findFile(index,i);
            if (exm==null || exm.size()==0)
                continue;

            orderBypage(exm,rankrenf);
            for (String aa: exm){
                //System.out.println(i+ aa);
                if (out==null||out.contains(aa))
                    continue;
                else
                    out.add(aa);
            }

        }
        for (String i : order){
            if (i==null)
                continue;
            //System.out.println(i);
            try {
               // System.out.println(tyc.tongyici(i));
                List<String> aaa=tyc.tongyici(i);
                if (aaa==null)
                    return;
                for (String j: aaa){
                    HashSet <String> exm;
                    if (j==null)
                        continue;
                    exm=application.findFile(index,j);
                    if (exm==null || exm.size()==0)
                        continue;
                    for (String aa: exm){
                        //System.out.println(i+ aa);
                        if (out==null||out.contains(aa))
                            continue;
                        else
                            out.add(aa);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
