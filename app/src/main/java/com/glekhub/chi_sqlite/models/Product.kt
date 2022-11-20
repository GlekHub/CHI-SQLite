package com.glekhub.chi_sqlite.models

import android.provider.Contacts.SettingsColumns.KEY
import com.glekhub.chi_sqlite.models.Category.Companion.CATEGORY_ID


data class Product(
    val productId: Int,
    val productTitle: String,   //coffee, lemonade, cake, cookie
    val productSize: String,    //S, M, L
    val productPrice: Int,
    val categoryId: Int        //hot drink, cold drink, meal, snack
) {
    companion object {
        const val TABLE = "Product"
        const val PRODUCT_ID = "_id"
        const val PRODUCT_TITLE = "product_title"
        const val PRODUCT_SIZE = "product_size"
        const val PRODUCT_PRICE = "product_price"
        const val CATEGORY_ID = "category_id"

        const val SQL_CREATE_PRODUCT =
            "CREATE TABLE $TABLE (" +
                    "$PRODUCT_ID INTEGER PRIMARY KEY," +
                    "$PRODUCT_TITLE TEXT NOT NULL," +
                    "$PRODUCT_SIZE TEXT NOT NULL," +
                    "$PRODUCT_PRICE INTEGER NOT NULL," +
                    "$CATEGORY_ID INTEGER," +
                    "FOREIGN KEY ($CATEGORY_ID) REFERENCES ${Category.TABLE} (${Category.CATEGORY_ID}));"

        const val SQL_DELETE_PRODUCT = "DROP TABLE IF EXISTS $TABLE"
    }
}