
public class Tree {
    private Node root;
    private Node min ;


    public Tree(int degree) {
        this.root =null;

        this.min=null;

        this.degree = degree;
    }

    private void initTree() {
        Node x = new Node(Integer.MAX_VALUE); // Internal node with key +∞
        Node l = new Node(Integer.MIN_VALUE); // Leaf node with key -∞
        Node m = new Node(Integer.MAX_VALUE); // Leaf node with key +∞

        // Setting up the parent-child relationships
        l.parent = m.parent = x;
        x.left = l;
        x.middle = m;

        // The right child is not used in the initial configuration
        x.right = null;

        // Setting the root of the tree
        this.root = x;
    }

    public void displayTree() {
        if (root != null) {
            System.out.println("Root Key: " + root.key);
            System.out.println("Left Child Key: " + root.left.key);
            System.out.println("Middle Child Key: " + root.middle.key);
        }
    }


    //SEARCH UNE KEY

    public Node search(int key) {
        return search(root, key);
    }

    private Node search(Node x, int key) {
        if (x == null) {
            return null; // Retourne null si x est null pour gérer les appels sur des nœuds inexistants
        }

        // Si x est une feuille, vérifier la clé
        if (x.isLeaf()) {
            if (x.key.equals(key)) {
                return x; // Clé trouvée
            } else {
                return null; // Clé non trouvée
            }
        }

        // Si x est un nœud interne, continuer la recherche dans le sous-arbre approprié
        if (key <= x.left.key) {
            return search(x.left, key);
        } else if (x.middle != null && key <= x.middle.key) { // Vérifier si le nœud du milieu existe
            return search(x.middle, key);
        } else {
            return search(x.right, key); // La recherche continue dans le nœud de droite
        }
    }

    public Node findMinimum() {
        if (root == null || root.key.equals(Integer.MAX_VALUE)) {
            throw new IllegalStateException("T is empty");
        }

        Node x = root;
        while (!x.isLeaf()) {
            x = x.left; // Toujours aller à gauche pour trouver le minimum
        }

        if (!x.key.equals(Integer.MAX_VALUE)) {
            return x;
        } else {
            throw new IllegalStateException("T is empty");
        }
    }



    public Node findSuccessor(Node x) {
        if (x == null) {
            return null; // Retourne null si x est null
        }

        Node z = x.parent;
        // Monter dans l'arbre tant que nous sommes sur le chemin "droit" ou le seul chemin "moyen"
        while (z != null && (x == z.right || (z.right == null && x == z.middle))) {
            x = z;
            z = z.parent;
        }

        // Si nous avons atteint la racine sans trouver un nœud interne approprié, il n'y a pas de successeur
        if (z == null) {
            return null;
        }

        // Déterminer le chemin vers le successeur
        Node y;
        if (x == z.left) {
            y = z.middle != null ? z.middle : z.right; // Prendre le milieu si possible, sinon le droit
        } else {
            y = z.right;
        }

        // Descendre à la feuille la plus à gauche dans le sous-arbre sélectionné
        while (y != null && !y.isLeaf()) {
            y = y.left;
        }

        // Vérifier que le successeur a une clé valide
        if (y != null && y.key < Integer.MAX_VALUE) {
            return y;
        } else {
            return null; // Aucun successeur valide trouvé
        }
    }


    public void updateKey(Node x) {
        // Mise à jour basée sur le nœud enfant le plus à gauche
        if (x.left != null) {
            x.key = x.left.key;
        }

        // Si le nœud du milieu existe, utiliser sa clé
        if (x.middle != null) {
            x.key = x.middle.key;
        }

        // Si le nœud de droite existe, utiliser sa clé, car elle serait la plus grande
        if (x.right != null) {
            x.key = x.right.key;
        }


    }

    public static void setChildren(Node x, Node l, Node m, Node r) {
        // Assigner les enfants
        x.left = l;
        x.middle = m;
        x.right = r;

        // Mettre à jour le lien parent de l vers x
        l.parent = x;

        // Mettre à jour le lien parent de m vers x, si m n'est pas null
        if (m != null) {
            m.parent = x;
        }

        // Mettre à jour le lien parent de r vers x, si r n'est pas null
        if (r != null) {
            r.parent = x;
        }

        // A IMPLEMENTER DANS NODEEEEEEEEEEE
        // Mettre à jour la clé de x pour refléter la clé maximale parmi ses enfants
        x.updateKey();
    }





    public Node insertAndSplit(Node x, Node z) {
        Node l = x.left, m = x.middle, r = x.right;

        // Cas où il n'y a pas besoin de scission
        if (r == null) {
            if (z.key < l.key) {
                setChildren(x, z, l, m);
            } else if (z.key < m.key) {
                setChildren(x, l, z, m);
            } else {
                setChildren(x, l, m, z);
            }
            return null; // Aucune scission n'a été nécessaire
        }

        // Création d'un nouveau nœud interne y pour la scission
        Node y = new Node(null); // La clé sera mise à jour par setChildren

        if (z.key < l.key) {
            setChildren(x, z, l, null);
            setChildren(y, m, r, null);
        } else if (z.key < m.key) {
            setChildren(x, l, z, null);
            setChildren(y, m, r, null);
        } else if (z.key < r.key) {
            setChildren(x, l, m, null);
            setChildren(y, z, r, null);
        } else {
            setChildren(x, l, m, null);
            setChildren(y, r, z, null);
        }

        // Mettre à jour les clés des nouveaux nœuds interne x et y
        x.updateKey();
        y.updateKey();

        // Retourner le nouveau nœud y pour permettre la réorganisation de l'arbre au niveau supérieur
        return y;
    }




    public void insert(Node z) {
        // Si l'arbre est vide, z devient la racine
        if (root == null) {
            root = z;
            return;
        }

        Node y = root;
        // Descendre dans l'arbre jusqu'à atteindre une feuille
        while (!y.isLeaf()) {
            if (z.key < y.left.key) {
                y = y.left;
            } else if (y.middle != null && z.key < y.middle.key) {
                y = y.middle;
            } else {
                y = y.right;
            }
        }

        // À ce point, y est le parent où z doit être inséré
        Node x = y.parent;
        Node split = insertAndSplit(x, z);

        // Réorganiser l'arbre en remontant, si nécessaire
        while (x != root && split != null) {
            x = x.parent;
            split = insertAndSplit(x, split);
        }

        // Si un split est retourné à la racine, créer un nouveau nœud w comme racine
        if (split != null) {
            Node w = new Node(null); // Créer un nouveau nœud interne w
            setChildren(w, root, split, null);
            root = w; // Mettre à jour la racine de l'arbre
        }
    }


    public Node borrowOrMerge(Node y) {
        Node z = y.parent; // Le parent de y
        Node x; // Le frère de y à partir duquel on peut emprunter ou avec lequel on peut fusionner

        if (y == z.left) { // y est l'enfant de gauche de z
            x = z.middle; // Le frère de y est le milieu
            if (x.right != null) { // x peut prêter un enfant
                setChildren(y, y.left, x.left, null);
                setChildren(x, x.middle, x.right, null);
            } else { // Fusionner x et y
                setChildren(x, y.left, x.left, x.middle);
                delete(y); // Supprimez y de l'arbre
                setChildren(z, x, z.right, null);
            }
        } else if (y == z.middle) { // y est l'enfant du milieu de z
            x = z.left; // Le frère de y est le gauche
            if (x.right != null) { // x peut prêter un enfant
                setChildren(y, x.right, y.left, null);
                setChildren(x, x.left, x.middle, null);
            } else { // Fusionner x et y
                setChildren(x, x.left, x.middle, y.left);
                delete(y); // Supprimez y de l'arbre
                setChildren(z, x, z.right, null);
            }
        } else { // y est l'enfant de droite de z
            x = z.middle; // Le frère de y est le milieu
            if (x.right != null) { // x peut prêter un enfant
                setChildren(y, x.right, y.left, null);
                setChildren(x, x.left, x.middle, null);
            } else { // Fusionner x et y
                setChildren(x, x.left, x.middle, y.left);
                delete(y); // Supprimez y de l'arbre
                setChildren(z, z.left, x, null);
            }
        }

        return z; // Retournez le parent après l'emprunt ou la fusion
    }



    private void delete(Node node) {
        if (node == null || node.parent == null) {
            // Le nœud est null ou est la racine sans parent (cas spécial)
            return;
        }

        Node parent = node.parent;

        // Détecter la position de node par rapport à son parent et ajuster les liens en conséquence
        if (parent.left == node) {
            parent.left = null; // node est l'enfant gauche
        } else if (parent.middle == node) {
            parent.middle = null; // node est l'enfant du milieu
        } else if (parent.right == node) {
            parent.right = null; // node est l'enfant droit
        }

        // Après avoir retiré node, vérifier si parent doit emprunter un enfant ou fusionner
        if ((parent.left == null && parent.middle == null) || (parent.middle == null && parent.right == null)) {
            // Si le parent a maintenant moins de deux enfants, il pourrait avoir besoin d'emprunter ou de fusionner
            borrowOrMerge(parent);
        } else {
            // Sinon, simplement mettre à jour la clé du parent si nécessaire
            updateKey(parent);
        }
    }



}

