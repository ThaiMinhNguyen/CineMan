package com.nemo.cineman.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nemo.cineman.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val playwrite = FontFamily(Font(R.font.playwritecu_regular, FontWeight.Black))

// Font Narrow App (dùng cho các tiêu đề hẹp hoặc đặc biệt)
val narrowTitle = FontFamily(
    Font(R.font.facebook_narrow_app_regular, FontWeight.Normal)
)

// Font Sans Regular (dùng cho nội dung chính hoặc tiêu đề thông thường)
val body = FontFamily(
    Font(R.font.facebook_sans_regular, FontWeight.Normal)
)

// Font Sans Bold (dùng cho tiêu đề đậm hoặc nội dung nổi bật)
val title = FontFamily(
    Font(R.font.facebook_sans_bold, FontWeight.Bold)
)

// Font Sans Bold Italic (dùng cho tiêu đề đậm nghiêng hoặc chú thích đặc biệt)
val boldItalic = FontFamily(
    Font(R.font.facebook_sans_bold_italic, FontWeight.Bold, FontStyle.Italic)
)

// Font Sans Hairline (dùng cho văn bản nhẹ, mảnh, như chú thích nhỏ)
val light = FontFamily(
    Font(R.font.facebook_sans_hairline, FontWeight.Thin)
)

// Font Sans Hairline Italic (dùng cho văn bản mảnh nghiêng)
val lightItalic = FontFamily(
    Font(R.font.facebook_sans_hairline_italic, FontWeight.Thin, FontStyle.Italic)
)

// Font Sans Heavy (dùng cho tiêu đề rất đậm, nổi bật)
val heavyTitle = FontFamily(
    Font(R.font.facebook_sans_heavy, FontWeight.ExtraBold)
)

// Font Sans Heavy Italic (dùng cho tiêu đề rất đậm nghiêng)
val heavyItalic = FontFamily(
    Font(R.font.facebook_sans_heavy_italic, FontWeight.ExtraBold, FontStyle.Italic)
)

// Font Sans Italic (dùng cho văn bản nghiêng thông thường)
val italic = FontFamily(
    Font(R.font.facebook_sans_italic, FontWeight.Normal, FontStyle.Italic)
)

// Font Sans Light (dùng cho văn bản nhẹ, như phụ đề)
val subtitle = FontFamily(
    Font(R.font.facebook_sans_light, FontWeight.Light)
)

// Font Sans Light Italic (dùng cho văn bản nhẹ nghiêng)
val subtitleItalic = FontFamily(
    Font(R.font.facebook_sans_light_italic, FontWeight.Light, FontStyle.Italic)
)