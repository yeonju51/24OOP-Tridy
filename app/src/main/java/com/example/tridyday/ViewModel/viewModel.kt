package com.example.tridyday.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.tridyday.Model.Repository
import com.example.tridyday.Model.Travel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

//위치 등록후 스케쥴 로 넘어갈 데이터
var newLocate = Travel.Schedule.Locate("", "", 0.0, 0.0, "")
var selectedTravelId: String? = "-OEC94JfCWPl9Uf0Uwz_" // 문제 1

class ViewModel : ViewModel() {

    private val repository = Repository()

    private val _travels = MutableLiveData<MutableList<Travel>>()
    val travels: LiveData<MutableList<Travel>> get() = _travels

    private val _schedules = MutableLiveData<MutableList<Travel.Schedule>>()
    val schedules: LiveData<MutableList<Travel.Schedule>> get() = _schedules

    init {
        repository.observeSchedule(_schedules)
        repository.observeTravels(_travels)
    }


    val selectedTravel: LiveData<Travel?> = _travels.map { travelList ->
        Log.e("ViewModel - SelectedTravel", "Current Travel ID: $selectedTravelId")
        travelList.find { it.id == selectedTravelId }
    }

    fun addSchedule(travel: Travel, schedule: Travel.Schedule) {
        Log.e("ViewModel - addSchedule", "Current Travel ID: ${travel.id}")
        repository.postSchedule(travel.id.toString(), schedule, onSuccess = {
            _schedules.value?.add(schedule)
            _schedules.value = _schedules.value // LiveData 갱신
        }, onFailure = {
            Log.e("ScheduleViewModel", "스케줄 등록 실패: ${it.message}")
        })
    }

    fun observeTravels() {
        repository.observeTravels(_travels) // Repository에서 데이터를 가져와서 LiveData 업데이트
    }

    // 여행 데이터를 추가할 때 여행 일수를 계산하고 저장
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTravel(travel: Travel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.saveTravel(travel, {
            // 성공 시 _travels 업데이트
            val currentTravels = _travels.value?.toMutableList() ?: mutableListOf()
            currentTravels.add(travel)
            _travels.value = currentTravels
            onSuccess()
        }, onFailure)
    }

//    private fun observeTravel() {
//        repository.observeTravel { travelList ->
//            _travels.postValue(travelList) // Firebase의 변경 사항을 즉시 UI에 반영
//        }
//    }




    // startDate, endDate를 저장할 MutableLiveData
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate

    // 날짜를 ViewModel에서 처리하기 위해 startDate 값을 설정
    fun setStartDate(date: String) {
        _startDate.value = date
    }

    // 날짜를 ViewModel에서 처리하기 위해 endDate 값을 설정
    fun setEndDate(date: String) {
        _endDate.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)

    fun calculateDaysBetween(startDate: String, endDate: String): Int {
        val start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE)
        val end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE)
        return start.until(end, java.time.temporal.ChronoUnit.DAYS).toInt() + 1 // +1은 시작일부터 포함
    }

    private var selTravel = 0

    fun sendSelTravel(sel: Int){
        selTravel = sel
    }
    fun recvSelTravel():Int {
        return selTravel
    }
}
