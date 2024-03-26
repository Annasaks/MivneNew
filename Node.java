public class Node {
    Node left ;
    Node right;
    Node middle ;
    Node parent;
    int key ;
    int size ;
    Runner runner;

    public Node(int key, Runner runner) {
            this.key = key;
            this.size = 1;
            this.runner = runner;
        }
}

