package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Patterns.Creational.PurchaseBuilder;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final List<User> users = new ArrayList<>();
    public static final List<Movie> movies = new ArrayList<>();
    public static final List<Screening> screenings = new ArrayList<>();
    public static final List<ProductComponent> products = new ArrayList<>();
    public static final List<Purchase> allPurchases = new ArrayList<>();

    static {
        Customer ana = new Customer("C-001", "Ana", "ana@uq.edu.co", "1234", "3001234567");
        ana.getCard().recharge(200_000);

        Customer carlos = new Customer("C-002", "Carlos", "carlos@uq.edu.co", "1234", "3009876543");
        carlos.getCard().recharge(150_000);

        Admin admin = new Admin("A-001", "Admin", "admin@uq.edu.co", "admin", "Coordinator");

        users.add(ana);
        users.add(carlos);
        users.add(admin);


        Movie movie1 = new Movie("Interestelar", "Ciencia Ficción", 120, "PG-13");
        Movie movie2 = new Movie("El Padrino", "Drama", 175, "R");
        movies.add(movie1);
        movies.add(movie2);

        Room room1 = new Room("Sala 1", 50);
        Room room2 = new Room("Sala 2", 50);

        Screening screening1 = new Screening(movie1, room1, LocalDateTime.now().plusHours(30), 16000);
        Screening screening2 = new Screening(movie2, room2, LocalDateTime.now().plusHours(3), 15000);
        screenings.add(screening1);
        screenings.add(screening2);


        Combo comboPareja = new Combo("Combo Pareja", 25000, 3000);
        comboPareja.addItem(new Product("Palomitas", 15000));
        comboPareja.addItem(new Product("Soda", 10000));

        Product palomitas = new Product("Palomitas Grandes", 18000);
        Product soda = new Product("Soda Mediana", 8000);

        products.add(palomitas);
        products.add(soda);
        products.add(comboPareja);

        PurchaseBuilder builder1 = new PurchaseBuilder()
                .withId("V-001")
                .withCustomer(ana)
                .withScreening(screening1)
                .withScreeningDate(screening1.getDateTime())
                .addTicket(new Ticket(new Seat("A", 1), screening1.getBasePrice()))
                .addTicket(new Ticket(new Seat("A", 2), screening1.getBasePrice()));
        Purchase purchase1 = builder1.build();
        purchase1.setStatus(PurchaseStatus.PAID);
        allPurchases.add(purchase1);

        PurchaseBuilder builder2 = new PurchaseBuilder()
                .withId("V-002")
                .withCustomer(carlos)
                .withScreening(screening2)
                .withScreeningDate(screening2.getDateTime())
                .addTicket(new Ticket(new Seat("B", 5), screening2.getBasePrice()))
                .addProduct(comboPareja);
        Purchase purchase2 = builder2.build();
        purchase2.setStatus(PurchaseStatus.PAID);
        allPurchases.add(purchase2);


        PurchaseBuilder builder3 = new PurchaseBuilder()
                .withId("V-003")
                .withCustomer(ana)
                .withScreening(screening1)
                .withScreeningDate(screening1.getDateTime())
                .addProduct(palomitas)
                .addProduct(soda);
        Purchase purchase3 = builder3.build();
        purchase3.setStatus(PurchaseStatus.PAID);
        allPurchases.add(purchase3);


        PurchaseBuilder builder4 = new PurchaseBuilder()
                .withId("V-004")
                .withCustomer(carlos)
                .withScreening(screening1)
                .withScreeningDate(screening1.getDateTime())
                .addTicket(new Ticket(new Seat("C", 1), screening1.getBasePrice()))
                .addTicket(new Ticket(new Seat("C", 2), screening1.getBasePrice()))
                .addTicket(new Ticket(new Seat("C", 3), screening1.getBasePrice()));
        Purchase purchase4 = builder4.build();
        purchase4.setStatus(PurchaseStatus.PAID);
        allPurchases.add(purchase4);
    }

    public static User authenticate(String email, String password) {
        for (User user : users) {
            if (user.login(email, password)) return user;
        }
        return null;
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Customer) customers.add((Customer) u);
        }
        return customers;
    }

    public static void addPurchase(Purchase purchase) {
        allPurchases.add(purchase);
    }

    public static List<Purchase> getPurchasesByCustomer(Customer customer) {
        List<Purchase> result = new ArrayList<>();
        for (Purchase p : allPurchases) {
            if (p.getCustomer() != null && p.getCustomer().getId().equals(customer.getId())) {
                result.add(p);
            }
        }
        return result;
    }
}