
import com.groupdocs.search.Index;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
        public static void main(String [] agvs) {
            application.createIndex();
            BoolStack as = new BoolStack();
            List<File> out=new ArrayList<>();
            BasicOperation.getAllFile(new File(BasicOperation.getRunPath() + "/data"), out);
            HashMap<String,rankhelp> outrank=new HashMap<>();
            application.getRank(outrank);
            List<String> file=new ArrayList<>();
            tfidf he=new tfidf(outrank);
            String query=" BLCU renamed another";
            he.querybypagerank(file,query,outrank);
            summary.summaryall(file,query);
        }
}