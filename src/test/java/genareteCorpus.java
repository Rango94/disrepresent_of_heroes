import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class genareteCorpus {
    public static void main(String[] args){
        genareteCorpus();
    }
    public void genareteCorpus(String path,int window,int length,boolean chaos){
        Double[][] cor=new Double[114][114];
        try {
            FileReader corpus=new FileReader(path);
            BufferedReader R=new BufferedReader(corpus);
            for(int i=0;i<114;i++){
                int count=0;
                for(String e:R.readLine().split(" ")){
                    cor[i][count]=Double.parseDouble(e);
                    count++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        List<List<String[]>> output=new ArrayList<List<String[]>>();
        for(int i=0;i<114;i++){
            String[] tmpline=new String[window*2+1];
            for(int j=0;j<length;j++){
                tmpline[window]=Integer.toString(i+1);


            }
        }


    }

    String[] positive(Double[] line,int window){
        HashMap<String,Double> pos=new HashMap<String, Double>();
        for(int i=0;i<line.length;i++){
            if(line[i]>0){
                pos.put(Integer.toString(i+1),line[i]);
            }
        }
    }




}
