package com.example.foodtracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodtracker.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "FoodsMain/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val foods = mutableListOf<Food>()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
//            val db = (application as FoodApplication).db
//            lifecycleScope.launch(Dispatchers.IO) {
//                db.foodDao().deleteAll()
//            }
            val intent = android.content.Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        foodAdapter = FoodAdapter(foods) { food ->
            Toast.makeText(this, "You clicked ${food.name}", Toast.LENGTH_SHORT).show()
        }
        binding.foods.adapter = foodAdapter
        binding.foods.layoutManager = LinearLayoutManager(this)
        binding.foods.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        lifecycleScope.launch {
            val db = (application as FoodApplication).db
            db.foodDao().getAll().collect { databaseList ->
                val mappedList = databaseList.map { entity ->
                    Food(entity.name, entity.calories)
                }
                foods.clear()
                foods.addAll(mappedList)

                Log.d(TAG, "Mapped foods: $mappedList")
                Log.d(TAG, "Mapped foods: $foods")
                foodAdapter.notifyDataSetChanged()
            }
        }

    }
}
