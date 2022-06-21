package com.webui.dataproviders;

import com.opencsv.CSVReader;
import com.webui.dto.FashionProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FashionProductDataprovider {

    private static Logger logger = LoggerFactory.getLogger(FashionProductDataprovider.class);

    @DataProvider(name = "fetchData")
    public Object[] fetchData() throws IOException {
        CSVReader csvReader = null;
        csvReader = new CSVReader(new FileReader( getClass().getClassLoader()
                .getResource("testdata/automationpracticeuitestdata/automationpractice.csv").getFile()));
        final List<FashionProduct> fashionProducts = new ArrayList<>();
        //read line by line
        String[] record = null;
        //skip header row
        csvReader.readNext();
        final Random random = new Random();
        while((record = csvReader.readNext()) != null){
            final FashionProduct fashionProduct = FashionProduct.builder().title(record[0])
                    .size(record[1])
                    .colour(record[2]).build();
            fashionProducts.add(fashionProduct);
        }

        csvReader.close();

        logger.debug("Fashion product list is : " + fashionProducts);

        return fashionProducts.toArray();
    }

}
