package com.android_tetris.ui.theme


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_tetris.infoStorage

@Composable
fun topBarView_Set(){
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = 4.dp,
        contentPadding = AppBarDefaults.ContentPadding
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "menu按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        //点击了按钮
                        infoStorage.currentScreen = 0
                        //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "Home",fontSize = 17.sp,color = Color.White)
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "menu按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        //点击了按钮
                        infoStorage.currentScreen = 2
                        //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "More",fontSize = 17.sp,color = Color.White)
        }
    }
}

@Preview
@Composable
fun topBarView_More(){
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = 4.dp,
        contentPadding = AppBarDefaults.ContentPadding
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "menu按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        //点击了按钮
                        infoStorage.currentScreen = 0
                        //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "Home",fontSize = 17.sp,color = Color.White)
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "menu按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        //点击了按钮
                        infoStorage.currentScreen = 1
                        //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "Setting",fontSize = 17.sp,color = Color.White)
        }
    }
}

