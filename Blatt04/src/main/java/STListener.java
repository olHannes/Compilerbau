public class STListener extends Blatt04BaseListener {
  private Scope scope;
  private boolean inFunction = false;

  @Override
  public void enterProgram(Blatt04Parser.ProgramContext ctx) {
    scope = new Scope(null);
    scope.bind(new BuiltIn("int"));
    scope.bind(new BuiltIn("bool"));
    scope.bind(new BuiltIn("string"));
  }

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
  public void enterFndecl(Blatt04Parser.FndeclContext ctx) {
    Symbol resolve = scope.resolve(ctx.ID().getText());
    try {
      Function functionResolve = (Function) resolve;
      if (functionResolve != null) {
        System.err.println("Function " + ctx.ID().getText() + " has already been defined!");
      } else {
        throw new NullPointerException();
      }
    } catch (Exception e) {
      inFunction = true;
      STType type = (STType) scope.resolve(ctx.type().getText());
      if (type != null) {
        scope.bind(new Function(ctx.ID().getText(), type));
      } else {
        System.err.println("Invalid type");
      }
    }
  }

  @Override
  public void exitFndecl(Blatt04Parser.FndeclContext ctx) {
    inFunction = false;
    scope = scope.getParent();
  }

  @Override
  public void enterParams(Blatt04Parser.ParamsContext ctx) {
    Scope newScope = new Scope(scope);
    scope = newScope;
    for (int i = 0; i < ctx.type().size(); i++) {
      STType type = (STType) scope.resolve(ctx.type(i).getText());
      if (type != null) {
        scope.bind(new Variable(ctx.ID(i).getText(), type));
      } else {
        System.err.println("Invalid type");
      }
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
  public void enterBlock(Blatt04Parser.BlockContext ctx) {
    Scope newScope = new Scope(scope);
    scope = newScope;
  }

  @Override
  public void exitBlock(Blatt04Parser.BlockContext ctx) {
    scope = scope.getParent();
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

  @Override
  public void exitType(Blatt04Parser.TypeContext ctx) {
    Symbol resolve = scope.resolve(ctx.getChild(0).getText());
    if (resolve == null) {
      System.err.println("Type " + ctx.getChild(0).getText() + " is invalid!");
    }
  }
}
