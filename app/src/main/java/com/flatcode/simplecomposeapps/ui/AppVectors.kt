package com.flatcode.simplecomposeapps.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppVectors {
    val Info: ImageVector = ImageVector.Builder(
        name = "Info",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        reflectiveCurveTo(4.48f, 22f, 12f, 22f)
        reflectiveCurveTo(22f, 17.52f, 22f, 12f)
        reflectiveCurveTo(17.52f, 2f, 12f, 2f)
        close()
        moveTo(13f, 17f)
        horizontalLineTo(11f)
        verticalLineTo(11f)
        horizontalLineTo(13f)
        verticalLineTo(17f)
        close()
        moveTo(13f, 9f)
        horizontalLineTo(11f)
        verticalLineTo(7f)
        horizontalLineTo(13f)
        verticalLineTo(9f)
        close()
    }.build()

    val Feed: ImageVector = ImageVector.Builder(
        name = "Feed",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(6.18f, 17.82f)
        moveToRelative(-2.18f, 0f)
        arcTo(2.18f, 2.18f, 0f, true, true, 8.36f, 17.82f)
        arcTo(2.18f, 2.18f, 0f, true, true, 4f, 17.82f)
    }.path(fill = SolidColor(Color.White)) {
        moveTo(4f, 4.44f)
        verticalLineToRelative(2.83f)
        curveTo(11.03f, 7.27f, 16.73f, 12.97f, 16.73f, 20f)
        horizontalLineToRelative(2.83f)
        curveTo(19.56f, 11.41f, 12.59f, 4.44f, 4f, 4.44f)
        close()
        moveTo(4f, 10.1f)
        verticalLineToRelative(2.83f)
        curveTo(7.9f, 12.93f, 11.07f, 16.1f, 11.07f, 20f)
        horizontalLineToRelative(2.83f)
        curveTo(13.9f, 14.53f, 9.47f, 10.1f, 4f, 10.1f)
        close()
    }.build()

    val Flag: ImageVector = ImageVector.Builder(
        name = "Flag",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(14.4f, 6f)
        lineTo(14f, 4f)
        horizontalLineTo(5f)
        verticalLineToRelative(17f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-7f)
        horizontalLineToRelative(5.6f)
        lineToRelative(0.4f, 2f)
        horizontalLineToRelative(7f)
        verticalLineTo(6f)
        close()
    }.build()

    val Game: ImageVector = ImageVector.Builder(
        name = "Game",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(21f, 6f)
        lineTo(3f, 6f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        verticalLineToRelative(8f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(18f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(23f, 8f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(11f, 13f)
        lineTo(8f, 13f)
        verticalLineToRelative(3f)
        lineTo(6f, 16f)
        verticalLineToRelative(-3f)
        lineTo(3f, 13f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(3f)
        lineTo(6f, 8f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(3f)
        horizontalLineToRelative(3f)
        verticalLineToRelative(2f)
        close()
        moveTo(15.5f, 15f)
        curveToRelative(-0.83f, 0f, -1.5f, -0.67f, -1.5f, -1.5f)
        reflectiveCurveToRelative(0.67f, -1.5f, 1.5f, -1.5f)
        reflectiveCurveToRelative(1.5f, 0.67f, 1.5f, 1.5f)
        reflectiveCurveToRelative(-0.67f, 1.5f, -1.5f, 1.5f)
        close()
        moveTo(19.5f, 12f)
        curveToRelative(-0.83f, 0f, -1.5f, -0.67f, -1.5f, -1.5f)
        reflectiveCurveToRelative(0.67f, -1.5f, 1.5f, -1.5f)
        reflectiveCurveToRelative(1.5f, 0.67f, 1.5f, 1.5f)
        reflectiveCurveToRelative(-0.67f, 1.5f, -1.5f, 1.5f)
        close()
    }.build()

    val Load: ImageVector = ImageVector.Builder(
        name = "Load",
        defaultWidth = 40.dp,
        defaultHeight = 40.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 4f)
        lineTo(12f, 1f)
        lineTo(8f, 5f)
        lineToRelative(4f, 4f)
        lineTo(12f, 6f)
        curveToRelative(3.31f, 0f, 6f, 2.69f, 6f, 6f)
        curveToRelative(0f, 1.01f, -0.25f, 1.97f, -0.7f, 2.8f)
        lineToRelative(1.46f, 1.46f)
        curveTo(19.54f, 15.03f, 20f, 13.57f, 20f, 12f)
        curveToRelative(0f, -4.42f, -3.58f, -8f, -8f, -8f)
        close()
        moveTo(12f, 18f)
        curveToRelative(-3.31f, 0f, -6f, -2.69f, -6f, -6f)
        curveToRelative(0f, -1.01f, 0.25f, -1.97f, 0.7f, -2.8f)
        lineTo(5.24f, 7.74f)
        curveTo(4.46f, 8.97f, 4f, 10.43f, 4f, 12f)
        curveToRelative(0f, 4.42f, 3.58f, 8f, 8f, 8f)
        verticalLineToRelative(3f)
        lineToRelative(4f, -4f)
        lineToRelative(-4f, -4f)
        verticalLineToRelative(3f)
        close()
    }.build()

    val Meal: ImageVector = ImageVector.Builder(
        name = "Meal",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(21.05f, 17.56f)
        lineTo(3.08f, 18.5f)
        lineTo(3f, 17f)
        lineToRelative(17.98f, -0.94f)
        lineTo(21.05f, 17.56f)
        close()
        moveTo(21f, 19.48f)
        horizontalLineTo(3f)
        verticalLineToRelative(1.5f)
        horizontalLineToRelative(18f)
        verticalLineTo(19.48f)
        close()
        moveTo(22f, 5f)
        verticalLineToRelative(7f)
        curveToRelative(0f, 1.1f, -0.9f, 2f, -2f, 2f)
        horizontalLineTo(4f)
        curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
        verticalLineTo(5f)
        curveToRelative(0f, -1.1f, 0.9f, -2f, 2f, -2f)
        horizontalLineToRelative(16f)
        curveToRelative(1.1f, 0f, 2f, 0.9f, 2f, 2f)
        close()
        moveTo(20f, 6f)
        curveToRelative(-1.68f, 0f, -3.04f, 0.98f, -3.21f, 2.23f)
        curveTo(16.15f, 7.5f, 14.06f, 5.5f, 10.25f, 5.5f)
        curveToRelative(-4.67f, 0f, -6.75f, 3f, -6.75f, 3f)
        reflectiveCurveToRelative(2.08f, 3f, 6.75f, 3f)
        curveToRelative(3.81f, 0f, 5.9f, -2f, 6.54f, -2.73f)
        curveTo(16.96f, 10.02f, 18.32f, 11f, 20f, 11f)
        verticalLineTo(6f)
        close()
    }.build()

    val Note: ImageVector = ImageVector.Builder(
        name = "Note",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 3f)
        horizontalLineTo(4.99f)
        curveTo(3.89f, 3f, 3f, 3.9f, 3f, 5f)
        lineToRelative(0.01f, 14f)
        curveToRelative(0f, 1.1f, 0.89f, 2f, 1.99f, 2f)
        horizontalLineToRelative(10f)
        lineToRelative(6f, -6f)
        verticalLineTo(5f)
        curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
        close()
        moveTo(7f, 8f)
        horizontalLineToRelative(10f)
        verticalLineToRelative(2f)
        horizontalLineTo(7f)
        verticalLineTo(8f)
        close()
        moveTo(12f, 14f)
        horizontalLineTo(7f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(5f)
        verticalLineTo(14f)
        close()
        moveTo(14f, 19.5f)
        verticalLineTo(14f)
        horizontalLineToRelative(5.5f)
        lineTo(14f, 19.5f)
        close()
    }.build()

    val Child: ImageVector = ImageVector.Builder(
        name = "Child",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(14.5f, 10.5f)
        moveToRelative(-1.25f, 0f)
        arcTo(1.25f, 1.25f, 0f, true, true, 15.75f, 10.5f)
        arcTo(1.25f, 1.25f, 0f, true, true, 13.25f, 10.5f)
    }.path(fill = SolidColor(Color.White)) {
        moveTo(9.5f, 10.5f)
        moveToRelative(-1.25f, 0f)
        arcTo(1.25f, 1.25f, 0f, true, true, 10.75f, 10.5f)
        arcTo(1.25f, 1.25f, 0f, true, true, 8.25f, 10.5f)
    }.path(fill = SolidColor(Color.White)) {
        moveTo(22.94f, 12.66f)
        curveToRelative(0.04f, -0.21f, 0.06f, -0.43f, 0.06f, -0.66f)
        reflectiveCurveToRelative(-0.02f, -0.45f, -0.06f, -0.66f)
        curveToRelative(-0.25f, -1.51f, -1.36f, -2.74f, -2.81f, -3.17f)
        curveToRelative(-0.53f, -1.12f, -1.28f, -2.1f, -2.19f, -2.91f)
        curveTo(16.36f, 3.85f, 14.28f, 3f, 12f, 3f)
        reflectiveCurveTo(7.64f, 3.85f, 6.06f, 5.26f)
        curveToRelative(-0.92f, 0.81f, -1.67f, 1.8f, -2.19f, 2.91f)
        curveTo(2.42f, 8.6f, 1.31f, 9.82f, 1.06f, 11.34f)
        curveToRelative(-0.04f, 0.21f, -0.06f, 0.43f, -0.06f, 0.66f)
        reflectiveCurveToRelative(0.02f, 0.45f, 0.06f, 0.66f)
        curveToRelative(0.25f, 1.51f, 1.36f, 2.74f, 2.81f, 3.17f)
        curveToRelative(0.52f, 1.11f, 1.27f, 2.09f, 2.17f, 2.89f)
        curveTo(7.62f, 20.14f, 9.71f, 21f, 12f, 21f)
        reflectiveCurveToRelative(4.38f, -0.86f, 5.97f, -2.28f)
        curveToRelative(0.9f, -0.8f, 1.65f, -1.79f, 2.17f, -2.89f)
        curveToRelative(1.44f, -0.43f, 2.55f, -1.65f, 2.8f, -3.17f)
        close()
        moveTo(19f, 14f)
        curveToRelative(-0.1f, 0f, -0.19f, -0.02f, -0.29f, -0.03f)
        curveToRelative(-0.2f, 0.67f, -0.49f, 1.29f, -0.86f, 1.86f)
        curveTo(16.6f, 17.74f, 14.45f, 19f, 12f, 19f)
        reflectiveCurveToRelative(-4.6f, -1.26f, -5.85f, -3.17f)
        curveToRelative(-0.37f, -0.57f, -0.66f, -1.19f, -0.86f, -1.86f)
        curveToRelative(-0.1f, 0.01f, -0.19f, 0.03f, -0.29f, 0.03f)
        curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
        reflectiveCurveToRelative(0.9f, -2f, 2f, -2f)
        curveToRelative(0.1f, 0f, 0.19f, 0.02f, 0.29f, 0.03f)
        curveToRelative(0.2f, -0.67f, 0.49f, -1.29f, 0.86f, -1.86f)
        curveTo(7.4f, 6.26f, 9.55f, 5f, 12f, 5f)
        reflectiveCurveToRelative(4.6f, 1.26f, 5.85f, 3.17f)
        curveToRelative(0.37f, 0.57f, 0.66f, 1.19f, 0.86f, 1.86f)
        curveToRelative(0.1f, -0.01f, 0.19f, -0.03f, 0.29f, -0.03f)
        curveToRelative(1.1f, 0f, 2f, 0.9f, 2f, 2f)
        reflectiveCurveToRelative(-0.9f, 2f, -2f, 2f)
        close()
        moveTo(7.5f, 14f)
        curveToRelative(0.76f, 1.77f, 2.49f, 3f, 4.5f, 3f)
        reflectiveCurveToRelative(3.74f, -1.23f, 4.5f, -3f)
        horizontalLineToRelative(-9f)
        close()
    }.build()

    val Movie: ImageVector = ImageVector.Builder(
        name = "Movie",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(18f, 3f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-2f)
        lineTo(16f, 3f)
        lineTo(8f, 3f)
        verticalLineToRelative(2f)
        lineTo(6f, 5f)
        lineTo(6f, 3f)
        lineTo(4f, 3f)
        verticalLineToRelative(18f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(8f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        lineTo(20f, 3f)
        horizontalLineToRelative(-2f)
        close()
        moveTo(8f, 17f)
        lineTo(6f, 17f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
        moveTo(8f, 13f)
        lineTo(6f, 13f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
        moveTo(8f, 9f)
        lineTo(6f, 9f)
        lineTo(6f, 7f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
        moveTo(18f, 17f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
        moveTo(18f, 13f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
        moveTo(18f, 9f)
        horizontalLineToRelative(-2f)
        lineTo(16f, 7f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        close()
    }.build()

    val Word: ImageVector = ImageVector.Builder(
        name = "Word",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(9f, 4f)
        verticalLineToRelative(3f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(12f)
        horizontalLineToRelative(3f)
        lineTo(17f, 7f)
        horizontalLineToRelative(5f)
        lineTo(22f, 4f)
        lineTo(9f, 4f)
        close()
        moveTo(3f, 12f)
        horizontalLineToRelative(3f)
        verticalLineToRelative(7f)
        horizontalLineToRelative(3f)
        verticalLineToRelative(-7f)
        horizontalLineToRelative(3f)
        lineTo(12f, 9f)
        lineTo(3f, 9f)
        verticalLineToRelative(3f)
        close()
    }.build()

    val Night: ImageVector = ImageVector.Builder(
        name = "Night",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(11.1f, 12.08f)
        curveTo(8.77f, 7.57f, 10.6f, 3.6f, 11.63f, 2.01f)
        curveTo(6.27f, 2.2f, 1.98f, 6.59f, 1.98f, 12f)
        curveToRelative(0f, 0.14f, 0.02f, 0.28f, 0.02f, 0.42f)
        curveTo(2.62f, 12.15f, 3.29f, 12f, 4f, 12f)
        curveToRelative(1.66f, 0f, 3.18f, 0.83f, 4.1f, 2.15f)
        curveTo(9.77f, 14.63f, 11f, 16.17f, 11f, 18f)
        curveToRelative(0f, 1.52f, -0.87f, 2.83f, -2.12f, 3.51f)
        curveToRelative(0.98f, 0.32f, 2.03f, 0.5f, 3.11f, 0.5f)
        curveToRelative(3.5f, 0f, 6.58f, -1.8f, 8.37f, -4.52f)
        curveTo(18f, 17.72f, 13.38f, 16.52f, 11.1f, 12.08f)
        close()
    }.path(fill = SolidColor(Color.White)) {
        moveTo(7f, 16f)
        lineToRelative(-0.18f, 0f)
        curveTo(6.4f, 14.84f, 5.3f, 14f, 4f, 14f)
        curveToRelative(-1.66f, 0f, -3f, 1.34f, -3f, 1.7f)
        reflectiveCurveToRelative(1.34f, 3f, 3f, 3f)
        curveToRelative(0.62f, 0f, 2.49f, 0f, 3f, 0f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
    }.build()

    val Company: ImageVector = ImageVector.Builder(
        name = "Company",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(17f, 11f)
        verticalLineTo(3f)
        horizontalLineTo(7f)
        verticalLineToRelative(4f)
        horizontalLineTo(3f)
        verticalLineToRelative(14f)
        horizontalLineToRelative(8f)
        verticalLineToRelative(-4f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(4f)
        horizontalLineToRelative(8f)
        verticalLineTo(11f)
        horizontalLineTo(17f)
        close()
        moveTo(7f, 19f)
        horizontalLineTo(5f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(19f)
        close()
        moveTo(7f, 15f)
        horizontalLineTo(5f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(15f)
        close()
        moveTo(7f, 11f)
        horizontalLineTo(5f)
        verticalLineTo(9f)
        horizontalLineToRelative(2f)
        verticalLineTo(11f)
        close()
        moveTo(11f, 15f)
        horizontalLineTo(9f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(15f)
        close()
        moveTo(11f, 11f)
        horizontalLineTo(9f)
        verticalLineTo(9f)
        horizontalLineToRelative(2f)
        verticalLineTo(11f)
        close()
        moveTo(11f, 7f)
        horizontalLineTo(9f)
        verticalLineTo(5f)
        horizontalLineToRelative(2f)
        verticalLineTo(7f)
        close()
        moveTo(15f, 15f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(15f)
        close()
        moveTo(15f, 11f)
        horizontalLineToRelative(-2f)
        verticalLineTo(9f)
        horizontalLineToRelative(2f)
        verticalLineTo(11f)
        close()
        moveTo(15f, 7f)
        horizontalLineToRelative(-2f)
        verticalLineTo(5f)
        horizontalLineToRelative(2f)
        verticalLineTo(7f)
        close()
        moveTo(19f, 19f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(19f)
        close()
        moveTo(19f, 15f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(2f)
        verticalLineTo(15f)
        close()
    }.build()

    val Gamepad: ImageVector = ImageVector.Builder(
        name = "Gamepad",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(15f, 7.5f)
        verticalLineTo(2f)
        horizontalLineTo(9f)
        verticalLineToRelative(5.5f)
        lineToRelative(3f, 3f)
        lineToRelative(3f, -3f)
        close()
        moveTo(7.5f, 9f)
        horizontalLineTo(2f)
        verticalLineToRelative(6f)
        horizontalLineToRelative(5.5f)
        lineToRelative(3f, -3f)
        lineToRelative(-3f, -3f)
        close()
        moveTo(9f, 16.5f)
        verticalLineTo(22f)
        horizontalLineToRelative(6f)
        verticalLineToRelative(-5.5f)
        lineToRelative(-3f, -3f)
        lineToRelative(-3f, 3f)
        close()
        moveTo(16.5f, 9f)
        lineToRelative(-3f, 3f)
        lineToRelative(3f, 3f)
        horizontalLineTo(22f)
        verticalLineTo(9f)
        horizontalLineToRelative(-5.5f)
        close()
    }.build()

    val LiveTv: ImageVector = ImageVector.Builder(
        name = "LiveTv",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(21f, 6f)
        horizontalLineToRelative(-7.59f)
        lineToRelative(3.29f, -3.29f)
        lineTo(16f, 2f)
        lineToRelative(-4f, 4f)
        lineToRelative(-4f, -4f)
        lineToRelative(-0.71f, 0.71f)
        lineTo(10.59f, 6f)
        lineTo(3f, 6f)
        curveToRelative(-1.1f, 0f, -2f, 0.89f, -2f, 2f)
        verticalLineToRelative(12f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(18f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(23f, 8f)
        curveToRelative(0f, -1.11f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(21f, 20f)
        lineTo(3f, 20f)
        lineTo(3f, 8f)
        horizontalLineToRelative(18f)
        verticalLineToRelative(12f)
        close()
        moveTo(9f, 10f)
        verticalLineToRelative(8f)
        lineToRelative(7f, -4f)
        close()
    }.build()

    val Calculate: ImageVector = ImageVector.Builder(
        name = "Calculate",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 3f)
        horizontalLineTo(5f)
        curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
        verticalLineToRelative(14f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(14f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        verticalLineTo(5f)
        curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
        close()
        moveTo(13.03f, 7.06f)
        lineToRelative(1.06f, -1.06f)
        lineToRelative(1.41f, 1.41f)
        lineTo(16.91f, 6f)
        lineToRelative(1.06f, 1.06f)
        lineToRelative(-1.41f, 1.41f)
        lineToRelative(1.41f, 1.41f)
        lineToRelative(-1.06f, 1.06f)
        lineTo(15.5f, 9.54f)
        lineToRelative(-1.41f, 1.41f)
        lineToRelative(-1.06f, -1.06f)
        lineToRelative(1.41f, -1.41f)
        lineToRelative(-1.41f, -1.41f)
        close()
        moveTo(6.25f, 7.72f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(1.5f)
        horizontalLineToRelative(-5f)
        verticalLineTo(7.72f)
        close()
        moveTo(11.5f, 16f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        horizontalLineTo(8f)
        verticalLineToRelative(-2f)
        horizontalLineTo(6f)
        verticalLineToRelative(-1.5f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(1.5f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        verticalLineTo(16f)
        close()
        moveTo(18f, 17.25f)
        horizontalLineToRelative(-5f)
        verticalLineToRelative(-1.5f)
        horizontalLineToRelative(5f)
        verticalLineTo(17.25f)
        close()
        moveTo(18f, 14.75f)
        horizontalLineToRelative(-5f)
        verticalLineToRelative(-1.5f)
        horizontalLineToRelative(5f)
        verticalLineTo(14.75f)
        close()
    }.build()

    val HomeWork: ImageVector = ImageVector.Builder(
        name = "HomeWork",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(8.17f, 5.7f)
        lineTo(1f, 10.48f)
        verticalLineTo(21f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(-8f)
        horizontalLineToRelative(4f)
        verticalLineToRelative(8f)
        horizontalLineToRelative(5f)
        verticalLineTo(10.25f)
        close()
    }.path(fill = SolidColor(Color.White)) {
        moveTo(10f, 3f)
        verticalLineToRelative(1.51f)
        lineToRelative(2f, 1.33f)
        lineTo(13.73f, 7f)
        lineTo(15f, 7f)
        verticalLineToRelative(0.85f)
        lineToRelative(2f, 1.34f)
        lineTo(17f, 11f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(4f)
        horizontalLineToRelative(6f)
        lineTo(23f, 3f)
        lineTo(10f, 3f)
        close()
        moveTo(19f, 9f)
        horizontalLineToRelative(-2f)
        verticalLineTo(7f)
        horizontalLineToRelative(2f)
        verticalLineTo(9f)
        close()
    }.build()

    val PdfReader: ImageVector = ImageVector.Builder(
        name = "PdfReader",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(20f, 2f)
        lineTo(8f, 2f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        verticalLineToRelative(12f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(12f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(22f, 4f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(11.5f, 9.5f)
        curveToRelative(0f, 0.83f, -0.67f, 1.5f, -1.5f, 1.5f)
        lineTo(9f, 11f)
        verticalLineToRelative(2f)
        lineTo(7.5f, 13f)
        lineTo(7.5f, 7f)
        lineTo(10f, 7f)
        curveToRelative(0.83f, 0f, 1.5f, 0.67f, 1.5f, 1.5f)
        verticalLineToRelative(1f)
        close()
        moveTo(16.5f, 11.5f)
        curveToRelative(0f, 0.83f, -0.67f, 1.5f, -1.5f, 1.5f)
        horizontalLineToRelative(-2.5f)
        lineTo(12.5f, 7f)
        lineTo(15f, 7f)
        curveToRelative(0.83f, 0f, 1.5f, 0.67f, 1.5f, 1.5f)
        verticalLineToRelative(3f)
        close()
        moveTo(20.5f, 8.5f)
        lineTo(19f, 8.5f)
        verticalLineToRelative(1f)
        horizontalLineToRelative(1.5f)
        lineTo(20.5f, 11f)
        lineTo(19f, 11f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-1.5f)
        lineTo(17.5f, 7f)
        horizontalLineToRelative(3f)
        verticalLineTo(8.5f)
        close()
        moveTo(9f, 9.5f)
        horizontalLineToRelative(1f)
        verticalLineToRelative(-1f)
        lineTo(9f, 8.5f)
        verticalLineToRelative(1f)
        close()
        moveTo(4f, 6f)
        lineTo(2f, 6f)
        verticalLineToRelative(14f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(-2f)
        lineTo(4f, 20f)
        lineTo(4f, 6f)
        close()
        moveTo(14f, 11.5f)
        horizontalLineToRelative(1f)
        verticalLineToRelative(-3f)
        horizontalLineToRelative(-1f)
        verticalLineToRelative(3f)
        close()
    }.build()

    val StopWatch: ImageVector = ImageVector.Builder(
        name = "StopWatch",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 2f)
        curveTo(6.5f, 2f, 2f, 6.5f, 2f, 12f)
        reflectiveCurveToRelative(4.5f, 10f, 10f, 10f)
        reflectiveCurveToRelative(10f, -4.5f, 10f, -10f)
        reflectiveCurveTo(17.5f, 2f, 12f, 2f)
        close()
        moveTo(16.2f, 16.2f)
        lineTo(11f, 13f)
        verticalLineTo(7f)
        horizontalLineToRelative(1.5f)
        verticalLineToRelative(5.2f)
        lineToRelative(4.5f, 2.7f)
        lineToRelative(-0.8f, 1.3f)
        close()
    }.build()

    val Monetization: ImageVector = ImageVector.Builder(
        name = "Monetization",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
        reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
        reflectiveCurveTo(17.52f, 2f, 12f, 2f)
        close()
        moveTo(13.41f, 18.09f)
        lineTo(13.41f, 20f)
        horizontalLineToRelative(-2.67f)
        verticalLineToRelative(-1.93f)
        curveToRelative(-1.71f, -0.36f, -3.16f, -1.46f, -3.27f, -3.4f)
        horizontalLineToRelative(1.96f)
        curveToRelative(0.1f, 1.05f, 0.82f, 1.87f, 2.65f, 1.87f)
        curveToRelative(1.96f, 0f, 2.4f, -0.98f, 2.4f, -1.59f)
        curveToRelative(0f, -0.83f, -0.44f, -1.61f, -2.67f, -2.14f)
        curveToRelative(-2.48f, -0.6f, -4.18f, -1.62f, -4.18f, -3.67f)
        curveToRelative(0f, -1.72f, 1.39f, -2.84f, 3.11f, -3.21f)
        lineTo(10.74f, 4f)
        horizontalLineToRelative(2.67f)
        verticalLineToRelative(1.95f)
        curveToRelative(1.86f, 0.45f, 2.79f, 1.86f, 2.85f, 3.39f)
        lineToRelative(-1.96f, 0f)
        curveToRelative(-0.05f, -1.11f, -0.64f, -1.87f, -2.22f, -1.87f)
        curveToRelative(-1.5f, 0f, -2.4f, 0.68f, -2.4f, 1.64f)
        curveToRelative(0f, 0.84f, 0.65f, 1.39f, 2.67f, 1.91f)
        reflectiveCurveToRelative(4.18f, 1.39f, 4.18f, 3.91f)
        curveToRelative(-0.01f, 1.83f, -1.38f, 2.83f, -3.12f, 3.16f)
        close()
    }.build()

    val MultiDelete: ImageVector = ImageVector.Builder(
        name = "MultiDelete",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(15f, 16f)
        horizontalLineToRelative(4f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-4f)
        close()
        moveTo(15f, 8f)
        horizontalLineToRelative(7f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-7f)
        close()
        moveTo(15f, 12f)
        horizontalLineToRelative(6f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-6f)
        close()
        moveTo(3f, 18f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(6f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(13f, 8f)
        lineTo(3f, 8f)
        verticalLineToRelative(10f)
        close()
        moveTo(14f, 5f)
        horizontalLineToRelative(-3f)
        lineToRelative(-1f, -1f)
        lineTo(6f, 4f)
        lineTo(5f, 5f)
        lineTo(2f, 5f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(12f)
        close()
    }.build()

    val VideoPlayer: ImageVector = ImageVector.Builder(
        name = "VideoPlayer",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(13.05f, 9.79f)
        lineTo(10f, 7.5f)
        verticalLineToRelative(9f)
        lineToRelative(3.05f, -2.29f)
        lineTo(16f, 12f)
        close()
        moveTo(11f, 4.07f)
        lineTo(11f, 2.05f)
        curveToRelative(-2.01f, 0.2f, -3.84f, 1f, -5.32f, 2.21f)
        lineToRelative(1.42f, 1.42f)
        curveToRelative(1.11f, -0.86f, 2.44f, -1.44f, 3.9f, -1.62f)
        close()
        moveTo(5.69f, 7.1f)
        lineTo(4.26f, 5.68f)
        curveTo(3.05f, 7.16f, 2.25f, 8.99f, 2.05f, 11f)
        horizontalLineToRelative(2.02f)
        curveToRelative(0.18f, -1.46f, 0.76f, -2.79f, 1.62f, -3.9f)
        close()
        moveTo(4.07f, 13f)
        lineTo(2.05f, 13f)
        curveToRelative(0.2f, 2.01f, 1f, 3.84f, 2.21f, 5.32f)
        lineToRelative(1.43f, -1.43f)
        curveToRelative(-0.86f, -1.1f, -1.44f, -2.43f, -1.62f, -3.89f)
        close()
        moveTo(5.68f, 19.74f)
        curveTo(7.16f, 20.95f, 9f, 21.75f, 11f, 21.95f)
        verticalLineToRelative(-2.02f)
        curveToRelative(-1.46f, -0.18f, -2.79f, -0.76f, -3.9f, -1.62f)
        lineToRelative(-1.42f, 1.43f)
        close()
        moveTo(22f, 12f)
        curveToRelative(0f, 5.16f, -3.92f, 9.42f, -8.95f, 9.95f)
        verticalLineToRelative(-2.02f)
        curveTo(16.97f, 19.41f, 20f, 16.05f, 20f, 12f)
        reflectiveCurveToRelative(-3.03f, -7.41f, -6.95f, -7.93f)
        lineTo(13.05f, 2.05f)
        curveTo(18.08f, 2.58f, 22f, 6.84f, 22f, 12f)
        close()
    }.build()

    val ConnectionError: ImageVector = ImageVector.Builder(
        name = "ConnectionError",
        defaultWidth = 100.dp,
        defaultHeight = 100.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFFA9A9AC))) {
        moveTo(19.35f, 10.04f)
        curveTo(18.67f, 6.59f, 15.64f, 4f, 12f, 4f)
        curveToRelative(-1.48f, 0f, -2.85f, 0.43f, -4.01f, 1.17f)
        lineToRelative(1.46f, 1.46f)
        curveTo(10.21f, 6.23f, 11.08f, 6f, 12f, 6f)
        curveToRelative(3.04f, 0f, 5.5f, 2.46f, 5.5f, 5.5f)
        verticalLineToRelative(0.5f)
        horizontalLineTo(19f)
        curveToRelative(1.66f, 0f, 3f, 1.34f, 3f, 3f)
        curveToRelative(0f, 1.13f, -0.64f, 2.11f, -1.56f, 2.62f)
        lineToRelative(1.45f, 1.45f)
        curveTo(23.16f, 18.16f, 24f, 16.68f, 24f, 15f)
        curveToRelative(0f, -2.64f, -2.05f, -4.78f, -4.65f, -4.96f)
        close()
        moveTo(3f, 5.27f)
        lineToRelative(2.75f, 2.74f)
        curveTo(2.56f, 8.15f, 0f, 10.77f, 0f, 14f)
        curveToRelative(0f, 3.31f, 2.69f, 6f, 6f, 6f)
        horizontalLineToRelative(11.73f)
        lineToRelative(2f, 2f)
        lineTo(21f, 20.73f)
        lineTo(4.27f, 4f)
        lineTo(3f, 5.27f)
        close()
        moveTo(7.73f, 10f)
        horizontalLineToRelative(8f)
        verticalLineToRelative(8f)
        horizontalLineTo(6f)
        curveToRelative(-2.21f, 0f, -4f, -1.79f, -4f, -4f)
        reflectiveCurveToRelative(1.79f, -4f, 4f, -4f)
        horizontalLineToRelative(1.73f)
        close()
    }.build()

    val CircleGreen: ImageVector = ImageVector.Builder(
        name = "CircleGreen",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF4CAF50))) {
        moveTo(12f, 12f)
        moveToRelative(-10f, 0f)
        arcTo(10f, 10f, 0f, true, true, 22f, 12f)
        arcTo(10f, 10f, 0f, true, true, 2f, 12f)
    }.build()

    val CircleRed: ImageVector = ImageVector.Builder(
        name = "CircleRed",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFFF44336))) {
        moveTo(12f, 12f)
        moveToRelative(-10f, 0f)
        arcTo(10f, 10f, 0f, true, true, 22f, 12f)
        arcTo(10f, 10f, 0f, true, true, 2f, 12f)
    }.build()

    val Play: ImageVector = ImageVector.Builder(
        name = "Play",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(8f, 5f)
        verticalLineToRelative(14f)
        lineToRelative(11f, -7f)
        close()
    }.build()

    val Pause: ImageVector = ImageVector.Builder(
        name = "Pause",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(6f, 19f)
        horizontalLineToRelative(4f)
        verticalLineTo(5f)
        horizontalLineTo(6f)
        verticalLineToRelative(14f)
        close()
        moveTo(14f, 5f)
        verticalLineToRelative(14f)
        horizontalLineToRelative(4f)
        verticalLineTo(5f)
        horizontalLineToRelative(-4f)
        close()
    }.build()

    val Stop: ImageVector = ImageVector.Builder(
        name = "Stop",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(6f, 6f)
        horizontalLineToRelative(12f)
        verticalLineToRelative(12f)
        horizontalLineTo(6f)
        close()
    }.build()

    val Back: ImageVector = ImageVector.Builder(
        name = "Back",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(20f, 11f)
        horizontalLineTo(7.83f)
        lineToRelative(5.59f, -5.59f)
        lineTo(12f, 4f)
        lineToRelative(-8f, 8f)
        lineToRelative(8f, 8f)
        lineToRelative(1.41f, -1.41f)
        lineTo(7.83f, 13f)
        horizontalLineTo(20f)
        verticalLineToRelative(-2f)
        close()
    }.build()
}