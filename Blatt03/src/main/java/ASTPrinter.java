public class ASTPrinter {
  private int tabLevel = 0;
  private static final String tab = "    "; // 4 Leerzeichen pro Tab

  private String indent() {
    return tab.repeat(tabLevel);
  }

  private boolean containsElse(ASTNode node) {
    for (ASTNode child : node.getChildren()) {
      if (child.getValue().contains("else")) {
        return true;
      }
    }
    return false;
  }

  public String visit(ASTNode node) {
    StringBuilder sb = new StringBuilder();

    switch (node.getType()) {
      case "Program":
        for (ASTNode child : node.getChildren()) {
          sb.append(visit(child)).append("\n");
        }
        break;

      case "Stmt":
        sb.append(indent()).append(visit(node.getChildren().get(0))).append("\n");
        break;

      case "Assign":
        sb.append(visitAssign(node));
        break;

      case "While":
        sb.append(visitWhile(node));
        break;

      case "Block":
        sb.append(visitBlock(node));
        break;

      case "Casedef":
        sb.append(visitCasedef(node));
        break;

      case "Cond":
        sb.append(visitCond(node));
        break;

      case "Expr":
        sb.append(visitExpr(node));
        break;

      default:
        sb.append(node.getValue()); // Return value for other node types
    }

    return sb.toString();
  }

  // Handling Assign statements
  private String visitAssign(ASTNode node) {
    StringBuilder sb = new StringBuilder();
    sb.append(indent())
        .append(node.getChildren().get(0).getValue()) // ID
        .append(" := ")
        .append(visit(node.getChildren().get(1))); // Expression
    return sb.toString();
  }

  // Handling While statements
  private String visitWhile(ASTNode node) {
    StringBuilder sb = new StringBuilder();
    sb.append(indent())
        .append("while ")
        .append(visit(node.getChildren().get(0))) // Condition
        .append(" ")
        .append(visit(node.getChildren().get(1))); // Block
    return sb.toString();
  }

  // Handling Block statements
  private String visitBlock(ASTNode node) {
    StringBuilder sb = new StringBuilder();
    sb.append("do\n");
    tabLevel++;
    for (ASTNode stmt : node.getChildren()) {
      sb.append(visit(stmt)).append("\n");
    }
    tabLevel--;
    sb.append(indent()).append("end");
    return sb.toString();
  }

  // Handling If-Else (Casedef)
  private String visitCasedef(ASTNode node) {
    StringBuilder sb = new StringBuilder();
    sb.append(indent())
        .append("if ")
        .append(node.getValue().toString())
        .append(visit(node.getChildren().get(0))) // Condition
        .append(" ")
        .append(visit(node.getChildren().get(1))); // Then Block

    if (node.getChildren().size() > 2) { // If-Else case
      sb.append("\n")
          .append(indent())
          .append("else ")
          .append(visit(node.getChildren().get(2))); // Else Block
    }
    return sb.toString();
  }

  // Handling Conditions
  private String visitCond(ASTNode node) {
    StringBuilder sb = new StringBuilder();
    sb.append(visit(node.getChildren().get(0))) // Expression 1
        .append(" ")
        .append(node.getChildren().get(1).getValue()) // Operator (==, !=, >, <)
        .append(" ")
        .append(visit(node.getChildren().get(2))); // Expression 2
    return sb.toString();
  }

  // Handling Expressions (for both operators and literals)
  private String visitExpr(ASTNode node) {
    if (node.getChildren().size() == 1) {
      return node.getChildren().get(0).getValue(); // Direct value (ID, INT, STRING)
    } else if (node.getChildren().size() == 3) {
      return visit(node.getChildren().get(0))
          + " "
          + node.getValue()
          + " "
          + visit(node.getChildren().get(2)); // Binary operators
    }
    return "";
  }
}
