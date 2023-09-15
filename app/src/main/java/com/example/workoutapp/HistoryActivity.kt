package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Workout History"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)

    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect{allCompletedDates ->
                if (allCompletedDates.isNotEmpty()){
                    binding?.rvItemsList?.visibility = View.VISIBLE
                    binding?.rvItemsList?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for (date in allCompletedDates){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvItemsList?.adapter = historyAdapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
