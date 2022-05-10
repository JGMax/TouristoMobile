package ru.inteam.touristo.view_pager

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClicksOwner
import ru.inteam.touristo.recycler.holder.ViewTypeFactory
import ru.inteam.touristo.recycler.item.RecyclerItem

class ViewPagerManager internal constructor(
    private val adapter: RecyclerAdapter,
    private val pager: ViewPager2
) : ClicksOwner by adapter {

    val itemCount: Int
        get() = adapter.itemCount

    val currentItemPosition: Int
        get() = pager.currentItem

    fun setCurrentItem(position: Int, isSmoothScroll: Boolean = true) {
        pager.setCurrentItem(position, isSmoothScroll)
    }

    fun setCurrentItem(item: RecyclerItem, isSmoothScroll: Boolean = true) {
        val position = adapter.positionOf(item)
        setCurrentItem(position, isSmoothScroll)
    }

    fun onPageChange(): Flow<Int> = callbackFlow {
        val listener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                trySend(position)
            }
        }
        pager.registerOnPageChangeCallback(listener)
        invokeOnClose { pager.unregisterOnPageChangeCallback(listener) }
    }

    fun currentItem(): RecyclerItem {
        return adapter[currentItemPosition]
    }

    fun submitList(items: List<RecyclerItem>, onCommit: () -> Unit = {}) {
        adapter.submitList(items, onCommit)
    }
}

class ViewPagerManagerBuilder internal constructor(private var pager: ViewPager2?) {
    private var diffCallback: DiffUtil.ItemCallback<RecyclerItem>? = null
    private var adapter: RecyclerAdapter? = null
    private var decorations: Array<out RecyclerView.ItemDecoration> = emptyArray()
    private var offscreenPageLimit: Int = 3
    private var orientation: Int = ViewPager2.ORIENTATION_HORIZONTAL
    private var pageTransformer: ViewPager2.PageTransformer? = null
    private var viewTypeFactory: ViewTypeFactory? = null

    fun viewTypeFactory(viewTypeFactory: ViewTypeFactory): ViewPagerManagerBuilder {
        this.viewTypeFactory = viewTypeFactory
        return this
    }

    fun diffCallback(
        diffCallback: DiffUtil.ItemCallback<RecyclerItem>
    ): ViewPagerManagerBuilder {
        this.diffCallback = diffCallback
        return this
    }

    fun adapter(adapter: RecyclerAdapter): ViewPagerManagerBuilder {
        this.adapter = adapter
        return this
    }

    fun decorations(vararg decorations: RecyclerView.ItemDecoration): ViewPagerManagerBuilder {
        this.decorations = decorations
        return this
    }

    fun pageTransformer(pageTransformer: ViewPager2.PageTransformer): ViewPagerManagerBuilder {
        this.pageTransformer = pageTransformer
        return this
    }

    fun pageTransformer(pageTransformer: (View, Float) -> Unit): ViewPagerManagerBuilder {
        this.pageTransformer = ViewPager2.PageTransformer { page, position ->
            pageTransformer(page, position)
        }
        return this
    }

    fun orientation(orientation: Int): ViewPagerManagerBuilder {
        this.orientation = orientation
        return this
    }

    fun offScreenPageLimit(offscreenPageLimit: Int): ViewPagerManagerBuilder {
        this.offscreenPageLimit = offscreenPageLimit
        return this
    }

    fun build(): ViewPagerManager {
        val adapter = adapter ?: RecyclerAdapter(null, diffCallback, viewTypeFactory)
        pager?.let {
            it.offscreenPageLimit = offscreenPageLimit
            it.adapter = adapter
            it.setPageTransformer(pageTransformer)
            decorations.forEach(it::addItemDecoration)
        }
        val manager = ViewPagerManager(adapter, pager!!)
        pager = null
        return manager
    }
}

fun ViewPager2.managerBuilder(): ViewPagerManagerBuilder {
    return ViewPagerManagerBuilder(this)
}

fun ViewPager2.manager(): ViewPagerManager = managerBuilder().build()
