package core.basesyntax.service.impl;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.Product;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private static final ProductDao<Product, Integer> dao = new ProductDaoImpl();
    private static final String TITLE = "fruit,quantity";

    @Override
    public List<String> createReport() {
        List<String> report = new ArrayList<>(List.of(TITLE));
        for (Product product : Product.values()) {
            if (dao.get(product) != null) {
                report.add(String.format("%s,%d",
                        product.name().toLowerCase(),
                        dao.get(product)));
            }
        }
        return report;
    }
}
