package com.anatame.flordia.presentation.widgets.flordia_player

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.os.HandlerCompat.postDelayed
import com.anatame.flordia.R
import com.anatame.flordia.databinding.PlayerCustomStubLandscapeBinding
import com.anatame.flordia.presentation.activities.MainActivity
import com.anatame.flordia.presentation.widgets.flordia_player.more_controls.MoreControlsDialogFragment
import com.github.vkay94.dtpv.DoubleTapPlayerView
import com.github.vkay94.dtpv.youtube.YouTubeOverlay
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

class FullScreenDialog(
    private val ctx: Context,
    private val activity: Activity,
    private val flordiaPlayer: FlordiaPlayer,
    private val playerView: DoubleTapPlayerView
): Dialog(ctx, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

    private lateinit var binding: PlayerCustomStubLandscapeBinding
    private lateinit var newPlayerView: PlayerView
    // controls
    private lateinit var fullScreenBtn: ImageButton
    private lateinit var exitFullScreenBtn: ImageButton
    private lateinit var moreBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var resizeBtn: ImageButton

    var moreDialog: MoreControlsDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlayerCustomStubLandscapeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newPlayerView = binding.vidPlayer

        flordiaPlayer.player?.let { PlayerView.switchTargetView(it, playerView, newPlayerView) }

        configureOverlay()
        setUpControls()

        handleGoingFullScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            handleGoingFullScreen()
        }, 200)
    }

//    override fun onStop() {
//        super.onStop()
//        this.window?.let { notFullScreen(it) }
//        activity.window?.let { notFullScreen(it) }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToPortrait()
    }

    fun backToPortrait(){
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        flordiaPlayer.player?.let { PlayerView.switchTargetView(it, newPlayerView, playerView) }
        notFullScreen(this.window!!)
        notFullScreen(activity.window!!)
        this.dismiss()
    }

    private fun setUpControls(){
        fullScreenBtn = binding.vidPlayer.findViewById(R.id.fullscreenBtn)
        exitFullScreenBtn = binding.vidPlayer.findViewById(R.id.exitFullScreenBtn)
        moreBtn = binding.vidPlayer.findViewById(R.id.more)
        backBtn = binding.vidPlayer.findViewById(R.id.backArrow)
        resizeBtn = binding.vidPlayer.findViewById(R.id.resize)

        controlsForLandscape()
    }
    private fun controlsForLandscape(){
        fullScreenBtn.visibility = View.INVISIBLE
        exitFullScreenBtn.visibility = View.VISIBLE
        resizeBtn.visibility = View.VISIBLE

        exitFullScreenBtn.setOnClickListener{
            backToPortrait()
        }
        backBtn.setOnClickListener{
            backToPortrait()
        }
        resizeBtn.setOnClickListener{
            resize()
        }
        moreBtn.setOnClickListener{
            handleShowingMoreControls()
        }
    }

    private fun handleShowingMoreControls() {
        val fm = (activity as MainActivity).supportFragmentManager
        moreDialog = MoreControlsDialogFragment(flordiaPlayer)
        moreDialog?.showNow(fm, "More Controls Dialog")
        moreDialog?.dialog?.setOnDismissListener {
            handleGoingFullScreen()
            moreDialog?.dismiss()
        }
        moreDialog?.dialog?.setOnCancelListener {
            handleGoingFullScreen()
            moreDialog?.dismiss()
        }
    }

    fun handleGoingFullScreen() {
        // fullScreenActivity(activity.window!!)

        this.window?.let {
            fullScreenActivity(it)
        }
        moreDialog?.dialog?.window?.let {
            fullScreenActivity(it)
        }
    }




    private fun resize(){
        when(newPlayerView.resizeMode){
            AspectRatioFrameLayout.RESIZE_MODE_FIT -> {
                newPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
            AspectRatioFrameLayout.RESIZE_MODE_ZOOM -> {
                newPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
            AspectRatioFrameLayout.RESIZE_MODE_FILL -> {

            }
            AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT -> {

            }
            AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH -> {
            }
        }

    }

    private fun configureOverlay() {
        flordiaPlayer.player?.let { binding.youtubeOverlay.player(it) }
        binding.youtubeOverlay
            .performListener(object : YouTubeOverlay.PerformListener {
                override fun onAnimationStart() {
                    // Do UI changes when circle scaling animation starts (e.g. hide controller views)
                    binding.youtubeOverlay.visibility = FrameLayout.VISIBLE
                }

                override fun onAnimationEnd() {
                    // Do UI changes when circle scaling animation starts (e.g. show controller views)
                    binding.youtubeOverlay.visibility = FrameLayout.GONE
                }
            })
    }

    private fun fullScreenActivity(window: Window?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)
            val controller =  window?.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                };
            }
        } else {
            window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window?.attributes?.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            };
        }
    }
    private fun notFullScreen(window: Window){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(true)
            val controller =  window.insetsController
            if (controller != null) {
                controller.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_DEFAULT
                window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
            };
        }

    }


}