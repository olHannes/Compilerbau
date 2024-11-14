import java.util.HashMap;

public class Scope {
  private Scope parent;
  private HashMap<String, Symbol> symbols;

  public Scope(Scope parent) {
    this.parent = parent;
    symbols = new HashMap<>();
  }

  public void bind(Symbol symbol) {
    symbol.setScope(this);
    symbols.put(symbol.getName(), symbol);
  }

  public Symbol resolve(String name) {
    Symbol value = symbols.get(name);
    if (value != null) {
      return value;
    }
    try {
      return parent.resolve(name);
    } catch (Exception e) {
      return null;
    }
  }

  public Symbol resolveCurrent(String name) {
    return symbols.get(name);
  }

  public Scope getParent() {
    return parent;
  }
}
