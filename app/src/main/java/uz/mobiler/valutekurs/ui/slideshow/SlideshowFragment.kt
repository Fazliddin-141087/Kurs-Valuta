package uz.mobiler.valutekurs.ui.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.mobiler.valutekurs.adapters.SpinnerAdapters
import uz.mobiler.valutekurs.database.AppDatabase
import uz.mobiler.valutekurs.databinding.FragmentSlideshowBinding
import uz.mobiler.valutekurs.models.Valute
import kotlin.math.roundToInt

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    lateinit var spinnerAdapters1: SpinnerAdapters
    lateinit var spinnerAdapters2: SpinnerAdapters
    lateinit var list: ArrayList<Valute>
    lateinit var appDatabase: AppDatabase
    private  val TAG = "SlideshowFragment"
    var position=-1
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[SlideshowViewModel::class.java]
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        appDatabase= AppDatabase.getInctance(requireContext())
        list= ArrayList()
        list=appDatabase.valuteDao().getAllValute() as ArrayList

        binding.shungaChiqadi.visibility=View.INVISIBLE

        val posit = arguments?.getInt("position")

        slideshowViewModel.getValute().observe(viewLifecycleOwner) {
            binding.editCurs.visibility=View.VISIBLE
            binding.input.visibility=View.VISIBLE
            binding.nbu1.visibility=View.VISIBLE
            binding.nbu2.visibility=View.VISIBLE
            binding.replace.visibility=View.VISIBLE
            binding.spinnerUsa.visibility=View.VISIBLE
            binding.spinnerUzb.visibility=View.VISIBLE
            binding.tv1.visibility=View.VISIBLE
            binding.tv2.visibility=View.VISIBLE
            binding.progress.visibility=View.INVISIBLE

            if (appDatabase.valuteDao().getAllValute().isEmpty()){
                for (valute in it) {
                    list.add(valute)
                }
            }

            spinnerAdapters1= SpinnerAdapters(list)
            binding.spinnerUsa.adapter=spinnerAdapters1

           list.add(Valute(1, "1","UZS", "07.02.2022 16:00:01","1","1","Uzbekistan"))

            spinnerAdapters2= SpinnerAdapters(list)
            binding.spinnerUzb.adapter=spinnerAdapters2
            binding.spinnerUzb.setSelection(0)

            if (position!=-1){
                binding.spinnerUsa.setSelection(+1)
            }else{
                binding.spinnerUsa.setSelection(1)
            }

            if (posit!=null){
                binding.spinnerUsa.setSelection(posit)
            }

            binding.editCurs.addTextChangedListener { edit->
                if (edit.toString().isNotEmpty()){
                    binding.shungaChiqadi.visibility=View.VISIBLE
                    var valute1 = list[binding.spinnerUsa.selectedItemPosition]
                    var valute2 = list[binding.spinnerUzb.selectedItemPosition]

                    if (valute1.nbu_buy_price.isEmpty()){
                        binding.nbu1.text="${valute1.cb_price} UZS "
                        binding.nbu2.text="${valute1.cb_price} UZS "
                    }else{
                        binding.nbu1.text="${valute1.nbu_buy_price} UZS "
                        binding.nbu2.text="${valute1.nbu_cell_price} UZS "
                    }

                    var a="${((binding.editCurs.text.toString().toDouble()*valute1.cb_price.toDouble())/valute2.cb_price.toDouble())}"
                    var b= (a.toDouble()/1).toInt()
                    var s=((a.toDouble()%1)*100).toInt()

                    binding.shungaChiqadi.text="$b.$s ${valute2.code}"

                }else{
                    binding.shungaChiqadi.visibility=View.INVISIBLE
                    var valute1 = list[binding.spinnerUsa.selectedItemPosition]

                    if (valute1.nbu_buy_price.isEmpty()){
                        binding.nbu1.text="${valute1.cb_price} UZS "
                        binding.nbu2.text="${valute1.cb_price} UZS "
                    }else{
                        binding.nbu1.text="${valute1.nbu_buy_price} UZS "
                        binding.nbu2.text="${valute1.nbu_cell_price} UZS "
                    }

                }
            }

            binding.spinnerUsa.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    var valute1 = list[p2]
                    var valute2 = list[binding.spinnerUzb.selectedItemPosition]

                    if (valute1.nbu_buy_price.isEmpty()){
                        binding.nbu1.text="${valute1.cb_price} UZS "
                        binding.nbu2.text="${valute1.cb_price} UZS "
                    }else{
                        binding.nbu1.text="${valute1.nbu_buy_price} UZS "
                        binding.nbu2.text="${valute1.nbu_cell_price} UZS "
                    }

                    if (binding.editCurs.text.toString().isNotEmpty()){
                        var a="${((binding.editCurs.text.toString().toDouble()*valute1.cb_price.toDouble())/valute2.cb_price.toDouble())}"
                        var b= (a.toDouble()/1).toInt()
                        var s=((a.toDouble()%1)*100).toInt()

                        binding.shungaChiqadi.text="$b.$s ${valute2.code}"

                    }else{
                        binding.shungaChiqadi.visibility=View.INVISIBLE
                    }

                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            binding.spinnerUzb.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    var valute1 = list[binding.spinnerUsa.selectedItemPosition]
                    var valute2 = list[p2]

                    if (valute1.nbu_buy_price.isEmpty()){
                        binding.nbu1.text="${valute1.cb_price} UZS "
                        binding.nbu2.text="${valute1.cb_price} UZS "
                    }else{
                        binding.nbu1.text="${valute1.nbu_buy_price} UZS "
                        binding.nbu2.text="${valute1.nbu_cell_price} UZS "
                    }

                    if (binding.editCurs.text.toString().isNotEmpty()){
                        var a="${((binding.editCurs.text.toString().toDouble()*valute1.cb_price.toDouble())/valute2.cb_price.toDouble())}"
                        var b= (a.toDouble()/1).toInt()
                        var s=((a.toDouble()%1)*100).toInt()

                        binding.shungaChiqadi.text="$b.$s ${valute2.code}"

                    }else{
                        binding.shungaChiqadi.visibility=View.INVISIBLE
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            binding.replace.setOnClickListener {
                val aa = binding.spinnerUzb.selectedItemPosition
                binding.spinnerUzb.setSelection(binding.spinnerUsa.selectedItemPosition,true)
                binding.spinnerUsa.setSelection(aa,true)

                var valute1 = list[binding.spinnerUsa.selectedItemPosition]
                var valute2 = list[binding.spinnerUzb.selectedItemPosition]

                if (valute1.nbu_buy_price.isEmpty()){
                    binding.nbu1.text="${valute1.cb_price} UZS "
                    binding.nbu2.text="${valute1.cb_price} UZS "
                }else{
                    binding.nbu1.text="${valute1.nbu_buy_price} UZS "
                    binding.nbu2.text="${valute1.nbu_cell_price} UZS "
                }

                if (binding.editCurs.text.toString().isNotEmpty()){
                var a="${((binding.editCurs.text.toString().toDouble()*valute1.cb_price.toDouble())/valute2.cb_price.toDouble())}"
                var b= (a.toDouble()/1).toInt()
                var s=((a.toDouble()%1)*100).toInt()

                binding.shungaChiqadi.text="$b.$s ${valute2.code}"

                }else{
                    binding.shungaChiqadi.visibility=View.INVISIBLE
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