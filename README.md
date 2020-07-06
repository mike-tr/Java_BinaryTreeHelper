# JavaBinaryTreeHelper
Just some ez of life, function for binary trees

functions:

printTree(Node root, int depth)
prints a tree, up to the specified depth.


getTreeOrder(Node root, order type, boolean log)
generate a tree, inorder/preorder/posrtorder array index.


GenerateTree(boolean full, int height, float failp)
generate a random tree, with height of height.

full -> a full tree.
if !full -> generate a tree with missing leaves/crosses.
failp -> in case of not full tree the chance that a node would be empty.


TreeFromInOrderAndPreOrder(int inorder[], int preorder[])
generate a tree from the tree inorder and preorder arrays.
