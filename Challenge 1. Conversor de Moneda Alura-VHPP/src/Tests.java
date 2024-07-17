import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tests {
    public static void main(String[] args) {
        // Códigos ANSI para colores
        String blanco = "\u001B[37m";
        String morado = "\u001B[35m";
        String verde = "\u001B[32m";
        String rojo = "\u001B[31m";
        String azul = "\u001B[34m";
        String amarillo = "\u001B[43m"; // Código ANSI para fondo amarillo
        String reset = "\u001B[0m"; // Restablece el color

        String happyFace = " :) ";
        String sadFace = " :( ";
        String surprisedFace = " :o ";

        String menu = """
      %s*******************************************%s
              %s  (¯`·¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.·´¯)
                ( \\                                                                     / )
                 ( )                ░▒▓▆▅▃▂▁  CONVERSOR DE MONEDAS VHPP-ALURA
                                               BIENVENIDOS ( AS)\s ▁▂▃▅▆▓▒░
                  (/                                                                   \\) \s
                   (.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯`·.¸¸.·´¯)  \s
      %s                                                                                                    %s▀▄▀▄▀▄ Hola, soy Víctor Hugo Pérez Pérez,%s
             1) Dollar %s<===(A)===>%s Peso Argentino      2) Peso Argentino %s<===(A)===>%s Dollar                       les presento mi conversor▄▀▄▀▄▀%s 
                                                                                                           ❚█══%sMuchas Gracias Alura, Muchas Gracias Luri%s══█❚
             3) Dollar %s<===(A)===>%s Real Brasileño      4) Real Brasileño %s<===(A)===>%s Dollar                                 
                                                                                                                  
             5) Dollar %s<===(A)===>%s Peso Colombiano     6) Peso Colombiano %s<===(A)===>%s Dollar                                                                                
               
                                                   7) %sSalir%s
            """.formatted(amarillo, reset, morado, reset, rojo, reset, verde, reset, verde, reset, reset, azul, reset,verde, reset, verde,
                reset, verde, reset, verde, reset, rojo, reset);
        int opcionElegida = 0;
        double dolares, pesosArgentinos, realesBrasilenos, pesosColombianos;

        Scanner teclea = new Scanner(System.in);

        while (opcionElegida != 7) {
            System.out.println(menu);
            try {
                opcionElegida = teclea.nextInt(); // opcionElegida tiene que ser igual a un número ingresado por
                // el usuario, que corresponda algún número del menú, por eso debe asignarse a teclea.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingresa un número.");
                teclea.nextLine(); // Limpiar el buffer de entrada
                continue; // Volver al inicio del bucle
            }
            switch (opcionElegida) {
                case 1:
                    System.out.println("Vas a cambiar de Dollar a Peso Argentino");
                    System.out.print("Ingresa la cantidad de dólares: ");
                    dolares = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrlVHPP = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/USD";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrlVHPP))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("ARS");

                        // Realizar la conversión
                        pesosArgentinos = dolares * tipoCambio;
                        System.out.printf("%.2f dólares son %.2f pesos argentinos%n", dolares, pesosArgentinos);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Vas a cambiar de Peso Argentino a Dollar");
                    System.out.print("Ingresa la cantidad de pesos argentinos: ");
                    pesosArgentinos = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrl = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/ARS";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("USD");

                        // Realizar la conversión
                        dolares = pesosArgentinos / tipoCambio;
                        System.out.printf("%.2f pesos argentinos son %.2f dólares%n", pesosArgentinos, dolares);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Vas a cambiar de Dollar a Real Brasileño");
                    System.out.print("Ingresa la cantidad de dólares: ");
                    dolares = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrl = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/USD";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("BRL");

                        // Realizar la conversión
                        realesBrasilenos = dolares * tipoCambio;
                        System.out.printf("%.2f dólares son %.2f reales brasileños%n", dolares, realesBrasilenos);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Vas a cambiar de Real Brasileño a Dollar");
                    System.out.print("Ingresa la cantidad de reales brasileños: ");
                    realesBrasilenos = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrl = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/BRL";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("USD");

                        // Realizar la conversión
                        dolares = realesBrasilenos / tipoCambio;
                        System.out.printf("%.2f reales brasileños son %.2f dólares%n", realesBrasilenos, dolares);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Vas a cambiar de Dollar a Peso Colombiano");
                    System.out.print("Ingresa la cantidad de dólares: ");
                    dolares = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrl = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/USD";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("COP");

                        // Realizar la conversión
                        pesosColombianos = dolares * tipoCambio;
                        System.out.printf("%.2f dólares son %.2f pesos colombianos%n", dolares, pesosColombianos);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Vas a cambiar de Peso Colombiano a Dollar");
                    System.out.print("Ingresa la cantidad de pesos colombianos: ");
                    pesosColombianos = teclea.nextDouble();

                    try {
                        // Construir la URL de la API
                        String apiUrl = "https://v6.exchangerate-api.com/v6/" + ApiConfig.API_KEY + "/latest/COP";

                        // Crear el cliente HTTP y enviar la solicitud
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // Procesar la respuesta JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        double tipoCambio = jsonResponse.getJSONObject("conversion_rates").getDouble("USD");

                        // Realizar la conversión
                        dolares = pesosColombianos / tipoCambio;
                        System.out.printf("%.2f pesos colombianos son %.2f dólares%n", pesosColombianos, dolares);
                    } catch (IOException | InterruptedException | org.json.JSONException e) {
                        System.out.println("Error al consultar la API: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("¡Muy buen día! Se despide, cordialmente, Víctor Hugo Pérez Pérez");
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        }

    }
}