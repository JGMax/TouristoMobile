package ru.inteam.touristo.feature.post_creation.location_selector.ui.recycler.model

import androidx.core.view.isGone
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.databinding.LocationSelectorLocationItemBinding
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewTypeInitializer
import ru.inteam.touristo.recycler.holder.binding
import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class LocationSelectorLocationItem(
    val id: String,
    private val title: String,
    private val subtitle: String,
    private val distance: String
) : RecyclerItem({ LocationSelectorLocationItemViewInit() }) {
    override val layoutId: Int = R.layout.location_selector_location_item

    override fun bind(holder: RecyclerViewHolder) =
        holder.binding<LocationSelectorLocationItemBinding> {
            titleTv.text = title
            subtitleTv.text = subtitle
            subtitleTv.isGone = subtitle.isEmpty()
            distanceBtn.text = distance
        }
}

internal class LocationSelectorLocationItemViewInit : ViewTypeInitializer() {

    override fun init(
        holder: RecyclerViewHolder
    ) = holder.binding(LocationSelectorLocationItemBinding.bind(holder.itemView)) {
        clicks(holder.itemView, holder)
    }
}
