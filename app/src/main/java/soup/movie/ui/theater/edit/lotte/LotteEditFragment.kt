package soup.movie.ui.theater.edit.lotte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import soup.movie.data.model.Theater
import soup.movie.databinding.TheaterEditChildFragmentBinding
import soup.movie.ui.theater.edit.TheaterEditChildFragment
import soup.movie.ui.theater.edit.TheaterEditChildListAdapter
import soup.movie.util.observe

class LotteEditFragment : TheaterEditChildFragment() {

    override val title: String = "롯데시네마"

    private val viewModel: LotteEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TheaterEditChildFragmentBinding.inflate(inflater, container, false)
            .also { setupListener(it) }
            .root
    }

    private fun setupListener(binding: TheaterEditChildFragmentBinding) {
        val listAdapter = TheaterEditChildListAdapter(object : TheaterEditChildListAdapter.Listener {

            override fun add(theater: Theater): Boolean {
                return viewModel.add(theater)
            }

            override fun remove(theater: Theater) {
                viewModel.remove(theater)
            }
        })
        binding.listView.adapter = listAdapter
        viewModel.uiModel.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.areaGroupList, it.selectedTheaterIdSet)
        }
    }
}
