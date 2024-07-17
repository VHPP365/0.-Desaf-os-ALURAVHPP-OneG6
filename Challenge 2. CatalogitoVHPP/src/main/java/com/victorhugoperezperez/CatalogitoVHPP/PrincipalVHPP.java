package com.victorhugoperezperez.CatalogitoVHPP;

import java.text.Normalizer;
import java.util.*;

// Importa las clases necesarias para la API Gutendex
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class PrincipalVHPP {
    // Lista global para almacenar los libros buscados
    List<String> librosBuscados = new ArrayList<>();

    // Lista global para almacenar los autores buscados
    List<String> autoresBuscados = new ArrayList<>();

    // Mapa para traducir los códigos de idioma a sus nombres completos
    Map<String, String> idiomaMap = new HashMap<>();

    public void muestraElMenu() {    //aquí EMPIEZA el MÉTODO muestraElMenu

        // Inicializa el mapa de idiomas
        idiomaMap.put("es", "español");
        idiomaMap.put("en", "inglés");
        idiomaMap.put("fr", "francés");
        idiomaMap.put("pt", "portugués");
        idiomaMap.put("hu", "húngaro");
        idiomaMap.put("fi", "finlandés");
        idiomaMap.put("ca", "catalán");

        //COMIENZA el MENÜ de OPCIONES
        String menu = """
                                                             SISTEMA DE LIBROS LiterAluraVHPP
                                                           
                                                               
                                                               1) Búsqueda de libros por título o Autor
                                                                              
                                    2) Lista de Libros Buscados                        4) Lista de libros por idiomas 
                                                    
                                    3) Lista de Autores Buscados                       5) Lista de autores vivos en un determinado año
                                                                
                                                                6) Salir de LiterAluraVHPP
                                                                7)Borrar las consultas

                """; //TERMINA EL MENÚ DE OPCIONES

        Integer opcionElegida = 0;
        Scanner teclea = new Scanner(System.in);

        while (opcionElegida != 6) {   //aquí EMPIEZA el ciclo WHILE
            System.out.println(menu);
            try {
                opcionElegida = teclea.nextInt(); // Lee la entrada del usuario
                teclea.nextLine(); // Limpia el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingresa un número.");
                teclea.nextLine(); // Limpia el buffer de entrada
                continue; // Vuelve al inicio del bucle
            }

            switch (opcionElegida) {
//                case 1:
//                    System.out.println("Anota el libro que deseas buscar:");
//                    String tituloLibro = teclea.nextLine();
                case 1:
//
                    System.out.println("Anota el libro o Autor que deseas buscar:");
                    String tituloLibro = teclea.nextLine();

                    // Se notaron errores con la búsqueda de acentos y se Convertió el título del libro a minúsculas y se
                    // eliminaon los acentos
                    tituloLibro = tituloLibro.toLowerCase(); // Convertisión a minúsculas
                    tituloLibro = Normalizer.normalize(tituloLibro, Normalizer.Form.NFD); //eliminación de acentos
                    tituloLibro = tituloLibro.replaceAll("[^\\p{ASCII}]", ""); //por si las moscas




                    // Llama a la API Gutendex y obtiene los resultados
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("https://gutendex.com/books/?search=" + tituloLibro.replace(" ", "+"))
                                .build();
                        Response response = client.newCall(request).execute();

                        if (response.isSuccessful()) {
                            String jsonResponse = response.body().string();
                            JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                            if (results.length() > 0) {
                                System.out.println("Resultados de la Búsqueda");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject book = results.getJSONObject(i);
                                    String title = book.getString("title");
                                    String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                    // Acceder al arreglo "languages"
                                    JSONArray languages = book.getJSONArray("languages");
                                    System.out.print("Idiomas:");
                                    for (int j = 0; j < languages.length(); j++) {
                                        String language = languages.getString(j);
                                        String nombreIdioma = idiomaMap.getOrDefault(language, language);
                                        System.out.print(" " + nombreIdioma);
                                    }
                                    System.out.println();

                                    int downloads = book.getInt("download_count");

                                    System.out.println("Título: " + title);
                                    System.out.println("Autor: " + author);
                                    System.out.println("Número de descargas: " + downloads);
                                    System.out.println("");

                                    // Verifica si el libro ya está en la lista antes de agregarlo
                                    if (!librosBuscados.contains(title)) {
                                        librosBuscados.add(title);
                                    } else if (!librosBuscados.isEmpty()) { // Verifica si la lista no está vacía
                                        //System.out.println("Hola, ya has buscado este libro.");
                                    }

                                    // Verifica si el autor ya está en la lista antes de agregarlo
                                    if (!autoresBuscados.contains(author)) {
                                        autoresBuscados.add(author);
                                    } else if (!autoresBuscados.isEmpty()) { // Verifica si la lista no está vacía
                                        //System.out.println("Hola, ya has buscado a este autor.");
                                    }

                                }
                            } else {
                                System.out.println("No se encontraron libros con ese título.");
                            }
                        } else {
                            System.out.println("Error al conectar con la API Gutendex.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Lista de Libros Buscados:");
                    if (librosBuscados.isEmpty()) {
                        System.out.println("No se han buscado libros aún.");
                    } else {
                        for (String libro : librosBuscados) {
                            System.out.println(" " + libro);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Lista de Autores Buscados:");
                    if (autoresBuscados.isEmpty()) {
                        System.out.println("No se han buscado autores aún.");
                    } else {
                        for (String autor : autoresBuscados) {
                            System.out.println(" " + autor);
                        }
                    }
                    break;

                case 4:
                    System.out.println("""
                                                                             Idiomas de los libros que has buscado
                                                             
                                                                           A)Libros en español (teclea e)
                                                                           
                                                                           B) Libros en Inglés (teclea i)
                                                                           
                                                                           C) Libros en Francés (teclea f)
                                                                           
                                                                           D)Libros en Portugés  (teclea p)
                                                                           
                                                           
                                                           """);

                    Scanner teclea1 = new Scanner(System.in); // Crea un nuevo Scanner para leer la entrada del usuario
                    String opcionIdioma = teclea1.nextLine(); // Lee la entrada del usuario

                    switch (opcionIdioma.toLowerCase()) { // Convierte la entrada a minúsculas para que no sea sensible a mayúsculas
                        case "e":
                        // Muestra los libros en español
                        System.out.println("Libros en español:");
                        for (String libro : librosBuscados) {
                            // Realiza la búsqueda de libros en español en la API Gutendex
                            try {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url("https://gutendex.com/books/?search=" + libro.replace(" ", "+") + "&languages=es") // Busca por libro y idioma
                                        .build();
                                Response response = client.newCall(request).execute();

                                if (response.isSuccessful()) {
                                    String jsonResponse = response.body().string();
                                    JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                                    if (results.length() > 0) {
                                        for (int i = 0; i < results.length(); i++) {
                                            JSONObject book = results.getJSONObject(i);
                                            String title = book.getString("title");
                                            String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                            // Acceder al arreglo "languages"
                                            JSONArray languages = book.getJSONArray("languages");
                                            System.out.print("Idiomas:");
                                            for (int j = 0; j < languages.length(); j++) {
                                                String language = languages.getString(j);
                                                String nombreIdioma = idiomaMap.getOrDefault(language, language);
                                                System.out.print(" " + nombreIdioma);
                                            }
                                            System.out.println();

                                            int downloads = book.getInt("download_count");

                                            System.out.println("Título: " + title);
                                            System.out.println("Autor: " + author);
                                            System.out.println("Número de descargas: " + downloads);
                                            System.out.println("");
                                        }
                                    } else {
                                        System.out.println("No se encontraron libros en español con ese título.");
                                    }
                                } else {
                                    System.out.println("Error al conectar con la API Gutendex.");
                                }
                            } catch (Exception e) {
                                System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                            }
                        }
                        break;


                        case "i":
                            // Muestra los libros en inglés
                            System.out.println("Libros en inglés:");
                            for (String libro : librosBuscados) {
                                // Realiza la búsqueda de libros en inglés en la API Gutendex
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url("https://gutendex.com/books/?search=" + libro.replace(" ", "+") + "&languages=en") // Busca por libro y idioma
                                            .build();
                                    Response response = client.newCall(request).execute();

                                    if (response.isSuccessful()) {
                                        String jsonResponse = response.body().string();
                                        JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                                        if (results.length() > 0) {
                                            for (int i = 0; i < results.length(); i++) {
                                                JSONObject book = results.getJSONObject(i);
                                                String title = book.getString("title");
                                                String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                                // Acceder al arreglo "languages"
                                                JSONArray languages = book.getJSONArray("languages");
                                                System.out.print("Idiomas:");
                                                for (int j = 0; j < languages.length(); j++) {
                                                    String language = languages.getString(j);
                                                    String nombreIdioma = idiomaMap.getOrDefault(language, language);
                                                    System.out.print(" " + nombreIdioma);
                                                }
                                                System.out.println();

                                                int downloads = book.getInt("download_count");

                                                System.out.println("Título: " + title);
                                                System.out.println("Autor: " + author);
                                                System.out.println("Número de descargas: " + downloads);
                                                System.out.println("");
                                            }
                                        } else {
                                            System.out.println("No se encontraron libros en inglés con ese título.");
                                        }
                                    } else {
                                        System.out.println("Error al conectar con la API Gutendex.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                                }
                            }
                            break;


                        case "f":
                            // Muestra los libros en francés
                            System.out.println("Libros en francés:");
                            for (String libro : librosBuscados) {
                                // Realiza la búsqueda de libros en francés en la API Gutendex
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url("https://gutendex.com/books/?search=" + libro.replace(" ", "+") + "&languages=fr") // Busca por libro y idioma
                                            .build();
                                    Response response = client.newCall(request).execute();

                                    if (response.isSuccessful()) {
                                        String jsonResponse = response.body().string();
                                        JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                                        if (results.length() > 0) {
                                            for (int i = 0; i < results.length(); i++) {
                                                JSONObject book = results.getJSONObject(i);
                                                String title = book.getString("title");
                                                String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                                // Acceder al arreglo "languages"
                                                JSONArray languages = book.getJSONArray("languages");
                                                System.out.print("Idiomas:");
                                                for (int j = 0; j < languages.length(); j++) {
                                                    String language = languages.getString(j);
                                                    String nombreIdioma = idiomaMap.getOrDefault(language, language);
                                                    System.out.print(" " + nombreIdioma);
                                                }
                                                System.out.println();

                                                int downloads = book.getInt("download_count");

                                                System.out.println("Título: " + title);
                                                System.out.println("Autor: " + author);
                                                System.out.println("Número de descargas: " + downloads);
                                                System.out.println("");
                                            }
                                        } else {
                                            System.out.println("No se encontraron libros en francés con ese título.");
                                        }
                                    } else {
                                        System.out.println("Error al conectar con la API Gutendex.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                                }
                            }
                            break;


                        case "p":
                            // Muestra los libros en portugués
                            System.out.println("Libros en portugués:");
                            for (String libro : librosBuscados) {
                                // Realiza la búsqueda de libros en portugués en la API Gutendex
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url("https://gutendex.com/books/?search=" + libro.replace(" ", "+") + "&languages=pt") // Busca por libro y idioma
                                            .build();
                                    Response response = client.newCall(request).execute();

                                    if (response.isSuccessful()) {
                                        String jsonResponse = response.body().string();
                                        JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                                        if (results.length() > 0) {
                                            for (int i = 0; i < results.length(); i++) {
                                                JSONObject book = results.getJSONObject(i);
                                                String title = book.getString("title");
                                                String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                                // Acceder al arreglo "languages"
                                                JSONArray languages = book.getJSONArray("languages");
                                                System.out.print("Idiomas:");
                                                for (int j = 0; j < languages.length(); j++) {
                                                    String language = languages.getString(j);
                                                    String nombreIdioma = idiomaMap.getOrDefault(language, language);
                                                    System.out.print(" " + nombreIdioma);
                                                }
                                                System.out.println();

                                                int downloads = book.getInt("download_count");

                                                System.out.println("Título: " + title);
                                                System.out.println("Autor: " + author);
                                                System.out.println("Número de descargas: " + downloads);
                                                System.out.println("");
                                            }
                                        } else {
                                            System.out.println("No se encontraron libros en portugués con ese título.");
                                        }
                                    } else {
                                        System.out.println("Error al conectar con la API Gutendex.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                                }
                            }
                            break;


















                        default:
                            System.out.println("Opción inválida. Por favor, elige una opción del menú.");
                    }
                    break;

                case 5:
                    System.out.println("Ingresa el año para buscar autores vivos:");
                    Scanner teclea2 = new Scanner(System.in);
                    int año = teclea2.nextInt();
                    teclea.nextLine(); // Limpia el buffer de entrada

                    // Llama a la API Gutendex y obtiene los resultados
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("https://gutendex.com/books/?birth_date__lte=" + año + "&death_date__gt=" + año) // Busca por autores que nacieron antes del año ingresado y murieron después del año ingresado
                                .build();
                        Response response = client.newCall(request).execute();

                        if (response.isSuccessful()) {
                            String jsonResponse = response.body().string();
                            JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                            if (results.length() > 0) {
                                System.out.println("Autores vivos en " + año + ":");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject book = results.getJSONObject(i);
                                    String author = book.getJSONArray("authors").getJSONObject(0).getString("name");

                                    System.out.println(" " + author);
                                }
                            } else {
                                System.out.println("No se encontraron autores vivos en " + año + ".");
                            }
                        } else {
                            System.out.println("Error al conectar con la API Gutendex.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al realizar la búsqueda: " + e.getMessage());
                    }
                    break;






                case 6:
                    System.out.println("¡Hasta luego!");
                    break;
                case 7:
                    // Borra las búsquedas almacenadas
                    librosBuscados.clear();
                    autoresBuscados.clear();
                    idiomaMap.clear(); // Limpia el mapa idiomaMap
                    System.out.println("Las búsquedas almacenadas se han borrado.");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elige una opción del menú.");
            }
        }
        teclea.close(); // Cierra el Scanner
    }
}