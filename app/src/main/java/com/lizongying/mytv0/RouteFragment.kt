package com.lizongying.mytv0

import MainViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lizongying.mytv0.databinding.RouteBinding

class RouteFragment : Fragment(), RouteAdapter.ItemListener {
    private var _binding: RouteBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 5000L

    private lateinit var routeAdapter: RouteAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RouteBinding.inflate(inflater, container, false)
        val application = requireActivity().applicationContext as MyTVApplication

        val listWidth = application.px2Px(binding.list.layoutParams.width)
        binding.list.layoutParams.width = if (SP.compactMenu) {
            listWidth * 4 / 5
        } else {
            listWidth
        }

        binding.routeContainer.setOnClickListener {
            hideSelf()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireActivity()
        viewModel = ViewModelProvider(context)[MainViewModel::class.java]
        onVisible()
    }

    fun onVisible() {
        val context = context ?: return
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        }

        val tvModel = viewModel.groupModel.getCurrent() ?: return
        val currentVideoIndex = tvModel.videoIndexValue

        routeAdapter = RouteAdapter(
            context,
            binding.list,
            tvModel,
            currentVideoIndex
        )
        binding.list.adapter = routeAdapter
        binding.list.layoutManager = LinearLayoutManager(context)
        routeAdapter.setItemListener(this)

        routeAdapter.toPosition(currentVideoIndex)

        resetTimer()
    }

    fun hideSelf() {
        handler.removeCallbacks(hideRunnable)
        if (isAdded && !isHidden) {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .commitAllowingStateLoss()
        }
    }

    private val hideRunnable = Runnable {
        hideSelf()
    }

    fun resetTimer() {
        handler.removeCallbacks(hideRunnable)
        handler.postDelayed(hideRunnable, delay)
    }

    override fun onItemClicked(position: Int) {
        hideSelf()
        (activity as MainActivity).switchRoute(position)
    }

    override fun onDismissRequest() {
        hideSelf()
    }

    override fun onKeyAction() {
        resetTimer()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            handler.removeCallbacks(hideRunnable)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(hideRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(hideRunnable)
        _binding = null
    }

    companion object {
        const val TAG = "RouteFragment"
    }
}
