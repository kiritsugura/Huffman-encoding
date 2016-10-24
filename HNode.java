
public class HNode implements Comparable{
    public HNode left,right,parent;
    public int frequency;
    public int me;
    public int depth;
    public HNode(int c,HNode par,int freq){
        me=c;
        parent=par;
        frequency=freq;
    }
    public boolean isLeaf(){
        return left==null || right==null;
    }
    public void add(int symbol, int frequency){
        if(me>symbol){
            if(right==null){
                right=new HNode(symbol,this,frequency);
            }else{
                right.add(symbol,frequency);
            }
        }else if(me<symbol){
            if(left==null){
                left=new HNode(symbol,this,frequency);
            }else{
                left.add(symbol,frequency);
            }                
        }else{
            frequency++;
        }
    }
    public void setDepth(int dep){
        depth=dep;
        if(left!=null){
            left.setDepth(dep+1);
        }
        if(right!=null){
            right.setDepth(dep+1);
        }
    }
    public void setChildren(HNode lchild,HNode rchild){
        left=lchild;
        left.parent=this;
        right=rchild;
        right.parent=this;
    }
    public HNode merge(HNode other){
        return new HNode((me+other.me),null,0);
    }
    public boolean isLess(HNode other){
        return frequency<other.frequency;
    }
    public String getString(){
        String s="";
        if(right!=null){
            s+=right.getString();
        }    
        s+=spaces()+"["+me+":"+frequency+"]\n";
        if(left!=null){
            s+=left.getString();
        }
        return s;
    }
    public HNode find(int cv){
        if((char)me==cv && frequency!=0){
            return this;
        }
        if(left!=null){
            HNode val=left.find(cv);
            if(val!=null && val.frequency!=0){
                return val;
            }            
        }
        if(right!=null){
            HNode val=right.find(cv);
            if(val!=null && val.frequency!=0){
                return val;
            }
        }
        return null;
    }
    public String spaces(){
        String s="";
        for(int i=1;i<depth;i++){
            s+=" ";
        }
        return s;
    }
    @Override
    public int compareTo(Object t) {
        HNode other=(HNode)t;
        if(other.me>me){
            return -3;
        }else if(other.me<me){
            return 3;
        }else if(other.frequency>this.frequency){
            return -2;
        }else{
            return 2;
        }
    }
}

