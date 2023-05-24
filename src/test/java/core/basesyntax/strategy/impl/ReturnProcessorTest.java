package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.Product;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@DisplayName("ReturnProcessor Test")
class ReturnProcessorTest {

    private static final ProductDao<Product, Integer> DAO = new ProductDaoImpl();
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static final Product APPLE = Product.APPLE;
    private static final Product BANANA = Product.BANANA;
    private static OperationProcessor returnProcessor;

    @BeforeAll
    static void beforeAll() {
        returnProcessor = new ReturnProcessor();
    }

    @AfterEach
    void tearDown() {
        DAO.clear();
    }

    @DisplayName("Check return operation with valid value (Apple) and empty Storage")
    @Order(1)
    @Test
    void operate_checkReturnAppleEmptyStorage_ok() {
        returnProcessor.operate(new FruitTransaction(RETURN, APPLE, 20));
        assertEquals(DAO.get(APPLE), 20);
    }

    @DisplayName("Check return operation with valid value (Apple) and non empty Storage")
    @Order(2)
    @Test
    void operate_checkReturnApple_ok() {
        DAO.put(APPLE, 10);
        returnProcessor.operate(new FruitTransaction(RETURN, APPLE, 20));
        assertEquals(DAO.get(APPLE), 30);
    }

    @DisplayName("Check return operation with valid value (Apple) and empty Storage")
    @Order(3)
    @Test
    void operate_checkReturnBananaEmptyStorage_ok() {
        returnProcessor.operate(new FruitTransaction(RETURN, BANANA, 20));
        assertEquals(DAO.get(BANANA), 20);
    }

    @DisplayName("Check return operation with valid value (Apple) and non empty Storage")
    @Order(4)
    @Test
    void operate_checkReturnBanana_ok() {
        DAO.put(BANANA, 10);
        returnProcessor.operate(new FruitTransaction(RETURN, BANANA, 20));
        assertEquals(DAO.get(BANANA), 30);
    }

    @DisplayName("Check return operation with negative value")
    @Order(5)
    @Test
    void operate_checkReturnNegative_notOk() {
        assertThrows(RuntimeException.class, () -> returnProcessor.operate(
                new FruitTransaction(RETURN, BANANA, -20)));
    }
}
