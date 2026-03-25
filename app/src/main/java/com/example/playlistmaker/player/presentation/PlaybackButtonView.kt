package com.example.playlistmaker.player.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.compose.ui.geometry.Size
import androidx.core.graphics.drawable.toBitmap
import com.example.playlistmaker.R

class PlaybackButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    enum class State {
        PLAY,
        PAUSE
    }

    var state: State = State.PLAY
        private set

    private var playBitmap: Drawable? = null
    private var pauseBitmap: Drawable? = null

    private var boundsRect = Rect()

    var onStateChanged: ((newState: State) -> Unit)? = null

    init {
        try {
            val attrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PlaybackButtonView,
                0, 0
            ).apply {

                    playBitmap = getDrawable(R.styleable.PlaybackButtonView_playImage)
                    pauseBitmap = getDrawable(R.styleable.PlaybackButtonView_pauseImage)


            }
            attrs.recycle()
        } catch(e: Exception) {
            //
        }

        isClickable = true
    }

    fun setState(newState: State) {
        if (state != newState) {
            state = newState
            invalidate()
        }
    }

    private fun toggleState() {
        setState(if (state == State.PLAY) State.PAUSE else State.PLAY)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val drawable = when (state) {
            State.PLAY -> playBitmap
            State.PAUSE -> pauseBitmap
        } ?: return
        drawable.setBounds(0, 0, boundsRect.width(), boundsRect.height());
        drawable.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        boundsRect.set(0, 0, w, h)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                toggleState()
                onStateChanged?.invoke(state)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}
