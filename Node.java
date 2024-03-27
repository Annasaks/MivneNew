public class Node {
    Node left ;
    Node right;
    Node middle ;
    Node parent;
    int key ;
    int size ;

    int rank;
    Runner runner;

    public Node(int key, Runner runner) {
            this.key = key;
            this.size = 1;
            this.runner = runner;
        }


    public Node(int key) {
        this.key = key;
        this.size = 1;
        this.runner = runner;
    }

    public Node(){

    }

    public void updateKey() {
        // Mise à jour basée sur le nœud enfant le plus à gauche
        if (this.left != null) {
            this.key = this.left.key;
        }

        // Si le nœud du milieu existe, utiliser sa clé
        if (this.middle != null) {
            this.key = this.middle.key;
        }

        // Si le nœud de droite existe, utiliser sa clé, car elle serait la plus grande
        if (this.right != null) {
            this.key = this.right.key;
        }


    }

    public boolean isLeaf() {
        // Un nœud est une feuille s'il n'a pas d'enfants
        return this.left == null && this.middle == null && this.right == null;
    }

}

