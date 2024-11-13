import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  public static void printTree(ASTNode node, String indent, boolean isLast) {
    // Ausgabe des aktuellen Knotens mit Einrückung und Verzweigungssymbol
    System.out.print(indent);
    if (isLast) {
      System.out.print("L__ "); // Letzter Knoten auf der aktuellen Ebene
      indent += "    ";
    } else {
      System.out.print("|-- "); // Weitere Knoten auf der aktuellen Ebene
      indent += "|   ";
    }
    System.out.println(node);

    // Rekursives Durchlaufen der Kinder des aktuellen Knotens
    List<ASTNode> children = node.getChildren();
    for (int i = 0; i < children.size(); i++) {
      printTree(children.get(i), indent, i == children.size() - 1);
    }
  }

  public static void main(String... args) throws IOException {
    String input =
        "int f95(int n) {\n"
            + "    if (n == 0) {\n"
            + "        return 1;\n"
            + "    } else {\n"
            + "        if (n == 1) {\n"
            + "            return 1;\n"
            + "        } else {\n"
            + "            return f95(n - 1) + f95(n - 2) + f95(n - 3) + f95(n - 4) + f95(n - 5);\n"
            + "        }\n"
            + "    }\n"
            + "}\n"
            + "\n"
            + "int n = 10;\n"
            + "f95(n);";
    Blatt04Lexer lexer = new Blatt04Lexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Blatt04Parser parser = new Blatt04Parser(tokens);

    ParseTree tree = parser.program();
    ASTVisitor visitor = new ASTVisitor();
    ASTNode node = visitor.visit(tree);
    printTree(node, "", true);
  }
}
