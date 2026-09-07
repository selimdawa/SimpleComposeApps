package com.flatcode.simplecomposeapps.pdfreader.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.pdfreader.ui.PdfReaderScreen
import com.flatcode.simplecomposeapps.pdfreader.viewmodel.PdfViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class PdfReaderActivity : ComponentActivity() {

    private val viewModel: PdfViewModel by viewModels()

    private val documentPickerLauncher = registerForActivityResult(OpenDocument()) { selectedUri ->
        selectedUri?.let { viewModel.setUri(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        intent.data?.let { viewModel.setUri(it) }

        if (viewModel.uiState.value.uri == null) {
            documentPickerLauncher.launch(arrayOf("application/pdf"))
        }

        setContent {
            PdfReaderNavHost(
                viewModel = viewModel,
                onPickFile = { documentPickerLauncher.launch(arrayOf("application/pdf")) },
                onShare = { shareFile() },
                onPrint = { printDocument() }
            )
        }
    }

    private fun shareFile() {
        val uri = viewModel.uiState.value.uri
        uri?.let {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, it)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(intent, Strings.SHARE_FILE))
        }
    }

    private fun printDocument() {
        val mgr = getSystemService(PRINT_SERVICE) as PrintManager
        val uri = viewModel.uiState.value.uri
        uri?.let {
            mgr.print("PDF Document", SimplePdfPrintAdapter(this, it), null)
        }
    }

    private class SimplePdfPrintAdapter(private val context: Context, private val uri: Uri) :
        PrintDocumentAdapter() {

        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes?,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback?,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback?.onLayoutCancelled()
                return
            }
            val info = PrintDocumentInfo.Builder("document.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            callback?.onLayoutFinished(info, true)
        }

        override fun onWrite(
            pages: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback?
        ) {
            try {
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(destination?.fileDescriptor).use { output ->
                        input.copyTo(output)
                    }
                }
                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } catch (e: IOException) {
                callback?.onWriteFailed(e.message)
            }
        }
    }
}


@Composable
fun PdfReaderNavHost(
    viewModel: PdfViewModel,
    onPickFile: () -> Unit,
    onShare: () -> Unit,
    onPrint: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "reader") {
        composable("reader") {
            PdfReaderScreen(
                viewModel = viewModel,
                onPickFile = onPickFile,
                onMeta = { /* Reverted */ },
                onShare = onShare,
                onPrint = onPrint,
                onFullscreen = { viewModel.toggleBottomBar() }
            )
        }
    }
}

