public class ASTBuilder extends Blatt03BaseVisitor<ASTNode> {

  @Override
  public ASTNode visitProgram(Blatt03Parser.ProgramContext ctx) {
    ASTNode programNode = new ASTNode("Program", ""); // "Program" hat keinen eigenen Wert
    for (Blatt03Parser.StmtContext stmtCtx : ctx.stmt()) {
      ASTNode stmtNode = visit(stmtCtx);
      programNode.addChild(stmtNode);
    }
    return programNode;
  }

  @Override
  public ASTNode visitStmt(Blatt03Parser.StmtContext ctx) {
    return visit(ctx.getChild(0)); // Stmt ist immer eine Zuweisung, While, If, etc.
  }

  @Override
  public ASTNode visitAssign(Blatt03Parser.AssignContext ctx) {
    ASTNode assignNode = new ASTNode("Assign", ":=");
    ASTNode idNode = new ASTNode("ID", ctx.ID().getText());
    ASTNode exprNode = visit(ctx.expr()); // Besuch der Ausdrucksregel
    assignNode.addChild(idNode);
    assignNode.addChild(exprNode);
    return assignNode;
  }

  @Override
  public ASTNode visitWhile(Blatt03Parser.WhileContext ctx) {
    ASTNode whileNode = new ASTNode("While", "while");
    ASTNode condNode = visit(ctx.cond());
    ASTNode blockNode = visit(ctx.block());
    whileNode.addChild(condNode);
    whileNode.addChild(blockNode);
    return whileNode;
  }

  @Override
  public ASTNode visitBlock(Blatt03Parser.BlockContext ctx) {
    ASTNode blockNode = new ASTNode("Block", "do");
    for (Blatt03Parser.StmtContext stmtCtx : ctx.stmt()) {
      ASTNode stmtNode = visit(stmtCtx);
      blockNode.addChild(stmtNode);
    }
    return blockNode;
  }

  @Override
  public ASTNode visitCasedef(Blatt03Parser.CasedefContext ctx) {
    ASTNode caseNode = new ASTNode("If", "if");
    ASTNode condNode = visit(ctx.cond());
    ASTNode blockNode = visit(ctx.block());

    caseNode.addChild(condNode);
    caseNode.addChild(blockNode);

    if (ctx.getChildCount() > 4) { // Wenn ein 'else' vorhanden ist
      ASTNode elseNode = new ASTNode("Else", "else");
      ASTNode elseBlockNode = visit(ctx.block());
      caseNode.addChild(elseNode);
      caseNode.addChild(elseBlockNode);
    }

    return caseNode;
  }

  @Override
  public ASTNode visitCond(Blatt03Parser.CondContext ctx) {
    ASTNode condNode = new ASTNode("Cond", ctx.getChild(1).getText()); // Operator ==, !=, etc.
    ASTNode leftExprNode = visit(ctx.expr(0));
    ASTNode rightExprNode = visit(ctx.expr(1));
    condNode.addChild(leftExprNode);
    condNode.addChild(rightExprNode);
    return condNode;
  }

  @Override
  public ASTNode visitExpr(Blatt03Parser.ExprContext ctx) {
    if (ctx.getChildCount() == 1) {
      // Direktwert (ID, INT, STRING)
      return new ASTNode("Expr", ctx.getText());
    }

    // Operator-Ausdruck
    ASTNode exprNode = new ASTNode("Expr", ctx.getChild(1).getText()); // Operator
    ASTNode leftExpr = visit(ctx.expr(0));
    ASTNode rightExpr = visit(ctx.expr(1));
    exprNode.addChild(leftExpr);
    exprNode.addChild(rightExpr);
    return exprNode;
  }
}
