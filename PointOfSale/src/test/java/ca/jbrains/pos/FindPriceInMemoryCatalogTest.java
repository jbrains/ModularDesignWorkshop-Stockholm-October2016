package ca.jbrains.pos;

import ca.jbrains.pos.test.Catalog;
import ca.jbrains.pos.test.Price;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
    @Override
    protected Catalog catalogWith(String barcode, Price price) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put(String.format("Not %s", barcode), Price.kronor(0));
            put(String.format("Definitely not %s", barcode), Price.kronor(1));
            put(barcode, price);
            put(String.format("So totally not %s", barcode), Price.kronor(13764));
        }});
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap<String, Price>() {{
            put(String.format("Not %s", barcodeToAvoid), Price.kronor(0));
            put(String.format("Definitely not %s", barcodeToAvoid), Price.kronor(1));
            put(String.format("So totally not %s", barcodeToAvoid), Price.kronor(13764));
        }});
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
