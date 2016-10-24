
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Encoding{
    File file;
    HTree items;
    int[] frequency;
    PriorityQueue<HNode> nodes;
    ArrayList<HNode> leafs;
    HTree total;
    String fileName;
    public Encoding(String fname){
        fileName=fname;
        file=new File(fname+".txt");
        frequency=new int[256];
        nodes=new PriorityQueue();
        leafs=new ArrayList();
        createCharItems();
        createQueue();
        mergeNodes();
        createH();
    }
    private void mergeNodes(){
        while(nodes.size()!=1){
            HNode lowest=nodes.poll();
            HNode secondL=nodes.poll();
            HNode parent=lowest.merge(secondL);
            parent.setChildren(lowest, secondL);
            nodes.add(parent);
            if(lowest.frequency!=0){
                leafs.add(lowest);
            }
            if(secondL.frequency!=0){
                leafs.add(secondL);
            }
        }
        total=new HTree(nodes.remove());
    }
    private void createCharItems(){
        FileInputStream input=null;
        try{
            input=new FileInputStream(file);
            int nextByte=input.read();
            while(nextByte!=-1){
                frequency[nextByte]++;
                nextByte=input.read();
            }
            input.close();
        }catch(Exception e){
        }
    }
    private void createQueue(){
        for(int i=0;i<frequency.length;i++){
            if(frequency[i]!=0){
                nodes.add(new HNode(i,null,frequency[i]));
            }
        }
    }
    private String Tree(){
        int i=0;
        String items="[";
        while(leafs.size()>i){
            items+=leafs.get(i).me+":"+leafs.get(i).frequency+",";
            i++;
        }
        return items.substring(0,items.length()-1)+"]";
    }
    private void createH(){
        FileInputStream input=null;
        FileOutputStream output=null;
        String bytes="";
        String header=Tree();
        boolean isLast=false;
        try{
            output=new FileOutputStream(fileName+".dat");
            input=new FileInputStream(file);
            int nextByte=input.read(),nextNext=input.read();  
            output.write(header.getBytes());            
            while(nextByte!=-1 || bytes.length()>0){
                if(nextByte!=-1){
                    bytes+=total.getBytePath((char)nextByte);
                }else{
                    while(bytes.length()<8){
                        bytes+="0";
                    }
                    isLast=true;
                }
                nextByte=nextNext;
                nextNext=input.read();
                if(bytes.length()>=15 || isLast){
                    short next=Short.parseShort(bytes.substring(0,8), 2);
                    if(!isLast || bytes.length()>=8)
                        bytes=bytes.substring(8,bytes.length());
                    byte val=ByteBuffer.allocate(2).putShort(next).array()[1];                   
                    output.write(val);
                    if(isLast && bytes.length()==0)
                        break;
                }
            }
            input.close();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
