import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Group Node class made for the purpose of it always being identified as not a leaf.
 */
public class GroupNode extends DefaultMutableTreeNode{
    /**
     * Constructor.
     * @param obj Name of this node.
     */
    public GroupNode(Object obj){
        super(obj);
    }

    /**
     * Always returns false so this is never seen as a leaf.
     * @return false
     */
    public boolean isLeaf() {
        return false;
    }
}
