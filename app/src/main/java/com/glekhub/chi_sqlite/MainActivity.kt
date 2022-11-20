package com.glekhub.chi_sqlite

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.glekhub.chi_sqlite.database.LiteDbManager
import com.glekhub.chi_sqlite.databinding.ActivityMainBinding
import com.glekhub.chi_sqlite.models.Category
import com.glekhub.chi_sqlite.models.Orders
import com.glekhub.chi_sqlite.models.Product
import com.glekhub.chi_sqlite.models.User
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dbManager: LiteDbManager? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this.deleteDatabase("Lite.db")
        dbManager = LiteDbManager(this)

        binding.apply {
            insert.setOnClickListener {
                insertUser()
                insertCategory()
                insertProduct()
                insertOrders()
                Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()
            }
            fetch.setOnClickListener {
                fetchProduct()
            }
            delete.setOnClickListener {
                deleteOrders()
            }
            update.setOnClickListener {
                updateOrders()
            }
        }
    }

    private fun insertUser() {
        dbManager?.insertUser(
            listOf(
                User(1, 987654321, "Vlad"),
                User(2, 123456789, "Max"),
                User(3, 888888888, "Papin")
            )
        )
    }

    private fun insertCategory() {
        dbManager?.insertCategory(
            listOf(
                Category(1, "hot drink"),
                Category(2, "cold drink"),
                Category(3, "meal"),
                Category(4, "snack")
            )
        )
    }

    private fun insertProduct() {
        dbManager?.insertProduct(
            listOf(
                Product(1, "coffee", "S", 30, 1),
                Product(2, "lemonade", "L", 50, 2),
                Product(3, "cake", "M", 40, 3),
                Product(4, "snack", "S", 20, 4)
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun insertOrders() {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = formatter.format(Date())
        dbManager?.insertOrders(
            listOf(
                Orders(current, 1, 1),
                Orders(current, 2, 2),
                Orders(current, 2, 3),
                Orders(current, 3, 1),
                Orders(current, 3, 4)
            )
        )
    }

    private fun fetchProduct() {
        dbManager?.fetchProduct()?.forEach {
            Log.d(
                "TAG", "Fetch product:\t${it.productId}\t-\t${it.productTitle}\t-\t" +
                        "${it.productSize}\t-\t${it.productPrice}\t-\t${it.categoryId}"
            )
        }
    }

    private fun deleteOrders() {
        dbManager?.deleteOrders()
    }

    private fun updateOrders() {
        val result = dbManager?.updateOrders()
        Toast.makeText(this, "Updated $result items", Toast.LENGTH_SHORT).show()
    }
}