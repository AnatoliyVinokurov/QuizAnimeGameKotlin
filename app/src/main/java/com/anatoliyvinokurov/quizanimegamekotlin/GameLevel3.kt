package com.anatoliyvinokurov.quizanimegamekotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import java.util.*

class GameLevel3 : AppCompatActivity() {
    var levels = IntArray(30) // created an array containing answers (correct/incorrect/ and unanswered)
    private lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamelevels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //loading the levels array from the save
        loadAnArrayWithLevels()

        //set the background image for the first level
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.main_background_level3)

        //An array of elements to display the progress of the game
        val progress = intArrayOf(
            R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5,
            R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10,
            R.id.textView11, R.id.textView12, R.id.textView13, R.id.textView14, R.id.textView15,
            R.id.textView16, R.id.textView17, R.id.textView18, R.id.textView19, R.id.textView20,
            R.id.textView21, R.id.textView22, R.id.textView23, R.id.textView24, R.id.textView25,
            R.id.textView26, R.id.textView27, R.id.textView28, R.id.textView29, R.id.textView30
        )

        //Paint the screen buttons with level numbers
        for (i in 0..13) { //if the level I-th element in the array is zero
            if (levels[i] == 0 && i in 0..12) {
                val tv = findViewById<TextView>(progress[i + 1]) //then we take neither this, but the next and....
                tv.setBackgroundResource(R.drawable.btn_locker) //...draw a lock
                tv.text = ""
                tv.isEnabled = false //blocking the click
            } else if (levels[i] == 1) { //if the level is passed correctly ...
                val tv = findViewById<TextView>(progress[i])
                tv.setBackgroundResource(R.drawable.btn_star) //...draw an asterisk
                tv.isEnabled = true //unlock pressing
            } else if (levels[i] == -1) { //if the level is passed incorrectly ...
                val tv = findViewById<TextView>(progress[i])
                tv.setBackgroundResource(R.drawable.btn_empty_star) //...draw an empty star
                tv.isEnabled = true //unlock pressing
            }
        }

        // code for on-screen Back button"
        val buttonStart = findViewById<Button>(R.id.button_back)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@GameLevel3, MainActivity::class.java)
                intent.putExtra("slideNumber", 2)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }

        //processing pressing // --start--//
        //Path to screen TextView
        val textView1 = findViewById<View>(R.id.textView1) as TextView
        val textView2 = findViewById<View>(R.id.textView2) as TextView
        val textView3 = findViewById<View>(R.id.textView3) as TextView
        val textView4 = findViewById<View>(R.id.textView4) as TextView
        val textView5 = findViewById<View>(R.id.textView5) as TextView
        val textView6 = findViewById<View>(R.id.textView6) as TextView
        val textView7 = findViewById<View>(R.id.textView7) as TextView
        val textView8 = findViewById<View>(R.id.textView8) as TextView
        val textView9 = findViewById<View>(R.id.textView9) as TextView
        val textView10 = findViewById<View>(R.id.textView10) as TextView
        val textView11 = findViewById<View>(R.id.textView11) as TextView
        val textView12 = findViewById<View>(R.id.textView12) as TextView
        val textView13 = findViewById<View>(R.id.textView13) as TextView
        val textView14 = findViewById<View>(R.id.textView14) as TextView
        val textView15 = findViewById<View>(R.id.textView15) as TextView
        val textView16 = findViewById<View>(R.id.textView16) as TextView
        val textView17 = findViewById<View>(R.id.textView17) as TextView
        val textView18 = findViewById<View>(R.id.textView18) as TextView
        val textView19 = findViewById<View>(R.id.textView19) as TextView
        val textView20 = findViewById<View>(R.id.textView20) as TextView
        val textView21 = findViewById<View>(R.id.textView21) as TextView
        val textView22 = findViewById<View>(R.id.textView22) as TextView
        val textView23 = findViewById<View>(R.id.textView23) as TextView
        val textView24 = findViewById<View>(R.id.textView24) as TextView
        val textView25 = findViewById<View>(R.id.textView25) as TextView
        val textView26 = findViewById<View>(R.id.textView26) as TextView
        val textView27 = findViewById<View>(R.id.textView27) as TextView
        val textView28 = findViewById<View>(R.id.textView28) as TextView
        val textView29 = findViewById<View>(R.id.textView29) as TextView
        val textView30 = findViewById<View>(R.id.textView30) as TextView

        //hided buttens
        textView15.isInvisible = true
        textView16.isInvisible = true
        textView17.isInvisible = true
        textView18.isInvisible = true
        textView19.isInvisible = true
        textView20.isInvisible = true
        textView21.isInvisible = true
        textView22.isInvisible = true
        textView23.isInvisible = true
        textView24.isInvisible = true
        textView25.isInvisible = true
        textView26.isInvisible = true
        textView27.isInvisible = true
        textView28.isInvisible = true
        textView29.isInvisible = true
        textView30.isInvisible = true

        //create an intent that performs the transition to the first-level
        val intent = Intent(this@GameLevel3, Level3::class.java)

        //find out which element the user clicked on
        val onClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.textView1 -> try {
                        intent.putExtra("count", 0)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView2 -> try {
                        intent.putExtra("count", 1)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView3 -> try {
                        intent.putExtra("count", 2)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView4 -> try {
                        intent.putExtra("count", 3)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView5 -> try {
                        intent.putExtra("count", 4)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView6 -> try {
                        intent.putExtra("count", 5)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView7 -> try {
                        intent.putExtra("count", 6)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView8 -> try {
                        intent.putExtra("count", 7)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView9 -> try {
                        intent.putExtra("count", 8)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView10 -> try {
                        intent.putExtra("count", 9)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView11 -> try {
                        intent.putExtra("count", 10)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView12 -> try {
                        intent.putExtra("count", 11)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView13 -> try {
                        intent.putExtra("count", 12)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView14 -> try {
                        intent.putExtra("count", 13)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView15 -> try {
                        intent.putExtra("count", 14)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView16 -> try {
                        intent.putExtra("count", 15)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView17 -> try {
                        intent.putExtra("count", 16)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView18 -> try {
                        intent.putExtra("count", 17)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView19 -> try {
                        intent.putExtra("count", 18)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView20 -> try {
                        intent.putExtra("count", 19)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView21 -> try {
                        intent.putExtra("count", 20)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView22 -> try {
                        intent.putExtra("count", 21)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView23 -> try {
                        intent.putExtra("count", 22)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView24 -> try {
                        intent.putExtra("count", 23)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView25 -> try {
                        intent.putExtra("count", 24)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView26 -> try {
                        intent.putExtra("count", 25)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView27 -> try {
                        intent.putExtra("count", 26)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView28 -> try {
                        intent.putExtra("count", 27)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView29 -> try {
                        intent.putExtra("count", 28)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                    R.id.textView30 -> try {
                        intent.putExtra("count", 29)
                        startActivity(intent)
                        finish()
                    } catch (e: java.lang.Exception) {
                    }
                }
            }

        //assign an action to textView elements
        textView1.setOnClickListener(onClickListener)
        textView2.setOnClickListener(onClickListener)
        textView3.setOnClickListener(onClickListener)
        textView4.setOnClickListener(onClickListener)
        textView5.setOnClickListener(onClickListener)
        textView6.setOnClickListener(onClickListener)
        textView7.setOnClickListener(onClickListener)
        textView8.setOnClickListener(onClickListener)
        textView9.setOnClickListener(onClickListener)
        textView10.setOnClickListener(onClickListener)
        textView11.setOnClickListener(onClickListener)
        textView12.setOnClickListener(onClickListener)
        textView13.setOnClickListener(onClickListener)
        textView14.setOnClickListener(onClickListener)
        textView15.setOnClickListener(onClickListener)
        textView16.setOnClickListener(onClickListener)
        textView17.setOnClickListener(onClickListener)
        textView18.setOnClickListener(onClickListener)
        textView19.setOnClickListener(onClickListener)
        textView20.setOnClickListener(onClickListener)
        textView21.setOnClickListener(onClickListener)
        textView22.setOnClickListener(onClickListener)
        textView23.setOnClickListener(onClickListener)
        textView24.setOnClickListener(onClickListener)
        textView25.setOnClickListener(onClickListener)
        textView26.setOnClickListener(onClickListener)
        textView27.setOnClickListener(onClickListener)
        textView28.setOnClickListener(onClickListener)
        textView29.setOnClickListener(onClickListener)
        textView30.setOnClickListener(onClickListener)
        //processing pressing / / --end--//

    }

    //load the levels array from the save
    private fun loadAnArrayWithLevels() {
        try {
            sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
            var savedString: String = sPref.getString("level_three", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            val st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levels[i] = st.nextToken().toInt()
            }
        } catch (e:Exception) {
        }
    }

    // code for system back button
    override fun onBackPressed() {
        try {
            val intent = Intent(this@GameLevel3, MainActivity::class.java)
            intent.putExtra("slideNumber", 2)
            startActivity(intent)
            finish()
        } catch (e:Exception) {
        }
    }

    //the code that is triggered when you resume working with the activity
    override fun onResume() {
        super.onResume()
        loadAnArrayWithLevels() //loading the levels array from the save
    }
}