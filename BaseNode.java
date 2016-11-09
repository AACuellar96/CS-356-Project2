import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Abstract class so every node in the tree will have to have a getID function
 */
public abstract class BaseNode extends DefaultMutableTreeNode {
    /**
     * Constructor
     * @param obj
     */
    public BaseNode(Object obj){
        super(obj);
    }

    /**
     * ID for the purposes of hashtable identification
     * @return hashtable ID
     */
    abstract String getID();

}
