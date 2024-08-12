package com.jobseeker.cluedetectivenotes.ui.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jobseeker.cluedetectivenotes.R

@Composable
fun GameCardView(context: Context, type:String, cardName:String, clickAction: () -> Unit, colorFilter: ColorFilter?) {

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(0.dp),
        onClick = { clickAction() }
    ) {
        var id = try {
            context.resources.getIdentifier(
                "img_" + type + "_" + cardName.lowercase(),
                "drawable",
                context.packageName
            )
        } catch (_: Exception) {
            0x0
        }
        if (id == 0x0) {
            id = R.drawable.img_none
        }
        Image(
            painterResource(id = id),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            colorFilter = colorFilter,
            //modifier = Modifier.align(Alignment.Center)
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(width = 1.dp, color = Color.Black)
                .background(color = Color(MaterialTheme.colorScheme.surface.value))
        )
    }
}