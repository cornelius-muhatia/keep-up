package com.cmuhatia.android.keepup.ui.timer

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cmuhatia.android.keepup.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    TimerListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class TimerListDialogFragment(
        private val callback: CallbackInterface) : BottomSheetDialogFragment() {

    private var labelS0: TextView? = null
    private var labelS1: TextView? = null
    private var labelM0: TextView? = null
    private var labelM1: TextView? = null
    private var labelH0: TextView? = null
    private var labelH1: TextView? = null

    private var labelIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_timer_form, container, false)

        labelS0 = root.findViewById(R.id.labelS0)
        labelS1 = root.findViewById(R.id.labelS1)
        labelM0 = root.findViewById(R.id.labelM0)
        labelM1 = root.findViewById(R.id.labelM1)
        labelH0 = root.findViewById(R.id.labelH0)
        labelH1 = root.findViewById(R.id.labelH1)

        val btnPad0: Button = root.findViewById(R.id.btnPad0)
        btnPad0.setOnClickListener{
            setTimeLabel(btnPad0.text.toString())
        }
        val btnPad1 = root.findViewById<Button>(R.id.btnPad1)
        btnPad1.setOnClickListener{
            setTimeLabel(btnPad1.text.toString())
        }
        val btnPad2: Button = root.findViewById(R.id.btnPad2)
        btnPad2.setOnClickListener{
            setTimeLabel(btnPad2.text.toString())
        }
        val btnPad3: Button = root.findViewById(R.id.btnPad3)
        btnPad3.setOnClickListener{
            setTimeLabel(btnPad3.text.toString())
        }
        val btnPad4: Button = root.findViewById(R.id.btnPad4)
        btnPad4.setOnClickListener{
            setTimeLabel(btnPad4.text.toString())
        }
        val btnPad5: Button = root.findViewById(R.id.btnPad5)
        btnPad5.setOnClickListener{
            setTimeLabel(btnPad5.text.toString())
        }
        val btnPad6: Button = root.findViewById(R.id.btnPad6)
        btnPad6.setOnClickListener{
            setTimeLabel(btnPad6.text.toString())
        }
        val btnPad7: Button = root.findViewById(R.id.btnPad7)
        btnPad7.setOnClickListener{
            setTimeLabel(btnPad7.text.toString())
        }
        val btnPad8: Button = root.findViewById(R.id.btnPad8)
        btnPad8.setOnClickListener{
            setTimeLabel(btnPad8.text.toString())
        }
        val btnPad9: Button = root.findViewById(R.id.btnPad9)
        btnPad9.setOnClickListener{
            setTimeLabel(btnPad9.text.toString())
        }

        val backspace: ImageButton = root.findViewById(R.id.btnBackspace)
        backspace.setOnClickListener{
            delTimeLabel()
        }

        val okBtn: ImageButton = root.findViewById(R.id.btnOk)
        okBtn.setOnClickListener{
            val totalSeconds = getTotalSeconds()
            val label = labelH1!!.text.toString() + labelH0!!.text.toString() + ":" +
                    labelM1!!.text.toString() + labelM0!!.text.toString() + ":" + labelS1!!.text.toString() +
                    labelS0!!.text.toString()
            callback.callback(label, totalSeconds)
            super.dismiss()

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        list.layoutManager = GridLayoutManager(context, 2)
//        activity?.findViewById<RecyclerView>(R.id.list)?.adapter =
//            arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }

    }
//
//    private inner class ViewHolder internal constructor(
//        inflater: LayoutInflater,
//        parent: ViewGroup
//    ) : RecyclerView.ViewHolder(
//        inflater.inflate(
//            R.layout.fragment_timer_form_dialog,
//            parent,
//            false
//        )
//    ) {
//
//        internal val text: TextView = itemView.findViewById(R.id.text)
//    }

    /**
     * Updates timer label values
     *
     *
     */
    private fun setTimeLabel(value: String){

        var activeColor = context?.let { ContextCompat.getColor(it, R.color.labelActive) }
        if(activeColor == null){
            activeColor = Color.BLUE
        }
        when (labelIdx) {
            0 -> {
                labelS0!!.text = value
                labelIdx++
//                labelS1!!.setTextColor(activeColor)
            }
            1 -> {
                labelS1!!.text = value
                labelIdx++
//                labelM0!!.setTextColor(activeColor)
//                labelS0!!.setTextColor(Color.GRAY)
            }
            2 -> {
                labelM0!!.text = value
                labelIdx++
//                labelM1!!.setTextColor(activeColor)
//                labelS1!!.setTextColor(Color.GRAY)
            }
            3 -> {
                labelM1!!.text = value
                labelIdx++
//                labelH0!!.setTextColor(activeColor)
            }
            4 -> {
                labelH0!!.text = value
                labelIdx++
//                labelH1!!.setTextColor(activeColor)
            }
            5 -> {
                labelH1!!.text = value
                labelIdx++
            }
        }
    }

    /**
     * Delete values
     */
    private fun delTimeLabel(){
        when (labelIdx) {
            0 -> {
                labelS0!!.text = "0"
            }
            1 -> {
                labelS1!!.text = "0"
                labelIdx--
            }
            2 -> {
                labelM0!!.text = "0"
                labelIdx--
            }
            3 -> {
                labelM1!!.text = "0"
                labelIdx--
            }
            4 -> {
                labelH0!!.text = "0"
                labelIdx--
            }
            else -> {
                labelH1!!.text = "0"
                labelIdx--
            }
        }

    }

    /**
     * Get total number of seconds from timer label
     */
    private fun getTotalSeconds(): Long{
        var totalSeconds: Long = labelH1!!.text.toString().toLong() * 60 * 60 * 10
        totalSeconds += (labelH0!!.text.toString().toLong() * 60 * 60)
        totalSeconds += (labelM1!!.text.toString().toLong() * 60 * 10)
        totalSeconds += (labelM0!!.text.toString().toLong() * 60)
        totalSeconds += (labelS0!!.text.toString().toLong() * 10)
        totalSeconds += labelS0!!.text.toString().toLong()
        return totalSeconds
    }
}

interface  CallbackInterface{
    fun callback(label: String, seconds: Long)
}