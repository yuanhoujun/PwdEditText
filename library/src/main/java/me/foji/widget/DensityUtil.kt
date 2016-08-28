package me.foji.widget

import android.content.Context
import android.util.TypedValue

/**
 *  单位换算
 *
 *  @author Scott Smith @Date 2016年08月16/8/28日 10:47
 */
// dp to px
fun dp2px(context: Context,dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.resources.displayMetrics)
}

// px to dp
fun px2dp(context: Context, px: Float): Float {
    val density = context.resources.displayMetrics.density
    return px / density
}