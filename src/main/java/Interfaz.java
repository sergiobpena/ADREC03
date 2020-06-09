
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;

public class Interfaz {
    private Scanner sc = new Scanner(System.in);
    private Facade fc;
    private static Interfaz inf = null;

    private Interfaz(String rutaDb, String xml, String xml_actualizacion) {
        this.fc = Facade.getFacade(rutaDb, xml, xml_actualizacion);
    }

    public static Interfaz getInterfaz(String absolutePath, String xml, String xml_actualizado) {
        if (inf == null) {
            inf = new Interfaz(absolutePath, xml, xml_actualizado);
        }
        return inf;
    }

    public void selector() {
        Boolean flag = true;
        while (flag) {
            System.out.print("\n\nIndique a operacion que desexe realizar, introducindo o numero correspondente: \n\n"
                    + "[1]-Paises con mais casos que un determinado numero a introducir \t \n"
                    + "[2]-Maior numero de mortes nun dia  por pais \n"
                    + "[3]-Listado de paises con mais casos detectados por dia \n"
                    + "[4]-Listado de paises con maior crecemento de casos detectados nun dia a introducir \n"
                    + " \n\n"
                    + "[10]-Actualizar información co arquivo coronavirus_actualizado.xml \n"
                    + "[99]-Finaliza programa \n\n[Operacion]: ");
            int operacion = this.sc.nextInt();


            switch (operacion) {
                case 1:
                    System.out.println("Introduce numero de casos detectados : ");
                    try {
                        String numero = this.sc.next();
                        fc.listaListaPaisesNumero(Integer.parseInt(numero));
                    }catch (NumberFormatException e){
                        System.out.println("Introducir numero enteiro");
                        break;
                    }
                    break;
                case 2:
                    System.out.println("Maximo de casos nun dia por pais : ");
                    fc.paisMaisCasosDia();
                    break;
                case 3:
                    System.out.println("Listado de paises con mais casos por dia");
                    fc.paisMaisCasosPorDia();
                    break;
                case 4:
                    System.out.println("Introduce data en formato dd/MM/yyyy");
                    String dataString = this.sc.next();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date data = null;
                    try {
                        if(!this.comprobaFormatoData(dataString)) throw new ParseException("data Erronea",1);
                        data = sdf.parse(dataString);
                        fc.maximoCrecementoPorDia(data);
                    } catch (ParseException e) {
                        System.out.println("Erro no formato da data introducida");
                    }

                    break;

                case 10:
                    System.out.println("Cargando arquivo actualización");
                    fc.actualizarSerie();
                    System.out.println("Datos actualizados");
                    break;
                case 99:
                    flag = false;
                    break;
            }
        }
        System.out.println("Saindo do programa");
        fc.pechaConexion();
    }


    private boolean comprobaFormatoData(String s){
        String[] separado=s.split("/");
        if ((separado.length==3)&&separado[0].length()==2 && separado[1].length()==2 && separado[2].length()==4){
            return true;
        }else{
            return false;
        }
    }



}
