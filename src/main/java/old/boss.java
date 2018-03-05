package old;

import com.ansj.vec.Word2VEC;
import me.xiaosheng.word2vec.Word2Vec;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class boss {
    public static void main(String[] args){
//        old.generateheroessentence con=new old.generateheroessentence("E:/disrepresent_of_heroes/anti.txt",10,5000);
        try {
            Word2Vec.trainJavaModel("E:/disrepresent_of_heroes/anticorpus.txt", "E:/disrepresent_of_heroes/anti.model",200,1);
        }catch (IOException e){
            e.printStackTrace();
        }
        Word2Vec heromodel=new Word2Vec();
        try {
            heromodel.loadJavaModel("E:/disrepresent_of_heroes/anti.model");
        }catch (IOException e){
            e.printStackTrace();
        }
        float si=0;
        for (int i=1;i<115;i++){
//            System.out.println(i);
            if(i==24)
                continue;
            for (int j=1;j<115;j++){
                if(j==24||i==j)
                    continue;
                float si_tmp=heromodel.wordSimilarity(Integer.toString(i),Integer.toString(j));
                if(si_tmp>0.7){
                    si=si_tmp;
                    System.out.println(Integer.toString(i)+' '+Integer.toString(j)+" "+si);
                }
            }
//            if(i!=24){
//                System.out.println(i+" "+ Arrays.toString(heromodel.getWordVector(Integer.toString(i))));
//            }
        }
    }

}
