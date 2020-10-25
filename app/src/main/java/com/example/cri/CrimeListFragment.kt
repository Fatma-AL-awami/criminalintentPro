package com.example.cri

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cri.CrimeListFragment.Companion.newInstance
import kotlinx.android.synthetic.main.crime_list_fragment.*
import java.util.*

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    var empty:Boolean = false
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }
    
    private var callbacks: Callbacks? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.crime_list_fragment, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        //  updateUI()
        return view
    }



    private fun updateUI(crimes: List<Crime>) {

        if(crimes.size==0) {
           emptyView.visibility=View.VISIBLE
            crime_recycler_view.visibility=View.GONE
            newCrime.visibility=View.VISIBLE

        }else{
            emptyView.visibility= View.GONE
            newCrime.visibility=View.GONE
            crime_recycler_view.visibility=View.VISIBLE

            adapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = adapter
            val adapt=  crimeRecyclerView.adapter as CrimeAdapter
            adapt.submitList(crimes)}

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(ContentValues.TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)

                }
            })

        newCrime.setOnClickListener {

            val crime = Crime()
            crimeListViewModel.addCrime(crime)
            callbacks?.onCrimeSelected(crime.id)
            true
        }
    }



    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var crime: Crime
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {

            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }


        override fun onClick(v: View) {

            //Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()

            callbacks?.onCrimeSelected(crime.id)
        }
    }
   /*  private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }
        override fun getItemCount() = crimes.size
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]

            holder.bind(crime)
        }}*/
   private inner class CrimeAdapter(var crimes: List<Crime>) : ListAdapter<Crime,CrimeHolder>(CrimeDiffutil()) {

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
         //  val toast = Toast.makeText(context,(crimes.size).toString() , Toast.LENGTH_LONG)
           //toast.show()

           val  view= layoutInflater.inflate(R.layout.list_item_crime, parent, false)
           return CrimeHolder(view)
       }
       override fun getItemCount() = crimes.size
       override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
           val crime = crimes[position]

           holder.bind(crime)
       }
   }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                crimeListViewModel.addCrime(crime)
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}