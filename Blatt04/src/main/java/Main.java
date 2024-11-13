import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


//https://www.hsbi.de/elearning/data/FH-Bielefeld/lm_data/lm_1360443/homework/sheet04.html
public class Main {


  public static void printTree(ASTNode node, String indent, boolean isLast) {

    System.out.print(indent);
    if (isLast) {
      System.out.print("L__ ");
      indent += "    ";
    } else {
      System.out.print("|-- ");
      indent += "|   ";
    }
    System.out.println(node);

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
