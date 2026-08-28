package com.example.data.repository

import com.example.data.local.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NoorRepository(private val db: NoorDatabase) {
    val allProducts: Flow<List<ProductEntity>> = db.productDao().getAllProducts()
    val featuredProducts: Flow<List<ProductEntity>> = db.productDao().getFeaturedProducts()
    val allCategories: Flow<List<CategoryEntity>> = db.categoryDao().getAllCategories()
    val allOrders: Flow<List<OrderEntity>> = db.orderDao().getAllOrders()
    val allReviews: Flow<List<ReviewEntity>> = db.reviewDao().getAllReviews()

    // Dynamic Coupons Store
    private val _coupons = kotlinx.coroutines.flow.MutableStateFlow<List<Coupon>>(
        listOf(
            Coupon("NOOR10", discountPercent = 10.0, minSpend = 0.0, descUg = "بارلىق ماللارغا 10% ئېتىبار", descAr = "خصم 10% على كل السلة", descEn = "10% Off All Orders"),
            Coupon("YENGILIK", discountAmount = 100.0, minSpend = 1000.0, descUg = "1000 يۈەندىن ئاشسا 100 يۈەن كېمەيتىش", descAr = "خصم 100¥ للطلبات فوق 1000¥", descEn = "¥100 Off for orders over ¥1000"),
            Coupon("VIP2026", discountAmount = 200.0, minSpend = 2000.0, descUg = "2000 يۈەندىن ئاشسا 200 يۈەن كېمەيتىش", descAr = "خصم 200¥ للطلبات فوق 2000¥", descEn = "¥200 Off for orders over ¥2000"),
            Coupon("TEZLIK", discountAmount = 50.0, minSpend = 500.0, descUg = "500 يۈەندىن ئاشسا 50 يۈەن كېمەيتىش + تېز يەتكۈزۈش", descAr = "خصم 50¥ + توصيل سريع", descEn = "¥50 Off + Express Delivery")
        )
    )
    val coupons: kotlinx.coroutines.flow.StateFlow<List<Coupon>> = _coupons

    fun addCoupon(coupon: Coupon) {
        val current = _coupons.value.toMutableList()
        current.removeAll { it.code.equals(coupon.code, ignoreCase = true) }
        current.add(0, coupon)
        _coupons.value = current
    }

    fun deleteCoupon(code: String) {
        val current = _coupons.value.toMutableList()
        current.removeAll { it.code.equals(code, ignoreCase = true) }
        _coupons.value = current
    }

    suspend fun updateOrderStatus(orderId: Int, status: String) {
        db.orderDao().updateOrderStatus(orderId, status)
    }

    suspend fun deleteOrder(orderId: Int) {
        db.orderDao().deleteOrder(orderId)
    }

    suspend fun updateProductPrice(productId: Int, price: Double) {
        db.productDao().updateProductPrice(productId, price)
    }

    fun getReviewsForProduct(productId: Int): Flow<List<ReviewEntity>> {
        return db.reviewDao().getReviewsForProduct(productId)
    }

    suspend fun addReview(review: ReviewEntity): Long {
        return db.reviewDao().insertReview(review)
    }

    suspend fun replyToReview(reviewId: Int, reply: String) {
        db.reviewDao().updateAdminReply(reviewId, reply)
    }

    suspend fun deleteReview(reviewId: Int) {
        db.reviewDao().deleteReview(reviewId)
    }

    suspend fun incrementLikes(productId: Int) {
        db.productDao().incrementLikes(productId)
    }

    suspend fun incrementHearts(productId: Int) {
        db.productDao().incrementHearts(productId)
    }

    fun getProductsByCategory(catId: String): Flow<List<ProductEntity>> {
        return db.productDao().getProductsByCategory(catId)
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return db.productDao().getProductById(id)
    }

    suspend fun insertProduct(product: ProductEntity): Long {
        return db.productDao().insertProduct(product)
    }

    suspend fun updateProduct(product: ProductEntity) {
        db.productDao().updateProduct(product)
    }

    suspend fun deleteProduct(id: Int) {
        db.productDao().deleteProductById(id)
    }

    suspend fun insertOrder(order: OrderEntity): Long {
        return db.orderDao().insertOrder(order)
    }

    suspend fun seedInitialDataIfEmpty() {
        try {
            val currentCategories = db.categoryDao().getAllCategories().first()
        if (currentCategories.isEmpty()) {
            val categories = listOf(
                CategoryEntity("phones", "تىلىپۇنلار", "الهواتف", "Phones", "phone_iphone"),
                CategoryEntity("tablets", "پەدلەر", "الأجهزة اللوحية", "Tablets / Pads", "tablet_mac"),
                CategoryEntity("accessories", "زاپچاسلار", "الملحقات", "Accessories", "headphones"),
                CategoryEntity("watches", "ئەقلىي سائەتلەر", "الساعات الذكية", "Smart Watches", "watch")
            )
            db.categoryDao().insertCategories(categories)
        }

        val currentProducts = db.productDao().getAllProducts().first()
        if (currentProducts.isEmpty()) {
            val sampleProducts = listOf(
                ProductEntity(
                    nameUg = "iPhone 16 Pro Max (512GB)",
                    nameAr = "آيفون 16 برو ماكس (512 جيجابايت)",
                    nameEn = "iPhone 16 Pro Max (512GB)",
                    descriptionUg = "ئەڭ يېڭى A18 Pro چىپ، 48MP كوئاد پىكسېل كامېرا، تىتان گەۋدە. ئالىي دەرىجىلىك كۆزنەك.",
                    descriptionAr = "أحدث شريحة A18 Pro، كاميرا 48 ميجابكسل، جسم من التيتانيوم. شاشة ممتازة.",
                    descriptionEn = "Latest A18 Pro Chip, 48MP Quad Pixel Camera, Titanium body, Super Retina XDR.",
                    price = 9999.0,
                    originalPrice = 10499.0,
                    categoryId = "phones",
                    brand = "Apple",
                    imageResName = "img_phones_1786037591338",
                    isFeatured = true,
                    inStock = true,
                    specsUg = "RAM: 8GB | ساقلاش: 512GB | باتارېيە: 4685mAh",
                    specsAr = "الرام: 8 جيجابايت | التخزين: 512 جيجابايت | البطارية: 4685 مللي أمبير",
                    specsEn = "RAM: 8GB | Storage: 512GB | Battery: 4685mAh",
                    likesCount = 48,
                    heartsCount = 23
                ),
                ProductEntity(
                    nameUg = "Samsung Galaxy S25 Ultra (1TB)",
                    nameAr = "سامسونج جالاكسي S25 أولترا (1 تيرابايت)",
                    nameEn = "Samsung Galaxy S25 Ultra (1TB)",
                    descriptionUg = "Snapdragon 8 Elite چىپ، 200MP زووم كامېرا، S-Pen قەلەم، titanium ئالىي كورپۇس.",
                    descriptionAr = "معالج Snapdragon 8 Elite، كاميرا 200 ميجابكسل مع تقريب، قلم S-Pen الذكي.",
                    descriptionEn = "Snapdragon 8 Elite, 200MP Zoom Camera, Built-in S-Pen stylus, titanium body.",
                    price = 9599.0,
                    originalPrice = 9999.0,
                    categoryId = "phones",
                    brand = "Samsung",
                    imageResName = "img_phones_1786037591338",
                    isFeatured = true,
                    inStock = true,
                    specsUg = "RAM: 16GB | ساقلاش: 1TB | ئېكران: 6.8 بوصە AMOLED 120Hz",
                    specsAr = "الرام: 16 جيجابايت | التخزين: 1 تيرابايت | الشاشة: 6.8 بوصة AMOLED 120Hz",
                    specsEn = "RAM: 16GB | Storage: 1TB | Screen: 6.8 inch Dynamic AMOLED 2X",
                    likesCount = 36,
                    heartsCount = 15
                ),
                ProductEntity(
                    nameUg = "iPad Pro 13-inch M4 (Cellular)",
                    nameAr = "آيباد برو 13 بوصة M4 (شريحة)",
                    nameEn = "iPad Pro 13-inch M4 (Cellular)",
                    descriptionUg = "ئالما M4 چىپ، Tandem OLED ئېكران، ئىنتايىن نېپىز 5.1mm گەۋدە، Pencil Pro ماس كېلىدۇ.",
                    descriptionAr = "شريحة Apple M4، شاشة Tandem OLED الفائقة، تصميم نحيف للغاية 5.1 ملم.",
                    descriptionEn = "Apple M4 Chip, Tandem OLED Display, Ultra thin 5.1mm design, Apple Pencil Pro support.",
                    price = 8999.0,
                    originalPrice = 9399.0,
                    categoryId = "tablets",
                    brand = "Apple",
                    imageResName = "img_tablets_1786037603482",
                    isFeatured = true,
                    inStock = true,
                    specsUg = "چىپ: M4 | ساقلاش: 256GB | ئېكران: 13 بوصە Ultra Retina XDR",
                    specsAr = "المعالج: M4 | التخزين: 256 جيجابايت | الشاشة: 13 بوصة Ultra Retina",
                    specsEn = "Chip: M4 | Storage: 256GB | Display: 13-inch Ultra Retina XDR",
                    likesCount = 29,
                    heartsCount = 14
                ),
                ProductEntity(
                    nameUg = "Xiaomi Pad 7 Pro 12.4",
                    nameAr = "شاومي باد 7 برو 12.4",
                    nameEn = "Xiaomi Pad 7 Pro 12.4",
                    descriptionUg = "Snapdragon 8s Gen 3، 144Hz 3.2K ئېكران، 10000mAh باتارېيە ۋە 67W تېز قۇۋۋەتلىگۈچ.",
                    descriptionAr = "معالج Snapdragon 8s Gen 3، شاشة 3.2K بسرعة 144Hz، بطارية 10000mAh مع شاحن 67W.",
                    descriptionEn = "Snapdragon 8s Gen 3, 144Hz 3.2K Display, 10000mAh Battery with 67W Fast Charging.",
                    price = 2899.0,
                    originalPrice = 3199.0,
                    categoryId = "tablets",
                    brand = "Xiaomi",
                    imageResName = "img_tablets_1786037603482",
                    isFeatured = false,
                    inStock = true,
                    specsUg = "RAM: 12GB | ساقلاش: 256GB | قۇۋۋەتلەش: 67W Fast Charge",
                    specsAr = "الرام: 12 جيجابايت | التخزين: 256 جيجابايت | الشحن: 67 واط",
                    specsEn = "RAM: 12GB | Storage: 256GB | Charging: 67W Turbo",
                    likesCount = 19,
                    heartsCount = 8
                ),
                ProductEntity(
                    nameUg = "Anker 100W Gan 3-Port Fast Charger",
                    nameAr = "شاحن أنكر السريع 100 واط GaN 3 منافذ",
                    nameEn = "Anker 100W GaN 3-Port Fast Charger",
                    descriptionUg = "تېز قۇۋۋەتلىگۈچ، تېلېفون ۋە پەدلەرنى ئوخشاش ۋاقىتتا يۇقىرى سۈرئەتتە توكلاش ئىقتىدارى.",
                    descriptionAr = "شاحن سريع وعالي الجودة للهواتف والأجهزة اللوحية بقدرة 100 واط مع تقنية GaN.",
                    descriptionEn = "High speed 100W GaN Charger for laptops, tablets, and flagship smartphones.",
                    price = 299.0,
                    originalPrice = 350.0,
                    categoryId = "accessories",
                    brand = "Anker",
                    imageResName = "img_hero_banner_1786037578646",
                    isFeatured = false,
                    inStock = true,
                    specsUg = "قۇۋۋەت: 100W | USB-C x2 + USB-A | GaN III",
                    specsAr = "الطاقة: 100 واط | USB-C عدد 2 + USB-A | تقنية GaN",
                    specsEn = "Output: 100W | Ports: 2x USB-C + 1x USB-A | GaN Fast",
                    likesCount = 42,
                    heartsCount = 17
                ),
                ProductEntity(
                    nameUg = "Apple Watch Ultra 2 Titanium",
                    nameAr = "ساعة آبل ألترا 2 تيتانيوم",
                    nameEn = "Apple Watch Ultra 2 Titanium",
                    descriptionUg = "S9 SiP چىپ، 3000 nits يورۇق ئېكران، GPS تېز سېزىم، سۇدىن قوغداش ۋە تەنتەربىيە ئىقتىدارى.",
                    descriptionAr = "شريحة S9 SiP، شاشة براقة 3000 شمعة، مقاومة للماء والرياضات القاسية.",
                    descriptionEn = "S9 SiP Chip, 3000 nits bright display, dual-frequency GPS, extreme sports watch.",
                    price = 5999.0,
                    originalPrice = 6299.0,
                    categoryId = "watches",
                    brand = "Apple",
                    imageResName = "img_app_icon_1786037564036",
                    isFeatured = true,
                    inStock = true,
                    specsUg = "گەۋدە: 49mm Titanium | باتارېيە: 36 سائەت | GPS + Cellular",
                    specsAr = "الهيكل: 49 ملم تيتانيوم | البطارية: 36 ساعة | GPS + شريحة",
                    specsEn = "Case: 49mm Titanium | Battery: 36h | GPS + Cellular",
                    likesCount = 55,
                    heartsCount = 31
                )
            )

            for (p in sampleProducts) {
                db.productDao().insertProduct(p)
            }
        } else {
            // Update existing products if their counts are zero to ensure initial display
            val initialLikes = listOf(48, 36, 29, 19, 42, 55)
            val initialHearts = listOf(23, 15, 14, 8, 17, 31)
            currentProducts.forEachIndexed { index, product ->
                if (product.likesCount == 0 && product.heartsCount == 0) {
                    val lCount = initialLikes.getOrElse(index) { 15 + index * 3 }
                    val hCount = initialHearts.getOrElse(index) { 8 + index * 2 }
                    db.productDao().updateLikesAndHearts(product.id, lCount, hCount)
                }
            }
        }
    } catch (e: Exception) {
        android.util.Log.e("NoorRepository", "Failed to seed data: ${e.message}", e)
    }
}

    suspend fun refreshStoreData() {
        seedInitialDataIfEmpty()
    }
}
