import java.util.ArrayList;
import java.util.List;

public class ASTNode {
  private String type;
  private String value;
  private List<ASTNode> children = new ArrayList<>();

  public ASTNode(String type) {
    this.type = type;
  }

  public ASTNode(String type, String value) {
    this.type = type;
    this.value = value;
  }

  public void addChild(ASTNode child) {
    children.add(child);
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public List<ASTNode> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return type + (value != null ? ": " + value : " ");
  }
}
