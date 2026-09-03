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

    val FolderOpen: ImageVector = ImageVector.Builder(
        name = "FolderOpen",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(19f, 20f)
        horizontalLineTo(4f)
        curveTo(2.89f, 20f, 2f, 19.1f, 2f, 18f)
        verticalLineTo(6f)
        curveTo(2f, 4.89f, 2.89f, 4f, 4f, 4f)
        horizontalLineTo(10f)
        lineTo(12f, 6f)
        horizontalLineTo(19f)
        arcTo(2f, 2f, 0f, false, true, 21f, 8f)
        horizontalLineTo(21f)
        lineTo(4f, 8f)
        verticalLineTo(18f)
        lineTo(6.14f, 10f)
        horizontalLineTo(23.21f)
        lineTo(20.93f, 18.5f)
        curveTo(20.7f, 19.37f, 19.92f, 20f, 19f, 20f)
        close()
    }.build()

    val Fullscreen: ImageVector = ImageVector.Builder(
        name = "Fullscreen",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(7f, 14f)
        lineTo(5f, 14f)
        verticalLineToRelative(5f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(-2f)
        lineTo(7f, 17f)
        verticalLineToRelative(-3f)
        close()
        moveTo(5f, 10f)
        horizontalLineToRelative(2f)
        lineTo(7f, 7f)
        horizontalLineToRelative(3f)
        lineTo(10f, 5f)
        lineTo(5f, 5f)
        verticalLineToRelative(5f)
        close()
        moveTo(17f, 17f)
        horizontalLineToRelative(-3f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(-5f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(3f)
        close()
        moveTo(14f, 5f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(3f)
        verticalLineToRelative(3f)
        horizontalLineToRelative(2f)
        lineTo(19f, 5f)
        horizontalLineToRelative(-5f)
        close()
    }.build()

    val Infinity: ImageVector = ImageVector.Builder(
        name = "Infinity",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(18.6f, 6.62f)
        curveToRelative(-1.44f, 0f, -2.8f, 0.56f, -3.77f, 1.53f)
        lineTo(12f, 10.66f)
        lineTo(10.48f, 12f)
        horizontalLineToRelative(0.01f)
        lineTo(7.8f, 14.39f)
        curveToRelative(-0.64f, 0.64f, -1.49f, 0.99f, -2.4f, 0.99f)
        curveToRelative(-1.87f, 0f, -3.39f, -1.51f, -3.39f, -3.38f)
        reflectiveCurveTo(3.53f, 8.62f, 5.4f, 8.62f)
        curveToRelative(0.91f, 0f, 1.76f, 0.35f, 2.44f, 1.03f)
        lineToRelative(1.13f, 1f)
        lineToRelative(1.51f, -1.34f)
        lineTo(9.22f, 8.2f)
        curveTo(8.2f, 7.18f, 6.84f, 6.62f, 5.4f, 6.62f)
        curveTo(2.42f, 6.62f, 0f, 9.04f, 0f, 12f)
        reflectiveCurveToRelative(2.42f, 5.38f, 5.4f, 5.38f)
        curveToRelative(1.44f, 0f, 2.8f, -0.56f, 3.77f, -1.53f)
        lineToRelative(2.83f, -2.5f)
        lineToRelative(0.01f, 0.01f)
        lineTo(13.52f, 12f)
        horizontalLineToRelative(-0.01f)
        lineToRelative(2.69f, -2.39f)
        curveToRelative(0.64f, -0.64f, 1.49f, -0.99f, 2.4f, -0.99f)
        curveToRelative(1.87f, 0f, 3.39f, 1.51f, 3.39f, 3.38f)
        reflectiveCurveToRelative(-1.52f, 3.38f, -3.39f, 3.38f)
        curveToRelative(-0.9f, 0f, -1.76f, -0.35f, -2.44f, -1.03f)
        lineToRelative(-1.14f, -1.01f)
        lineToRelative(-1.51f, 1.34f)
        lineToRelative(1.27f, 1.12f)
        curveToRelative(1.02f, 1.01f, 2.37f, 1.57f, 3.82f, 1.57f)
        curveToRelative(2.98f, 0f, 5.4f, -2.41f, 5.4f, -5.38f)
        reflectiveCurveToRelative(-2.42f, -5.37f, -5.4f, -5.37f)
        close()
    }.build()

    val Article: ImageVector = ImageVector.Builder(
        name = "Article",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 3f)
        lineTo(5f, 3f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        verticalLineToRelative(14f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(14f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(21f, 5f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(14f, 17f)
        lineTo(7f, 17f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(7f)
        verticalLineToRelative(2f)
        close()
        moveTo(17f, 13f)
        lineTo(7f, 13f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(10f)
        verticalLineToRelative(2f)
        close()
        moveTo(17f, 9f)
        lineTo(7f, 9f)
        lineTo(7f, 7f)
        horizontalLineToRelative(10f)
        verticalLineToRelative(2f)
        close()
    }.build()

    val Add: ImageVector = ImageVector.Builder(
        name = "Add",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 13f)
        horizontalLineToRelative(-6f)
        verticalLineToRelative(6f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(-6f)
        horizontalLineTo(5f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(6f)
        verticalLineTo(5f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(6f)
        horizontalLineToRelative(6f)
        verticalLineToRelative(2f)
        close()
    }.build()

    val Check: ImageVector = ImageVector.Builder(
        name = "Check",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(9f, 16.17f)
        lineTo(4.83f, 12f)
        lineToRelative(-1.42f, 1.41f)
        lineTo(9f, 19f)
        lineTo(21f, 7f)
        lineToRelative(-1.41f, -1.41f)
        close()
    }.build()

    val DateRange: ImageVector = ImageVector.Builder(
        name = "DateRange",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(9f, 11f)
        lineTo(7f, 11f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        close()
        moveTo(13f, 11f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        close()
        moveTo(17f, 11f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        close()
        moveTo(19f, 4f)
        horizontalLineToRelative(-1f)
        lineTo(18f, 2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        lineTo(8f, 4f)
        lineTo(8f, 2f)
        lineTo(6f, 2f)
        verticalLineToRelative(2f)
        lineTo(5f, 4f)
        curveToRelative(-1.11f, 0f, -1.99f, 0.9f, -1.99f, 2f)
        lineTo(3f, 20f)
        curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
        horizontalLineToRelative(14f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(21f, 6f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(19f, 20f)
        lineTo(5f, 20f)
        lineTo(5f, 9f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(11f)
        close()
    }.build()

    val Delete: ImageVector = ImageVector.Builder(
        name = "Delete",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(6f, 19f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(8f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        verticalLineTo(7f)
        horizontalLineTo(6f)
        verticalLineToRelative(12f)
        close()
        moveTo(19f, 4f)
        horizontalLineToRelative(-3.5f)
        lineToRelative(-1f, -1f)
        horizontalLineToRelative(-5f)
        lineToRelative(-1f, 1f)
        horizontalLineTo(5f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(14f)
        verticalLineTo(4f)
        close()
    }.build()

    val Favorite: ImageVector = ImageVector.Builder(
        name = "Favorite",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 21.35f)
        lineToRelative(-1.45f, -1.32f)
        curveTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
        curveTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
        curveToRelative(1.74f, 0f, 3.41f, 0.81f, 4.5f, 2.09f)
        curveTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
        curveTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
        curveToRelative(0f, 3.78f, -3.4f, 6.86f, -8.55f, 11.54f)
        lineTo(12f, 21.35f)
        close()
    }.build()

    val FavoriteBorder: ImageVector = ImageVector.Builder(
        name = "FavoriteBorder",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(16.5f, 3f)
        curveToRelative(-1.74f, 0f, -3.41f, 0.81f, -4.5f, 2.09f)
        curveTo(10.91f, 3.81f, 9.24f, 3f, 7.5f, 3f)
        curveTo(4.42f, 3f, 2f, 5.42f, 2f, 8.5f)
        curveToRelative(0f, 3.78f, 3.4f, 6.86f, 8.55f, 11.54f)
        lineTo(12f, 21.35f)
        lineToRelative(1.45f, -1.32f)
        curveTo(18.6f, 15.36f, 22f, 12.28f, 22f, 8.5f)
        curveTo(22f, 5.42f, 19.58f, 3f, 16.5f, 3f)
        close()
        moveTo(12.1f, 18.55f)
        lineToRelative(-0.1f, 0.1f)
        lineToRelative(-0.1f, -0.1f)
        curveTo(7.14f, 14.24f, 4f, 11.39f, 4f, 8.5f)
        curveTo(4f, 6.5f, 5.5f, 5f, 7.5f, 5f)
        curveToRelative(1.54f, 0f, 3.04f, 0.99f, 3.57f, 2.36f)
        horizontalLineToRelative(1.87f)
        curveTo(13.46f, 5.99f, 14.96f, 5f, 16.5f, 5f)
        curveToRelative(2f, 0f, 3.5f, 1.5f, 3.5f, 3.5f)
        curveTo(20f, 2.89f, 16.86f, 5.74f, 12.1f, 10.05f)
        close()
    }.build()

    val PriorityHigh: ImageVector = ImageVector.Builder(
        name = "PriorityHigh",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFFF13A3A))) {
        moveTo(12f, 19f)
        moveToRelative(-2f, 0f)
        arcTo(2f, 2f, 0f, true, true, 14f, 19f)
        arcTo(2f, 2f, 0f, true, true, 10f, 19f)
    }.path(fill = SolidColor(Color(0xFFF13A3A))) {
        moveTo(10f, 3f)
        horizontalLineToRelative(4f)
        verticalLineToRelative(12f)
        horizontalLineToRelative(-4f)
        close()
    }.build()

    val Search: ImageVector = ImageVector.Builder(
        name = "Search",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(15.5f, 14f)
        horizontalLineToRelative(-0.79f)
        lineToRelative(-0.28f, -0.27f)
        curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
        curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
        reflectiveCurveTo(3f, 5.91f, 3f, 9.5f)
        reflectiveCurveTo(5.91f, 16f, 9.5f, 16f)
        curveToRelative(1.61f, 0f, 3.09f, -0.59f, 4.23f, -1.57f)
        lineToRelative(0.27f, 0.28f)
        verticalLineToRelative(0.79f)
        lineToRelative(5f, 4.99f)
        lineTo(20.49f, 19f)
        lineToRelative(-4.99f, -5f)
        close()
        moveTo(9.5f, 14f)
        curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
        reflectiveCurveTo(7.01f, 5f, 9.5f, 5f)
        reflectiveCurveTo(14f, 7.01f, 14f, 9.5f)
        reflectiveCurveTo(11.99f, 14f, 9.5f, 14f)
        close()
    }.build()

    val Sort: ImageVector = ImageVector.Builder(
        name = "Sort",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(3f, 18f)
        horizontalLineToRelative(6f)
        verticalLineToRelative(-2f)
        lineTo(3f, 16f)
        verticalLineToRelative(2f)
        close()
        moveTo(3f, 6f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(18f)
        lineTo(21f, 6f)
        lineTo(3f, 6f)
        close()
        moveTo(3f, 13f)
        horizontalLineToRelative(12f)
        verticalLineToRelative(-2f)
        lineTo(3f, 11f)
        verticalLineToRelative(2f)
        close()
    }.build()

    val BrokenImage: ImageVector = ImageVector.Builder(
        name = "BrokenImage",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFFA9A9AC))) {
        moveTo(21f, 5f)
        verticalLineToRelative(6.59f)
        lineToRelative(-3f, -3.01f)
        lineToRelative(-4f, 4.01f)
        lineToRelative(-4f, -4f)
        lineToRelative(-4f, 4f)
        lineToRelative(-3f, -3.01f)
        lineTo(3f, 5f)
        curveToRelative(0f, -1.1f, 0.9f, -2f, 2f, -2f)
        horizontalLineToRelative(14f)
        curveToRelative(1.1f, 0f, 2f, 0.9f, 2f, 2f)
        close()
        moveTo(18f, 11.42f)
        lineToRelative(3f, 3.01f)
        lineTo(21f, 19f)
        curveToRelative(0f, 1.1f, -0.9f, 2f, -2f, 2f)
        lineTo(5f, 21f)
        curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
        verticalLineToRelative(-6.58f)
        lineToRelative(3f, 2.99f)
        lineToRelative(4f, -4f)
        lineToRelative(4f, 4f)
        lineToRelative(4f, -3.99f)
        close()
    }.build()

    val Category: ImageVector = ImageVector.Builder(
        name = "Category",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(12f, 2f)
        lineToRelative(-5.5f, 9f)
        horizontalLineToRelative(11f)
        close()
    }.path(fill = SolidColor(Color.Black)) {
        moveTo(17.5f, 17.5f)
        moveToRelative(-4.5f, 0f)
        arcTo(4.5f, 4.5f, 0f, true, true, 22f, 17.5f)
        arcTo(4.5f, 4.5f, 0f, true, true, 13f, 17.5f)
    }.path(fill = SolidColor(Color.Black)) {
        moveTo(3f, 13.5f)
        horizontalLineToRelative(8f)
        verticalLineToRelative(8f)
        horizontalLineTo(3f)
        close()
    }.build()

    val CheckCircle: ImageVector = ImageVector.Builder(
        name = "CheckCircle",
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
        moveTo(10f, 17f)
        lineToRelative(-5f, -5f)
        lineToRelative(1.41f, -1.41f)
        lineTo(10f, 14.17f)
        lineToRelative(7.59f, -7.59f)
        lineTo(19f, 8f)
        lineToRelative(-9f, 9f)
        close()
    }.build()

    val ClearAll: ImageVector = ImageVector.Builder(
        name = "ClearAll",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(5f, 13f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(-2f)
        horizontalLineTo(5f)
        verticalLineToRelative(2f)
        close()
        moveTo(3f, 17f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(-2f)
        horizontalLineTo(3f)
        verticalLineToRelative(2f)
        close()
        moveTo(7f, 7f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(14f)
        verticalLineTo(7f)
        horizontalLineTo(7f)
        close()
    }.build()

    val Close: ImageVector = ImageVector.Builder(
        name = "Close",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 6.41f)
        lineTo(17.59f, 5f)
        lineTo(12f, 10.59f)
        lineTo(6.41f, 5f)
        lineTo(5f, 6.41f)
        lineTo(10.59f, 12f)
        lineTo(5f, 17.59f)
        lineTo(6.41f, 19f)
        lineTo(12f, 13.41f)
        lineTo(17.59f, 19f)
        lineTo(19f, 17.59f)
        lineTo(13.41f, 12f)
        close()
    }.build()

    val EventNote: ImageVector = ImageVector.Builder(
        name = "EventNote",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(17f, 10f)
        lineTo(7f, 10f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(10f)
        verticalLineToRelative(-2f)
        close()
        moveTo(19f, 3f)
        horizontalLineToRelative(-1f)
        lineTo(18f, 1f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        lineTo(8f, 3f)
        lineTo(8f, 1f)
        lineTo(6f, 1f)
        verticalLineToRelative(2f)
        lineTo(5f, 3f)
        curveToRelative(-1.11f, 0f, -1.99f, 0.9f, -1.99f, 2f)
        lineTo(3f, 19f)
        curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
        horizontalLineToRelative(14f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        lineTo(21f, 5f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(19f, 19f)
        lineTo(5f, 19f)
        lineTo(5f, 8f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(11f)
        close()
        moveTo(14f, 14f)
        lineTo(7f, 14f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(7f)
        verticalLineToRelative(-2f)
        close()
    }.build()

    val Folder: ImageVector = ImageVector.Builder(
        name = "Folder",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF009688))) {
        moveTo(10f, 4f)
        horizontalLineTo(4f)
        curveTo(2.89f, 4f, 2.01f, 4.9f, 2.01f, 6f)
        lineTo(2f, 18f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        horizontalLineToRelative(16f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        verticalLineTo(8f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        horizontalLineToRelative(-8f)
        lineToRelative(-2f, -2f)
        close()
    }.build()

    val Home: ImageVector = ImageVector.Builder(
        name = "Home",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(10f, 20f)
        verticalLineToRelative(-6f)
        horizontalLineToRelative(4f)
        verticalLineToRelative(6f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(-8f)
        horizontalLineToRelative(3f)
        lineTo(12f, 3f)
        lineTo(2f, 12f)
        horizontalLineToRelative(3f)
        verticalLineToRelative(8f)
        close()
    }.build()

    val Location: ImageVector = ImageVector.Builder(
        name = "Location",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 2f)
        curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
        curveToRelative(0f, 5.25f, 7f, 13f, 7f, 13f)
        reflectiveCurveToRelative(7f, -7.75f, 7f, -13f)
        curveToRelative(0f, -3.87f, -3.13f, -7f, -7f, -7f)
        close()
        moveTo(12f, 11.5f)
        curveToRelative(-1.38f, 0f, -2.5f, -1.12f, -2.5f, -2.5f)
        reflectiveCurveToRelative(1.12f, -2.5f, 2.5f, -2.5f)
        reflectiveCurveToRelative(2.5f, 1.12f, 2.5f, 2.5f)
        reflectiveCurveToRelative(-1.12f, 2.5f, -2.5f, 2.5f)
        close()
    }.build()

    val Minus: ImageVector = ImageVector.Builder(
        name = "Minus",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(19f, 13f)
        horizontalLineTo(5f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(14f)
        verticalLineToRelative(2f)
        close()
    }.build()

    val More: ImageVector = ImageVector.Builder(
        name = "More",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(12f, 8f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
        reflectiveCurveToRelative(-2f, 0.9f, -2f, 2f)
        reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
        close()
        moveTo(12f, 10f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
        reflectiveCurveToRelative(2f, -0.9f, 2f, -2f)
        reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
        close()
        moveTo(12f, 16f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        reflectiveCurveToRelative(0.9f, 2f, 2f, 2f)
        reflectiveCurveToRelative(2f, -0.9f, 2f, -2f)
        reflectiveCurveToRelative(-0.9f, -2f, -2f, -2f)
        close()
    }.build()

    val Person: ImageVector = ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 12f)
        curveToRelative(2.21f, 0f, 4f, -1.79f, 4f, -4f)
        reflectiveCurveToRelative(-1.79f, -4f, -4f, -4f)
        reflectiveCurveToRelative(-4f, 1.79f, -4f, 4f)
        reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
        close()
        moveTo(12f, 14f)
        curveToRelative(-2.67f, 0f, -8f, 1.34f, -8f, 4f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(16f)
        verticalLineToRelative(-2f)
        curveToRelative(0f, -2.66f, -5.33f, -4f, -8f, -4f)
        close()
    }.build()

    val SelectAll: ImageVector = ImageVector.Builder(
        name = "SelectAll",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.Black)) {
        moveTo(3f, 5f)
        horizontalLineToRelative(2f)
        lineTo(5f, 3f)
        curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
        close()
        moveTo(3f, 13f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        lineTo(3f, 11f)
        verticalLineToRelative(2f)
        close()
        moveTo(7f, 21f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        lineTo(7f, 19f)
        verticalLineToRelative(2f)
        close()
        moveTo(3f, 9f)
        horizontalLineToRelative(2f)
        lineTo(5f, 7f)
        lineTo(3f, 7f)
        verticalLineToRelative(2f)
        close()
        moveTo(13f, 3f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        lineTo(13f, 3f)
        close()
        moveTo(19f, 3f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(5f, 21f)
        verticalLineToRelative(-2f)
        lineTo(3f, 19f)
        curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
        close()
        moveTo(3f, 17f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        lineTo(3f, 15f)
        verticalLineToRelative(2f)
        close()
        moveTo(9f, 3f)
        lineTo(7f, 3f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(2f)
        lineTo(9f, 3f)
        close()
        moveTo(11f, 21f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(19f, 13f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(19f, 21f)
        curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(19f, 9f)
        horizontalLineToRelative(2f)
        lineTo(21f, 7f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(19f, 17f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(15f, 21f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(15f, 5f)
        horizontalLineToRelative(2f)
        lineTo(17f, 3f)
        horizontalLineToRelative(-2f)
        verticalLineToRelative(2f)
        close()
        moveTo(7f, 17f)
        horizontalLineToRelative(10f)
        lineTo(17f, 7f)
        lineTo(7f, 7f)
        verticalLineToRelative(10f)
        close()
        moveTo(9f, 9f)
        horizontalLineToRelative(6f)
        verticalLineToRelative(6f)
        lineTo(9f, 15f)
        lineTo(9f, 9f)
        close()
    }.build()

    val Star: ImageVector = ImageVector.Builder(
        name = "Star",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(12f, 17.27f)
        lineTo(18.18f, 21f)
        lineToRelative(-1.64f, -7.03f)
        lineTo(22f, 9.24f)
        lineToRelative(-7.19f, -0.61f)
        lineTo(12f, 2f)
        lineTo(9.19f, 8.63f)
        lineTo(2f, 9.24f)
        lineToRelative(5.46f, 4.73f)
        lineTo(5.82f, 21f)
        close()
    }.build()

    val Video: ImageVector = ImageVector.Builder(
        name = "Video",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(21f, 3f)
        lineTo(3f, 3f)
        curveToRelative(-1.11f, 0f, -2f, 0.89f, -2f, 2f)
        verticalLineToRelative(12f)
        curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
        horizontalLineToRelative(5f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(8f)
        verticalLineToRelative(-2f)
        horizontalLineToRelative(5f)
        curveToRelative(1.1f, 0f, 1.99f, -0.9f, 1.99f, -2f)
        lineTo(23f, 5f)
        curveToRelative(0f, -1.11f, -0.9f, -2f, -2f, -2f)
        close()
        moveTo(21f, 17f)
        lineTo(3f, 17f)
        lineTo(3f, 5f)
        horizontalLineToRelative(18f)
        verticalLineToRelative(12f)
        close()
        moveTo(16f, 11f)
        lineToRelative(-7f, 4f)
        lineTo(9f, 7f)
        close()
    }.build()

    val InfoOutline: ImageVector = ImageVector.Builder(
        name = "InfoOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(11f, 9f)
        horizontalLineToRelative(2f)
        verticalLineTo(7f)
        horizontalLineToRelative(-2f)
        close()
        moveTo(12f, 20f)
        curveToRelative(-4.41f, 0f, -8f, -3.59f, -8f, -8f)
        reflectiveCurveToRelative(3.59f, -8f, 8f, -8f)
        reflectiveCurveToRelative(8f, 3.59f, 8f, 8f)
        reflectiveCurveToRelative(-3.59f, 8f, -8f, 8f)
        close()
        moveTo(12f, 2f)
        arcTo(10f, 10f, 0f, false, false, 2f, 12f)
        arcTo(10f, 10f, 0f, false, false, 12f, 22f)
        arcTo(10f, 10f, 0f, false, false, 22f, 12f)
        arcTo(10f, 10f, 0f, false, false, 12f, 2f)
        close()
        moveTo(11f, 17f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(-6f)
        horizontalLineToRelative(-2f)
        verticalLineTo(17f)
        close()
    }.build()

    val Lock: ImageVector = ImageVector.Builder(
        name = "Lock",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(12f, 1f)
        arcTo(5f, 5f, 0f, false, false, 7f, 6f)
        verticalLineTo(8f)
        horizontalLineTo(6f)
        arcTo(2f, 2f, 0f, false, false, 4f, 10f)
        verticalLineTo(20f)
        arcTo(2f, 2f, 0f, false, false, 6f, 22f)
        horizontalLineTo(18f)
        arcTo(2f, 2f, 0f, false, false, 20f, 20f)
        verticalLineTo(10f)
        arcTo(2f, 2f, 0f, false, false, 18f, 8f)
        horizontalLineTo(17f)
        verticalLineTo(6f)
        arcTo(5f, 5f, 0f, false, false, 12f, 1f)
        close()
        moveTo(12f, 2.9f)
        curveTo(13.71f, 2.9f, 15.1f, 4.29f, 15.1f, 6f)
        verticalLineTo(8f)
        horizontalLineTo(8.9f)
        verticalLineTo(6f)
        curveTo(8.9f, 4.29f, 10.29f, 2.9f, 12f, 2.9f)
        close()
        moveTo(11f, 11f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(4f)
        horizontalLineToRelative(-2f)
        verticalLineTo(11f)
        close()
        moveTo(11f, 17f)
        horizontalLineToRelative(2f)
        verticalLineToRelative(2f)
        horizontalLineToRelative(-2f)
        verticalLineTo(17f)
        close()
    }.build()

    val MetaInfo: ImageVector = ImageVector.Builder(
        name = "MetaInfo",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(13f, 9f)
        horizontalLineTo(18.5f)
        lineTo(13f, 3.5f)
        verticalLineTo(9f)
        close()
        moveTo(6f, 2f)
        horizontalLineTo(14f)
        lineTo(20f, 8f)
        verticalLineTo(20f)
        arcTo(2f, 2f, 0f, false, true, 18f, 22f)
        horizontalLineTo(6f)
        curveTo(4.89f, 22f, 4f, 21.1f, 4f, 20f)
        verticalLineTo(4f)
        curveTo(4f, 2.89f, 4.89f, 2f, 6f, 2f)
        close()
        moveTo(15f, 18f)
        verticalLineToRelative(-2f)
        horizontalLineTo(6f)
        verticalLineToRelative(2f)
        horizontalLineTo(15f)
        close()
        moveTo(18f, 14f)
        verticalLineToRelative(-2f)
        horizontalLineTo(6f)
        verticalLineToRelative(2f)
        horizontalLineTo(18f)
        close()
    }.build()

    val Print: ImageVector = ImageVector.Builder(
        name = "Print",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(18f, 3f)
        horizontalLineTo(6f)
        verticalLineTo(7f)
        horizontalLineTo(18f)
        close()
        moveTo(19f, 12f)
        arcTo(1f, 1f, 0f, false, true, 18f, 11f)
        arcTo(1f, 1f, 0f, false, true, 19f, 10f)
        arcTo(1f, 1f, 0f, false, true, 20f, 11f)
        arcTo(1f, 1f, 0f, false, true, 19f, 12f)
        close()
        moveTo(16f, 19f)
        horizontalLineTo(8f)
        verticalLineTo(14f)
        horizontalLineTo(16f)
        close()
        moveTo(19f, 8f)
        horizontalLineTo(5f)
        arcTo(3f, 3f, 0f, false, false, 2f, 11f)
        verticalLineTo(17f)
        horizontalLineTo(6f)
        verticalLineTo(21f)
        horizontalLineTo(18f)
        verticalLineTo(17f)
        horizontalLineTo(22f)
        verticalLineTo(11f)
        arcTo(3f, 3f, 0f, false, false, 19f, 8f)
        close()
    }.build()

    val Share: ImageVector = ImageVector.Builder(
        name = "Share",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color(0xFF339999))) {
        moveTo(18f, 16.08f)
        curveTo(17.24f, 16.08f, 16.56f, 16.38f, 16.04f, 16.85f)
        lineTo(8.91f, 12.7f)
        curveTo(8.96f, 12.47f, 9f, 12.24f, 9f, 12f)
        reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
        lineTo(15.96f, 7.19f)
        curveTo(16.5f, 7.69f, 17.21f, 8f, 18f, 8f)
        arcTo(3f, 3f, 0f, false, false, 21f, 5f)
        arcTo(3f, 3f, 0f, false, false, 18f, 2f)
        arcTo(3f, 3f, 0f, false, false, 15f, 5f)
        curveToRelative(0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
        lineTo(8.04f, 9.81f)
        curveTo(7.5f, 9.31f, 6.79f, 9f, 6f, 9f)
        arcTo(3f, 3f, 0f, false, false, 3f, 12f)
        arcTo(3f, 3f, 0f, false, false, 6f, 15f)
        curveToRelative(0.79f, 0f, 1.5f, -0.31f, 2.04f, -0.81f)
        lineTo(15.16f, 18.34f)
        curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.66f)
        curveToRelative(0f, 1.61f, 1.31f, 2.91f, 2.92f, 2.91f)
        reflectiveCurveToRelative(2.92f, -1.3f, 2.92f, -2.91f)
        arcTo(2.92f, 2.92f, 0f, false, false, 18f, 16.08f)
        close()
    }.build()

    val TodoCheck: ImageVector = ImageVector.Builder(
        name = "TodoCheck",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(fill = SolidColor(Color.White)) {
        moveTo(18f, 7f)
        lineToRelative(-1.41f, -1.41f)
        lineToRelative(-6.34f, 6.34f)
        lineToRelative(1.41f, 1.41f)
        lineTo(18f, 7f)
        close()
        moveTo(22.24f, 5.59f)
        lineTo(11.66f, 16.17f)
        lineTo(7.48f, 12f)
        lineToRelative(-1.41f, 1.41f)
        lineTo(11.66f, 19f)
        lineTo(23.23f, 7f)
        lineToRelative(-1.42f, -1.41f)
        close()
        moveTo(0.41f, 13.41f)
        lineTo(6f, 19f)
        lineToRelative(1.41f, -1.41f)
        lineTo(1.83f, 12f)
        lineTo(0.41f, 13.41f)
        close()
    }.build()

    val Loading: ImageVector = ImageVector.Builder(
        name = "Loading",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 72f,
        viewportHeight = 72f
    ).path(fill = SolidColor(Color(0xFFCCCCCCFF)), stroke = SolidColor(Color(0xFFE7E7E7)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(36.06f, 28.92f)
        lineTo(36.06f, 32.18f)
    }.path(fill = SolidColor(Color(0xFFC8C8CC)), stroke = SolidColor(Color(0xFFCACACA)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(39.45f, 29.88f)
        lineTo(37.82f, 32.71f)
    }.path(fill = SolidColor(Color(0xFFBBBBBE)), stroke = SolidColor(Color(0xFFCDCDCD)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(42.12f, 32.32f)
        lineTo(39.3f, 33.95f)
    }.path(fill = SolidColor(Color(0xFFB2B2B7)), stroke = SolidColor(Color(0xFFCBCBCB)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(39.8f, 35.98f)
        lineTo(43.06f, 35.98f)
    }.path(fill = SolidColor(Color(0xFFD0D0D4)), stroke = SolidColor(Color(0xFFEDEDED)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(32.77f, 29.99f)
        lineTo(34.4f, 32.81f)
    }.path(fill = SolidColor(Color(0xFF949497)), stroke = SolidColor(Color(0xFF525252)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(30.1f, 32.42f)
        lineTo(32.92f, 34.05f)
    }.path(fill = SolidColor(Color(0xFF97979B)), stroke = SolidColor(Color(0xFF6E6E6E)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(32.42f, 35.98f)
        lineTo(29.16f, 35.98f)
    }.path(fill = SolidColor(Color(0xFFA8A8AC)), stroke = SolidColor(Color(0xFFA0A0A0)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(36.06f, 43.08f)
        lineTo(36.06f, 39.82f)
    }.path(fill = SolidColor(Color(0xFFCACACA)), stroke = SolidColor(Color(0xFFCACACA)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(39.7f, 41.99f)
        lineTo(38.07f, 39.16f)
    }.path(fill = SolidColor(Color(0xFFB6B6BA)), stroke = SolidColor(Color(0xFFCCCCCC)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(42.19f, 39.4f)
        lineTo(39.37f, 37.77f)
    }.path(fill = SolidColor(Color(0xFFA1A1A5)), stroke = SolidColor(Color(0xFF909090)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(32.46f, 41.98f)
        lineTo(34.09f, 39.16f)
    }.path(fill = SolidColor(Color(0xFF9D9DA0)), stroke = SolidColor(Color(0xFF7A7A7A)), strokeLineWidth = 1f, strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round) {
        moveTo(29.85f, 39.4f)
        lineTo(32.67f, 37.77f)
    }.build()
}