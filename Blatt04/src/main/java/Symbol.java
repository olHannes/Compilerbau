public class Symbol {
  private String name;
  private STType type;
  private Scope scope;

  public Symbol(String name, STType type) {
    this.name = name;
    this.type = type;
  }

  public Symbol(String name) {
    this.name = name;
    type = null;
  }

  public String getName() {
    return name;
  }

  public STType getType() {
    return type;
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
