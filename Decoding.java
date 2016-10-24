import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.PriorityQueue;

public class Decoding {
    File file;
    String fileName="";
    PriorityQueue<HNode> nodes;
    HTree encoding;
    public Decoding(String fname){
        fileName=fname;
        file=new File(fname+".dat");
        nodes=new PriorityQueue();
        decode();
    }
    public void decode(){
        try{
            FileInputStream input=new FileInputStream(file);
            FileOutputStream output=new FileOutputStream(new File(fileName+"_decode.txt"));
            int nextByte=input.read(),nextNext=input.read();
            boolean isheader=true,isFirst=true;
            String header="";
            String[] items=new String[256];
            int currentSize=0;
            while(nextByte!=-1  || nextNext!=-1){
                if(isheader){
                    header+=(char)nextByte;
                    if(header.indexOf(',')>0){
                        items[currentSize]=header.substring(0,header.indexOf(','));
                        header="";
                        currentSize++;               
                    }
                    if(header.indexOf(']')!=-1){
                        isheader=false;
                        items[currentSize]=header.substring(0,header.indexOf(']'));
                        currentSize++;
                        createNodes(items,currentSize);
                    }
                    nextByte=nextNext;
                    nextNext=input.read();
                }else{
                    if(isFirst){
                        header=ByteToBinary(nextByte)+ByteToBinary(nextNext);
                        isFirst=false;
                        nextByte=input.read();
                        nextNext=input.read();
                    }else{
                        header+=ByteToBinary(nextByte);
                        nextByte=nextNext;
                        nextNext=input.read();
                    }
                    while(header.length()>8){
                        output.write(encoding.getCharFromPath(header));
                        header=encoding.adjustedString;                   
                    } 
                }               
            }
            input.close();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    private String ByteToBinary(int b){
        if(b>128){
            return Integer.toBinaryString(b);
        }else{
            String build=Integer.toBinaryString(b);
            while(build.length()<8){
                build="0"+build;
            }
            return build;
        }
    }
    private void createNodes(String[] items,int currentSize){
        int i=currentSize-1;
        items[0]=items[0].substring(items[0].indexOf("["));
        items[0]=items[0].substring(1);   
        while(i>=0){
            String[] vals=items[i].split(":");
            nodes.add(new HNode(Integer.valueOf(vals[0]),null,Integer.valueOf(vals[1])));
            i--;
        }
        createTree();
    }
    private void createTree(){
        while(nodes.size()!=1){
            HNode lowest=nodes.poll();
            HNode secondL=nodes.poll();
            HNode parent=lowest.merge(secondL);
            parent.setChildren(lowest, secondL);
            nodes.add(parent);
        }
        encoding=new HTree(nodes.remove());     
    }
}
