package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@DisplayName("OperationStrategyImpl Test")
class OperationStrategyImplTest {

    @DisplayName("Check method get in Operation Strategy with Balance Processor")
    @Order(1)
    @Test
    void get_getBalanceOperationStrategy_ok() {
        OperationStrategy strategy = new OperationStrategyImpl();
        Class<? extends OperationProcessor> actualOperationClass =
                strategy.get(FruitTransaction.Operation.BALANCE).getClass();
        Class<? extends OperationProcessor> expectedClass = BalanceProcessor.class;
        assertEquals(expectedClass, actualOperationClass);
    }

    @DisplayName("Check method get in Operation Strategy with Purchase Processor")
    @Order(2)
    @Test
    void get_getPurchaseOperationStrategy_ok() {
        OperationStrategy strategy = new OperationStrategyImpl();
        Class<? extends OperationProcessor> actualOperationClass =
                strategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<? extends OperationProcessor> expectedClass = PurchaseProcessor.class;
        assertEquals(expectedClass, actualOperationClass);
    }

    @DisplayName("Check method get in Operation Strategy with Return Processor")
    @Order(3)
    @Test
    void get_getReturnOperationStrategy_ok() {
        OperationStrategy strategy = new OperationStrategyImpl();
        Class<? extends OperationProcessor> actualOperationClass =
                strategy.get(FruitTransaction.Operation.RETURN).getClass();
        Class<? extends OperationProcessor> expectedClass = ReturnProcessor.class;
        assertEquals(expectedClass, actualOperationClass);
    }

    @DisplayName("Check method get in Operation Strategy with Supply Processor")
    @Order(4)
    @Test
    void get_getSupplyOperationStrategy_ok() {
        OperationStrategy strategy = new OperationStrategyImpl();
        Class<? extends OperationProcessor> actualOperationClass =
                strategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<? extends OperationProcessor> expectedClass = SupplyProcessor.class;
        assertEquals(expectedClass, actualOperationClass);
    }

    @DisplayName("Check method get in Operation Strategy with invalid data - null")
    @Order(5)
    @Test
    void get_getNullOperationStrategy_notOk() {
        OperationStrategy strategy = new OperationStrategyImpl();
        assertThrows(RuntimeException.class, () -> strategy.get(null));
    }
}
