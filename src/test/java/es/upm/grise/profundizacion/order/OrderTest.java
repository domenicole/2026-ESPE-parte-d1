package es.upm.grise.profundizacion.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import es.upm.grise.exceptions.IncorrectItemException;

public class OrderTest {

    /* =====================
     * Smoke test
     * ===================== */
    @Test
    public void smokeTest() {
        Order order = new Order();
        assertNotNull(order);
    }

    @Test
    public void constructor_initializesEmptyItems() {
        Order order = new Order();
        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    /* =====================
     * addItem tests
     * ===================== */

    @Test
    public void addItem_validItem_addsItem() throws IncorrectItemException {
        Order order = new Order();
        Item item = new TestItem(1L, 2, 10.0);

        order.addItem(item);

        assertEquals(1, order.getItems().size());
    }

    @Test
    public void addItem_negativePrice_throwsException() {
        Order order = new Order();
        Item item = new TestItem(1L, 1, -5.0);

        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        });
    }

    @Test
    public void addItem_zeroQuantity_throwsException() {
        Order order = new Order();
        Item item = new TestItem(1L, 0, 10.0);

        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        });
    }

    @Test
    public void addItem_sameProductSamePrice_mergesQuantities() throws IncorrectItemException {
        Order order = new Order();

        Item item1 = new TestItem(1L, 2, 10.0);
        Item item2 = new TestItem(1L, 3, 10.0);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(1, order.getItems().size());

        Item result = order.getItems().iterator().next();
        assertEquals(5, result.getQuantity());
    }

    @Test
    public void addItem_sameProductDifferentPrice_addsNewItem() throws IncorrectItemException {
        Order order = new Order();

        Item item1 = new TestItem(1L, 2, 10.0);
        Item item2 = new TestItem(1L, 3, 12.0);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(2, order.getItems().size());
    }

    /* =====================
     * Test stub for Item
     * ===================== */
    private static class TestItem implements Item {

        private Product product;
        private int quantity;
        private double price;

        TestItem(long productId, int quantity, double price) {
            this.product = new Product();
            this.product.setId(productId);
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public Product getProduct() {
            return product;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public void setQuantity(int i) {
            this.quantity = i;
        }

        @Override
        public double getPrice() {
            return price;
        }
    }
}
