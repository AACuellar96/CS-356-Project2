
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * The adminUI class. Creates a JFrame and all components in it for the UI.
 */
public class adminUI extends javax.swing.JFrame{
    /**
     * Constant UserGroup root. Will always be the top UserGroup.
     */
    private UserGroup root;
    /**
     * Only ever instance of adminUI that will exist.
     */
    public static adminUI adminControlPanel;
    /**
     * List of all userUI opened.
     */
    private List<userUI> userPanels;
    /**
     * All of the below are swing components.
     */
    private javax.swing.JButton addGroupButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton showMessageTotalButton;
    private javax.swing.JButton showUserTotalButton;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JTextField groupIDTextArea;
    private javax.swing.JButton openUserViewButton;
    private javax.swing.JButton showGroupTotalButton;
    private javax.swing.JButton showPosPercButton;
    private javax.swing.JTree tree;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JTextField userIDTextArea;
    private GroupNode treeNode1;

    /**
     * Constructor. Creates everything.
     */
    private adminUI(){
        initComponents();
        userPanels = new ArrayList<>();
        root = new UserGroup("Root");
        HashTable.getTable().put("Root",root);
    }

    /**
     * Method that returns the adminUI.
     * @return adminControlPanel
     */
    public static adminUI getUI(){
        if(adminControlPanel==null) {
            synchronized (adminUI.class) {
                if(adminControlPanel==null)
                    adminControlPanel = new adminUI();
            }
        }
        return adminControlPanel;
    }

    /**
     * Updates all of the userUI's current followings. Unnecessary unless multiple instances of the same
     * user's userUI are opened at the same time.
     */
    public void updateALLCF(){
        userPanels.forEach(userUI::updateCF);
    }

    /**
     * Updates all of the userUI"s news feeds.
     */
    public void updateALLNF(){
        userPanels.forEach(userUI::updateNF);
    }

    /**
     * Handler for when addUserButton is pressed.Gets the currently selected node, or root if no other, and adds
     * a new user to that node if possible.
     * @param evt
     */
    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String uniqueName=userIDTextArea.getText();
        if(HashTable.getTable().get(uniqueName+"USER")==null) {
            DefaultMutableTreeNode parentNode = null;
            TreePath parentPath = tree.getSelectionPath();
            if (parentPath == null) {
                parentNode = treeNode1;
            }
            else {
                parentNode=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            }
            if(parentNode.getAllowsChildren()) {
                UserNode newNode = new UserNode(uniqueName);
                parentNode.add(newNode);
                tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
                tree.makeVisible(new TreePath(newNode.getPath()));
                User newUser = new User(uniqueName);
                ((UserGroup) HashTable.getTable().get(parentNode.toString()+"GROUP")).addToGroup(newUser);
                HashTable.getTable().put(uniqueName, newUser);
           }
        }
    }
    /**
     * Handler for when addGroupButton is pressed.Gets the currently selected node, or root if no other, and adds
     * a new group to that node if possible.
     * @param evt
     */
    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String uniqueName=groupIDTextArea.getText();
        if(HashTable.getTable().get(uniqueName+"GROUP")==null) {
            DefaultMutableTreeNode parentNode = null;
            TreePath parentPath = tree.getSelectionPath();
            if (parentPath == null) {
                parentNode = treeNode1;
            }
            else {
                parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
            }
            if(parentNode.getAllowsChildren()) {
                GroupNode newNode = new GroupNode(uniqueName);
                parentNode.add(newNode);
                tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
                tree.makeVisible(new TreePath(newNode.getPath()));
                UserGroup newGroup = new UserGroup(uniqueName);
                ((UserGroup) HashTable.getTable().get(parentNode.toString()+"GROUP")).addToGroup(newGroup);
                HashTable.getTable().put(uniqueName,newGroup);
            }
        }
    }
    /**
     * Handler for when openUserViewButton is pressed. If a userNode is selected opens that User's userUI.
     * @param evt
     */
    private void openUserViewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null)
            parentNode = treeNode1;
        else {
            parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        }
        if(HashTable.getTable().get(parentNode.toString()+"USER")!=null && parentNode.isLeaf()) {
            baseUser temp = HashTable.getTable().get(parentNode.toString()+"USER");
            userUI uUI = new userUI((User) temp);
            userPanels.add(uUI);
            uUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            uUI.setVisible(true);
        }
        else{
            String message = "A User is not selected.";
            JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Handler for when showMessageTotalButton is pressed. Shows the amount of messages in the currently selected node's
     * group.
     * @param evt
     */
    private void showMessageTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null)
            parentNode = treeNode1;
        else {
            parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        }
        MsgTotalVisitor vis = new MsgTotalVisitor();
        baseUser target;
        if(parentNode.getAllowsChildren())
            target=HashTable.getTable().get(parentNode.toString()+"GROUP");
        else
            target=HashTable.getTable().get(parentNode.toString()+"USER");
        target.accept(vis);
        String message = "There are " + vis.getValue() + " messages in all news feeds.";
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * Handler for when showUserTotalButton is pressed. Shows the amount of user in the currently selected node's
     * group.
     * @param evt
     */
    private void showUserTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null)
            parentNode = treeNode1;
        else {
            parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        }
        UTotalVisitor vis = new UTotalVisitor();
        baseUser target;
        if(parentNode.getAllowsChildren())
            target=HashTable.getTable().get(parentNode.toString()+"GROUP");
        else
            target=HashTable.getTable().get(parentNode.toString()+"USER");
        target.accept(vis);
        String message = "There are " + vis.getValue() + " users.";
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * Handler for when showGroupTotalButton is pressed. Shows the amount of group in the currently selected node's
     * group.
     * @param evt
     */
    private void showGroupTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null)
            parentNode = treeNode1;
        else {
            parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        }
        GTotalVisitor vis = new GTotalVisitor();
        baseUser target;
        if(parentNode.getAllowsChildren())
            target=HashTable.getTable().get(parentNode.toString()+"GROUP");
        else
            target=HashTable.getTable().get(parentNode.toString()+"USER");
        target.accept(vis);
        String message = "There are " + vis.getValue() + " groups.";
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * Handler for when showPositivePercentageButton is pressed. Shows the amount of positive messages
     * out of all messages in the currently selected node's group.
     * @param evt
     */
    private void showPosPercButtonActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null)
            parentNode = treeNode1;
        else {
            parentNode = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        }
        PosPercVisitor vis = new PosPercVisitor();
        baseUser target;
        if(parentNode.getAllowsChildren())
            target=HashTable.getTable().get(parentNode.toString()+"GROUP");
        else
            target=HashTable.getTable().get(parentNode.toString()+"USER");
        target.accept(vis);
        String message = "The percentage of positive messages is " + vis.getValue() + "%.";
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Handler for when text is entered in the userIDTextArea.
     * @param evt
     */
    private void userIDTextAreaActionPerformed(java.awt.event.ActionEvent evt) {
    }
    /**
     * Handler for when text is entered in the groupIDTextArea.
     * @param evt
     */
    private void groupIDTextAreaActionPerformed(java.awt.event.ActionEvent evt) {
    }

    /**
     * Creates all of the components of the UI.
     */
    private void initComponents() {

        adminPanel = new javax.swing.JPanel();
        addGroupButton = new javax.swing.JButton();
        showPosPercButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        showGroupTotalButton = new javax.swing.JButton();
        showMessageTotalButton = new javax.swing.JButton();
        showUserTotalButton = new javax.swing.JButton();
        groupIDTextArea = new javax.swing.JTextField();
        userIDTextArea = new javax.swing.JTextField();
        openUserViewButton = new javax.swing.JButton();
        treeScrollPane = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        adminPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(106, 90, 205)));
        adminPanel.setPreferredSize(new java.awt.Dimension(600, 400));

        addGroupButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        addGroupButton.setForeground(new java.awt.Color(106, 90, 205));
        addGroupButton.setText("Add Group");
        addGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGroupButtonActionPerformed(evt);
            }
        });

        showPosPercButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        showPosPercButton.setForeground(new java.awt.Color(106, 90, 205));
        showPosPercButton.setText("Show Positive Percentage");
        showPosPercButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPosPercButtonActionPerformed(evt);
            }
        });

        addUserButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        addUserButton.setForeground(new java.awt.Color(106, 90, 205));
        addUserButton.setText("Add User");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tree.getTreeSelectionListeners();
                addUserButtonActionPerformed(evt);
            }
        });

        showGroupTotalButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        showGroupTotalButton.setForeground(new java.awt.Color(106, 90, 205));
        showGroupTotalButton.setText("Show Group Total");
        showGroupTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showGroupTotalButtonActionPerformed(evt);
            }
        });

        showMessageTotalButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        showMessageTotalButton.setForeground(new java.awt.Color(106, 90, 205));
        showMessageTotalButton.setText("Show Messages Total");
        showMessageTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMessageTotalButtonActionPerformed(evt);
            }
        });

        showUserTotalButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        showUserTotalButton.setForeground(new java.awt.Color(106, 90, 205));
        showUserTotalButton.setText("Show User Total");
        showUserTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUserTotalButtonActionPerformed(evt);
            }
        });

        groupIDTextArea.setEditable(true);
        groupIDTextArea.setBackground(new java.awt.Color(240, 240, 240));
        groupIDTextArea.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        groupIDTextArea.setForeground(new java.awt.Color(106, 90, 205));
        groupIDTextArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        groupIDTextArea.setText("GROUP ID");
        groupIDTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupIDTextAreaActionPerformed(evt);
            }
        });

        userIDTextArea.setEditable(true);
        userIDTextArea.setBackground(new java.awt.Color(240, 240, 240));
        userIDTextArea.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        userIDTextArea.setForeground(new java.awt.Color(106, 90, 205));
        userIDTextArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        userIDTextArea.setText("USER ID");
        userIDTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDTextAreaActionPerformed(evt);
            }
        });

        openUserViewButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        openUserViewButton.setForeground(new java.awt.Color(106, 90, 205));
        openUserViewButton.setText("Open User View");
        openUserViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openUserViewButtonActionPerformed(evt);
            }
        });


        tree.setBackground(new java.awt.Color(240, 240, 240));
        tree.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tree.setForeground(new java.awt.Color(106, 90, 205));
        treeNode1 = new GroupNode("Root");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
            }
        });
        treeScrollPane.setViewportView(tree);

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminPanelLayout.createSequentialGroup()
                                .addContainerGap(249, Short.MAX_VALUE)
                                .addComponent(userIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(adminPanelLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(treeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(openUserViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(adminPanelLayout.createSequentialGroup()
                                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(groupIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showUserTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showMessageTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showGroupTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showPosPercButton))))
                                        .addGap(22, 22, 22)))
        );
        adminPanelLayout.setVerticalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(userIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(327, Short.MAX_VALUE))
                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(adminPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(adminPanelLayout.createSequentialGroup()
                                                        .addGap(78, 78, 78)
                                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(groupIDTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18, 18, 18)
                                                        .addComponent(openUserViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(showGroupTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showUserTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(showPosPercButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(showMessageTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addComponent(treeScrollPane))
                                        .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        pack();
        setLocationRelativeTo(null);
    }

}
