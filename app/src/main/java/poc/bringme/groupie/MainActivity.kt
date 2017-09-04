package poc.bringme.groupie

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import poc.bringme.groupie.data.Data
import poc.bringme.groupie.interactor.Callbacks
import poc.bringme.groupie.interactor.Listeners
import poc.bringme.groupie.item.decoration.HeaderItemDecoration
import poc.bringme.groupie.item.decoration.InsetItemDecoration
import poc.bringme.groupie.utils.AdapterUtils
import poc.bringme.groupie.utils.dummyMessageGroup
import poc.bringme.groupie.utils.dummyMessageSection

val INSET_TYPE_KEY = "inset_type"
val INSET = "inset"
val PAGE_COUNT = 5

class MainActivity : AppCompatActivity(), MainView {

    private val data = Data()
    private val groupAdapter = GroupAdapter<ViewHolder>()

    private var gutter = 0
    private lateinit var groupLayoutManager: GridLayoutManager
    private lateinit var callbacks: Callbacks
    private lateinit var listeners: Listeners
    private lateinit var adapterUtils: AdapterUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        groupLayoutManager = GridLayoutManager(this, 1)

        callbacks = Callbacks(this)
        listeners = Listeners(this, groupAdapter, groupLayoutManager, data)
        adapterUtils = AdapterUtils(groupAdapter, data)
        gutter = resources.getDimensionPixelSize(R.dimen.padding_small)

        fab.setOnClickListener { onFabClick() }
        refreshLayout.setOnRefreshListener({ onRefresh() })

        initListView()

        adapterUtils.addFirstPageOfMessages()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_terms, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addGroup -> {
                onAddGroupClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAddGroupClicked() {
        groupAdapter.add(0, dummyMessageGroup())
        recyclerView.scrollToPosition(0)

    }

    override fun onRefresh() {
        adapterUtils.clear()
        groupAdapter.add(data.createRandomMessages(6))
        groupAdapter.add(data.loader)
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(listeners.onBottomReached().apply { reset() })
        refreshLayout.isRefreshing = false
    }

    override fun onSwipeRemove(payload: Callbacks.SwipeRemovePayload) {
        Snackbar.make(contentView, getString(R.string.messages_delete_success), Snackbar.LENGTH_LONG).addCallback(
                object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            // TODO onDeleteMessageAccepted
                        }
                    }
                })
                .setAction(R.string.btn_undo, { onUndoRemove(payload) })
                .show()
    }

    override fun onUndoRemove(payload: Callbacks.SwipeRemovePayload) {
        payload.section.add(payload.position, payload.item)
    }

    override fun onFabClick() {
        groupAdapter.add(0, dummyMessageSection())
        recyclerView.scrollToPosition(0)
    }

    private fun initListView() {
        groupAdapter.apply {
            setOnItemClickListener(listeners.onItemClick)
        }

        recyclerView.apply {
            layoutManager = groupLayoutManager
            addItemDecoration(HeaderItemDecoration(gutter, R.layout.item_header))
            addItemDecoration(InsetItemDecoration(gutter, INSET_TYPE_KEY, INSET))
            adapter = groupAdapter

            addOnScrollListener(listeners.onBottomReached())
        }

        ItemTouchHelper(callbacks.swipe(groupAdapter, data.messageSection)).attachToRecyclerView(recyclerView)
    }
}
