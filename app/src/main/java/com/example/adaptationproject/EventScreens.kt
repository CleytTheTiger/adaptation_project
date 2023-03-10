package com.example.adaptationproject

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adaptationproject.ui.theme.softGreen
import com.example.adaptationproject.ui.theme.softRed
import org.json.JSONObject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AvailableEventScreen(context: Context, availableEvents : MutableList<eventMenuItem>, user_id : String) {
    var text = ""
    if (availableEvents.size == 0){
        text = "Для вас нет доступных мероприятий :("
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(modifier = Modifier.padding(10.dp), text = text)
        }

    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(1),
                contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                modifier = Modifier.fillMaxHeight()) {
                items(availableEvents.size){
                    EventItem(item = availableEvents[it], type = "available", context, user_id)
                }
            }

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignedEventScreen(context: Context, signedEvents : MutableList<eventMenuItem>, user_id : String) {
    var text = ""
    if (signedEvents.size == 0){
        text = "Пока что вы никуда не записаны. Может стоит это исправить? :("
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(modifier = Modifier.padding(10.dp), text = text)
        }

    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(1),
                contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                modifier = Modifier.fillMaxHeight()) {
                items(signedEvents.size){
                    EventItem(item = signedEvents[it], type = "signed", context, user_id)
                }
            }
        }
    }

}

@Composable
fun EventDetailsScreen(item: MutableList<eventMenuItem>){
    val status = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Записаться") }
    val buttonColor = remember { mutableStateOf(softGreen) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.LightGray)
        .clickable { }) {
        Row(modifier = Modifier.padding(4.dp)) {
            Column {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(5.dp)
                    .size(125.dp)
                    .background(Color.Gray)) {

                }
            }
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                Text(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight(),text = item[0].name)
                Text(modifier = Modifier
                    .padding(4.dp),text = item[0].date)
                Button(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(), colors = ButtonDefaults.buttonColors(buttonColor.value),onClick = {
                    buttonText.value = "\u2713"
                    buttonColor.value = Color.Transparent
                    status.value = false

                },
                    enabled = status.value) {
                    Text(text = buttonText.value, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun EventItem (item : eventMenuItem, type : String, context: Context, user_id : String) {
    val status = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("") }
    val buttonColor = remember { mutableStateOf(softGreen) }
    if (type == "signed") {
        buttonText.value = "Отписаться"
        buttonColor.value = softRed
    } else {
        buttonText.value = "Записаться"
        buttonColor.value = softGreen
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.LightGray)
        .clickable {

        }) {
        Row(modifier = Modifier.padding(4.dp)) {
            Column {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(5.dp)
                        .size(125.dp)
                        .background(Color.Gray)
                ) {

                }
            }
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight(), text = item.name
                )
                Text(
                    modifier = Modifier
                        .padding(4.dp), text = item.date
                )
                Button(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(buttonColor.value),
                    onClick = {
                        var dialog = ProgressDialog.show(context, "", "", true)
                        when (type) {
                            "signed" -> {
                                eventQuery(context, user_id, item.id, "api/DeleteUserEvent.php")
                            }
                            "available" -> {
                                eventQuery(context, user_id, item.id, "api/InsertUserEvent.php")
                            }
                        }
                        buttonText.value = "\u2713"
                        buttonColor.value = Color.Transparent
                        status.value = false
                        dialog?.dismiss()
                    },
                    enabled = status.value
                ) {
                    Text(text = buttonText.value, fontSize = 16.sp)
                }
            }
        }
    }
}

fun eventQuery(context: Context, userID : String, eventID : String, query : String){

    val url = Constants.BASE_URL + query
    val requestQueue = Volley.newRequestQueue(context)
    Toast.makeText(context, "Действие успешно выполнено!",Toast.LENGTH_SHORT).show()
    val stringRequest = object : StringRequest(
        Request.Method.POST, url,
        Response.Listener { response ->
            Log.e("Response","response: " + response)

            // Handle Server response here
            try {
                //val responseObj = JSONObject(response)
                //val isSuccess = responseObj.getBoolean("isSuccess")
                //val code = responseObj.getInt("code")
                //val message = responseObj.getString("message")
                //if (responseObj.has("data")) {
                //    val data = responseObj.getJSONObject("data")
                //    // Handle your server response data here
                //}

            } catch (e: Exception) { // caught while parsing the response
                Log.e("Error","problem occurred")
                e.printStackTrace()
            }
        },
        Response.ErrorListener { volleyError -> // error occurred
            Log.e("Error","problem occurred, volley error: " + volleyError.message)
        }) {
        override fun getParams(): HashMap<String,String>{
            val params = HashMap<String,String>()
            params["user_id"] = userID
            params["event_id"] = eventID
            return params
        }
    }
    requestQueue.add(stringRequest)
    requestQueue.start()
}
