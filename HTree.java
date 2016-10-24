
import java.util.ArrayList;
public class HTree{
    HNode root;
    String adjustedString;
    public HTree(HNode rt){
        root=rt;
        root.setDepth(1);
        adjustedString="";
    }
    public String toString(){
        return root.getString();
    }
    public int getCharFromPath(String path){
        HNode val=root;
        int index=0;
        while(true){   
            if(path.charAt(index)=='1' && val.frequency==0){
                val=val.left;
            }else if(path.charAt(index)=='0' && val.frequency==0){
                val=val.right;
            }           
            if(val.isLeaf()){
                adjustedString=path.substring(index+1,path.length());
                val.frequency--;
                break;
            }           
            index++;
        }
        return val.me;
    }
    public String getBytePath(char ch) {
        HNode val = root.find((int) ch);
        String s = "";
        while (val.parent != null) {
            if (val.parent.right == val) {
                s = "0" + s;
            } else if (val.parent.left == val) {
                s = "1" + s;
            }
            val = val.parent;
        }
        return s;
    }
   
}
