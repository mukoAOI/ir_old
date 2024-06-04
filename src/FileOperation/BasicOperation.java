import  java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BasicOperation {
    public static String getRunPath(){
        return System.getProperty("user.dir");
    }
    public static void getAllFile(File FileName, List<File> allFileList) {
        File[] fileList = FileName.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                getAllFile(file, allFileList);
            } else {
                allFileList.add(file);
            }
        }
    }

    public static void OutRank(String FileName, List<List<String>> outlist) {
        List<String>ou=new LinkedList<>();
        HashMap<String,String> dict= (HashMap<String, String>) dataOperation.getDict2(dataOperation.getDict1(ou));
        try {
            BufferedReader reader1=new BufferedReader(new FileReader(FileName));
            String Line;
            List <String> li=new LinkedList<>();
            while ((Line=reader1.readLine())!=null){
                Line=Line.strip();
                li=new LinkedList<>();
                for (String word:Line.split("%"))
                {
                    if (word==null||word=="")
                        continue;
                    else {

                        li.add(word);
                    }
                }
                outlist.add(li);
            }
            reader1.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void OutFile(String FileName, List<List<String>> outlist) {
        List<String>ou=new LinkedList<>();
        HashMap<String,String> dict= (HashMap<String, String>) dataOperation.getDict2(dataOperation.getDict1(ou));
        try {
            BufferedReader reader1=new BufferedReader(new FileReader(FileName));
            String Line;
            List <String> li=new LinkedList<>();

            while ((Line=reader1.readLine())!=null){
                Line=Line.strip();
                li=new LinkedList<>();
                //System.out.print(Line);
                for (String word:Line.split("%"))
                {


                    if (word==null||word=="")
                        continue;
                    else {

                        li.add(word);
                    }
                }
                outlist.add(li);
            }
            reader1.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Infile(String FileName,String value) {
        try{
            FileWriter writer =new FileWriter(FileName,false);
            writer.write(value);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void InFileAppend(String fileName, String value) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
