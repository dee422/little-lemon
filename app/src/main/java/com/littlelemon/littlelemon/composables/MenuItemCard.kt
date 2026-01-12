package com.littlelemon.littlelemon.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.littlelemon.littlelemon.database.MenuItemEntity


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(item: MenuItemEntity) {

    val imageUrl = item.image
        .replace("github.com", "raw.githubusercontent.com")
        .replace("/blob/", "/")
        .replace("?raw=true", "")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        // ===== 左侧文字区 =====
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF495E57) // Little Lemon 深绿
            )
        }

        // ===== 右侧图片 =====
        GlideImage(
            model = imageUrl,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

