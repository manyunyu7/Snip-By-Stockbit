package com.feylabs.content_viewer

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.feylabs.content_viewer.databinding.FragmentContentViewerBinding
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class ContentViewerFragment : Fragment() {


    private var _binding: FragmentContentViewerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentViewerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // In the destination fragment's code
        val navController = findNavController()
        val contentId = arguments?.getString("contentId")  ?: ""
        val contentType = arguments?.getString("contentType")  ?: ""
        val contentURL = arguments?.getString("contentURL")  ?: ""

        var url = ""
        if(contentType=="unboxing-sectoral" || contentType=="unboxing-stock" ){
            url = "https://academy.stockbit.com/unboxing/$contentId?theme=light"
        }


        if (contentType == "snip") {
            try {
                val decodedUrl = URLDecoder.decode(contentId, StandardCharsets.UTF_8.toString())
                url = decodedUrl
            } catch (e: UnsupportedEncodingException) {
                // Handle the exception
            }
        }

        val mWebView = binding.webview
        mWebView.gone()
        mWebView.loadUrl(url)
        // Enable Javascript
        val webSettings: WebSettings = mWebView.getSettings()
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.webViewClient = WebViewClient()
        mWebView.settings.setDomStorageEnabled(true)
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        mWebView.clearCache(true)

        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val handler = Handler()
                val delayMillis = 0 // Delay of 2 seconds (adjust as needed)

                // Post a delayed runnable to the UI thread
                handler.postDelayed({
                    // Code inside this block will run after the delay
                    binding.loadingIndicator.gone()
                    mWebView.visible()
                }, delayMillis.toLong())
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                binding.loadingIndicator.visible()
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                binding.loadingIndicator.gone()
                mWebView.visible()
                super.onReceivedError(view, request, error)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
            }
        }
    }

    fun titleToSlug(title: String): String {
        val normalizedString = title
            .lowercase() // Convert to lowercase
            .replace("[^a-z0-9\\s-]".toRegex(), "") // Remove non-alphanumeric characters except spaces and hyphens
            .replace("\\s+".toRegex(), "-") // Replace spaces with hyphens
            .replace("-+".toRegex(), "-") // Replace consecutive hyphens with a single hyphen

        return normalizedString
    }
}