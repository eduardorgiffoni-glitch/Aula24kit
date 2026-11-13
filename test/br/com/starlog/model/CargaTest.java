package br.com.starlog.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CargaTest {
    @Test void seguroBasico() {
        assertEquals(20.0, new Carga("A1", "NORMAL", 20, 1000).calcularSeguro(), 0.001);
    }
    @Test void sobretaxaPerigosa() {
        assertEquals(170.0, new Carga("A2", "PERIGOSA", 10, 1000).calcularSeguro(), 0.001);
    }
    @Test void sobretaxaPeso() {
        assertEquals(170.0, new Carga("A3", "NORMAL", 101, 1000).calcularSeguro(), 0.001);
    }
    @Test void sobretaxaNaoAcumula() {
        assertEquals(170.0, new Carga("A4", "PERIGOSA", 101, 1000).calcularSeguro(), 0.001);
    }
    @Test void rejeitaInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Carga(" ", "NORMAL", 10, 100));
        assertThrows(IllegalArgumentException.class, () -> new Carga("A", " ", 10, 100));
        assertThrows(IllegalArgumentException.class, () -> new Carga("A", "NORMAL", 0, 100));
        assertThrows(IllegalArgumentException.class, () -> new Carga("A", "NORMAL", 10, -50));
    }
}
