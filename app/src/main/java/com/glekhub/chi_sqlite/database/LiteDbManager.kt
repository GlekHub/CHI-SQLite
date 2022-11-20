package com.glekhub.chi_sqlite.database

import android.content.ContentValues
import android.content.Context
import com.glekhub.chi_sqlite.models.Category
import com.glekhub.chi_sqlite.models.Orders
import com.glekhub.chi_sqlite.models.Product
import com.glekhub.chi_sqlite.models.User

class LiteDbManager(context: Context) {

    private val dbHelper = LiteDbHelper(context)
    private var values = ContentValues()

    fun insertUser(list: List<User>) {

        val db = dbHelper.writableDatabase

        list.forEach {
            values.apply {
                put(User.USER_ID, it.userId)
                put(User.USER_NUMBER, it.userNumber)
                put(User.USER_NAME, it.userName)
            }
            db.insert(User.TABLE, null, values)
            values.clear()
        }
        db.close()
    }

    fun insertCategory(list: List<Category>) {
        val db = dbHelper.writableDatabase

        list.forEach {
            values.apply {
                put(Category.CATEGORY_ID, it.categoryId)
                put(Category.CATEGORY_TITLE, it.categoryTitle)
            }
            db.insert(Category.TABLE, null, values)
            values.clear()
        }
        db.close()
    }

    fun insertProduct(list: List<Product>) {
        val db = dbHelper.writableDatabase

        list.forEach {
            values.apply {
                put(Product.PRODUCT_ID, it.productId)
                put(Product.PRODUCT_TITLE, it.productTitle)
                put(Product.PRODUCT_SIZE, it.productSize)
                put(Product.PRODUCT_PRICE, it.productPrice)
                put(Product.CATEGORY_ID, it.categoryId)
            }
            db.insert(Product.TABLE, null, values)
            values.clear()
        }
        db.close()
    }

    fun insertOrders(list: List<Orders>) {
        val db = dbHelper.writableDatabase

        list.forEach {
            values.apply {
                put(Orders.ORDER_DATE, it.orderDate)
                put(Orders.USER_ID, it.userId)
                put(Orders.PRODUCT_ID, it.productId)
            }
            db.insert(Orders.TABLE, null, values)
            values.clear()
        }
        db.close()
    }

    fun fetchProduct(): List<Product> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(Product.TABLE, null, null, null, null, null, null)

        if (cursor != null && cursor.count > 0) {
            val products = ArrayList<Product>(cursor.count)
            cursor.moveToFirst()

            do {
                var index = cursor.getColumnIndex(Product.PRODUCT_ID)
                val id = cursor.getInt(index)

                index = cursor.getColumnIndex(Product.PRODUCT_TITLE)
                val title = cursor.getString(index)

                index = cursor.getColumnIndex(Product.PRODUCT_SIZE)
                val size = cursor.getString(index)

                index = cursor.getColumnIndex(Product.PRODUCT_PRICE)
                val price = cursor.getInt(index)

                index = cursor.getColumnIndex(Product.CATEGORY_ID)
                val catId = cursor.getInt(index)

                products.add(Product(id, title, size, price, catId))
            } while (cursor.moveToNext())

            cursor.close()
            db.close()
            return products
        }
        db.close()
        return emptyList()
    }

    fun deleteOrders() {
        val db = dbHelper.writableDatabase
        db.delete(Orders.TABLE, "${Orders.USER_ID} == 3", null)
        db.close()
    }

    fun updateOrders(): Int {
        val db = dbHelper.writableDatabase
        values.apply {
            put(Orders.ORDER_DATE, "Order is accepted")
            put(Orders.PRODUCT_ID, "coffee")
        }
        return db.update(Orders.TABLE, values, "${Orders.PRODUCT_ID} == 1", null)
    }

    fun joinUserOrders() {
        val db = dbHelper.writableDatabase

        val joinQuery =
            "SELECT o.order_date, u.user_name FROM ${User.TABLE} u " +
                    "INNER JOIN ${Orders.TABLE} o ON u._id = o.user_id;"

        val cursor = db.rawQuery(joinQuery, null)
        cursor.close()
        db.close()
    }
}