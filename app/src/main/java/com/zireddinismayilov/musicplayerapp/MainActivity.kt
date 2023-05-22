package com.zireddinismayilov.musicplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.zireddinismayilov.musicplayerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mp: MediaPlayer
    lateinit var binding: ActivityMainBinding
    var totalTIme = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mp = MediaPlayer.create(this, R.raw.music)
        mp.isLooping = false
        mp.setVolume(0.5f, 0.5f)
        totalTIme = mp.duration
        binding.timeBar.max = totalTIme
        binding.volumeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val volumeLevel = progress / 100f
                    mp.setVolume(volumeLevel, volumeLevel)
                }
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {}
            override fun onStopTrackingTouch(seekbar: SeekBar?) {}
        })

        binding.timeBar.setOnSeekBarChangeListener(

            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            }
        )
        binding.timeBar.postDelayed(object : Runnable {
            override fun run() {
                binding.timeBar.progress = mp.currentPosition
                binding.elapsedTime.text = (mp.currentPosition / 1000).toString()
                binding.remainingTime.text = ((mp.currentPosition - totalTIme) / 1000).toString()
                binding.timeBar.postDelayed(this, 1000)
            }
        }, 0)
    }

    fun onClickPausePlay(btn: View) {
        btn.setOnClickListener {
            if (mp.isPlaying) {
                mp.pause()
                btn.setBackgroundResource(R.drawable.play)
            } else {
                mp.start()
                btn.setBackgroundResource(R.drawable.pause)
            }
        }
    }

}