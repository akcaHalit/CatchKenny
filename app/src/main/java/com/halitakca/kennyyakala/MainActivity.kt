package com.halitakca.kennyyakala

import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.halitakca.kennyyakala.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var imageArray = ArrayList<ImageView>()
    var score = 0
    var runnable =  Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Kenny Array
        imageArray.add(binding.kenny1)
        imageArray.add(binding.kenny2)
        imageArray.add(binding.kenny3)
        imageArray.add(binding.kenny4)
        imageArray.add(binding.kenny5)
        imageArray.add(binding.kenny6)

        hideImages()


        // Count Down
        object : CountDownTimer(15000,1000){
            override fun onTick(p0: Long) {
                binding.timeText.text = "Time: ${p0/1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Time: 0"

                handler.removeCallbacks(runnable)
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("GAME OVER")
                alert.setMessage("Restart the Game ?")
                alert.setPositiveButton("YES",DialogInterface.OnClickListener{dialogInterface, i ->
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })
                alert.setNegativeButton("NO",DialogInterface.OnClickListener{dialogInterface, i ->
                    Toast.makeText(this@MainActivity,"GAME OVER",Toast.LENGTH_LONG).show()
                })

                alert.show()
            }
        }.start()
    }

    fun hideImages(){

        runnable = object : Runnable{
            override fun run() {
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val randomIndex = Random.nextInt(0, 6)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)
    }




    fun getScore(view: View){
        score++
        binding.scoreText.text = "Score: ${score}"
    }

}