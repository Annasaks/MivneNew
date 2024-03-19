
public class Tree {
    private TreeNode root;
    private int degree;


    public Tree(int degree) {
        this.root = null;
        this.degree = degree;
    }

    public void insert(int data) {
        if (root == null) {
            root = new TreeNode(degree, true);
            root.keys[0] = data;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * degree - 1) {
                TreeNode newRoot = new TreeNode(degree, false);
                newRoot.children[0] = root;
                splitChild(newRoot, 0);
                int i = 0;
                if (newRoot.keys[0] < data)
                    i++;
                insertNonFull(newRoot.children[i], data);
                root = newRoot;
            } else {
                insertNonFull(root, data);
            }
        }
    }

    private void insertNonFull(TreeNode node, int data) {
        int i = node.numKeys - 1;
        if (node.leaf) {
            while (i >= 0 && node.keys[i] > data) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = data;
            node.numKeys++;
        } else {
            while (i >= 0 && node.keys[i] > data)
                i--;

            if (node.children[i + 1].numKeys == 2 * degree - 1) {
                splitChild(node, i + 1);
                if (node.keys[i + 1] < data)
                    i++;
            }
            insertNonFull(node.children[i + 1], data);
        }
    }

    private void splitChild(TreeNode parent, int i) {
        TreeNode child = parent.children[i];
        TreeNode newChild = new TreeNode(degree, child.leaf);
        newChild.numKeys = degree - 1;

        for (int j = 0; j < degree - 1; j++)
            newChild.keys[j] = child.keys[j + degree];

        if (!child.leaf) {
            for (int j = 0; j < degree; j++)
                newChild.children[j] = child.children[j + degree];
        }

        child.numKeys = degree - 1;

        for (int j = parent.numKeys; j >= i + 1; j--)
            parent.children[j + 1] = parent.children[j];

        parent.children[i + 1] = newChild;

        for (int j = parent.numKeys - 1; j >= i; j--)
            parent.keys[j + 1] = parent.keys[j];

        parent.keys[i] = child.keys[degree - 1];
        parent.numKeys++;
    }

    public int getRootValue() {
        if (root == null)
            throw new IllegalStateException();

        return root.keys[0];
    }

}

