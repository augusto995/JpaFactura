package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();



        try{
            entityManager.getTransaction().begin();

            Factura factura1 = new Factura();
            factura1.setNumero(12);
            factura1.setFecha("10/12/12");

            Domicilio dom = new Domicilio("San MArtin", 122);
            Cliente cliente = new Cliente("Pablo", "Muñoz", 213435);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoria perecederos = new Categoria("Perecederos");
            Categoria lacteos = new Categoria("Lacteos");
            Categoria limpieza = new Categoria("Limpieza");


            Articulo art1 = new Articulo(200, "Yogurt frutilla", 1500);
            Articulo art2 = new Articulo(300, "Detergente", 2000);

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);

            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);


            DetalleFactura det1 = new DetalleFactura();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalleFacturas().add(det1);
            factura1.getDetalleFacturas().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = new DetalleFactura();

            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(80);

            art2.getDetalleFacturas().add(det2);
            factura1.getDetalleFacturas().add(det2);
            det2.setFactura(factura1);


            factura1.setTotal(120);
            entityManager.persist(factura1);

            entityManager.flush();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
        entityManagerFactory.close();

    }
}
