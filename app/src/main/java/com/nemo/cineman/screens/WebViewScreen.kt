package com.nemo.cineman.screens

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun WebViewScreen(url: String?, navController: NavController) {
    val context = LocalContext.current
    val mUrl = if (url.isNullOrEmpty()) {
        "https://www.google.com"
    } else {
        url
    }  // URL bạn muốn tải

    // Thêm WebView vào Compose bằng AndroidView
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Thiết lập LayoutParams để WebView chiếm toàn bộ màn hình
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // Tùy chỉnh WebViewClient để can thiệp vào URL
                webViewClient = CustomWebViewClient(navController)

                // Tùy chỉnh WebChromeClient để xử lý các sự kiện web (ví dụ: sự kiện console, alert, v.v...)
                webChromeClient = CustomWebChromeClient()

                // Bật JavaScript
                settings.javaScriptEnabled = true

                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl(mUrl)
            }
        },
        modifier = Modifier.fillMaxSize(), // Đảm bảo WebView chiếm toàn bộ màn hình
        update = {
            it.loadUrl(mUrl) // Cập nhật URL khi cần thiết
        }
    )
}

class CustomWebViewClient(private val navController: NavController) : WebViewClient() {
    // Can thiệp vào các URL trước khi WebView xử lý chúng
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()

        // Nếu URL bắt đầu bằng "https://google.com", chặn việc mở WebView mới
        if (url.startsWith("https://google.com")) {
            // Chỉ chấp nhận URL này, không mở trình duyệt ngoài WebView
            return true
        }

        // Các URL khác sẽ vẫn mở bình thường trong WebView
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        // Kiểm tra nếu URL kết thúc bằng "/deny" hoặc "/allow"
        url?.let {
            when {
                it.endsWith("/deny") -> {
                    navController.navigate("login") {
                        // Xóa hết toàn bộ backstack và điều hướng đến login
                        popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
                        launchSingleTop = true // Đảm bảo màn hình không bị thêm vào backstack
                    }
                }
                it.endsWith("/allow") -> {
                    navController.navigate("menu") {
                        popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

class CustomWebChromeClient : WebChromeClient() {
    // Lắng nghe các sự kiện của WebView, ví dụ như console logs, alert dialogs, v.v...
    override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage?): Boolean {
        // Xử lý log console nếu cần
        consoleMessage?.let {
            println("MyLog: ${it.message()}")
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)

    }
}