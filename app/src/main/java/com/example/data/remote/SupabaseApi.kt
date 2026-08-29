package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object SupabaseConfig {
    const val BASE_URL = "https://yufuhjdmzgehwnypdpba.supabase.co/rest/v1/"
    const val API_KEY = "sb_publishable_db9lknMr2xWIuxzdBUIvww_le2sVZEu"
}

@JsonClass(generateAdapter = true)
data class SupabaseProductDto(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "name_ug") val nameUg: String,
    @Json(name = "name_ar") val nameAr: String = "",
    @Json(name = "name_en") val nameEn: String = "",
    @Json(name = "description_ug") val descriptionUg: String = "",
    @Json(name = "description_ar") val descriptionAr: String = "",
    @Json(name = "description_en") val descriptionEn: String = "",
    @Json(name = "price") val price: Double,
    @Json(name = "original_price") val originalPrice: Double = 0.0,
    @Json(name = "category_id") val categoryId: String,
    @Json(name = "brand") val brand: String = "",
    @Json(name = "image_res_name") val imageResName: String,
    @Json(name = "image_res_name2") val imageResName2: String = "",
    @Json(name = "image_res_name3") val imageResName3: String = "",
    @Json(name = "is_featured") val isFeatured: Boolean = false,
    @Json(name = "in_stock") val inStock: Boolean = true,
    @Json(name = "specs_ug") val specsUg: String = "",
    @Json(name = "specs_ar") val specsAr: String = "",
    @Json(name = "specs_en") val specsEn: String = "",
    @Json(name = "likes_count") val likesCount: Int = 0,
    @Json(name = "hearts_count") val heartsCount: Int = 0
)

@JsonClass(generateAdapter = true)
data class SupabaseOrderDto(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "customer_name") val customerName: String,
    @Json(name = "customer_phone") val customerPhone: String,
    @Json(name = "items_json") val itemsJson: String,
    @Json(name = "total_price") val totalPrice: Double,
    @Json(name = "order_date") val orderDate: Long,
    @Json(name = "status") val status: String = "pending",
    @Json(name = "note") val note: String = ""
)

@JsonClass(generateAdapter = true)
data class SupabaseReviewDto(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "product_id") val productId: Long,
    @Json(name = "user_name") val userName: String,
    @Json(name = "rating") val rating: Int,
    @Json(name = "comment") val comment: String,
    @Json(name = "admin_reply") val adminReply: String = "",
    @Json(name = "timestamp") val timestamp: Long
)

@JsonClass(generateAdapter = true)
data class SupabaseCouponDto(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "code") val code: String,
    @Json(name = "discount_percent") val discountPercent: Double = 0.0,
    @Json(name = "discount_amount") val discountAmount: Double = 0.0,
    @Json(name = "min_spend") val minSpend: Double = 0.0,
    @Json(name = "desc_ug") val descUg: String = "",
    @Json(name = "desc_ar") val descAr: String = "",
    @Json(name = "desc_en") val descEn: String = ""
)

interface SupabaseApi {
    @GET("products?select=*&order=id.asc")
    suspend fun getProducts(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<List<SupabaseProductDto>>

    @POST("products")
    suspend fun insertProduct(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Header("Prefer") prefer: String = "return=representation",
        @Body product: SupabaseProductDto
    ): Response<List<SupabaseProductDto>>

    @PATCH("products")
    suspend fun updateProduct(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Body product: Map<String, @JvmSuppressWildcards Any>
    ): Response<Unit>

    @DELETE("products")
    suspend fun deleteProduct(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<Unit>

    // Orders
    @GET("orders?select=*&order=order_date.desc")
    suspend fun getOrders(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<List<SupabaseOrderDto>>

    @POST("orders")
    suspend fun insertOrder(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Header("Prefer") prefer: String = "return=representation",
        @Body order: SupabaseOrderDto
    ): Response<List<SupabaseOrderDto>>

    @PATCH("orders")
    suspend fun updateOrderStatus(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Body updates: Map<String, @JvmSuppressWildcards Any>
    ): Response<Unit>

    @DELETE("orders")
    suspend fun deleteOrder(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<Unit>

    // Reviews
    @GET("reviews?select=*&order=timestamp.desc")
    suspend fun getReviews(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<List<SupabaseReviewDto>>

    @POST("reviews")
    suspend fun insertReview(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Header("Prefer") prefer: String = "return=representation",
        @Body review: SupabaseReviewDto
    ): Response<List<SupabaseReviewDto>>

    @PATCH("reviews")
    suspend fun updateReview(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Body updates: Map<String, @JvmSuppressWildcards Any>
    ): Response<Unit>

    @DELETE("reviews")
    suspend fun deleteReview(
        @Query("id") idFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<Unit>

    // Coupons
    @GET("coupons?select=*&order=id.asc")
    suspend fun getCoupons(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<List<SupabaseCouponDto>>

    @POST("coupons")
    suspend fun insertCoupon(
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}",
        @Header("Prefer") prefer: String = "return=representation",
        @Body coupon: SupabaseCouponDto
    ): Response<List<SupabaseCouponDto>>

    @DELETE("coupons")
    suspend fun deleteCoupon(
        @Query("code") codeFilter: String,
        @Header("apikey") apiKey: String = SupabaseConfig.API_KEY,
        @Header("Authorization") auth: String = "Bearer ${SupabaseConfig.API_KEY}"
    ): Response<Unit>
}

object SupabaseClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: SupabaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(SupabaseConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SupabaseApi::class.java)
    }
}
