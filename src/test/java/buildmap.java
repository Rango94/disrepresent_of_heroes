import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

public class buildmap {

    List<String> pos=new ArrayList<String>();
    List<String> neg=new ArrayList<String>();
    HashMap<String,Integer> cor_pos=new HashMap<String, Integer>();
    HashMap<String,Integer> cor_neg=new HashMap<String, Integer>();
    HashMap<String,Double> dou_pos=new HashMap<String, Double>();
    HashMap<String,Double> dou_neg=new HashMap<String, Double>();
    int size;


    public buildmap(double[] line,int size){
        this.size=size;

        double pos_int=0;
        for(int i=0;i<line.length;i++){
            if(i==23){
                continue;
            }
            if(line[i]>0){
                dou_pos.put(Integer.toString(i+1),line[i]);
                pos_int+=line[i];
            }
            ;
        }
        for(String e:dou_pos.keySet()){
//            System.out.println(e+"\t"+Double.toString(dou_pos.get(e)));

            cor_pos.put(e,(int)(size*(dou_pos.get(e)/pos_int)));
        }
        for(String e:cor_pos.keySet()){
            for(int i=0;i<cor_pos.get(e);i++){
                pos.add(e);
            }
        }

        double neg_int=0;
        for(int i=0;i<line.length;i++){
            if(i==23){
                continue;
            }
            if(line[i]<=0){
                dou_neg.put(Integer.toString(i+1),line[i]);
                neg_int+=line[i];
            }
        }
        for(String e:dou_neg.keySet()){
            cor_neg.put(e,(int)(size*(dou_neg.get(e)/neg_int)));
        }
        for(String e:cor_neg.keySet()){
            for(int i=0;i<cor_neg.get(e);i++){
                neg.add(e);
            }
        }
    }

    public List<String> pickpos(String hero,int num){
        List<String> out=new ArrayList<String>();
        for(int i=0;i<num;i++){
            int tmppick=(int)(Math.random()*pos.size());
            String id=pos.get(tmppick);
            while(id.equals(hero)||out.contains(id)){
                tmppick=(int)(Math.random()*pos.size());
                id=pos.get(tmppick);
            }
            boolean flag=true;
            for(int j=0;j<out.size();j++){
                if(dou_pos.get(id)<dou_pos.get(out.get(j))){
                    out.add(j,id);
                    flag=false;
                    break;
                }
            }
            if(flag){
                out.add(id);
            }
        }
        return out;
    }

    public List<String> pickneg(String hero,int num){
        List<String> out=new ArrayList<String>();
        for(int i=0;i<num;i++){
            int tmppick=(int)(Math.random()*neg.size());
            String id=neg.get(tmppick);
            while(id.equals(hero)||out.contains(id)){
                tmppick=(int)(Math.random()*neg.size());
                id=neg.get(tmppick);
            }
            boolean flag=true;
            for(int j=0;j<out.size();j++){
                if(dou_neg.get(id)<dou_neg.get(out.get(j))){
                    out.add(j,id);
                    flag=false;
                    break;
                }
            }
            if(flag){
                out.add(id);
            }
        }
        return out;
    }
}
