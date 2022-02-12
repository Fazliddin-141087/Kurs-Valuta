package uz.mobiler.valutekurs.ui.gallery

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.adapters.GalleryRvAdapters
import uz.mobiler.valutekurs.database.AppDatabase
import uz.mobiler.valutekurs.databinding.FragmentGalleryBinding
import uz.mobiler.valutekurs.models.Valute

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    lateinit var galleryRvAdapters: GalleryRvAdapters
    lateinit var list: ArrayList<Valute>
    lateinit var appDatabase: AppDatabase
    private  val TAG = "GalleryFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[GalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        appDatabase = AppDatabase.getInctance(requireContext())
        list = ArrayList()
        list = appDatabase.valuteDao().getAllValute() as ArrayList

        galleryRvAdapters =
            GalleryRvAdapters(list, object : GalleryRvAdapters.MyOnItemClickListener {
                override fun onItemClick(valute: Valute, position: Int) {
                    val bundle = Bundle()
                    bundle.putSerializable("valute", valute)
                    bundle.putInt("position", position)
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.nav_slideshow, bundle)
                }
            })

        binding.rvGallery.adapter = galleryRvAdapters

        galleryViewModel.getValute().observe(viewLifecycleOwner) {
            binding.progress.visibility = View.INVISIBLE
            binding.rvGallery.visibility = View.VISIBLE
            if (appDatabase.valuteDao().getAllValute().isEmpty()) {
                for (valute in it) {
                    appDatabase.valuteDao().insertValue(valute)
                    list.add(valute)
                }
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        val actionMenu = menu.findItem(R.id.search)
        val searchView = actionMenu.actionView as SearchView
        val editText = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        editText.hint="Search..."
        editText.setHintTextColor(Color.LTGRAY)
        editText.setBackgroundResource(android.R.color.transparent)
        editText.setTextColor(Color.BLACK)
        editText.addTextChangedListener {
            filters(it.toString())
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filters(code:String){
        val temp:MutableList<Valute> = ArrayList()
        for (valute in list) {
            if (valute.code.lowercase().contains(code.lowercase())){
                temp.add(valute)
            }
        }
      galleryRvAdapters.updateList(temp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

