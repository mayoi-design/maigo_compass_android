package jp.ac.mayoi.maigocompass.bottomNavigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.textStyleCaption

@Composable
fun BottomNavItem(
    icon:Int,
    label:String,
    onClick:()->Unit = {},
    color: Color
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ){
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.size(40.dp),
            tint = color,
            contentDescription = null,
        )
        Text(
            text = label,
            style = textStyleCaption,
            color = color,
        )
    }
}

@Preview
@Composable
fun BottomNavItemPreView(){
    BottomNavItem(
        icon = jp.ac.mayoi.maigocompass.R.drawable.navbaritem_travel,
        label = "旅をする",
        onClick = {},
        color = colorAccent,
    )
}