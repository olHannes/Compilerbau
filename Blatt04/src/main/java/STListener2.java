public class STListener2 extends STListener {
  private Scope scope;
  private boolean inFunction = false;

  @Override
  public void exitVardecl(Blatt04Parser.VardeclContext ctx) {
    Symbol resolve = scope.resolveCurrent(ctx.ID().getText());
    if (resolve != null) {
      System.err.println("Variable " + ctx.ID().getText() + " has already been defined!");
      return;
    }
    if (inFunction) {
      Symbol resolveParent = scope.getParent().resolveCurrent(ctx.ID().getText());
      if (resolveParent != null) {
        System.err.println(
            "Variable " + ctx.ID().getText() + " has already been defined in Parameters!");
        return;
      }
    }
    STType type = (STType) scope.resolve(ctx.type().getText());
    scope.bind(new Variable(ctx.ID().getText(), type));
  }

  @Override
  public void exitAssign(Blatt04Parser.AssignContext ctx) {
    Symbol resolve = scope.resolve(ctx.ID().getText());
    if (resolve == null) {
      System.err.println("Variable " + ctx.ID().getText() + " has not been defined!");
    }
  }

  @Override
  public void exitFncall(Blatt04Parser.FncallContext ctx) {
    Symbol resolve = scope.resolve(ctx.ID().getText());
    if (resolve == null) {
      System.err.println("Function " + ctx.ID().getText() + " has not been defined!");
      return;
    }
    try {
      Function resolveFunction = (Function) resolve;
    } catch (Exception e) {
      System.err.println("Function " + ctx.ID().getText() + " has not been defined!");
    }
  }

  @Override
  public void exitExpr(Blatt04Parser.ExprContext ctx) {
    if (ctx.ID() != null) {
      Symbol resolve = scope.resolve(ctx.ID().getText());
      if (resolve == null) {
        System.err.println("Variable " + ctx.ID().getText() + " has not been defined!");
      }
    }
  }
}
