package old;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class generateheroessentence {
    public generateheroessentence(String filename,int wide,int length) {
        Readfile rf=new Readfile();
        Double[][] cube=rf.readfile(filename);
        HashMap<Integer,HashMap<Integer,Integer>> po_corpus=getherocorpus(cube,1);
        HashMap<Integer,HashMap<Integer,Integer>> ne_corpus=getherocorpus(cube,-1);
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(filename.substring(0, filename.length() - 4)+"corpus.txt",false));
            for (int h=1;h<115;h++) {
                if(h==24)
                    continue;
                for (int i = 0; i < length; i++) {
                    int ex=h;
                    for (int j = 0; j < wide; j++) {
                        wr.write(ex+" ");
                        ex = po_corpus.get(ex).get((int) (Math.random() * po_corpus.get(ex).size()));
                        while(ex==24) {
                            ex = po_corpus.get(ex).get((int) (Math.random() * po_corpus.get(ex).size()));
                        }
                    }
                    wr.write("\n");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public Double[] positive(Double[] arg){
        List<Double> po=new ArrayList<Double>();
        for(Double each:arg){
            if(each>=0){
                po.add(each);
            }
        }
        return po.toArray(new Double[po.size()]);
    }
    public Double[] negative(Double[] arg){
        List<Double> ne=new ArrayList<Double>();
        for(Double each:arg){
            if(each<0){
                ne.add(each);
            }
        }
        return ne.toArray(new Double[ne.size()]);
    }
    public Double max(Double[] arg){
        Double max=arg[0];
        for(double each:arg){
            if(max<each){
                max=each;
            }
        }
        return max;
    }
    public Double min(Double[] arg){
        Double min=arg[0];
        for (double each:arg){
            if(min>each){
                min=each;
            }
        }
        return min;
    }
    public Double sum(Double[] arg){
        Double out=0.;
        for(Double each:arg){
            out=out+each;
        }
        return out;
    }
    public HashMap<Integer,HashMap<Integer,Integer>> getherocorpus(Double cube[][],int flag) {
        HashMap<Integer, HashMap<Integer, Integer>> herocorpus = new HashMap<Integer, HashMap<Integer, Integer>>();
        for (int i = 0; i < 114; i++) {
            Double po_sum = sum(positive(cube[i]));
            Double ne_sum = sum(negative(cube[i]));
            Integer N = 0;
            HashMap<Integer, Integer> eachhero = new HashMap<Integer, Integer>();
            for (int j = 0; j < 114; j++) {
                if (flag == 1) {
                    if (cube[i][j] >= 0) {
                        int num = (int) (20000 * (cube[i][j] / po_sum));
                        for (int n = 0; n < num; n++) {
                            eachhero.put(N, j+1);
                            N++;
                        }
                    }
                } else {
                    if (cube[i][j] < 0) {
                        int num = (int) (20000 * (cube[i][j] / ne_sum));
                        for (int n = 0; n < num; n++) {
                            eachhero.put(N, j+1);
                            N++;
                        }
                    }
                }

            }
            herocorpus.put(i+1,eachhero);
        }
//        System.out.print(herocorpus.toString());
        return herocorpus;
    }
    }
