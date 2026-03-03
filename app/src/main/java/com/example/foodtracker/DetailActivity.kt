package com.example.foodtracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.foodtracker.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FoodDetailActivity"
const val FOOD_EXTRA = "FOOD_EXTRA"

class DetailActivity : AppCompatActivity() {

    private lateinit var inputFoodNameTv: EditText
    private lateinit var inputFoodCaloriesTv: EditText
    private lateinit var submitButton: Button
    private lateinit var binding: ActivityDetailBinding

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputFoodNameTv = findViewById(R.id.inputFoodNameTv)
        inputFoodCaloriesTv = findViewById(R.id.inputFoodCaloriesTv)
        submitButton = findViewById(R.id.submitButton)

        db = AppDatabase.getInstance(this)

        submitButton.setOnClickListener {
            val foodName = inputFoodNameTv.text.toString().trim()
            val caloriesText = inputFoodCaloriesTv.text.toString().trim()

            // Validate input
            if (foodName.isEmpty() || caloriesText.isEmpty()) {
                Toast.makeText(this, "Please enter both name and calories", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                db.foodDao().insert(FoodEntity(name = foodName, calories = caloriesText))

                launch(Dispatchers.Main) {
                    Toast.makeText(this@DetailActivity, "Food added!", Toast.LENGTH_SHORT).show()
                }
            }

            val intent = android.content.Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
