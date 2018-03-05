package old;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Readfile {
    public Double[][] readfile(String filename){
        Double[][] out=new Double[114][114];
        try {
            File file = new File(filename); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "1";
            line = br.readLine();
            int y=0;
            while (line != null) {
                int x=0;
                String[] line_sep=line.split("\\s+");
                for(String each:line_sep){
                    out[y][x]=Double.parseDouble(each);
                    x+=1;
                }
                y+=1;
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
