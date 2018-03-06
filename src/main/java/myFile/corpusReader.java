package myFile;

import Modelhandler.Model;
import Modelhandler.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class corpusReader {

    public BufferedReader br;
    int windos;
    boolean weatherfill;
    String Path;
    public corpusReader(String path,int windos,boolean weatherfill, List<String> lowwords){
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.Path=path;
        this.weatherfill=weatherfill;
        this.windos=windos;
    }

    public corpusReader(){
    }


    public HashMap<List<String>,Vector> handlesent(Model mod){
        HashMap<List<String>,Vector> subcorpus=new HashMap<List<String>,Vector>();
        try {
            String line_str;
            String[] line=null;
            line_str=br.readLine();
            int num=0;
            while(line_str!=null&&num<200) {
                num++;
                line = line_str.split("\t");
                Vector input = new Vector(mod.getSize(), 0);
                int window = (line.length - 1) / 2;
                List<String> heroes=new ArrayList<String>();
                for (int i = 0; i < line.length; i++) {
                    heroes.add(line[i]);
                    if (i == window) {
                        continue;
                    }
                    Vector tmp = mod.getVector(line[i]);
                    if (tmp != null) {
                        input = Vector.adds(input, Vector.mult(1 / (Math.abs(i - window)), tmp));
                    } else {
                        System.out.println("发现单词" + line[i] + "没有对应向量");
                    }
                }
                subcorpus.put(heroes,input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subcorpus;
    }





    private List<String> substring(String[] ob,int start,int end){
        List<String> out=new ArrayList<String>();
        for(int i=0;i<end-start;i++){
            out.add(ob[i+start]);
//            System.out.print(ob[i+start]+"\t");
        }
//        System.out.print("\n");
        return out;
    }


}
