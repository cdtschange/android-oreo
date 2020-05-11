package com.cdts.demo.ui.animation.view

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.core.content.ContextCompat
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.cdts.demo.R
import com.cdts.demo.schema.view.BaseActivity
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity: BaseActivity() {

    override val layoutResID: Int = R.layout.activity_animation

    override val titleBar: ORToolBar?
        get() = demoToolBar

    var type: String? = null
    var title: String? = null

    private val typeEnum: AnimationType
        get() = AnimationType.valueOf(type ?: "")

    override fun setupUI() {
        super.setupUI()

        demoToolBar.centerText = title ?: ""
        itemView.setOnClickListener {
            animate()
        }
    }

    private fun animate() {
        when(typeEnum) {
            AnimationType.Frame -> {
                val animator = ValueAnimator.ofFloat(400f)
                animator.addUpdateListener {
                    itemView.translationX = it.animatedValue as Float
                    itemView.translationY = it.animatedValue as Float
                }
                animator.interpolator = LinearInterpolator()
                animator.duration = 400
                animator.repeatCount = 1
                animator.repeatMode = ValueAnimator.REVERSE
                animator.start()
            }
            AnimationType.FrameAccelerate -> {
                val animator = ValueAnimator.ofFloat(600f)
                animator.addUpdateListener {
                    itemView.translationY = it.animatedValue as Float
                }
                animator.interpolator = AccelerateInterpolator(1.5f)
                animator.duration = 400
                animator.repeatCount = 1
                animator.repeatMode = ValueAnimator.REVERSE
                animator.start()
            }
            AnimationType.Scale -> {
                itemView.animate().scaleX(2f).scaleY(2f).setDuration(400).withEndAction {
                    itemView.animate().scaleX(1f).scaleY(1f).setDuration(400).start()
                }.start()
            }
            AnimationType.Rotation -> {
                val animator = ValueAnimator.ofFloat(90f)
                animator.addUpdateListener {
                    itemView.rotation = it.animatedValue as Float
                }
                animator.interpolator = LinearInterpolator()
                animator.duration = 400
                animator.start()
            }
            AnimationType.Alpha -> {
                itemView.animate().alpha(0.1f).setDuration(400).withEndAction {
                    itemView.animate().alpha(1f).setDuration(400).start()
                }.start()
            }
            AnimationType.Color -> {
                val animator = ObjectAnimator.ofObject(itemView, "backgroundColor", ArgbEvaluator(),
                    ContextCompat.getColor(this, R.color.colorBlue), ContextCompat.getColor(this, R.color.colorYellow))
                animator.repeatCount = 1
                animator.repeatMode = ValueAnimator.REVERSE
                animator.start()
            }
            AnimationType.Combine -> {
                val animator = ValueAnimator.ofFloat(600f)
                animator.addUpdateListener {
                    itemView.translationY = it.animatedValue as Float
                }
                animator.interpolator = AccelerateInterpolator(1.5f)
                animator.repeatCount = 1
                animator.repeatMode = ValueAnimator.REVERSE
                animator.duration = 400

                val animator2 = ValueAnimator.ofFloat(90f)
                animator2.addUpdateListener {
                    itemView.rotation = it.animatedValue as Float
                }
                animator2.interpolator = LinearInterpolator()
                animator2.duration = 400

                val animator3 = ObjectAnimator.ofObject(itemView, "backgroundColor", ArgbEvaluator(),
                    ContextCompat.getColor(this, R.color.colorBlue), ContextCompat.getColor(this, R.color.colorYellow))
                animator3.repeatCount = 1
                animator3.repeatMode = ValueAnimator.REVERSE

                val animatorSet = AnimatorSet()
                animatorSet.play(animator).with(animator2).before(animator3)
                animatorSet.duration = 800
                animatorSet.start()
            }
        }
    }
}

enum class AnimationType {
    Frame, FrameAccelerate, Scale, Rotation, Alpha, Color, Combine
}