package com.example.playlistmaker.common.presentation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.annotation.Px

object Dimens {
    // ===== Padding =========================================================
    @Px val paddingXXS = 4.dp
    @Px val paddingXS   = 8.dp
    @Px val paddingXSS  = 10.dp
    @Px val paddingS    = 12.dp
    @Px val paddingSM   = 14.dp
    @Px val paddingM    = 16.dp
    @Px val paddingML   = 18.dp
    @Px val paddingL    = 20.dp
    @Px val paddingXL   = 24.dp
    @Px val padding2XL  = 26.dp
    @Px val padding4XL  = 28.dp
    @Px val padding5XL  = 30.dp
    @Px val padding6XL  = 38.dp
    @Px val padding7XL  = 42.dp
    @Px val padding8XL  = 54.dp
    @Px val padding9XL  = 102.dp
    @Px val padding10XL = 140.dp

    // ===== Radius ==========================================================
    @Px val radiusXXXs = 2.dp
    @Px val radiusXXS  = 4.dp
    @Px val radiusXS   = 8.dp
    @Px val radiusS    = 12.dp
    @Px val radiusM    = 16.dp
    @Px val radiusXXXL = 54.dp

    // ===== Text size =======================================================
    @Px val textXS   = 11.sp
    @Px val textS    = 13.sp
    @Px val textXM   = 14.sp
    @Px val textM    = 16.sp
    @Px val textL    = 19.sp
    @Px val textXL   = 22.sp
    @Px val textXXL  = 24.sp

    // ===== Icon size =======================================================
    @Px val iconS   = 12.dp
    @Px val iconM   = 24.dp
    @Px val iconL   = 44.dp
    @Px val iconXL  = 51.dp
    @Px val iconXXL = 84.dp
    @Px val iconXXXL= 120.dp

    // ===== Misc ============================================================
    @Px val switchSize   = 32.dp
    @Px val iconWidth    = 40.dp
    @Px val iconHeight   = 36.dp
    @Px val listItemHeight = 60.dp

//    // ----------------------------------------------------------------------
//    // Helper extensions – convert dp/sp to pixels at runtime
//    // ----------------------------------------------------------------------
//    private val Resources.dp: Int
//        get() = (this.displayMetrics.density * this@Dimensions).roundToInt()
//
//    private val Resources.sp: Int
//        get() = (this.displayMetrics.scaledDensity * this@Dimensions).roundToInt()
}