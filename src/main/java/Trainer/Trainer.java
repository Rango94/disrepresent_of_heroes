package Trainer;

import Huffman.Huffman;
import Modelhandler.Model;
import Modelhandler.*;
import myFile.corpusReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trainer {
    static Huffman hm1;
    static Huffman hm2;
    corpusReader cr1;
    corpusReader cr2;
    Model md;
    int windos;
    boolean weatherfill;
    double step=0.025;
    double Step=step;
    int maxloop=10000;
    HashMap<String,Integer> dictionary;

    public Trainer(Model md,int windos,boolean weatherfill,double step){
        this.weatherfill=weatherfill;
        this.windos=windos;
        this.md=md;
        this.step=step;
        this.Step=step;
        this.hm1=md.hm1;
        this.hm2=md.hm2;
        this.dictionary=hm1.dictionary;
        cr1=new corpusReader(md.PATH1,windos,weatherfill,md.hm1.lowwords);
        cr2=new corpusReader(md.PATH2,windos,weatherfill,md.hm2.lowwords);
    }

    public Model train(){
        double k=0.1;
        for(int i=0;i<maxloop;i++){
            if(i%2==0) {
                trainline(hm1,cr1);
            }else{
                trainline(hm2,cr2);
            }
            if(i%20==0) {
                step=Step*(1-(double)i/maxloop);
                System.out.println(md.getVector("mother"));
                System.out.println(md.getVector("father"));
                System.out.println(md.dis("mother","father"));
                System.out.println(md.getVector("king"));
                System.out.println(md.getVector("queen"));
                System.out.println(md.dis("king","queen"));
                System.out.println(md.dis("mother","queen"));
                System.out.println("training has completed "+Double.toString((double)i/maxloop*100)+"%");
                System.out.println("---------------------------------");
            }
            if((double)i/maxloop>k){
                k+=0.1;
                md.Savemodel("E:\\myw2v\\first.model");
            }
            if(i%5==0){
//                System.out.println(hm.getVectorofnotleafbyHuffman("-1"));
//                System.out.println(hm.getVectorofnotleafbyHuffman("111111"));
//                System.out.println(hm.getVectorofnotleafbyHuffman("1111111"));
//                System.out.println(hm.getVectorofnotleafbyHuffman("11111111"));
//                System.out.println(hm.getVectorofnotleafbyHuffman("111111111"));
//                System.out.println(hm.getVectorofnotleafbyHuffman("1111111111"));
//                System.out.println("----------------------------");
            }
        }
        return md;
    }
//  根据一行语料训练
    public void trainline(Huffman hm,corpusReader cr){
        HashMap<List<String>, Vector> corpusline = cr.handlesent(md);
        if (corpusline!=null) {
            for (List<String> e : corpusline.keySet()) {
                int termcont=dictionary.get(e.get(windos));
                double t=(double)termcont/hm.totalnum;
                Vector inputvector = corpusline.get(e);
                byte[] Huffmanofterm = hm.getHuffmancode(e.get(windos));
                if(Huffmanofterm==null){
                    System.out.println(e.get(windos));
                }
                if(Huffmanofterm!=null) {
                    List<String> pathlist = generatepath(Huffmanofterm);
                    Vector addofinput = new Vector(inputvector.getSize(), 0);
                    int path=0;
                    for (String subpath : pathlist) {
                        Vector pathvector = hm.getVectorofnotleafbyHuffman(subpath);

                        double q = active(Vector.mult(pathvector, inputvector));

                        double g = step * (1 - Huffmanofterm[path] - q);
                        path++;

                        addofinput = Vector.adds(addofinput, Vector.mult(g, pathvector));

                        hm.setVectorofnotleafbyHuffman(subpath, Vector.adds(Vector.mult(g, inputvector), pathvector));
                        Vector tmp=Vector.mult(g, inputvector);
                        for(int i=0;i<pathvector.getSize();i++) {
                            pathvector.vector[i]=pathvector.vector[i]+tmp.vector[i];
                        }
                    }
                    for (int i = 0; i < e.size(); i++) {
                        if (i != windos) {
                            try {
                                Vector tmp = md.getVector(e.get(i));
                                if (tmp != null) {
//                                    md.setVector(e.get(i), Vector.adds(addofinput, tmp));
                                    for(int k=0;k<tmp.getSize();k++){
                                        tmp.vector[k]=tmp.vector[k]+addofinput.vector[k];
                                    }
                                }else{
                                    System.out.println("发现单词"+e.get(i)+"没有对应向量");
                                }
                            } catch (NullPointerException E) {
                                E.fillInStackTrace();
                            }
                        }
                    }
                }
            }
        }
        else{
            System.out.println("语料库为空");
        }
    }

    private double active(double x){
        return 1/(1+Math.pow(Math.E,-x));
    }
//    生成路径序列以便训练
    private List<String> generatepath(byte[] path){
        List<String> pathlist=new ArrayList<String>();
        pathlist.add("-1");
        String tmp="";
        for(int i=0;i<path.length-1;i++){
            tmp+=Byte.toString(path[i]);
            pathlist.add(tmp);
        }
        return pathlist;
    }
}
