package com.aluracursos.challenge.Principal;

import com.aluracursos.challenge.Model.DatosGenerales;
import com.aluracursos.challenge.Model.DatosLibros;
import com.aluracursos.challenge.Services.ConsumoAPI;
import com.aluracursos.challenge.Services.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteDatos conversor = new ConvierteDatos();
    private static final Scanner input = new Scanner(System.in);


     public static void muestraElMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
         System.out.println(json);
         var datos = conversor.obtenerDatos(json, DatosGenerales.class);
         System.out.println(datos);


         //Top 10 libros mas descargados
         System.out.println("*****************************");
         System.out.println("Top 10 libros mas descargados");
         System.out.println("*****************************");

         datos.resultados().stream()
                 .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                 .limit(10)
                 .map(l -> l.titulo().toUpperCase())
                 .forEach(System.out::println);


         //Busqueda de libros por nombre

         System.out.println("Ingresa el nombre del libro que deseas buscar: ");
         var tituloLibro = input.nextLine();
         json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" " , "+"));
         var datosLibros = conversor.obtenerDatos(json, DatosGenerales.class);
         Optional <DatosLibros> libroBuscado = datosLibros.resultados().stream()
                 .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                 .findFirst();

         if(libroBuscado.isPresent()) {
             System.out.println("Libro encontrado: " + libroBuscado.get());
         } else {
             System.out.println("Libro no encontrado");
         }


         //Estadisticas
         DoubleSummaryStatistics est = datosLibros.resultados().stream()
                 .filter(i -> i.numeroDescargas() > 0)
                 .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
         System.out.println("Cantidad promedio de descargas: " + est.getAverage());
         System.out.println("Cantidad maxima de descargas: " + est.getMax());
         System.out.println("Cantidad minima de descargas: " + est.getMin());
         System.out.println("Cantidad total de descargas: " + est.getSum());



     }
}
