public class ASTVisitor extends Blatt04BaseVisitor<ASTNode> {
  @Override
  public ASTNode visitProgram(Blatt04Parser.ProgramContext ctx) {
    ASTNode programNode = new ASTNode(ASTType.PROGRAM); // "Program" hat keinen eigenen Wert
    for (var stmtCtx : ctx.stmt()) {
      programNode.addChild(visit(stmtCtx));
    }
    return programNode;
  }

  @Override
  public ASTNode visitStmt(Blatt04Parser.StmtContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public ASTNode visitVardecl(Blatt04Parser.VardeclContext ctx) {
    ASTNode vardeclNode = new ASTNode(ASTType.VARDECL);
    vardeclNode.addChild(visit(ctx.type()));
    vardeclNode.addChild(new ASTNode(ASTType.ID, ctx.ID().getText()));
    if (ctx.getChildCount() > 3) {
      vardeclNode.setValue("=");
      vardeclNode.addChild(visit(ctx.expr()));
    }
    return vardeclNode;
  }

  @Override
  public ASTNode visitAssign(Blatt04Parser.AssignContext ctx) {
    ASTNode assignNode = new ASTNode(ASTType.ASSIGN, "=");
    assignNode.addChild(new ASTNode(ASTType.ID, ctx.ID().getText()));
    assignNode.addChild(visit(ctx.expr()));
    return assignNode;
  }

  @Override
  public ASTNode visitFndecl(Blatt04Parser.FndeclContext ctx) {
    ASTNode fndeclNode = new ASTNode(ASTType.FNDECL);
    fndeclNode.addChild(visit(ctx.type()));
    fndeclNode.addChild(new ASTNode(ASTType.ID, ctx.ID().getText()));
    if (ctx.params() != null) {
      fndeclNode.addChild(visit(ctx.params()));
    }
    fndeclNode.addChild(visit(ctx.block()));
    return fndeclNode;
  }

  @Override
  public ASTNode visitParams(Blatt04Parser.ParamsContext ctx) {
    ASTNode paramsNode = new ASTNode(ASTType.PARAMS);
    for (int i = 0; i < ctx.type().size(); i++) {
      paramsNode.addChild(visit(ctx.type(i)));
      paramsNode.addChild(new ASTNode(ASTType.ID, ctx.ID(i).getText()));
    }
    return paramsNode;
  }

  @Override
  public ASTNode visitReturn(Blatt04Parser.ReturnContext ctx) {
    ASTNode returnNode = new ASTNode(ASTType.RETURN, "return");
    returnNode.addChild(visit(ctx.expr()));
    return returnNode;
  }

  @Override
  public ASTNode visitFncall(Blatt04Parser.FncallContext ctx) {
    ASTNode fncallNode = new ASTNode(ASTType.FNCALL);
    fncallNode.addChild(new ASTNode(ASTType.ID, ctx.ID().getText()));
    if (ctx.args() != null) {
      fncallNode.addChild(visit(ctx.args()));
    }
    return fncallNode;
  }

  @Override
  public ASTNode visitArgs(Blatt04Parser.ArgsContext ctx) {
    if (ctx.getChildCount() > 1) {
      ASTNode argsNode = new ASTNode(ASTType.ARGS);
      for (var child : ctx.expr()) {
        argsNode.addChild(visit(child));
      }
      return argsNode;
    } else {
      return visit(ctx.expr(0));
    }
  }

  @Override
  public ASTNode visitBlock(Blatt04Parser.BlockContext ctx) {
    ASTNode blockNode = new ASTNode(ASTType.BLOCK);
    for (var child : ctx.stmt()) {
      blockNode.addChild(visit(child));
    }
    return blockNode;
  }

  @Override
  public ASTNode visitWhile(Blatt04Parser.WhileContext ctx) {
    ASTNode whileNode = new ASTNode(ASTType.WHILE, "while");
    whileNode.addChild(visit(ctx.expr()));
    whileNode.addChild(visit(ctx.block()));
    return whileNode;
  }

  @Override
  public ASTNode visitCond(Blatt04Parser.CondContext ctx) {
    ASTNode condNode = new ASTNode(ASTType.COND, "if");
    condNode.addChild(visit(ctx.expr()));
    for (var block : ctx.block()) {
      condNode.addChild(visit(block));
    }
    return condNode;
  }

  @Override
  public ASTNode visitExpr(Blatt04Parser.ExprContext ctx) {
    if (ctx.getChildCount() == 1) {
      return ctx.NUMBER() != null
          ? new ASTNode(ASTType.NUMBER, ctx.NUMBER().getText())
          : ctx.ID() != null
              ? new ASTNode(ASTType.ID, ctx.ID().getText())
              : ctx.STRING() != null
                  ? new ASTNode(ASTType.STRING, ctx.STRING().getText())
                  : visit(ctx.fncall());
    } else {
      if (ctx.expr().size() == 1) {
        return visit(ctx.expr(0));
      } else {
        ASTNode exprNode = new ASTNode(ASTType.EXPR, ctx.getChild(1).getText());
        exprNode.addChild(visit(ctx.expr(0)));
        exprNode.addChild(visit(ctx.expr(1)));
        return exprNode;
      }
    }
  }

  @Override
  public ASTNode visitType(Blatt04Parser.TypeContext ctx) {
    return new ASTNode(ASTType.TYPE, ctx.getText());
  }
}
