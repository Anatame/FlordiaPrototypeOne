package com.anatame.flordia.presentation.widgets.flordia_player

import android.content.Context
import android.media.MediaFormat
import com.anatame.flordia.data.network.AppNetworkClient
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.VideoFrameMetadataListener
import timber.log.Timber

class FlordiaPlayer(private val context: Context) {
    var player: ExoPlayer = makePlayer(context)
    var currentUrl: String? = null

    val currentBitrate: Int?
        get() = player.videoFormat?.bitrate

    val currentTracksDataList = ArrayList<TrackData>()

    fun playVideo(url: String, playerView: PlayerView){
        currentUrl = url
        playerView.player = player
        player.setMediaSource(createMediaSource(url))
        player.prepare()
        player.playWhenReady = true
        listen()
    }

    fun getCurrentPlayerPosition(): Long = player.currentPosition

    private fun listen(){
        player.addListener(object: Player.Listener{
            override fun onTracksInfoChanged(tracksInfo: TracksInfo) {
                super.onTracksInfoChanged(tracksInfo)

                val tracks = tracksInfo.trackGroupInfos.firstOrNull()

                tracks?.let {
                    for(i in 0 until tracks.trackGroup.length){
                        val data = TrackData(
                            "${tracks.trackGroup.getFormat(i).width} x ${tracks.trackGroup.getFormat(i).height}",
                            tracks.trackGroup.getFormat(i).bitrate
                        )
                        if(!currentTracksDataList.contains(data))
                            currentTracksDataList.add(data)
                    }
                }
            }
        })
    }

    fun setVideoQuality(bitrate: Int){
        player.let{
            it.trackSelectionParameters = player.trackSelectionParameters
                .buildUpon()
                .setMaxVideoBitrate(bitrate)
                .setMinVideoBitrate(bitrate)
                .build()

            Timber.tag("setVidQual").d(bitrate.toString())

            it.setVideoFrameMetadataListener(object : VideoFrameMetadataListener {
                override fun onVideoFrameAboutToBeRendered(
                    presentationTimeUs: Long,
                    releaseTimeNs: Long,
                    format: Format,
                    mediaFormat: MediaFormat?
                ) {
                    Timber.d("""
                                 ${format.height} x ${format.width}
                                 ${format.bitrate}
                                 """.trimIndent())
                }
            })

        }
    }

    fun resume(pos: Long) {
        player.seekTo(pos)
        player.prepare()
        player.play()
    }

    fun stop(): Long {
        val currentPos = getCurrentPlayerPosition()
        player.stop()

        return currentPos
    }
    fun release(): Long {
        val currentPos = getCurrentPlayerPosition()
        player.stop()
        player.release()

        return currentPos
    }

    private fun createMediaSource(hls: String): MediaSource {

        val dataSourceFactory = OkHttpDataSource.Factory(AppNetworkClient.getClient())

        return HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(false)
            .createMediaSource(MediaItem.fromUri(hls))
    }
    private fun makePlayer(context: Context): ExoPlayer {
        Timber.d("shit this got called again bruh")
        return ExoPlayer.Builder(context).build()
    }
}

data class TrackData(
    val quality: String,
    val bitrate: Int
)
