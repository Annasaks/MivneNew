
public class Tree {
     Node root;
     Node min ;


    public Tree() {
        this.root =null;

        this.min=null;

        Node x = new Node(Integer.MAX_VALUE); // Internal node with key +∞
        Node l = new Node(Integer.MIN_VALUE); // Leaf node with key -∞
        Node m = new Node(Integer.MAX_VALUE); // Leaf node with key +∞

        // Setting up the parent-child relationships
        l.parent = m.parent = x;
        x.left = l;
        x.middle = m;

        // The right child is not used in the initial configuration
        x.right = null;
        x.parent=null;
        // Setting the root of the tree
        this.root = x;

        this.min = l;

    }



    public void displayTree() {
        displaySubTree(root, "", true);
    }

    private void displaySubTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key );
            // Plusieurs enfants nécessitent une logique pour gérer correctement l'affichage
            int nonNullChildren = 0;
            if (node.left != null) nonNullChildren++;
            if (node.middle != null) nonNullChildren++;
            if (node.right != null) nonNullChildren++;

            if (nonNullChildren > 0) displaySubTree(node.left, prefix + (isTail ? "    " : "│   "), nonNullChildren == 1);
            if (nonNullChildren > 1) displaySubTree(node.middle, prefix + (isTail ? "    " : "│   "), nonNullChildren == 2);
            if (nonNullChildren > 2) displaySubTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }


    //SEARCH UNE KEY

    public Node search(int key) {
        return search(root, key);
    }

    public Node search(Node x, int key) {
        if (x == null) {
            return null; // Retourne null si x est null pour gérer les appels sur des nœuds inexistants
        }

        // Si x est une feuille, vérifier la clé
        if (x.isLeaf()) {
            if (x.key==key) {
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
        if (root == null ) {
            throw new IllegalStateException("T is empty");
        }

        Node x = root;
        while (!x.isLeaf()) {
            x = x.left; // Toujours aller à gauche pour trouver le minimum
        }
        x = x.parent.middle;

        if (x.key!=Integer.MAX_VALUE) {
            return x;
        } else {
            throw new IllegalStateException("T is empty");
        }
    }

    public Node treeMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.parent.middle;
    }



    public Node treeSuccessor(Node x) {
        if (x.right != null) {
            return treeMinimum(x.right);
        }
        Node y = x.parent;
        Node z =x;
        while (y != null && z == y.right) {
            z = y;
            y = y.parent;
        }
        return y;
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

        //if (x.middle== null && x.right!=null){
          //  x.middle=x.right;
          //  delete(x.right);
        //}




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
        Node y = new Node(); // La clé sera mise à jour par setChildren

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
        updateSizeInsert(y);
        Node split = insertAndSplit(x, z);

        updateSizeInsert(y);


        // Réorganiser l'arbre en remontant, si nécessaire
        while (x != root && split != null) {
            x = x.parent;
            updateSizeInsert(x);
            split = insertAndSplit(x, split);
        }

        // Si un split est retourné à la racine, créer un nouveau nœud w comme racine
        if (split != null) {
            Node w = new Node(); // Créer un nouveau nœud interne w
            setChildren(w, root, split, null);
            root = w; // Mettre à jour la racine de l'arbre
        }

        this.min = findMinimum();
    }

    public void updateSizeInsert(Node x ){
        Node y =x;
        while(y!=null) {
            y.size++;
            y=y.parent;

        }
    }

    public void updateSizeDelete(Node x ){
        Node y =x.parent;
        while(y!=null) {
            y.size--;
            y=y.parent;

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
                //delete(y); // Supprimez y de l'arbre
                setChildren(z, x, z.right, null);
            }
        } else if (y == z.middle) { // y est l'enfant du milieu de z
            x = z.left; // Le frère de y est le gauche
            if (x.right != null) { // x peut prêter un enfant
                setChildren(y, x.right, y.left, null);
                setChildren(x, x.left, x.middle, null);
            } else { // Fusionner x et y
                setChildren(x, x.left, x.middle, y.left);
                //delete(y); // Supprimez y de l'arbre
                setChildren(z, x, z.right, null);
            }
        } else { // y est l'enfant de droite de z
            x = z.middle; // Le frère de y est le milieu
            if (x.right != null) { // x peut prêter un enfant
                setChildren(y, x.right, y.left, null);
                setChildren(x, x.left, x.middle, null);
            } else { // Fusionner x et y
                setChildren(x, x.left, x.middle, y.left);
                //delete(y); // Supprimez y de l'arbre
                setChildren(z, z.left, x, null);
            }
        }

        return z; // Retournez le parent après l'emprunt ou la fusion
    }



    public void delete(Node node) {
        Node x = search(node.key);
        updateSizeDelete(x);


        if (x == null) {
            return; // La clé n'existe pas dans l'arbre
        }

        Node y = x.parent;


        // Détecter la position de node par rapport à son parent et ajuster les liens en conséquence
        if (x == y.left) {
            setChildren(y, y.middle, y.right, null);
        } else if (x == y.middle) {
            setChildren(y, y.left, y.right, null);
        } else { // x est y.right
            setChildren(y, y.left, y.middle, null);
        }

        x.left = x.middle = x.right = x.parent = null;

        while (y != null) {
            if (y.middle != null) {

                // Pas nécessaire si l'arbre suit déjà les règles après la suppression
                updateKey(y);
                y = y.parent;
            }
            else {

                if (y != root) {
                    y = borrowOrMerge(y);
                }
                else {
                    root = y.left != null ? y.left : y.right;
                    if (root != null) {
                        root.parent = null;
                    }
                    this.min = findMinimum();
                    break;
                }

                this.min = findMinimum();
    }

            this.min = findMinimum();
        }

    }
}


