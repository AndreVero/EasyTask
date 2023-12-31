import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun EasyTaskTheme(
    content: @Composable () -> Unit
) {
    val metalsmithRegular = FontFamily(
        font("MetalSmith", "metalsmith_regular", FontWeight.Normal, FontStyle.Normal)
    )
    val ibmPlexMono = FontFamily(
        font("StoryStyle", "ibmplexmono_regular", FontWeight.Normal, FontStyle.Normal)
    )

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color.White,
            background = Color.Black
        ),
        typography = Typography(
            h3 = TextStyle(
                fontFamily = metalsmithRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            h4 = TextStyle(
                fontFamily = metalsmithRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            body1 = TextStyle(
                fontFamily = ibmPlexMono,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),
            body2 = TextStyle(
                fontFamily = ibmPlexMono,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
        )
    ) {
        content()
    }
}