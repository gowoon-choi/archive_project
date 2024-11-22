package com.nexters.fullstack.presentaion.viewmodel

import com.nexters.fullstack.presentaion.BaseViewModel

class OnBoardingViewModel : BaseViewModel() {

    fun getItem(order : Int) : OnBoardingData = list[order]

    companion object{
        val list = arrayListOf(
            OnBoardingData("스크린샷에\n라벨을 추가해보세요.", "라벨별로 스크린샷을 볼 수 있어요.", 1),
            OnBoardingData("앱 실행 없이 빠른\n라벨 추가가 가능해요.", "스크린 샷 공유 팝업에서 실행하세요.", 2),
            OnBoardingData("어딨더라?\n찾지말고 검색하세요!", "라벨을 통해 쉽고 빠르게 스크린샷을 찾으세요.", 3)
        )
    }

    data class OnBoardingData(
        val main : String,
        val sub : String,
        val order : Int
    )
}