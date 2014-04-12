// Encuentra conexiones usando escalada de colina
import java.util.*;
import java.io.*;
// Informacion de vuelo
class InfoVuelos {
  String desde;
  String hasta;
  int distancia;
  boolean saltar; // usado en el backtracking
  InfoVuelos(String f, String t, int d) {
    desde = f;
    hasta = t;
    distancia = d;
    saltar = false;
  }
}
