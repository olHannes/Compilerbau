import java.util.ArrayList;
import java.util.List;

public class ASTNode {
  private ASTType type;
  private String value;
  private List<ASTNode> children = new ArrayList<>();

  public ASTNode(ASTType type) {
    this.type = type;
  }

  public ASTNode(ASTType type, String value) {
    this.type = type;
    this.value = value;
  }

  public void addChild(ASTNode child) {
    children.add(child);
  }

  public ASTType getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<ASTNode> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return type + (value != null ? ": " + value : " ");
  }
}
