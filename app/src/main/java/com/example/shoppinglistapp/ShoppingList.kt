package com.example.shoppinglistapp

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.unit.dp

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    //false la gia tri mac dinh
    //muc dich kiem tra co dang trong trang thai Edit khong
    var isEditing: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp() {
    var sItems by remember {mutableStateOf(listOf<ShoppingItem>())}
    var showDialog by remember {mutableStateOf(false)}
    var itemName by remember {mutableStateOf("")}
    var itemQuantity by remember {mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),
//        đặt các thành phần con vào chính giữa theo chiều dọc của Column.
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {showDialog = true},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // ghi them ca {}, khong la bi loi
            items(sItems) {
                ShoppingListItem(item = it, onEditClick = { /*TODO*/ }) {
                    
                }
            }
        }
    }
    if(showDialog) {
        //onDismissRequest: khi user click ra ngoai AlertDialog thi tat
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        if(itemName.isNotBlank()) {
                            val newItem = ShoppingItem(
                                id = sItems.size + 1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            //them newItem vao danh sach sItems
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                        }
                    }) {
                        Text(text = "Add")
                    }
                    Button(onClick = { showDialog = false}) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = { Text(text = "Add Shopping Item")},
            //text: phan noi dung cua dialog
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        //it: String duoc nhap vao tu user
                        onValueChange = { itemName = it },
                        //cho phep chi nhap 1 dong vao
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    OutlinedTextField(
                        value = itemQuantity,
                        //it: String duoc nhap vao tu user
                        onValueChange = { itemQuantity = it },
                        //cho phep chi nhap 1 dong vao
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    //Ham Lamda: tu dinh nghia lai ham
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            )
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
    }
}
