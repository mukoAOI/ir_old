import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.*;
import java.util.List;


public class summary {

    public static String generateSummary(String text, String queryText) throws Exception {
        Directory directory = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Document doc = new Document();
        doc.add(new TextField("content", text, Field.Store.YES));

        indexWriter.addDocument(doc);
        indexWriter.close();
        QueryParser parser = new QueryParser("content", new StandardAnalyzer());
        Query query = parser.parse(queryText);
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(directory));
        searcher.setSimilarity(new BM25Similarity());
        TopDocs topDocs = searcher.search(query, 1);
        ScoreDoc[] hits = topDocs.scoreDocs;
        if (hits.length > 0) {
            int docId = hits[0].doc;
            Document document = searcher.doc(docId);
            String content = document.get("content");
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
            Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
            TokenStream tokenStream = TokenSources.getTokenStream("content", content, new StandardAnalyzer());
            String summary = highlighter.getBestFragments(tokenStream, content, 3, "...");
            return summary;
        }

        return "";
    }

    public static void summarysingle(String text,String query ) {
        try {
            String summary = generateSummary(text, query);
            System.out.println("摘要：" + summary.strip());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String readfile(String file){
        String out="";
        try {
            BufferedReader reader=new BufferedReader(new FileReader(file));

            String line;
            while ((line=reader.readLine())!=null){
                out+=line.strip();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    public static void summaryall(List<String> filepath, String query){
        for (String name: filepath){
            String text=readfile(name);
            System.out.println(name);
            summarysingle(text,query);
        }
    }

}
