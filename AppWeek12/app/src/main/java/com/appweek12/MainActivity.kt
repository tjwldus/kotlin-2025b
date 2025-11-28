package com.appweek12

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.appweek12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CounterViewModel by viewModels() //view 모델을 생성하는 것


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.count.observe(this){
            count -> binding.textViewCount.text = count.toString()

            when{
                count > 0 -> binding.textViewCount.setTextColor(Color.BLUE)
                count < 0 -> binding.textViewCount.setTextColor(Color.RED)
                else -> binding.textViewCount.setTextColor(Color.BLACK)
            }
        }
    }


    private fun setupListeners() {
        binding.buttonPlus.setOnClickListener {
            viewModel.increment()
        }
        binding.buttonMinus.setOnClickListener {
            viewModel.decrement()
        }
        binding.buttonReset.setOnClickListener {
            viewModel.reset()
        }
        binding.buttonPlus10.setOnClickListener {
            viewModel.incrementBy10()
        }
    }

}