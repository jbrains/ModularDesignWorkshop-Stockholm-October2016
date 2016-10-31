package ca.jbrains.pos;

import ca.jbrains.pos.test.Catalog;
import ca.jbrains.pos.test.Price;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        final Price price = Price.kronor(283746);

        Assert.assertEquals(
                price,
                catalogWith("12345", price).findPrice("12345"));
    }

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(
                null,
                catalogWithout("99999").findPrice("99999"));
    }

    protected abstract Catalog catalogWith(String barcode, Price price);

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
