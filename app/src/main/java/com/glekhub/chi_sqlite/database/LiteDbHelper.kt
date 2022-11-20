package com.glekhub.chi_sqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.glekhub.chi_sqlite.models.*

class LiteDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(User.SQL_CREATE_USER)
        db.execSQL(Category.SQL_CREATE_CATEGORY)
        db.execSQL(Product.SQL_CREATE_PRODUCT)
        db.execSQL(Orders.SQL_CREATE_ORDER)

        val createViewSql =
            "CREATE VIEW $VIEW_NAME AS SELECT o.${Orders.ORDER_DATE}, u.${User.USER_NAME} " +
                    "FROM ${User.TABLE} u, ${Orders.TABLE} o " +
                    "WHERE u.${User.USER_ID} = o.${Orders.USER_ID};"

        db.execSQL(createViewSql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        when (newVersion) {
            DATABASE_VERSION_2 -> {
                migrate(db)
            }
            DATABASE_VERSION_3 -> {
                db.execSQL(User.SQL_DELETE_USER)
                db.execSQL(Category.SQL_DELETE_CATEGORY)
                db.execSQL(Product.SQL_DELETE_PRODUCT)
                db.execSQL(Orders.SQL_DELETE_ORDER)
                db.execSQL(Goods.SQL_DELETE_GOODS)

                val deleteViewSql = "DROP VIEW IF EXISTS $VIEW_NAME"

                db.execSQL(deleteViewSql)
                onCreate(db)
            }
        }
    }

    private fun migrate(db: SQLiteDatabase) {
        val updateUserTable =
            "ALTER TABLE ${User.TABLE} " +
                    "ADD ${User.DEFAULT_PASS} TEXT NOT NULL DEFAULT 'qwerty1234';"

        db.execSQL(updateUserTable)
        db.execSQL(Goods.SQL_CREATE_GOODS)

        val updateProductTable =
            "INSERT INTO ${Goods.TABLE} (${Goods.GOODS_TITLE}, ${Goods.GOODS_SIZE}, ${Goods.GOODS_PRICE}) " +
                    "SELECT ${Product.PRODUCT_TITLE}, ${Product.PRODUCT_SIZE}, ${Product.PRODUCT_PRICE} " +
                    "FROM ${Product.TABLE}"

        db.execSQL(updateProductTable)

        val createTrigger =
            "CREATE TRIGGER update_value BEFORE INSERT ON ${Orders.TABLE} " +
                    "BEGIN " +
                    "SELECT " +
                    "CASE " +
                    "WHEN NEW.${Orders.PRODUCT_ID} NOT LIKE 5 THEN " +
                    "RAISE (ABORT,'Invalid product_id') " +
                    "END; " +
                    "END;"

        db.execSQL(createTrigger)
    }

    companion object {
        const val DATABASE_NAME = "Lite.db"
        const val VIEW_NAME = "CafeView"
        const val DATABASE_VERSION = 1
        const val DATABASE_VERSION_2 = 2
        const val DATABASE_VERSION_3 = 3
    }
}