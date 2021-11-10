package com.ismin.csproject

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FountainListFragment : Fragment(), FountainAdapter.OnItemClickListener {
    private lateinit var ftnShelf: ArrayList<Fountain>
    private var favShelf = arrayListOf<String>()
    private lateinit var rcvFountains: RecyclerView
    private lateinit var ftnAdapter: FountainAdapter

    private var listener: FountainListListener? = null

    interface FountainListListener {
        //fun favFromFragment(name: String)
        fun refresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FountainListListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement MyActivityCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ftnShelf = arguments!!.getSerializable("candies") as ArrayList<Fountain>
        favShelf = arguments!!.getSerializable("favs") as ArrayList<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_fountain_list, container, false)

        this.rcvFountains = rootView.findViewById(R.id.a_rcv_fountains)
        ftnAdapter = FountainAdapter(ftnShelf, favShelf, this)
        this.rcvFountains.adapter = FountainAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvFountains.layoutManager = linearLayoutManager


        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvFountains.addItemDecoration(dividerItemDecoration)

        return rootView
    }


    override fun onItemClick(position: Int) {
        val ftn: Fountain = ftnShelf[position]
        Toast.makeText(context, "Item ${ftn.id} clicked", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", ftn)
        intent.putExtra("fav", favShelf.contains(ftn.id))
        this.startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            var newfav = data?.getBooleanExtra("fav", false)
            var name = data?.getStringExtra("fountainname")
            if (name != null) {
                if (newfav!!) {
                    if (!favShelf.contains(name)) {
                        favShelf.add(name)
                    }
                } else {
                    if (favShelf.contains(name)) {
                        favShelf.remove(name)
                    }
                }
                listener?.refresh()
            }
        }
    }

    override fun favFromAdapter(position: Int) {
        val ftn: Fountain = ftnShelf[position]
        Toast.makeText(context, "Item ${ftn.id} faved", Toast.LENGTH_SHORT).show()
        if (favShelf.contains(ftn.id)) {
            favShelf.remove(ftn.id)
        } else {
            favShelf.add(ftn.id)
        }
        listener?.favFromFragment(ftn.id)
    }

    companion object {
        @JvmStatic
        fun newInstance(ftnShelf: ArrayList<Fountain>, favs: ArrayList<String>): FountainListFragment {
            val bundle = Bundle()
            bundle.putSerializable("fountains", ftnShelf)
            bundle.putStringArrayList("favs", favs)

            val ftnListFragment = FountainListFragment()
            ftnListFragment.arguments = bundle
            return ftnListFragment;
        }
    }
}