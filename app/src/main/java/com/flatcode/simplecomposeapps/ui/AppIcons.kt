package com.flatcode.simplecomposeapps.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.R

object AppIcons {
    // Bitmaps (PNG/JPG)
    val Logo = R.drawable.logo
    val LogoIcon = R.drawable.logo_icon
    val Background = R.drawable.background
    val CandyCrush = R.drawable.ic_candy_crush
    val RandomImage = R.drawable.ic_random
    val Blogger = R.drawable.ic_blogger
    val Joke = R.drawable.ic_joke
    val NewsMulti = R.drawable.ic_news
    val WebApp = R.drawable.ic_web
    val WordPress = R.drawable.ic_wordpress
    val Support = R.drawable.ic_support
    val Twitter = R.drawable.ic_twitter
    val Website = R.drawable.ic_website
    val AboutUs = R.drawable.ic_about_us
    val Facebook = R.drawable.ic_facebook
    val Instagram = R.drawable.ic_instagram
    val Rate = R.drawable.ic_rate
    val Email = R.drawable.email
    val Phone = R.drawable.ic_phone
    val Blur = R.drawable.blur
    val HelloKitty = R.drawable.hellokitty
    val InfoIcon = R.drawable.info
    val Down = R.drawable.down
    val Refresh = R.drawable.refresh
    val CandyBackground = R.drawable.ccs_82_background
    val PermissionsPattern = R.drawable.patterns_permissions

    // Candies
    val BlueCandy = R.drawable.bluecandy
    val GreenCandy = R.drawable.greencandy
    val RedCandy = R.drawable.redcandy
    val OrangeCandy = R.drawable.orangecandy
    val YellowCandy = R.drawable.yellowcandy
    val PurpleCandy = R.drawable.purplecandy

    private val LoadingImg: ImageVector by lazy {
        ImageVector.Builder(
            name = "LoadingImg",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            val offset = 26f
            val pathData = listOf(
                Triple("M36.06,28.92L36.06,32.18", 0xFFE7E7E7, 0xFFCCCCCf),
                Triple("M39.45,29.88L37.82,32.71", 0xFFCACACA, 0xFFC8C8CC),
                Triple("M42.12,32.32L39.3,33.95", 0xFFCDCDCD, 0xFFBBBBBE),
                Triple("M39.8,35.98L43.06,35.98", 0xFFCBCBCB, 0xFFB2B2B7),
                Triple("M32.77,29.99L34.4,32.81", 0xFFEDEDED, 0xFFD0D0D4),
                Triple("M30.1,32.42L32.92,34.05", 0xFF525252, 0xFF949497),
                Triple("M32.42,35.98L29.16,35.98", 0xFF6E6E6E, 0xFF97979B),
                Triple("M36.06,43.08L36.06,39.82", 0xFFA0A0A0, 0xFFA8A8AC),
                Triple("M39.7,41.99L38.07,39.16", 0xFFCACACA, 0xFFCACACA),
                Triple("M42.19,39.4L39.37,37.77", 0xFFCCCCCC, 0xFFB6B6BA),
                Triple("M32.46,41.98L34.09,39.16", 0xFF909090, 0xFFA1A1A5),
                Triple("M29.85,39.4L32.67,37.77", 0xFF7A7A7A, 0xFF9D9DA0)
            )
            pathData.forEach { (data, stroke, fill) ->
                path(
                    fill = SolidColor(Color(fill)),
                    stroke = SolidColor(Color(stroke)),
                    strokeLineWidth = 1f,
                    strokeLineCap = StrokeCap.Round
                ) {
                    val parts = data.substring(1).split("L", ",")
                    moveTo(parts[0].toFloat() - offset, parts[1].toFloat() - offset)
                    lineTo(parts[2].toFloat() - offset, parts[3].toFloat() - offset)
                }
            }
        }.build()
    }

    // Vectors (ImageVector)
    val StopWatch: ImageVector = Icons.Default.Timer
    val MultiDelete: ImageVector = Icons.Default.DeleteSweep
    val LiveTv: ImageVector = Icons.Default.LiveTv
    val PdfReader: ImageVector = Icons.Default.PictureAsPdf
    val VideoPlayer: ImageVector = Icons.Default.VideoLibrary
    val Dogs: ImageVector = Icons.Default.Pets
    val Countries: ImageVector = Icons.Default.Flag
    val Calculator: ImageVector = Icons.Default.Calculate
    val Crypto: ImageVector = Icons.Default.MonetizationOn
    val Dictionary: ImageVector = Icons.AutoMirrored.Filled.MenuBook
    val Meals: ImageVector = Icons.Default.Restaurant
    val Pop: ImageVector = Icons.Default.Gamepad
    val Movie: ImageVector = Icons.Default.Movie
    val News: ImageVector = Icons.Default.Newspaper
    val RickAndMorty: ImageVector = Icons.Default.Face
    val Weather: ImageVector = Icons.Default.WbSunny
    val Poke: ImageVector = Icons.Default.CatchingPokemon
    val TodoNote: ImageVector = Icons.AutoMirrored.Filled.Note
    val StockMarket: ImageVector = Icons.AutoMirrored.Filled.TrendingUp
    val Info: ImageVector = Icons.Default.Info
    val Load: ImageVector = Icons.Default.CloudDownload
    val ConnectionError: ImageVector = Icons.Default.WifiOff

    val CircleGreen: ImageVector = Icons.Default.Circle
    val CircleRed: ImageVector = Icons.Default.Circle

    val Play: ImageVector = Icons.Default.PlayArrow
    val Pause: ImageVector = Icons.Default.Pause
    val Stop: ImageVector = Icons.Default.Stop
    val Back: ImageVector = Icons.AutoMirrored.Filled.ArrowBack

    val FolderOpen: ImageVector = Icons.Default.FolderOpen
    val Fullscreen: ImageVector = Icons.Default.Fullscreen
    val Infinity: ImageVector = Icons.Default.AllInclusive
    val Article: ImageVector = Icons.AutoMirrored.Filled.Article
    val Add: ImageVector = Icons.Default.Add
    val Check: ImageVector = Icons.Default.Check
    val DateRange: ImageVector = Icons.Default.DateRange
    val Delete: ImageVector = Icons.Default.Delete
    val DeleteCal: ImageVector = Icons.Default.DeleteOutline
    val Favorite: ImageVector = Icons.Default.Favorite
    val FavoriteBorder: ImageVector = Icons.Outlined.FavoriteBorder
    val PriorityHigh: ImageVector = Icons.Default.PriorityHigh
    val Search: ImageVector = Icons.Default.Search
    val Sort: ImageVector = Icons.AutoMirrored.Filled.Sort
    val BrokenImage: ImageVector = Icons.Default.BrokenImage
    val Category: ImageVector = Icons.Default.Category
    val CheckCircle: ImageVector = Icons.Default.CheckCircle
    val ClearAll: ImageVector = Icons.Default.ClearAll
    val Close: ImageVector = Icons.Default.Close
    val EventNote: ImageVector = Icons.AutoMirrored.Filled.EventNote
    val Folder: ImageVector = Icons.Default.Folder
    val Home: ImageVector = Icons.Default.Home
    val Location: ImageVector = Icons.Default.LocationOn
    val Minus: ImageVector = Icons.Default.Remove
    val More: ImageVector = Icons.Default.MoreVert
    val Person: ImageVector = Icons.Default.Person
    val SelectAll: ImageVector = Icons.Default.SelectAll
    val Star: ImageVector = Icons.Default.Star
    val TodoCheck: ImageVector = Icons.Default.TaskAlt
    val Video: ImageVector = Icons.Default.VideoLibrary
    val InfoOutline: ImageVector = Icons.Outlined.Info
    val Loading: ImageVector by lazy { LoadingImg }
    val Lock: ImageVector = Icons.Default.Lock
    val MetaInfo: ImageVector = Icons.Default.Info
    val Print: ImageVector = Icons.Default.Print
    val Share: ImageVector = Icons.Default.Share
}