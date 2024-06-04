
import java.util.*;

public class index {
    String word;
        int freq;
        Set<String> DocID;
        HashMap<String,Integer> tf;
        public index(){
            word="";
         freq=1;
         DocID = new HashSet<>();
        }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFreq() {
        return freq;
    }

    public Set<String> getDocID() {
            return DocID;
        }

    public void setFreq() {
        freq++;
    }
    public void setDocIDAppend( String ID) {
        DocID .add(ID);
    }

    public void setDocID(Set<String> docID) {
        DocID = docID;
    }

    public String getWord() {
        return word;
    }

    public int  compareTo(index a) {
        if (this.getFreq() >= a.getFreq())
            return 1;
        else
            return 0;
    }
    public static void showPath(Set<String> exm){
        for (String sd:exm){
            System.out.print(sd+"\t");
        }
        System.out.println(" ");
    }
}


