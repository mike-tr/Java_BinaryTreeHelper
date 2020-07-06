import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum order{
    post,
    in,
    pre,
}

public class BinaryTreeHelper {
    public static class Node{
        public Node left,right;
        public int key;

        public Node(int key){
            this.key = key;
        }
    }

    public static void main(String[] args) {
        Node root = GenerateTree(4);

        root = TreeFromInOrderAndPreOrder(new int[]{3,4,5,6,7,8}, new int[]{5,4,3,7,6,8});
        printTree(root, 4);

        root = TreeFromInOrderAndPreOrder(new int[]{2,6,4,7,1,3,8,5,9,10}, new int[]{1,2,4,6,7,3,5,8,9,10});
        printTree(root, 4);

        root = TreeFromInOrderAndPreOrder(new int[]{1,3,5,7,9,11,13,15,17,19,21,23}, new int[]{9,3,1,7,5,15,13,11,19,17,21,23});
        printTree(root, 4);

        getTreeOrder(root, order.post, true);
        getTreeOrder(root, order.in, true);
        getTreeOrder(root, order.pre, true);
    }

    public static Node TreeFromInOrderAndPreOrder(int inorder[], int preorder[]){
        if(inorder.length != preorder.length){
            System.out.println("invalid input, inorder and preorder length dont match");
            return null;
        }else if(inorder.length < 1){
            System.out.println("the tree has 0, nodes!");
            return null;
        }
        class subTree {
             Node FindSub(int istart, int iend, int pstart, int pend){
                if(istart >= iend){
                    return null;
                }
                Node current = new Node(preorder[pstart]);
                ArrayList<Integer> sub = new ArrayList<>();
                int newM = istart;
                for (; newM < iend; newM++) {
                    if(inorder[newM] != current.key){
                        sub.add(inorder[newM]);
                    }else{
                        break;
                    }
                }

                int mid = pstart + 1;
                for (;mid < pend; mid++) {
                    if(!sub.contains(preorder[mid])){
                        break;
                    }
                }
                current.left = this.FindSub(istart, newM, pstart + 1, mid -1);
                current.right = this.FindSub(newM + 1, iend, mid, pend);
                return current;
            }
        }
        Node root = new subTree().FindSub(0, inorder.length, 0, preorder.length);
        return root;
    }

    public static int[] getTreeOrder(Node root, order type, boolean log){
        if(root == null){
            return null;
        }
        ArrayList<Integer> arr = new ArrayList<>();

        class inorder{
            void next(Node current){
                if(current == null){
                    return;
                }
                switch (type) {
                    case in:
                        next(current.left);
                        arr.add(current.key);
                        next(current.right);
                        break;
                    case pre:
                        next(current.left);
                        next(current.right);
                        arr.add(current.key);
                        break;
                    case post:
                        arr.add(current.key);
                        next(current.left);
                        next(current.right);
                        break;
                }
            }
        }
        new inorder().next(root);

        int ret[] = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            ret[i] = arr.get(i);
        }

        if(log){
            System.out.println(Arrays.toString(ret) + " " + type + "-order");
        }
        return ret;
    }


    public static Node GenerateTree(int height){
        return  GenerateTree(true, height, 1);
    }

    public static Node GenerateTree(boolean full, int height, float failp){
        if(failp > 1){
            failp = 1;
        }else if(failp < 0){
            failp = 0;
        }

        Node root = new Node(1);
        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        List<Node> nextLevel = new ArrayList<>();
        int level = 0;

        float fail = 1f;
        //System.out.println(fail + " ," + Math.random());
        while (level < height){
            while (currentLevel.size() > 0){
                Node current = currentLevel.get(0);
                currentLevel.remove(0);

                if(level + 1 == height && currentLevel.size() < 1){
                    fail = 0;
                }

                if(full || Math.random() < fail){
                    current.right = new Node(current.key * 2 + 1);
                    current.left = new Node( current.key * 2);

                    nextLevel.add(current.right);
                    nextLevel.add(current.left);
                }else{
                    if(Math.random() < fail) {
                        if (Math.random() > 0.5) {
                            current.right = new Node(current.key * 2 + 1);
                            nextLevel.add(current.right);
                        } else {
                            current.left = new Node(current.key * 2);
                            nextLevel.add(current.left);
                        }
                    }
                }
            }
            level++;
            fail *= failp;
            currentLevel = nextLevel;
            nextLevel = new ArrayList<>();
        }
        return root;
    }

    public static void printTree(Node root, int depth){
        // this code only prints the tree of to the depth specified, well it can be improved but nah
        String data = "";
        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        int level = 0;
        List<Node> nextLevel = new ArrayList<>();

        int spacesBetweenNodes = 16;
        int maxLength = (int)Math.pow(2, depth-1);
        int spacesBetween = maxLength * spacesBetweenNodes;
        while (level <= depth){
            data += addSpaces(spacesBetween/2);
            while (currentLevel.size() > 0){


                //data += addSpaces(spacesBetween);
                Node current = currentLevel.get(0);
                currentLevel.remove(0);

                String add = "";
                if(current != null) {
                    nextLevel.add(current.left);
                    nextLevel.add(current.right);

                    add += current.key;
                }else{
                    nextLevel.add(null);
                    nextLevel.add(null);
                }
                data += add;
                data += addSpaces(spacesBetween - add.length());
            }
            level++;
            spacesBetween /= 2;
            data += "\n\n";

            currentLevel = nextLevel;
            nextLevel = new ArrayList<>();
        }
        System.out.println(data);
    }

    public static String addSpaces(int c){
        String spaces = "";
        for (int i = 0; i < c; i++) {
            spaces += " ";
        }
        return spaces;
    }
}
