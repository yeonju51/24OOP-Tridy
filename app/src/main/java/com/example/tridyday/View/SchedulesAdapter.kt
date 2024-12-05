package com.example.tridyday.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tridyday.Model.Travel
import com.example.tridyday.databinding.ListSchedulesBinding

class SchedulesAdapter(val schedule: MutableList<Travel.Schedule>?) : RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    private var scheduleList = mutableListOf<Travel.Schedule>()

    fun setSchedules(schedules: List<Travel.Schedule>) {
        scheduleList.clear()
        scheduleList.addAll(schedules)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSchedulesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(scheduleList[position])
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    class Holder(private val binding: ListSchedulesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Travel.Schedule) {
            binding.txtName.text = schedule.title
            binding.txtStartTime.text = schedule.startTime.toString()
            binding.txtEndTime.text = schedule.endTime.toString()
        }
    }
}