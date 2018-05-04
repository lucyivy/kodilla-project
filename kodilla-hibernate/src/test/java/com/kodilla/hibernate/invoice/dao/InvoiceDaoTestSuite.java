package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoTestSuite {
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    ProductDao productDao;

    @Test
    public void testInvoiceDao() {
        //Given
        Product productFlour = new Product("Flour");
        Product productBread = new Product("Bread");

        productDao.save(productBread);
        productDao.save(productFlour);

        Item item1 = new Item(new BigDecimal(8.9), 2);
        Item item2 = new Item(new BigDecimal(4.1), 10);

        List<Item> itemList1 = new ArrayList<>();
        itemList1.add(item1);

        List<Item> itemList2 = new ArrayList<>();
        itemList2.add(item2);

        productBread.setItems(itemList1);
        productFlour.setItems(itemList2);

        Invoice invoice1 = new Invoice("1234567");
        Invoice invoice2 = new Invoice("8765322");

        invoice1.setItems(itemList1);
        invoice2.setItems(itemList2);

        //When
        invoiceDao.save(invoice1);
        int invoice1Id = invoice1.getId();
        invoiceDao.save(invoice2);
        int invoice2Id = invoice2.getId();


        //Then
        Assert.assertNotEquals(0, invoice1Id);
        Assert.assertNotEquals(0, invoice2Id);

        //Clean up
        try {
            itemDao.deleteAll();
        } catch (Exception e) {
            System.out.println("############## there was an exception: " + e);
        }
    }
}