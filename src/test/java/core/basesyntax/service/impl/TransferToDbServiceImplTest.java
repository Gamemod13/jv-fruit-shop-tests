package core.basesyntax.service.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static core.basesyntax.strategy.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.strategy.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.strategy.FruitTransaction.Operation.RETURN;
import static core.basesyntax.strategy.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import core.basesyntax.service.TransferToDbService;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceProcessor;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseProcessor;
import core.basesyntax.strategy.impl.ReturnProcessor;
import core.basesyntax.strategy.impl.SupplyProcessor;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TransferToDbImplTest")
class TransferToDbServiceImplTest {
    private static OperationStrategy STRATEGY;

    @BeforeAll
    static void beforeAll() {
        STRATEGY = new OperationStrategyImpl(Map.of(
                BALANCE, new BalanceProcessor(new ProductDaoImpl()),
                SUPPLY, new SupplyProcessor(new ProductDaoImpl()),
                PURCHASE, new PurchaseProcessor(new ProductDaoImpl()),
                RETURN, new ReturnProcessor(new ProductDaoImpl())));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @DisplayName("Test transfer to DB")
    @Test
    void transfer_transferToDb_ok() {
        List<FruitTransaction> transactions = getTransactions();
        TransferToDbService transfer = new TransferToDbServiceImpl(STRATEGY);
        transfer.transfer(transactions);
        Map<Product, Integer> expected = Map.of(BANANA, 20, APPLE, 20);
        Map<Product, Integer> actual = Map.of(BANANA, Storage.storage.get(BANANA),
                APPLE, Storage.storage.get(APPLE));
        assertEquals(expected, actual);
    }

    private List<FruitTransaction> getTransactions() {
        return List.of(
                new FruitTransaction(BALANCE, BANANA, 20),
                new FruitTransaction(SUPPLY, BANANA, 20),
                new FruitTransaction(RETURN, BANANA, 10),
                new FruitTransaction(PURCHASE, BANANA, 30),
                new FruitTransaction(BALANCE, APPLE, 20),
                new FruitTransaction(SUPPLY, APPLE, 20),
                new FruitTransaction(RETURN, APPLE, 10),
                new FruitTransaction(PURCHASE, APPLE, 30));
    }
}
