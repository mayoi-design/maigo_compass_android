package jp.ac.mayoi.maigocompass.bottomNavigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorTextCaption
import jp.ac.mayoi.core.resource.textStyleCaption

@Composable
fun BottomNavItem(
    @DrawableRes resId:Int,
    label:String,
    onClick:()->Unit,
    isSelected:Boolean,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ){
        Icon(
            painter = painterResource(resId),
            tint = if(isSelected){
                colorAccent
            }else{
                colorTextCaption
            },
            contentDescription = null,
            modifier = Modifier.size(40.dp),
        )
        Text(
            text = label,
            style = textStyleCaption,
            color = if(isSelected){
                colorAccent
            }else{
                colorTextCaption
            },
        )
    }
}

@Preview
@Composable
private fun BottomNavItemPreView(){
    BottomNavItem(
        resId = jp.ac.mayoi.maigocompass.R.drawable.navbaritem_travel,
        label = "旅をする",
        onClick = {},
        isSelected = true,
    )
}