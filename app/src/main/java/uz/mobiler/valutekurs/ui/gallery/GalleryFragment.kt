package uz.mobiler.valutekurs.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInctance(requireContext())
        list = ArrayList()
        list = appDatabase.valuteDao().getAllValute() as ArrayList

        galleryRvAdapters = GalleryRvAdapters(list)
        binding.rvGallery.adapter=galleryRvAdapters

        galleryViewModel.getValute().observe(viewLifecycleOwner) {
            binding.progress.visibility=View.INVISIBLE
            binding.rvGallery.visibility=View.VISIBLE
            if (appDatabase.valuteDao().getAllValute().isEmpty()){
                for (valute in it) {
                    appDatabase.valuteDao().insertValue(valute)
                    list.add(valute)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}