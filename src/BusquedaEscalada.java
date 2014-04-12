import java.util.*;
import java.io.*;


class BusquedaEscalada {
  final int MAX = 100;
  // Este Array contiene la informacion de los vuelos
  InfoVuelos vuelos[] = new InfoVuelos[MAX];
  
  int numVuelos = 0; // Numero de entradas en el Array de vuelos
  
  Stack btStack = new Stack(); // Backtrack Pila
  
  public static void main(String args[]){
    String hasta, desde;
    BusquedaEscalada ob = new BusquedaEscalada();
    BufferedReader br = new
    BufferedReader(new InputStreamReader(System.in));
    ob.setup();
    try {
      System.out.print("BUSQUEDA ESCALADA \n ");
      System.out.print("Origen: ");
      desde = br.readLine();
      System.out.print("Destino: ");
      hasta = br.readLine();
      ob.esVuelo(desde, hasta);
      if(ob.btStack.size() != 0)
        ob.ruta(hasta);
    } catch (IOException exc) {
      System.out.println("Error de entrada");
    }
  }
  // Inicializo la Base de Datos de los vuelos
  
  void setup(){
	    agregarVuelo("Londres", "Dublin", 288);
	    agregarVuelo("Londres", "Paris", 214);
	    agregarVuelo("Londres", "Berlin", 580);
	    agregarVuelo("Dublin", "Berlin", 820);
	    agregarVuelo("Paris", "Madrid", 655);
	    agregarVuelo("Paris", "Roma", 688);
	    agregarVuelo("Paris", "Dublin", 486);
	    agregarVuelo("Berlin", "Varsovia", 321);
	    agregarVuelo("Berlin", "Praga", 174);
	    agregarVuelo("Praga", "Roma", 574);
	    agregarVuelo("Berlin", "Roma", 737);
	
	
  }
  // Pone los vuelos en la Base de Datos
  void agregarVuelo(String desde, String hasta, int dist)
  {
    if(numVuelos < MAX) {
      vuelos[numVuelos] =
        new InfoVuelos(desde, hasta, dist);
      numVuelos++;
    }
    else System.out.println("Base de datos de vuelos llena.\n");
  }
  // Muestra la RUTA y la DISTANCIA TOTAL
  void ruta(String hasta){
    Stack rev = new Stack();
    int dist = 0;
    InfoVuelos f;
    int num = btStack.size();
    
    System.out.print("Ruta: ");
    
    //Invierte la Pila para mostrar la ruta
    for(int i=0; i < num; i++)
      rev.push(btStack.pop());
    for(int i=0; i < num; i++) {
      f = (InfoVuelos) rev.pop();
      System.out.print(f.desde + " a ");
      dist += f.distancia;
    }
    System.out.println(hasta);
    System.out.println("Distancia total: " + dist);
  }
  
  /*Si hay un vuelo directo entre desde y hasta
   devuelve la distancia del vuelo:
   sino retorna 0*/
  
  int match(String desde, String to){
    for(int i=numVuelos-1; i > -1; i--) {
      if(vuelos[i].desde.equals(desde) && vuelos[i].hasta.equals(to) && !vuelos[i].saltar){
          vuelos[i].saltar = true; //Evita la reutilizacion
          return vuelos[i].distancia;
        }
      }
      return 0; // No encontrado
    }
    //Dado desde, encuentra la conexion hacia destino    
    InfoVuelos encontrar(String desde){
      int pos = -1;
      int dist = 0;
      for(int i=0; i < numVuelos; i++) {
        if(vuelos[i].desde.equals(desde) &&
          !vuelos[i].saltar){
          //Utiliza el vuelo mas largo
          if(vuelos[i].distancia > dist) {
            pos = i;
            dist = vuelos[i].distancia;
          }
        }
      }
      if(pos != -1) {
        vuelos[pos].saltar = true; // Evita la reutilizacion
        InfoVuelos f = new InfoVuelos(vuelos[pos].desde,vuelos[pos].hasta,vuelos[pos].distancia);
        return f;
      }
      return null;
    }
    // Determina si hay una ruta entre desde y hasta.
    void esVuelo(String desde, String hasta){
      int dist;
      InfoVuelos f;
    // 
      dist = match(desde, hasta);
      if(dist != 0) {
    	  btStack.push(new InfoVuelos(desde, hasta, dist));
      return;
    }
    //Buscar otra conexion 
    f = encontrar(desde);
    if(f != null) {
      btStack.push(new InfoVuelos(desde, hasta, f.distancia));
      esVuelo(f.hasta, hasta);
    }
    else if(btStack.size() > 0) {
      //Vuelve y busca otra conexion
      f = (InfoVuelos) btStack.pop();
      esVuelo(f.desde, f.hasta);
    }
  }
}
