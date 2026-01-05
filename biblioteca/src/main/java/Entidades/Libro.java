package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

    public Libro() {
    }

    public Libro(String isbn) {
        setIsbn(isbn);
    }

    public Libro(String isbn, String titulo, String autor) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn.length() == 10)
            isbn10A13(isbn);
        else
            this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    private void isbn10A13(String isbn) {
        String codigoSinControl = isbn.substring(0, 9);
        String baseIsbn13 = "978" + codigoSinControl;

        int sumaTotal = 0;

        for (int i = 0 ; i < baseIsbn13.length() ; i++) {
            int digito = Character.getNumericValue(baseIsbn13.charAt(i));
            sumaTotal += (i % 2 == 0) ? digito : digito * 3; // Se multiplica el numero dependiendo de su posicion y se acumula
        }

        int numControl = (10 - (sumaTotal % 10)) % 10;
        this.isbn = baseIsbn13 + numControl;
    }

}