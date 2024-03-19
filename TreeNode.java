class TreeNode {
    int[] keys;
    TreeNode[] children;
    int numKeys;
    boolean leaf;

    public TreeNode(int degree, boolean leaf) {
        this.keys = new int[2 * degree - 1];
        this.children = new TreeNode[2 * degree];
        this.numKeys = 0;
        this.leaf = leaf;
    }
}

