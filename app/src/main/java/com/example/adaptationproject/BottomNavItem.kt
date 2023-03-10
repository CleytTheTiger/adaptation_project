package com.example.adaptationproject

import org.jetbrains.annotations.Nullable

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object AvailableEvents : BottomNavItem("Доступные", R.mipmap.ic_available_stuff_foreground,"available_events")
    object SignedEvents : BottomNavItem("Запланированные",R.mipmap.ic_search_result_foreground,"signed_events")
    //object EventDetails : BottomNavItem("",0,"event_details")
}