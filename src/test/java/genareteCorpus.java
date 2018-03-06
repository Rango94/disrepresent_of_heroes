import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class genareteCorpus {

    public static void main(String[] args){
        genareteCorpus("E:/disrepresent_of_heroes/anti.txt","E:/disrepresent_of_heroes/anticorpus.txt",3,10000,false);
        genareteCorpus("E:/disrepresent_of_heroes/comb.txt","E:/disrepresent_of_heroes/combcorpus.txt",3,10000,false);
    }
    public static void genareteCorpus(String inpath,String outpath,int window,int length,boolean chaos){
        double[][] cor=new double[114][114];
        try {
            FileReader corpus=new FileReader(inpath);
            BufferedReader R=new BufferedReader(corpus);
            for(int i=0;i<114;i++){
                int count=0;
                for(String e:R.readLine().split(" ")){
                    cor[i][count]=Double.parseDouble(e);
                    count++;
                }
            }
            R.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        List<List<String[]>> output=new ArrayList<List<String[]>>();
        for(int i=0;i<114;i++){
            if(i==23){
                continue;
            }
            System.out.println(i+1);
            List<String[]> suboutput=new ArrayList<String[]>();
            buildmap bm=new buildmap(cor[i],10000);
            for(int j=0;j<length;j++){
                String[] tmpline=new String[window*2+1];
                List<String> bm_pos=bm.pickpos(Integer.toString(i+1),3);
                List<String> bm_neg=bm.pickneg(Integer.toString(i+1),3);
                tmpline[window]=Integer.toString(i+1);
                for(int k=0;k<tmpline.length;k++){
                    if(k<window){
                        tmpline[k]=bm_pos.get(k);
                    }
                    if(k>window){
                        tmpline[k]=bm_neg.get(k-window-1);
                    }
                }

//                for(String e:tmpline){
//                    System.out.print(e+'\t');
//                }
//                System.out.print("\n");
//                for(int p=0;p<tmpline.length;p++){
//                    if(p<window) {
//                        System.out.print(bm.dou_pos.get(tmpline[p])+"\t");
//                    }
//                    if(p>window){
//                        System.out.print(bm.dou_neg.get(tmpline[p])+"\t");
//                    }
//                    if(p==window){
//                        System.out.print("0"+"\t");
//                    }
//                }
//                System.out.print("\n");


                suboutput.add(tmpline);
            }
            output.add(suboutput);

        }
        if(chaos){
            try {
                FileWriter F = new FileWriter(outpath);
                BufferedWriter W = new BufferedWriter(F);
                for(int i=0;i<114*length;i++){
                    List<String[]> heroline=output.get((int)(Math.random()*output.size()));
                    for(String e:heroline.get((int)(heroline.size()*Math.random()))){
                        W.write(e+'\t');
                    }
                    W.write("\n");
                }
                W.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try {
                FileWriter F = new FileWriter(outpath);
                BufferedWriter W = new BufferedWriter(F);
                for (List<String[]> eachhero : output) {
                    for (String[] line : eachhero) {
                        for (String e : line) {
                            W.write(e+"\t");
                        }
                        W.write("\n");
                    }
                }
                W.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }



    }
}
