import java.io.IOException;
import my.pkg.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {

  public String getGreeting() {
    return "Hello World!";
  }

  public static void printAST(ASTNode node, int indentLevel) {
    String indent = "    ".repeat(indentLevel); // 4 Leerzeichen für Einrückung
    System.out.println(indent + node.getType() + " : " + node.getValue());

    // Alle Kinder des Knotens ausgeben
    for (ASTNode child : node.getChildren()) {
      printAST(child, indentLevel + 1); // Rekursive Ausgabe mit höherem Einrückungslevel
    }
  }

  // https://www.hsbi.de/elearning/data/FH-Bielefeld/lm_data/lm_1360443/homework/sheet03.html
  public static void taskBlatt03() {
    System.out.println("Aufgaben: Blatt02\n");
    // Eingabestring direkt definieren
    String input =
        """
                a      := 3 + 4 * 9 /    2
                while a+4 > 6 do c := 2+  2 d :=      3*    5
                while 5 > 7 do c         := 3 end if 3 > 4 do  a :=    3 c := 34+12
                a := 123 if   a > 5 do a := 5 end
                a:= 1+2+  3+515/12+3    # Dies ist ein Kommentar und sollte ignoriert werden
                           else do a := 6 a :=        12
                                while 3 > 5 do a:=3 end end end
                """;

    // Lexer und Token-Stream erzeugen
    Blatt03Lexer lexer = new Blatt03Lexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // Parser erzeugen und Parse-Tree generieren
    Blatt03Parser parser = new Blatt03Parser(tokens);
    ParseTree tree = parser.program();

    System.out.println("Parse Tree:");
    System.out.println(tree.toStringTree(parser));

    // PrettyPrinterVisitor verwenden, um den formatierten Code auszugeben
    PrettyPrinterVisitor prettyVisitor = new PrettyPrinterVisitor();
    String formattedCode = prettyVisitor.visit(tree);

    System.out.println("Formatted Code:");
    System.out.println(formattedCode);

    // AST mit ASTBuilder erstellen
    ASTBuilder astBuilder = new ASTBuilder();
    ASTNode ast = astBuilder.visit(tree);

    // AST ausgeben (hier könnte man die `ASTNode` Struktur durch einen einfachen Besucher
    // durchlaufen)
    System.out.println("AST:");
    printAST(ast, 0);
  }

  public static void main(String... args) throws IOException {

    System.out.println("Start...");

    taskBlatt03(); // Aufgabe für Blatt 03
  }
}
