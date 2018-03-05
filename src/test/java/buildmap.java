import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

public class buildmap {

    List<String> pos=new ArrayList<String>();
    List<String> neg=new ArrayList<String>();
    HashMap<String,Integer> cor_pos=new HashMap<String, Integer>();
    HashMap<String,Integer> cor_neg=new HashMap<String, Integer>();

    public buildmap(double[] line,int size){
        double pos_int=0;
        HashMap<String,Double> tmp_pos=new HashMap<String, Double>();
        for(int i=0;i<line.length;i++){
            if(line[i]>0){
                tmp_pos.put(Integer.toString(i+1),line[i]);
                line[i]+=pos_int;
            }
        }
        for(String e:tmp_pos.keySet()){
            cor_pos.put(e,(int)(size*(tmp_pos.get(e)/pos_int)));
        }
        for(String e:cor_pos.keySet()){
            for(int i=0;i<cor_pos.get(e);i++){
                pos.add(e);
            }
        }

        double neg_int=0;
        HashMap<String,Double> tmp_neg=new HashMap<String, Double>();
        for(int i=0;i<line.length;i++){
            if(line[i]<=0){
                tmp_neg.put(Integer.toString(i+1),line[i]);
                line[i]+=neg_int;
            }
        }
        for(String e:tmp_neg.keySet()){
            cor_neg.put(e,(int)(size*(tmp_neg.get(e)/neg_int)));
        }
        for(String e:cor_neg.keySet()){
            for(int i=0;i<cor_neg.get(e);i++){
                neg.add(e);
            }
        }
    }




}
