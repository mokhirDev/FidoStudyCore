import java.util.stream.Stream;

public class TreeStream {
    private final TreeNode node;

    public TreeStream(TreeNode node) {
        this.node = node;
    }

    public Stream<TreeNode> stream() {
        return Stream.concat(
                Stream.of(node),
                node.getChildren().stream().flatMap(child->new TreeStream(child).stream())
        );
    }
}
