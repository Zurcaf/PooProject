
// definir o pacote
// para que serve isto? para organizar o codigo em pacotes
package exemplo_fireship;

public class helloWorld {
    public static void main(String[] args) {
        
        // definir uma variavel: type name = value;
        String hello = "Hello, World!";
        //String name = "Lucas";

        // printar a variavel
        System.out.println(hello); 

    }
    
    // definir um metodo: public static type name(params): return {code}
    // public = acessivel por qualquer classe
    // static = metodo pertence a classe e não a instancia
    public static String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}

//costum class
class coffee {
    //atributos
    String blend;
    boolean roast;
    int price;

    //constructor
    coffee(int price){
        this.price = price;
    }

    //metodos
    String brew () {
        return "Brewing coffee";
    }
}

class Main {
    public static void main(String[] args) {
        coffee joe = new coffee(10);
    }
}
