package poc.bringme.groupie.ui

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_groups.*
import poc.bringme.groupie.R
import poc.bringme.groupie.data.Data
import poc.bringme.groupie.interactor.GroupCallbacks
import poc.bringme.groupie.interactor.Listeners
import poc.bringme.groupie.item.MessageGroupHeader
import poc.bringme.groupie.item.decoration.InsetItemDecoration
import poc.bringme.groupie.utils.AdapterUtils
import poc.bringme.groupie.utils.dummyMessage
import poc.bringme.groupie.utils.dummyMessageGroup

class GroupsActivity : AppCompatActivity(), GroupsView {

    private val data = Data()
    private val groupAdapter = GroupAdapter<ViewHolder>()

    private var gutter = 0
    private lateinit var groupLayoutManager: GridLayoutManager
    private lateinit var callbacks: GroupCallbacks
    private lateinit var listeners: Listeners
    private lateinit var adapterUtils: AdapterUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.title_groups)

        initVars()
        initListeners()
        initListView()
        initData()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_groups, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.addGroupChild -> {
                onAddGroupChildClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAddGroupChildClicked() {
        recyclerView.scrollToPosition(0)
        val item = groupAdapter.getItem(0)
        if (item is MessageGroupHeader) {
            val expandableGroup = item.getExpandableGroup()
            expandableGroup.add(dummyMessage())
        }
    }

    override fun onSwipeRemove() {
        Snackbar.make(contentView, getString(R.string.messages_delete_success), Snackbar.LENGTH_LONG).addCallback(
                object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {}
                })
                .setAction(R.string.btn_undo, { onUndoRemove() })
                .show()
    }

    override fun onUndoRemove() {
        // TODO implement
    }

    override fun onFabClick() {
        groupAdapter.add(0, dummyMessageGroup())
        recyclerView.scrollToPosition(0)
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = false
        Handler().postDelayed({
            groupAdapter.clear()
            recreate()
        }, 250)
    }

    private fun initData() {
        groupAdapter.clear()
        adapterUtils.addFirstPageOfGroups()
    }

    private fun initVars() {
        groupLayoutManager = GridLayoutManager(this, 1)
        callbacks = GroupCallbacks(this)
        listeners = Listeners(this, groupAdapter, groupLayoutManager, data)
        adapterUtils = AdapterUtils(groupAdapter, data)
        gutter = resources.getDimensionPixelSize(R.dimen.padding_small)
    }

    private fun initListeners() {
        fab.setOnClickListener { onFabClick() }
        refreshLayout.setOnRefreshListener({ onRefresh() })
    }

    private fun initListView() {
        groupAdapter.apply {
            setOnItemClickListener(listeners.onItemClick)
        }

        recyclerView.apply {
            layoutManager = groupLayoutManager
            addItemDecoration(InsetItemDecoration(gutter))
            adapter = groupAdapter
        }

        ItemTouchHelper(callbacks.swipe(groupAdapter)).attachToRecyclerView(recyclerView)
    }
}
