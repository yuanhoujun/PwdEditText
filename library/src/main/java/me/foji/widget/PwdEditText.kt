package me.foji.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.EditText
/**
 *  支付密码输入框
 *
 *  @author Scott Smith @Date 2016年08月16/8/13日 13:23
 */
class PwdEditTextKt(context: Context,attrs: AttributeSet?): EditText(context,attrs) {
    // 默认外边缘线条宽度为1dp
    private val DEFAULT_OUT_LINE_WIDTH = dp2px(context,1.0f)
    // 默认外边缘线条颜色
    private val DEFAULT_OUT_LINE_COLOR = Color.parseColor("#bebebe")
    // 默认外边缘弧度
    private val DEFAULT_OUT_RADIUS = 0f
    // 默认间隔线宽度
    private val DEFAULT_DIVIDER_LINE_WIDTH = dp2px(context,1.0f)
    // 默认间隔线颜色
    private val DEFAULT_DIVIDER_LINE_COLOR = Color.parseColor("#eeeeee")
    // 默认占位圆点半径
    private val DEFAULT_DOT_RADIUS = dp2px(context,7.5f)
    // 默认占位圆点颜色
    private val DEFAULT_DOT_COLOR = Color.BLACK
    // 默认输入框宽度
    private val DEFAULT_INPUT_BOX_WIDTH = dp2px(context,47f)
    // 默认密码长度
    private val DEFAULT_PWD_LENGTH = 6

    private var mOutLineWidth: Float = DEFAULT_OUT_LINE_WIDTH
    private var mOutRadius: Float = DEFAULT_OUT_RADIUS
    private var mOutLineColor: Int = DEFAULT_OUT_LINE_COLOR
    private var mDividerLineWidth: Float = DEFAULT_DIVIDER_LINE_WIDTH
    private var mDividerLineColor: Int = DEFAULT_DIVIDER_LINE_COLOR
    private var mDotRadius: Float = DEFAULT_DOT_RADIUS
    private var mDotColor: Int = DEFAULT_DOT_COLOR
    private var mInputBoxWidth: Float = DEFAULT_INPUT_BOX_WIDTH
    private var mPwdLength: Int = DEFAULT_PWD_LENGTH

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 矩形外边框
    private var mOutRect: RectF? = null
    // 当前圆点索引
    private var mCurrDotLength: Int = 0
    var onInputListener: OnInputListener? = null

    constructor(context: Context): this(context,null)

    interface OnInputListener {
        fun onInput(text: CharSequence?)
        fun onInputFinish(text: CharSequence?)
    }

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.PwdEditText,0,0)
        mOutLineWidth = array.getDimension(R.styleable.PwdEditText_outLineWidth,DEFAULT_OUT_LINE_WIDTH)
        mOutRadius = array.getDimension(R.styleable.PwdEditText_outRadius,DEFAULT_OUT_RADIUS)
        mOutLineColor = array.getColor(R.styleable.PwdEditText_outLineColor,DEFAULT_OUT_LINE_COLOR)
        mDividerLineWidth = array.getDimension(R.styleable.PwdEditText_dividerLineWidth,DEFAULT_DIVIDER_LINE_WIDTH)
        mDividerLineColor = array.getColor(R.styleable.PwdEditText_dividerLineColor,DEFAULT_DIVIDER_LINE_COLOR)
        mDotRadius = array.getDimension(R.styleable.PwdEditText_dotRadius,DEFAULT_DOT_RADIUS)
        mDotColor = array.getColor(R.styleable.PwdEditText_dotColor,DEFAULT_DOT_COLOR)
        mInputBoxWidth = array.getDimension(R.styleable.PwdEditText_inputBoxWidth,DEFAULT_INPUT_BOX_WIDTH)
        mPwdLength = array.getInt(R.styleable.PwdEditText_pwdLength,DEFAULT_PWD_LENGTH)
        array.recycle()

        mOutRect = RectF(0f,0f, width.toFloat(), height.toFloat())
        filters = arrayOf(android.text.InputFilter.LengthFilter(mPwdLength))

        isCursorVisible = false
        setBackgroundDrawable(null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = (2 * mOutLineWidth + mPwdLength * mInputBoxWidth + (mPwdLength - 1) * mDividerLineWidth).toInt();
        val height = (2 * mOutLineWidth + mInputBoxWidth).toInt()
        setMeasuredDimension(width,height)
        mOutRect?.set(0f,0f,width.toFloat(),height.toFloat())
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        mCurrDotLength = text?.length!!
        invalidate()
        if(mPwdLength == text?.length) {
            onInputListener?.onInputFinish(text)
        }
        onInputListener?.onInput(text)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        setSelection(text.length)
    }

    override fun onDraw(canvas: Canvas?) {
        // 先绘制外边框
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mOutLineColor
        mPaint.strokeWidth = mOutLineWidth * 2
        if(mOutRadius > 0) {
            mPaint.strokeWidth = mOutLineWidth
            mOutRect!!.set(mOutLineWidth / 2,mOutLineWidth / 2,width - mOutLineWidth / 2,height - mOutLineWidth / 2)
        }
        canvas?.drawRoundRect(mOutRect,mOutRadius,mOutRadius,mPaint)

        // 绘制间隔线
        mPaint.style = Paint.Style.FILL
        mPaint.color = mDividerLineColor
        mPaint.strokeWidth = mDividerLineWidth * 2
        for(i: Int in 0..mPwdLength - 2) {
            val startX = mOutLineWidth + (i + 1) * mInputBoxWidth + mDividerLineWidth * i
            val startY = mOutLineWidth
            val stopX = startX
            val stopY = startY + mInputBoxWidth
            canvas?.drawLine(startX,startY,stopX,stopY,mPaint)
        }

        // 绘制占位符小圆点
        mPaint.color = mDotColor
        for(i: Int in 0..mCurrDotLength - 1) {
            val cx = mOutLineWidth + mInputBoxWidth / 2 + mInputBoxWidth * i + mDividerLineWidth * i
            val cy = mOutLineWidth + mInputBoxWidth / 2
            canvas?.drawCircle(cx,cy,mDotRadius,mPaint)
        }
    }
}