import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.IStemmer;

public class tyc {
    private static String WORDNET_PATH = "D:\\wordnet\\dict";

    public static List<String> tongyici(String queryword) throws IOException{
        File wnDir=new File(WORDNET_PATH);
        URL url=new URL("file", null, WORDNET_PATH);
        IDictionary dict=new Dictionary(url);
        dict.open();//打开词典
        ISynset oo=getSynonyms(dict,queryword);
        List<String> out= new LinkedList<>();
        if (oo==null)
            return null;
        for (IWord simword: oo.getWords()){
            out.add(simword.getLemma());
        }
        return out;
    }

    public static ISynset getSynonyms(IDictionary dict,String queryword){
        // look up first sense of the word "go"
        //IIndexWord idxWord2 = dict.


        IIndexWord idxWord =dict.getIndexWord(queryword, POS.VERB);
        if (idxWord==null)
            return null;
        IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset (); //ISynset是一个词的同义词集的接口

        // iterate over words associated with the synset
        //for(IWord w : synset.getWords())
            //System.out.println(w.getLemma());//打印同义词集中的每个同义词
        return synset;
    }

}
