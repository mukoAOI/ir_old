import spark.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HttpServer {
    public static void main(String[] args) {
        Spark.port(8080);
        BoolStack as = new BoolStack();
        String path="public";
        Spark.staticFileLocation(path);
        Spark.get("/", (request, response) -> {
            File index=new File(path+"/index.html");
            BufferedReader br = new BufferedReader(new FileReader(index));
            String line;
            String out="";
            while ((line=br.readLine())!=null) {
                out+=line;
            }
            br.close();
            return out;
        });
        Spark.post("/query", (request, response) -> {
            String number = request.queryParams("num");
            String text = request.queryParams("query");
            if (number == null || !number.matches("1|2") || text == null || text.isEmpty()) {
                return errorPage("请填写正确的数字和字符串。");
            }
            String result = query(as ,number, text);
            if (result == null) {
                return errorPage("查询出错或没有结果。");
            }
            return successPage(result);
        });
    }
    private static String query(BoolStack as,String number, String text) {
        return String.join ("|||||||||",as.query(text,Integer.parseInt(number)));
    }
    private static String errorPage(String errorMessage) {
        return "<html><body><h1>错误</h1><p>" + errorMessage + "</p></body></html>";
    }
    private static String
    successPage(String result) {
        return "<html><body> <link rel=\"stylesheet\" type=\"text/css\" href=\"pic/style.css\"><h1>查询结果</h1><p>" + result + "</p></body></html>";
    }
}