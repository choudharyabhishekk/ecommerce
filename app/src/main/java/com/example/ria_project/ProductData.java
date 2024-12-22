package com.example.ria_project;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductData {

    private final ProductDao productDao;
    private final ExecutorService executorService;

    public ProductData(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        productDao = db.productDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertProduct(final Product product) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                productDao.insert(product);
            }
        });
    }

    public List<Product> getAllProducts() {
        Future<List<Product>> future = executorService.submit(new Callable<List<Product>>() {
            @Override
            public List<Product> call() {
                return productDao.getAllProducts();
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to insert initial dummy data
    public void insertInitialData() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Product> existingProducts = productDao.getAllProducts();

                if (existingProducts.isEmpty()) {
                    Product product1 = new Product();
                    product1.setName("Nike Air Force 1");
                    product1.setPrice(89.99);
                    product1.setDescription("Classic and timeless, the Nike Air Force 1 is perfect for everyday wear. With its enduring design and versatile appeal, this shoe has become a staple in sneaker culture and continues to be a favorite among enthusiasts.");
                    product1.setImageResourceName("shoes1");
                    productDao.insert(product1);

                    Product product2 = new Product();
                    product2.setName("Off-White Out of Office");
                    product2.setPrice(299.99);
                    product2.setDescription("Elevate your sneaker game with the Off-White Out of Office sneakers. Known for their distinctive style and high-quality construction, these sneakers will make a bold statement wherever you go. They offer both comfort and a unique design that stands out.");
                    product2.setImageResourceName("shoes2");
                    productDao.insert(product2);

                    Product product3 = new Product();
                    product3.setName("Amiri Skel-Toe Low");
                    product3.setPrice(499.99);
                    product3.setDescription("Unique skeleton design, the Amiri Skel-Toe Low is a statement piece. With intricate detailing and a standout aesthetic, these shoes are designed to make an impression. The bold design coupled with luxurious materials provides a one-of-a-kind footwear experience.");
                    product3.setImageResourceName("shoes3");
                    productDao.insert(product3);

                    Product product4 = new Product();
                    product4.setName("Nike Air Max Furyosa");
                    product4.setPrice(179.99);
                    product4.setDescription("Comfort meets style with the futuristic Nike Air Max Furyosa. These sneakers feature innovative design elements and advanced cushioning technology, offering unparalleled comfort and a modern look that is perfect for those who appreciate cutting-edge fashion.");
                    product4.setImageResourceName("shoes4");
                    productDao.insert(product4);

                    Product product5 = new Product();
                    product5.setName("Balenciaga Runner");
                    product5.setPrice(899.99);
                    product5.setDescription("Distressed look and bold branding, the Balenciaga Runner is for trendsetters. Its rugged design and high-end materials create a fashion-forward statement, making it an essential piece for those who embrace high-fashion aesthetics.");
                    product5.setImageResourceName("shoes5");
                    productDao.insert(product5);

                    Product product6 = new Product();
                    product6.setName("Coach Citysole Court");
                    product6.setPrice(150.00);
                    product6.setDescription("Sleek and comfortable, the Coach Citysole Court is ideal for casual outings. With a minimalist design and premium materials, these sneakers provide a refined look that pairs well with both casual and semi-formal attire.");
                    product6.setImageResourceName("shoes6");
                    productDao.insert(product6);

                    Product product7 = new Product();
                    product7.setName("Michael Kors Keaton");
                    product7.setPrice(125.00);
                    product7.setDescription("Stylish and chic, the Michael Kors Keaton sneakers add flair to any outfit. Featuring a combination of luxurious materials and a modern design, these sneakers are perfect for those who want to make a statement with their footwear.");
                    product7.setImageResourceName("shoes7");
                    productDao.insert(product7);

                    Product product8 = new Product();
                    product8.setName("Adidas Ultraboost 22");
                    product8.setPrice(180.00);
                    product8.setDescription("The Adidas Ultraboost 22 offers superior comfort and support for runners. With its responsive Boost cushioning and sleek design, these shoes provide an excellent balance of performance and style, making them perfect for both workouts and casual wear.");
                    product8.setImageResourceName("shoes8");
                    productDao.insert(product8);
                    Product product9 = new Product();
                    product9.setName("Puma RS-X3 Puzzle");
                    product9.setPrice(130.00);
                    product9.setDescription("The Puma RS-X3 Puzzle combines bold colors and geometric patterns for a standout look. These sneakers are not only stylish but also offer excellent comfort and support, making them a great choice for everyday wear and fashion-forward outfits.");
                    product9.setImageResourceName("shoes9");
                    productDao.insert(product9);

                }
            }
        });
    }
}
