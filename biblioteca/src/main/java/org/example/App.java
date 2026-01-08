package org.example;

import Entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.graph.internal.RootGraphImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
        boolean admin = false;

        System.out.print("Correo: ");
        String correo = entrada.next();
        System.out.println();

        System.out.print("Contraseña: ");
        String password = entrada.next();
        System.out.println();

        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        q.setParameter("email", correo);

        try {
            Usuario usuario = (Usuario) q.getSingleResult();
            if (usuario.getPassword().equals(password)) {
                if (usuario.getTipo().equals("Administrador")) {
                    admin = true;
                }
                System.out.println("Bienvenido, " + usuario.getNombre());
            } else {
                System.out.println("Correo o contraseña incorrectos.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Correo o contraseña incorrectos.");
            return;
        }

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
                            case 1 -> registrarLibro(em);

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
                            case 1 -> registrarEjemplar(em);

                            case 2 -> verStock(em);

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
                            case 1 -> registrarUsuario(em);

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
                            case 1 -> prestarLibro(em);

                            case 2 -> devolverLibro(em);

                            case 0 -> System.out.println("Saliendo al menu principal ...");
                        }
                    }

                    opcion = -1;
                }

                case 5 -> verInfo(admin, em, correo);

                case 0 -> System.out.println("Saliendo ...");
            }

        }

        em.close();
        emf.close();
    }

    private static void verInfo(boolean admin, EntityManager em, String correo) {
        List<Prestamo> prestamos;
        Query query;

        if (admin) {
            query = em.createQuery("SELECT prestamo FROM Prestamo prestamo");

        } else {
            query = em.createQuery(
                    "SELECT prestamo FROM Prestamo prestamo " +
                    "WHERE prestamo.usuario.email = :email"
            );
            query.setParameter("email", correo);
        }

        prestamos = query.getResultList();

        for (Prestamo p : prestamos) {
            System.out.println(
                    p.getUsuario().getNombre() + " | " +
                    p.getEjemplar().getIsbn().getTitulo() + " | " +
                    p.getFechaInicio() + " | " +
                    p.getFechaDevolucion()
            );
        }
    }

    private static void devolverLibro(EntityManager em) {
        System.out.print("Indica dni del usuario: ");
        String dni = entrada.next();
        System.out.println();
        Usuario usuario = em.find(Usuario.class, dni);
        if (usuario == null) {
            System.out.println("El usuario no existe.");
            return;
        }

        System.out.println("Indica el isbn del libro a devolver: ");
        String isbn = entrada.next();
        System.out.println();

        Query query = em.createQuery(
                "SELECT p.ejemplar FROM Prestamo p " +
                "WHERE p.ejemplar.isbn.isbn = :isbn " +
                "AND p.usuario.id = :usuario " +
                "AND p.fechaDevolucion IS NULL"
        );
        query.setParameter("isbn", isbn);
        query.setParameter("usuario", usuario.getId());

        Ejemplar ejemplar = (Ejemplar) query.getSingleResult();

        if (ejemplar.getEstado().equals("Disponible")) {
            System.out.println("El ejemplar no esta prestado.");
        } else if (ejemplar.getEstado().equals("Prestado")) {
            Prestamo prestamo = em.find(Prestamo.class, usuario.getDni());

            if (
                    prestamo.getFechaLimite() != null &&
                    prestamo.getFechaLimite().isBefore(LocalDate.now())
            ) {
                System.out.println("Este libro es entregado con retraso, se pondrá una amonestación de 15 días.");
                em.getTransaction().begin();
                usuario.setPenalizacionHasta(LocalDate.now().plusDays(15));
                em.getTransaction().commit();
            }

            em.getTransaction().begin();
            prestamo.setFechaDevolucion(LocalDate.now());
            ejemplar.setEstado("Disponible");
            em.getTransaction().commit();
        }
    }

    private static void prestarLibro(EntityManager em) {
        System.out.print("Indica dni del usuario: ");
        String dni = entrada.next();
        System.out.println();

        Query prestamosDeUsuario = em.createQuery(
                "SELECT COUNT(prestamo) FROM Prestamo prestamo" +
                " WHERE prestamo.usuario.dni = :dni " +
                "AND prestamo.fechaDevolucion IS NULL"
        );
        prestamosDeUsuario.setParameter("dni", dni);

        Long prestamos = (Long) prestamosDeUsuario.getSingleResult();
        if (prestamos >= 3) {
            System.out.println("El usuario ya tiene 3 libros prestados, no se pueden prestar mas libros.");
            return;
        }

        if (
                em.find(Usuario.class, dni).getPenalizacionHasta() != null &&
                em.find(Usuario.class, dni).getPenalizacionHasta().isAfter(LocalDate.now())
        ) {
            System.out.println("El usuario tiene una penalización hasta: " + em.find(Usuario.class, dni).getPenalizacionHasta());
            return;
        }

        System.out.println("Indica ISBN: ");
        String isbn = entrada.next();
        System.out.println();

        Query ejemplarDisponible = em.createQuery(
                "SELECT e FROM Ejemplar e " +
                "WHERE e.isbn.isbn = :isbn " +
                "AND e.estado = 'Disponible'"
        );
        ejemplarDisponible.setParameter("isbn", isbn);

        Ejemplar ejemplar = (Ejemplar) ejemplarDisponible.getSingleResult();

        if (ejemplar == null) {
            System.out.println("El ejemplar no existe o no esta disponible.");
            return;
        }

        Prestamo p = new Prestamo(em.find(Usuario.class, dni), ejemplar, LocalDate.now());
        em.getTransaction().begin();
        em.persist(p);
        ejemplar.setEstado("Prestado");
        em.getTransaction().commit();
    }

    private static void registrarUsuario(EntityManager em) {
        System.out.print("Indica el DNI: ");
        String dni = entrada.next();
        System.out.println();

        System.out.print("Indica el nombre: ");
        String nombre = entrada.next();
        System.out.println();

        System.out.print("Indica el email: ");
        String email = entrada.next();
        System.out.println();

        System.out.print("Indica contrasenia: ");
        String contrasenia = entrada.next();
        System.out.println();

        System.out.println("Indica el tipo de usuario: ");
        String tipo = entrada.next();
        System.out.println();

        Usuario usuario = new Usuario(dni, nombre, email, contrasenia, tipo);

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    private static void verStock(EntityManager em) {
        System.out.print("Indica el isbn del libro a buscar: ");
        String isbn = verificarISBN();
        System.out.println();

        Query query = em.createQuery(
                "SELECT COUNT(ej.estado) FROM Ejemplar ej " +
                "WHERE ej.isbn.isbn = :isbn " +
                "AND ej.estado = 'Disponible'"
        );
        query.setParameter("isbn", isbn);

        Long stock = (Long) query.getSingleResult();

        System.out.println(stock);
    }

    private static void registrarEjemplar(EntityManager em) {
        System.out.print("Indica el ISBN (sin guiones): ");
        String isbn = verificarISBN();
        System.out.println();

        System.out.print("Indica el estado del ejemplar (Disponible; Prestado; Dañado): ");
        String estado = entrada.next();

        System.out.println();

        Libro libro = em.find(Libro.class, isbn);
        if (libro == null) {
            System.out.println("El libro no existe.");
            return;
        }
        Ejemplar ejemplar = new Ejemplar(libro, estado);

        em.getTransaction().begin();
        em.persist(ejemplar);
        em.getTransaction().commit();
    }

    private static void registrarLibro(EntityManager em) {
        System.out.print("Indica el ISBN (sin guiones): ");
        String isbn = verificarISBN();
        System.out.println();

        System.out.print("Indica el título");
        entrada.nextLine();
        String titulo = entrada.nextLine();
        System.out.println();

        System.out.print("Indica el autor");
        String autor = entrada.next();
        System.out.println();

        Libro libro = new Libro(isbn, titulo, autor);

        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
    }

    private static String verificarISBN() {
        String isbn = entrada.next();

        while (isbn.length() != 13 && isbn.length() != 10) {
            System.out.println("Formato de código invalido. ");
            System.out.print("Indica el ISBN (sin guiones): ");
            isbn = entrada.next();
            System.out.println();
        }

        return isbn;
    }
}
