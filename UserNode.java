import javax.swing.tree.DefaultMutableTreeNode;

/**
 * UserNode class only exists to make sure that a User can never have children.
 */
public class UserNode extends DefaultMutableTreeNode{
    /**
     * Constructor.Sets allows children to false.
     * @param obj Name of this node.
     */
    public UserNode(Object obj){
        super(obj);
        setAllowsChildren(false);
    }

    /**
     * Always returns true so it will always be only a leaf.
     * @return true.
     */
    public boolean isLeaf() {
        return true;
    }
}
