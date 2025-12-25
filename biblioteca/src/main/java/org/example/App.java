package org.example;

import Entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class App {
    private static final Scanner entrada = new Scanner(System.in);
    private static final String MENU_PRINCIPAL = """
            MENU PRINCIPAL
    1. Gestionar libros
    2. Gestionar ejemplares
    3. Gestionar usuarios
    4. Gestionar préstamos
    5. Visualizar información
    0. Salir
    """;

    private static final String MENU_LIBROS = """
            MENU LIBROS
    1. Registrar libro
    0. Salir al menu principal
    """;

    private static final String MENU_EJEMPLARES = """
            MENU EJEMPLARES
    1. Registrar ejemplar
    2. Ver stock
    0. Salir al menu principal
    """;

    private static final String MENU_USUARIOS = """
            MENU USUARIOS
    1. Registrar usuario
    2. Ver usuarios
    0. Salir al menu principal
    """;

    private static final String MENU_PRESTAMOS = """
            MENU PRÉSTAMOS
    1. Registrar préstamo
    2. Registrar devolución
    0. Salir al menu principal
    """;

    public static void main( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyc_biblioteca");
        EntityManager em = emf.createEntityManager();

        int opcion = -1;

        while (opcion != 0) {
            System.out.println(MENU_PRINCIPAL);
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1 -> {
                    while (opcion != 0) {
                        System.out.println(MENU_LIBROS);
                        opcion = entrada.nextInt();

                        switch (opcion) {
                            case 1 -> {
                                System.out.print("Indica el ISBN (sin guiones): ");
                                String isbn = entrada.next();
                                System.out.println();

                                while (isbn.length() != 13 && isbn.length() != 10) {
                                    System.out.println("Formato de código invalido. ");
                                    System.out.print("Indica el ISBN (sin guiones): ");
                                    isbn = entrada.next();
                                    System.out.println();
                                }

                                System.out.print("Indica el título");
                                String titulo = entrada.next();
                                System.out.println();

                                System.out.print("Indica el autor");
                                String autor = entrada.next();
                                System.out.println();

                                Libro libro = new Libro(isbn, titulo, autor); // Poner un try para verificar que se ha generado correctamente el isbn10 -> 13

                                em.getTransaction().begin();
                                em.persist(libro);
                                em.getTransaction().commit();
                            }

                            case 0 -> System.out.println("Saliendo al menu principal ...");
                        }
                    }

                    opcion = -1;
                }

                case 2 -> {
                    while (opcion != 0) {
                        System.out.println(MENU_EJEMPLARES);
                        opcion = entrada.nextInt();

                        switch (opcion) {
                            case 1 -> {

                            }

                            case 2 -> {

                            }

                            case 0 -> System.out.println("Saliendo al menu principal ...");
                        }
                    }

                    opcion = -1;
                }

                case 3 -> {
                    while (opcion != 0) {
                        System.out.println(MENU_USUARIOS);
                        opcion = entrada.nextInt();

                        switch (opcion) {
                            case 1 -> {

                            }

                            case 2 -> {

                            }

                            case 0 -> System.out.println("Saliendo al menu principal ...");
                        }
                    }

                    opcion = -1;
                }

                case 4 -> {
                    while (opcion != 0) {
                        System.out.println(MENU_PRESTAMOS);
                        opcion = entrada.nextInt();

                        switch (opcion) {
                            case 1 -> {

                            }

                            case 2 -> {

                            }

                            case 0 -> System.out.println("Saliendo al menu principal ...");
                        }
                    }
                }

                case 5 -> {

                }

                case 0 -> System.out.println("Saliendo ...");
            }

        }
    }
}
