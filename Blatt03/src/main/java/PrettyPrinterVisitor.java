public class PrettyPrinterVisitor extends Blatt03BaseVisitor<String> {
  private int tabLevel = 0;
  private static final String tab = "    "; // 4 Leerzeichen pro Tab

  private String indent() {
    return tab.repeat(tabLevel);
  }

  private boolean containsElse(Blatt03Parser.CasedefContext ctx) {
    for (var child : ctx.children) {
      if (child.getText().contains("else")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String visitProgram(Blatt03Parser.ProgramContext ctx) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < ctx.stmt().size(); i++) {
      sb.append(visit(ctx.stmt(i)));
    }
    return sb.toString();
  }

  @Override
  public String visitStmt(Blatt03Parser.StmtContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(indent()).append(visit(ctx.getChild(0))).append("\n");
    return sb.toString();
  }

  @Override
  public String visitExpr(Blatt03Parser.ExprContext ctx) {
    StringBuilder sb = new StringBuilder();
    if (ctx.getChildCount() == 3) {
      sb.append(visit(ctx.expr(0)))
          .append(" ")
          .append(ctx.getChild(1))
          .append(" ")
          .append(visit(ctx.expr(1)));
    } else {
      sb.append(ctx.getText());
    }
    return sb.toString();
  }

  @Override
  public String visitAssign(Blatt03Parser.AssignContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(ctx.ID().getText())
        .append(" ")
        .append(ctx.getChild(1))
        .append(" ")
        .append(visit(ctx.expr()));
    return sb.toString();
  }

  @Override
  public String visitWhile(Blatt03Parser.WhileContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(ctx.getChild(0).getText())
        .append(" ")
        .append(visit(ctx.cond()))
        .append(" ")
        .append(visit(ctx.block()));
    return sb.toString();
  }

  @Override
  public String visitBlock(Blatt03Parser.BlockContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(ctx.getChild(0).getText()).append("\n");
    tabLevel++;
    for (var stmt : ctx.stmt()) {
      sb.append(visit(stmt));
    }
    tabLevel--;
    sb.append(indent()).append(ctx.getChild(ctx.getChildCount() - 1).getText());
    return sb.toString();
  }

  @Override
  public String visitCasedef(Blatt03Parser.CasedefContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(ctx.getChild(0).getText()).append(" ").append(visit(ctx.cond())).append(" ");
    if (containsElse(ctx)) {
      sb.append(ctx.getChild(2).getText()).append("\n");
      tabLevel++;
      int index = 3;
      for (var stmt : ctx.stmt()) {
        index++;
        sb.append(visit(stmt));
      }
      tabLevel--;
      sb.append(indent())
          .append(ctx.getChild(index).getText())
          .append(" ")
          .append(visit(ctx.block()));
    } else {
      sb.append(visit(ctx.block()));
    }

    return sb.toString();
  }

  @Override
  public String visitCond(Blatt03Parser.CondContext ctx) {
    StringBuilder sb = new StringBuilder();
    sb.append(visit(ctx.expr(0)))
        .append(" ")
        .append(ctx.getChild(1).getText())
        .append(" ")
        .append(visit(ctx.expr(1)));
    return sb.toString();
  }
}
