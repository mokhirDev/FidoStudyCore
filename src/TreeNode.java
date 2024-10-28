import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String value;
    private List<TreeNode> children;

    public TreeNode(String value){
        this.value = value;
        this.children = new ArrayList<TreeNode>();
    }

    public String getValue(){
        return this.value;
    }

    public List<TreeNode> getChildren(){
        return this.children;
    }

    public void addChild(TreeNode child){
        this.children.add(child);
    }
}
