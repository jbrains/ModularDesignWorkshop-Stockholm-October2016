package ca.jbrains.pos.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void productFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final Price price = Price.kronor(175);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("::barcode::"));
            will(returnValue(price));

            oneOf(display).displayPrice(with(price));
        }});

        final SellOneItemController controller
                = new SellOneItemController(catalog, display);
        controller.onBarcode("::barcode::");

    }

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("::barcode not found::"));
            will(returnValue(null));

            oneOf(display).displayProductNotFoundMessage(with("::barcode not found::"));
        }});

        final SellOneItemController controller
                = new SellOneItemController(catalog, display);
        controller.onBarcode("::barcode not found::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            oneOf(display).displayScannedEmptyBarcode();
        }});

        final SellOneItemController controller
                = new SellOneItemController(null, display);
        controller.onBarcode("");
    }

    public interface Catalog {
        Price findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcodeNotFound);

        void displayScannedEmptyBarcode();
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcode();
                return;
            }

            final Price price = catalog.findPrice(barcode);
            if (price == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(price);
        }
    }

    public static class Price {
        public static Price kronor(int kronorValue) {
            return new Price();
        }

        @Override
        public String toString() {
            return "a Price";
        }
    }
}
