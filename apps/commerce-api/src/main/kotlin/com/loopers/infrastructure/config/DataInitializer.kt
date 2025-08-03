package com.loopers.infrastructure.config

import com.loopers.domain.brand.Brand
import com.loopers.domain.brand.BrandRepository
import com.loopers.domain.inventory.Inventory
import com.loopers.domain.inventory.InventoryRepository
import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class DataInitializer(
    private val brandRepository: BrandRepository,
    private val inventoryRepository: InventoryRepository,
    private val productRepository: ProductRepository,
) : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @Transactional
    override fun run(args: ApplicationArguments?) {
        logger.info("Starting data initialization...")

        // Check if data already exists
        val existingProducts = productRepository.findProductsData(
            null,
            "name",
            org.springframework.data.domain.PageRequest.of(0, 1),
        )
        if (existingProducts.content.isNotEmpty()) {
            logger.info("Data already exists, skipping initialization")
            return
        }

        try {
            // Create Brands
            val brands = createBrands()
            logger.info("Created ${brands.size} brands")

            // Create Inventories
            val inventories = createInventories()
            logger.info("Created ${inventories.size} inventories")

            // Create Products
            val products = createProducts(brands, inventories)
            logger.info("Created ${products.size} products")

            logger.info("Data initialization completed successfully!")
        } catch (e: Exception) {
            logger.error("Error during data initialization", e)
            throw e
        }
    }

    private fun createBrands(): List<Brand> {
        val brandData = listOf(
            "Apple" to "Premium technology brand",
            "Samsung" to "Global electronics manufacturer",
            "Nike" to "Athletic wear and footwear",
            "Adidas" to "Sports apparel and equipment",
            "Sony" to "Consumer electronics and entertainment",
        )

        return brandData.map { (name, description) ->
            val brand = Brand(
                id = 0L,
                name = name,
                description = description,
            )
            brandRepository.save(brand)
        }
    }

    private fun createInventories(): List<Inventory> {
        val inventoryData = listOf(
            Triple(100, 95, 90),
            Triple(200, 180, 160),
            Triple(50, 48, 45),
            Triple(300, 290, 280),
            Triple(150, 140, 130),
            Triple(80, 75, 70),
            Triple(120, 115, 110),
            Triple(250, 240, 220),
            Triple(60, 58, 55),
            Triple(180, 170, 160),
        )

        return inventoryData.map { (total, actual, available) ->
            val inventory = Inventory(
                id = 0L,
                totalStock = total,
                actualStock = actual,
                availableStock = available,
            )
            inventoryRepository.save(inventory)
        }
    }

    private fun createProducts(brands: List<Brand>, inventories: List<Inventory>): List<Product> {
        val productData = listOf(
            Triple("iPhone 15 Pro", BigDecimal("1199000"), 0),
            Triple("Galaxy S24 Ultra", BigDecimal("1398000"), 1),
            Triple("Air Jordan 1", BigDecimal("189000"), 2),
            Triple("Ultraboost 23", BigDecimal("220000"), 3),
            Triple("WH-1000XM5", BigDecimal("398000"), 4),
            Triple("MacBook Pro 14", BigDecimal("2390000"), 0),
            Triple("Galaxy Book Pro", BigDecimal("1590000"), 1),
            Triple("Air Max 270", BigDecimal("159000"), 2),
            Triple("Stan Smith", BigDecimal("99000"), 3),
            Triple("PlayStation 5", BigDecimal("698000"), 4),
        )

        return productData.mapIndexed { index, (name, price, brandIndex) ->
            val brand = brands[brandIndex]
            val inventory = inventories[index]

            val productDataToSave = com.loopers.domain.product.ProductData(
                id = 0L,
                name = name,
                brandId = brand.id,
                inventoryId = inventory.id,
                price = price,
            )

            logger.info("Creating product: $name (Brand: ${brand.name}, Price: $price)")
            val savedProductData = productRepository.save(productDataToSave)

            Product(
                id = savedProductData.id,
                name = savedProductData.name,
                brand = brand,
                inventory = inventory,
                price = savedProductData.price,
            )
        }
    }
}
