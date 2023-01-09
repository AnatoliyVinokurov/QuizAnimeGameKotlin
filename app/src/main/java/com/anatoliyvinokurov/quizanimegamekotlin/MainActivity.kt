package com.anatoliyvinokurov.quizanimegamekotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isInvisible
import androidx.viewpager.widget.ViewPager
import java.util.*
import kotlin.Array

class MainActivity : AppCompatActivity() {

    private var backPressedTime:Long = 0
    private lateinit var backToast:Toast

    private var slideViewPager: ViewPager? = null
    private var dotsLayout: LinearLayout? = null
    private var sliderAdapter: SliderAdapter? = null
    private var dots: Array<TextView?> = arrayOfNulls(3)
    private var main_background: ImageView? = null
    private var buttonStart: Button? = null
    private var buttonAbout: Button? = null
    private var currentPage = 0

    var dialog: Dialog? = null

    private var slideNumber = 0
    var levelOne = IntArray(30)
    var levelTwo = IntArray(30)
    var levelThree = IntArray(30)
    var starsLevelOne = 0
    var starsLevelTwo = 0
    var starsLevelThree = 0
    private lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        slideViewPager = findViewById<View>(R.id.slideViewPager) as ViewPager
        dotsLayout = findViewById<View>(R.id.dotsLayout) as LinearLayout

        main_background = findViewById<ImageView>(R.id.main_background) as ImageView
        buttonStart = findViewById<Button>(R.id.button_start) as Button
        buttonAbout = findViewById<Button>(R.id.button_about) as Button

        sliderAdapter = SliderAdapter(this)
        slideViewPager!!.setAdapter(sliderAdapter)

        addDotsIndicator(0)
        slideViewPager!!.addOnPageChangeListener(viewListener)

        //assign a value to the variable count from the Intent using the key count
        slideNumber = intent.getIntExtra("slideNumber", 0)

        //set starting position of slider_page
        slideViewPager!!.setCurrentItem(slideNumber)
        /*
            slideViewPager!!.setCurrentItem(0) //set starting position of slider_page
         */

        //load the number of stars in the levels
        loadAnArrayWithLevels()

        rasstanovkaZamkov()
        ustanovkaTexstaKnopkam(currentPage)

        // code for on-screen Start button
        buttonStart!!.setOnClickListener {
            when (currentPage) {
                0 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel1::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
                1 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel2::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
                2 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel3::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
            }
        }


        // code for on-screen About button
        buttonAbout!!.setOnClickListener {
            //-1-//call the dialog box at the beginning of the game
            dialog = Dialog(this)//created a new dialog box
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
            dialog!!.setContentView(R.layout.previewdialog) //the path to the dialog box layout
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
            dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

            //put the image
            val previewimg = dialog!!.findViewById<ImageView>(R.id.previewimg)
            previewimg.setImageResource(R.drawable.preview_img_three)
            //put the text
            val textdescription = dialog!!.findViewById<View>(R.id.textdescription) as TextView
            textdescription.setText(R.string.aboutme)
            //put the background image
            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
            dialogfom.setBackgroundResource(R.drawable.preview_background_one)

            //hid the button
            // button to close the dialog box
            val btnclose = dialog!!.findViewById<View>(R.id.btncloce) as TextView
            btnclose.isInvisible = true //hide the button
            //hid the button

            // continue button
            val btncontinue = dialog!!.findViewById<View>(R.id.btncontinue) as Button
            btncontinue.setOnClickListener {
                try {
                    dialog!!.dismiss() //closes the dialog box
                } catch (e:Exception){
                }
            }

            dialog!!.show()//show dialog box
        }

    }

    // code for system back button
    override fun onBackPressed() {
        //if the current time is + 2 seconds longer than the current time
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            backToast.cancel() //command to close the pop-up message
            super.onBackPressed() //the team that closes the game
            return
        }
        else
        {
            backToast = Toast.makeText(getBaseContext(), R.string.exetpress, Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    //function for coloring points according to the selected page-slides
    fun addDotsIndicator(position: Int) {
        dots = arrayOfNulls<TextView>(3)
        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.setText(Html.fromHtml("&#8226;"))
            dots[i]!!.setTextSize(35f)
            dots[i]!!.setTextColor(resources.getColor(R.color.colorTransparentWhite))
            dotsLayout!!.addView(dots[i]!!)
        }
        if (dots.size > 0) {
            dots[position]!!.setTextColor(resources.getColor(R.color.colorWhite))
        }
    }

    //function for setting a page-slides of OnPageChangeListener
    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        //the override function that is called when browsing the slides
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        //the override function that is called when browsing the slides
        override fun onPageSelected(position: Int) {
            addDotsIndicator(position)
            ustanovkaTexstaKnopkam(position)
            currentPage = position
            when (position) {
                0 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level1)
                }
                1 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level2)
                }
                2 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level3)
                }
            }
        }

        //the override function that is called when browsing the slides
        override fun onPageScrollStateChanged(state: Int) {}
    }

    //load the number of stars in the levels
    private fun loadAnArrayWithLevels() {
        try {
            sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)

            var savedString: String = sPref.getString("level_one", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            var st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levelOne[i] = st.nextToken().toInt()
            }

            savedString = sPref.getString("level_two", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levelTwo[i] = st.nextToken().toInt()
            }

            savedString = sPref.getString("level_three", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levelThree[i] = st.nextToken().toInt()
            }

        } catch (e:Exception) {
        }

        //counting the number of stars
        starsLevelOne = 0
        for (i in 0..29) {
            if (levelOne[i] == 1) {
                starsLevelOne++
            }
        }

        //counting the number of stars
        starsLevelTwo = 0
        for (i in 0..29) {
            if (levelTwo[i] == 1) {
                starsLevelTwo++
            }
        }

        //counting the number of stars
        starsLevelThree = 0
        for (i in 0..29) {
            if (levelThree[i] == 1) {
                starsLevelThree++
            }
        }
    }

    private fun rasstanovkaZamkov(){

        if (starsLevelOne < 12 ){ //if the user scored less than 15 points in the first level
            sliderAdapter!!.slideImages[1] = (R.drawable.main_img_locker) //lock
        } else { //if you score more than 15 points
            if (levelOne[24] == 0) { // then we check whether he passed it? //if not passed
                sliderAdapter!!.slideImages[1] = (R.drawable.main_img_locker)//lock
            }
            //otherwise, we don't do anything, but let the program work
        }


        if (starsLevelTwo < 6 ) {
            sliderAdapter!!.slideImages[2] = (R.drawable.main_img_locker)
        } else {
            if (levelTwo[12] == 0) {
                sliderAdapter!!.slideImages[2] = (R.drawable.main_img_locker)
            }
        }

    }

    private fun ustanovkaTexstaKnopkam(position: Int) {
        when (position) {
            0 -> {
                if (levelOne[0] == 0) { //if the user did not play the game (first element of the first level = 0)
                    buttonStart!!.setText(R.string.start)
                    buttonStart!!.isEnabled = true
                } else { //otherwise (if you played the game)
                    buttonStart!!.setText(R.string.textcontinue)
                    buttonStart!!.isEnabled = true
                }
            }
            1 -> {
                if (levelOne[24] == 0) { //if the value of the last element of the first level = 0
                    buttonStart!!.setText(R.string.start)
                    buttonStart!!.isEnabled = false
                } else { // иначе
                    if (starsLevelOne < 12) { // check whether the user has scored 15 points (passed the level successfully)
                        buttonStart!!.setText(R.string.start)
                        buttonStart!!.isEnabled = false
                    } else {
                        buttonStart!!.setText(R.string.start)
                        buttonStart!!.isEnabled = true
                    }
                }

                if (levelTwo[0] !== 0) { //if the user played the game (the first element of the first level is not zero)
                    buttonStart!!.setText(R.string.textcontinue)
                    buttonStart!!.isEnabled = true
                }

            }
            2 -> {
                if (levelTwo[12] == 0) {
                    buttonStart!!.setText(R.string.start)
                    buttonStart!!.isEnabled = false
                } else {
                    if (starsLevelTwo < 6) {
                        buttonStart!!.setText(R.string.start)
                        buttonStart!!.isEnabled = false
                    } else {
                        buttonStart!!.setText(R.string.start)
                        buttonStart!!.isEnabled = true
                    }
                }

                if (levelThree[0] !== 0) {
                    buttonStart!!.setText(R.string.textcontinue)
                    buttonStart!!.isEnabled = true
                }

            }
        }
    }

    //the code that is triggered when you resume working with the activity
    override fun onResume() {
        super.onResume()
        loadAnArrayWithLevels() //loading the levels array from the save
        rasstanovkaZamkov()
        ustanovkaTexstaKnopkam(currentPage)
    }

}