package com.lizongying.mytv0

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizongying.mytv0.databinding.RouteItemBinding
import com.lizongying.mytv0.models.TVModel

class RouteAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val tvModel: TVModel,
    private val currentIndex: Int,
) : RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    private var listener: ItemListener? = null
    val application = context.applicationContext as MyTVApplication
    private val uris = tvModel.tv.uris

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = RouteItemBinding.inflate(inflater, parent, false)

        binding.icon.layoutParams.width = application.px2Px(binding.icon.layoutParams.width)
        binding.icon.layoutParams.height = application.px2Px(binding.icon.layoutParams.height)
        binding.icon.setPadding(application.px2Px(binding.icon.paddingTop))

        binding.title.layoutParams.width = application.px2Px(binding.title.layoutParams.width)
        binding.title.layoutParams.height = application.px2Px(binding.title.layoutParams.height)
        binding.title.textSize = application.px2PxFont(binding.title.textSize)

        binding.tag.layoutParams.height = application.px2Px(binding.tag.layoutParams.height)
        binding.tag.textSize = application.px2PxFont(binding.tag.textSize)

        return ViewHolder(context, binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val isCurrent = position == currentIndex
        viewHolder.bind(position, isCurrent)
        val view = viewHolder.itemView

        view.isFocusable = true
        view.isFocusableInTouchMode = true

        view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            viewHolder.focus(hasFocus, isCurrent)
            if (hasFocus) {
                listener?.onKeyAction()
            }
        }

        view.setOnClickListener {
            listener?.onItemClicked(position)
        }

        view.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                listener?.onKeyAction()
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK,
                    KeyEvent.KEYCODE_ESCAPE,
                    KeyEvent.KEYCODE_DPAD_LEFT,
                    KeyEvent.KEYCODE_DPAD_RIGHT,
                    KeyEvent.KEYCODE_MENU -> {
                        listener?.onDismissRequest()
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_ENTER,
                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        listener?.onItemClicked(position)
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        if (position == 0) {
                            val lastPos = itemCount - 1
                            scrollToPositionAndSelect(lastPos)
                            return@setOnKeyListener true
                        }
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        if (position == itemCount - 1) {
                            val firstPos = 0
                            scrollToPositionAndSelect(firstPos)
                            return@setOnKeyListener true
                        }
                    }
                }
            }
            false
        }
    }

    override fun getItemCount() = uris.size

    class ViewHolder(private val context: Context, val binding: RouteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, isCurrent: Boolean) {
            binding.title.text = "线路 ${position + 1}"
            if (isCurrent) {
                binding.icon.visibility = View.VISIBLE
                binding.icon.setImageResource(R.drawable.baseline_done_24)
                binding.icon.setColorFilter(ContextCompat.getColor(context, R.color.focus))
                binding.tag.visibility = View.VISIBLE
                binding.tag.text = "当前"
                binding.tag.setTextColor(ContextCompat.getColor(context, R.color.description_blur))
            } else {
                binding.icon.visibility = View.INVISIBLE
                binding.tag.visibility = View.GONE
            }
            focus(false, isCurrent)
        }

        fun focus(hasFocus: Boolean, isCurrent: Boolean) {
            if (hasFocus) {
                binding.root.setBackgroundResource(R.color.focus)
                binding.title.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.icon.setColorFilter(ContextCompat.getColor(context, R.color.white))
                binding.tag.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.root.setBackgroundResource(R.color.blur)
                binding.title.setTextColor(ContextCompat.getColor(context, R.color.title_blur))
                binding.icon.setColorFilter(ContextCompat.getColor(context, R.color.focus))
                binding.tag.setTextColor(ContextCompat.getColor(context, R.color.description_blur))
            }
        }
    }

    fun toPosition(position: Int) {
        scrollToPositionAndSelect(position)
    }

    private fun scrollToPositionAndSelect(position: Int) {
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        layoutManager?.let {
            recyclerView.post {
                it.scrollToPositionWithOffset(position, 0)
                recyclerView.postDelayed({
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    viewHolder?.itemView?.isSelected = true
                    viewHolder?.itemView?.requestFocus()
                }, 50)
            }
        }
    }

    interface ItemListener {
        fun onItemClicked(position: Int)
        fun onDismissRequest()
        fun onKeyAction()
    }

    fun setItemListener(listener: ItemListener) {
        this.listener = listener
    }

    companion object {
        private const val TAG = "RouteAdapter"
    }
}
