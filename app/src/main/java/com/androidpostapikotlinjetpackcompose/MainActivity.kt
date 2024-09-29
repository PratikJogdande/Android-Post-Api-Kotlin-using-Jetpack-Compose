package com.androidpostapikotlinjetpackcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.androidpostapikotlinjetpackcompose.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPostApiKotlinJetPackComposeTheme {
                // on below line we are specifying background color for our application
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    // on the below line we are specifying the theme as the scaffold.
                    Scaffold(
                        // in scaffold we are specifying top bar.
                        topBar = {

                            // inside top bar we are specifying background color.
                            TopAppBar(backgroundColor = greenColor,

                                // along with that we are specifying title for our top bar.
                                title = {

                                    // in the top bar we are specifying tile as a text
                                    Text(
                                        // on below line we are specifying text to display in top app bar.
                                        text = "Retrofit POST Request in Android",

                                        // on below line we are specifying modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying color for our text.
                                        color = Color.White
                                    )
                                })
                        }) {
                        // on the below line we are calling the pop window dialog method to display ui.
                        postData()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun postData() {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val job = remember {
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }

    val inputModifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()

    // Define your text style
    val inputTextStyle = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 15.sp)
    // on below line we are creating a column.
    Column(
        // on below line we are adding a modifier to it
        // and setting max size, max height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        // on below line we are adding vertical
        // arrangement and horizontal alignment.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        // on below line we are creating a text field for our email.
        TextField(
            // on below line we are specifying value for our email text field.
            value = userName.value,
            // on below line we are adding on value change for text field.
            onValueChange = { userName.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = {
                Text(
                    text = "Enter your name",
                    style = TextStyle(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)))
            },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = inputModifier.border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.horizontalGradient(listOf(Color(0xFFBB86FC), Color(0xFF6200EE))) // Gradient colors
                ),
                shape = RoundedCornerShape(50)
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = inputTextStyle,
            singleLine = true,
        )

        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(5.dp))
        // on below line we are creating a text field for our email.
        TextField(
            // on below line we are specifying value for our email text field.
            value = job.value,
            // on below line we are adding on value change for text field.
            onValueChange = { job.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Enter your job", style = TextStyle(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))) },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier =  inputModifier.border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.horizontalGradient(listOf(Color(0xFFBB86FC), Color(0xFF6200EE))) // Gradient colors
                ),
                shape = RoundedCornerShape(50)
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = inputTextStyle,
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(10.dp))
        // on below line we are creating a button

        val verticalGradient = Brush.verticalGradient(
            colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
            startY = 0f,
            endY = 100f
        )
        Button(
            onClick = {
                // on below line we are calling make payment method to update data.
                postDataUsingRetrofit(
                    ctx, userName, job, response
                )
            },
            // on below line we are adding modifier to our button.
            modifier = Modifier
                .padding(12.dp)
                .clickable(onClick = {})
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradient)
                .padding(12.dp)
        ) {
            // on below line we are adding text for our button
            Text(text = "Post Data", modifier = Modifier.padding(8.dp))
        }
        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are creating a text for displaying a response.
        Text(
            text = response.value,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun postDataUsingRetrofit(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,
    result: MutableState<String>
) {
    var url = "https://reqres.in/api/"
    // on below line we are creating a retrofit
    // builder and passing our base url
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        // as we are sending data in json format so
        // we have to add Gson converter factory
        .addConverterFactory(GsonConverterFactory.create())
        // at last we are building our retrofit builder.
        .build()
    // below the line is to create an instance for our retrofit api class.
    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
    // passing data from our text fields to our model class.
    val dataModel = DataModel(userName.value.text, job.value.text)
    // calling a method to create an update and passing our model class.
    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)
    // on below line we are executing our method.
    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            // this method is called when we get response from our api.
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            // we are getting a response from our body and
            // passing it to our model class.
            val model: DataModel? = response.body()
            // on below line we are getting our data from model class
            // and adding it to our string.
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model!!.name + "\n" + "Job : " + model!!.job
            // below line we are setting our string to our response.
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            // we get error response from API.
            result.value = "Error found is : " + t.message
        }
    })
}