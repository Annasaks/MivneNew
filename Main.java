

public class Main {
    public static void main(String[] args) {


    Tree arbre = new Tree();

    Node node_1 = new Node(1);
    Node node_3 = new Node(3);
        Node node_4 = new Node(4);
    Node node_5 = new Node(5);
    Node node_7 = new Node(7);
    Node node_10 = new Node(10);
    Node node_18 = new Node(18);

     arbre.insert(node_4);
     arbre.insert(node_1);
        arbre.insert(node_18);
        arbre.insert(node_3);

        arbre.insert(node_5);
        arbre.insert(node_7);
        arbre.insert(node_10);



     arbre.displayTree();

        System.out.println(arbre.root.left.left.size);
    }
}
