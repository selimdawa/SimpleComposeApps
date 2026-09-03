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
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.pdfreader.ui.PdfReaderScreen
import com.flatcode.simplecomposeapps.pdfreader.viewmodel.PdfViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings
import java.io.FileOutputStream
import java.io.IOException

class PdfReaderActivity : ComponentActivity() {

    private lateinit var viewModel: PdfViewModel

    private val documentPickerLauncher = registerForActivityResult(OpenDocument()) { selectedUri ->
        selectedUri?.let { viewModel.setUri(it, this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PdfViewModel::class.java]

        intent.data?.let { viewModel.setUri(it, this) }

        if (viewModel.uiState.value.uri == null) {
            documentPickerLauncher.launch(arrayOf("application/pdf"))
        }

        setContent {
            PdfReaderScreen(
                viewModel = viewModel,
                onPickFile = { documentPickerLauncher.launch(arrayOf("application/pdf")) },
                onMeta = { /* Reverted */ },
                onShare = { shareFile() },
                onPrint = { printDocument() },
                onFullscreen = { viewModel.toggleBottomBar() }
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
