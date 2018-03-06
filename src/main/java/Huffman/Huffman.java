package Huffman;

import Modelhandler.Vector;
import myFile.corpusReader;

import java.io.*;
import java.util.*;

public class Huffman {
//叶子节点的名字和其huffman编码
    public HashMap<String, byte[]> tree=new HashMap<String, byte[]>();
//    最优二叉树
    public node Tree=new node();
//    叶子节点的名字set
    public Set<String> Termset;
//    非叶子节点的huffman编码与其向量的hashmap
    public HashMap<String,Vector> huff_vector=new HashMap<String, Vector>();
//    huffman编码的最大长度
    private static int maxlenthofhuffman=0;
//    非叶子节点的名字和其huffman编码
    public HashMap<String, byte[]> treenotleaf=new HashMap<String, byte[]>();

    public static HashMap<String,Integer> dictionary=new HashMap<String, Integer>();

    public int totalnum=0;

    public List<String> lowwords=new ArrayList<String>();

    public int getMaxlenthofhuffman() {
        return maxlenthofhuffman;
    }

    public Vector getVectorofnotleafbyHuffman(String huffman){
        return huff_vector.get(huffman);
    }

    public boolean equals(byte[] a,byte[] b){
        if(a.length!=b.length){
            return false;
        }else{
            for(int i=0;i<a.length;i++){
                if (a[i]!=b[i]){
                    return false;
                }
            }
        }
        return true;
    }
    public void setVectorofnotleafbyHuffman(String huffman,Vector vec){
        huff_vector.put(huffman,vec);
    }


    private void gennaratehuff_vector(int size){
        for(String e:treenotleaf.keySet()){
            huff_vector.put(bytetoString(treenotleaf.get(e)),new Vector(size,0));
        }
        huff_vector.put("-1",new Vector(size,0));
    }

    private String bytetoString(byte[] in){
        String out="";
        for(byte e:in){
            out+=Byte.toString(e);
        }
        return out;
    }

    public Huffman(){
    }

    public byte[] next_binary(byte[] in){
        int size=in.length;
        int indx=size-1;
        int up=1;
        while(up==1&&indx!=-1){
            int tmp=in[indx]+up;
            if(tmp>1){
                in[indx]=(byte)(tmp%2);
                indx-=1;
            }else{
                in[indx]=(byte)tmp;
                up=0;
            }
        }
        if(up==1){
            byte[] newin=new byte[size+1];
            newin[0]=1;
            for(int i=0;i<size;i++){
                newin[i+1]=in[i];
            }
            return newin;
        }
        return in;
    }

//  根据编码获取原信息
    public String encode(byte[] huffman){
        node dicnode=Tree;
        for(byte e:huffman){

            if(e==(byte)0){
                dicnode=dicnode.getLeftnode();
            }else{
                dicnode=dicnode.getRightnode();
            }
        }
        return dicnode.getName();
    }

    public void setnodevec(byte[] huffman,Vector in){
        node nd=new node();
        for(byte e:huffman){
            if(e==0){
                nd=Tree.getLeftnode();
            }else{
                nd=Tree.getRightnode();
            }
        }
    }

//  两个构造方法，可以分别通过地址读取语料库 或者直接使用hashmap语料库
    public Huffman(HashMap<String, Integer> corpus,int size) {
        generatetree(corpus,size);
    }

    public Huffman(String corpuspath,int size) {
        HashMap<String, Integer> corpus = readcorpus(corpuspath);
        generatetree(corpus,size);
    }

//  获取单个元素的huffman编码
    public byte[] getHuffmancode(String name){
        return tree.get(name);
    }

    public String getHuffmancode_str(String name){
        String out="";
        for(byte e:tree.get(name)){
            out+=Byte.toString(e);
        }
        return out;
    }

//    叶子节点的输出
    public void leafstoString(){
        for(String e:tree.keySet()){
            String out="";
            out+=e+"=";
            for (byte b:tree.get(e)){
                out+=Byte.toString(b);
            }
            System.out.println(out);
        }
    }

    public void notleafstoString(){
        for(String e:treenotleaf.keySet()){
            String out="";
            out+=e+"=";
            for (byte b:treenotleaf.get(e)){
                out+=Byte.toString(b);
            }
            System.out.println(out);
        }
    }


//  生成huffman编码对应表
    private void generatetree(HashMap<String, Integer> corpus,int size){
        Termset=corpus.keySet();
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(corpus.entrySet());
        Collections.sort(infoIds, new Comparator<HashMap.Entry<String, Integer>>() {
            public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2) {
                return (o1.getValue() - o2.getValue());
            }
        });
        List<node> nodeset=toNodeset(infoIds);
        List<node> newnodeset=new ArrayList<node>();
        int idofunleaf=0;
        node newnode=new node();
        Set<String> nameofnotleaf=new HashSet<String>();
        while(nodeset.size()!=1) {
//            选择nodeset中最小的两个
            newnode=addnode(nodeset.get(0),nodeset.get(1),idofunleaf,size);
            nameofnotleaf.add(newnode.getName());
            idofunleaf+=1;
//            将新结点加入newset
            newnodeset.add(newnode);
//            更新nodeset
            nodeset.remove(0);
            nodeset.remove(0);
            nodeset.add(0,newnode);
            for(int i=0;i<nodeset.size();i++){
                if(nodeset.get(i).isIsleaf()){
                    break;
                }
            }
            nodeset = nodesort(nodeset);
        }
        Tree=newnode;
        listcode( newnodeset, corpus.keySet(),nameofnotleaf);
        gennaratehuff_vector(size);
        System.out.println("deep of Huffman tree is "+Integer.toString(maxlenthofhuffman)+"");
    }

//  对元素集合编码
    private void listcode(List<node> nodeset,Set<String> nameset,Set<String> namesetnotleaf){
        HashMap<String,String> leftList=new HashMap<String,String>();
        HashMap<String,String> rightList=new HashMap<String,String>();
        for (node e:nodeset){
            leftList.put(e.getLeftnode().getName(),e.getName());
            rightList.put(e.getRightnode().getName(),e.getName());
        }
        for (String e:nameset){
            tree.put(e,tobyte(code(leftList,rightList,e)));
        }
        for(String e:namesetnotleaf){
            treenotleaf.put(e,tobyte(code(leftList,rightList,e)));
        }
//        System.out.println();
    }

//  String转byte[]
    private byte[] tobyte(String code){
        byte[] out=new byte[code.length()];
        for (int i=0;i<code.length();i++){
            out[i]=Byte.parseByte(code.substring(i,i+1));
        }
        if(out.length>maxlenthofhuffman){
            maxlenthofhuffman=out.length;
        }
        return out;
    }

//    对单个元素进行编码
//    我又检查了一边这个函数应该是对的，但这个逻辑好牛逼，我好像现在想不到这个逻辑了
    private String code(HashMap<String,String> leftList,HashMap<String,String> rightList,String name){
        String out="";
        while(leftList.containsKey(name) || rightList.containsKey(name)){
            if(leftList.containsKey(name)){
                out="1"+out;
                name=leftList.get(name);
            }
            if(rightList.containsKey(name)){
                out="0"+out;
                name=rightList.get(name);
            }
        }
        return out;
    }

//  读取语料文件
    private HashMap<String, Integer> readcorpus(String corpuspath) {
        try {
            Reader  br = new InputStreamReader (new FileInputStream( new File(corpuspath)));
            int tempbyte;
            String term="";
            while ((tempbyte = br.read()) != -1) {
                char w=(char)tempbyte;
                if(Character.isSpaceChar(w)||tempbyte==10||tempbyte==13){
                    if(term!=""){
                        totalnum++;
                        if (dictionary.containsKey(term)) {
                            dictionary.put(term, dictionary.get(term) + 1);
                        } else {
                            dictionary.put(term, 1);
                        }
                    }
                    term="";
                }else {
                    term += String.valueOf(w);
                }
            }
            br.close();
            lowwords=new ArrayList<String>();
            for(String e:dictionary.keySet()){
                if(dictionary.get(e)<10){
                    lowwords.add(e);
                }
            }
            for(String e:lowwords){
                dictionary.remove(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

//  将语料库转化为标准形式
    private List<node> toNodeset(List<Map.Entry<String, Integer>> infoIds){
        List<node> nodeset=new ArrayList<node>();
        for(Map.Entry<String, Integer> each:infoIds){
            node nd=new node();
            nd.setIsleaf(true);
            nd.setWeight(each.getValue());
            nd.setName(each.getKey());
            nodeset.add(nd);
        }
        return nodeset;
    }

//  对元素按权值排序
    private List<node> nodesort(List<node> nodeset){
        int weight=nodeset.get(0).getWeight();
        for (int i=0;i<nodeset.size();i++){
            if(nodeset.get(i).getWeight()>weight){
                nodeset.add(i,nodeset.get(0));
                nodeset.remove(0);
                break;
            }
        }
        return nodeset;
    }

//  元素的合并
    private node addnode(node nd1,node nd2,int id,int size){
        node nd=new node();
        nd.setName("N-"+Integer.toString(id));
        nd.setVec(new Vector(size));
        nd.setIsleaf(false);
        nd.setWeight(nd1.getWeight()+nd2.getWeight());
        nd.setLeftnode(nd1);
        nd.setRightnode(nd2);
        return nd;
    }

}
