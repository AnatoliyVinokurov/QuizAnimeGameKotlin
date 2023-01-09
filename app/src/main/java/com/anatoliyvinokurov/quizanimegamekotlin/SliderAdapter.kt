package com.anatoliyvinokurov.quizanimegamekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter


class SliderAdapter(var context: Context) : PagerAdapter() {

    var layoutInflater: LayoutInflater? = null

    //Array
    var slideImages = intArrayOf(
        R.drawable.main_img_1,
        R.drawable.main_img_2,
        R.drawable.main_img_3
    )

    override fun getCount(): Int {
        return slideImages.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view =
            layoutInflater!!.inflate(R.layout.slide_layout, container, false)
        val slideImage =
            view.findViewById<View>(R.id.slideImage) as ImageView
        slideImage.setImageResource(slideImages[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as RelativeLayout)
    }

}