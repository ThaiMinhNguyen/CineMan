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
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = CustomWebViewClient(navController)

                webChromeClient = CustomWebChromeClient()

                settings.javaScriptEnabled = true

                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl(mUrl)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            it.loadUrl(mUrl)
        }
    )
}

class CustomWebViewClient(private val navController: NavController) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()

        if (url.startsWith("https://google.com")) {
            return true
        }

        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        url?.let {
            when {
                it.endsWith("/deny") -> {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
                        launchSingleTop = true
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