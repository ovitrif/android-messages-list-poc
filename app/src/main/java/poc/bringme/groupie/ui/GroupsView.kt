package poc.bringme.groupie.ui

import poc.bringme.groupie.interactor.GroupCallbacks

interface GroupsView : GroupCallbacks.SwipeCallback {

    fun onRefresh()
    fun onUndoRemove()
    fun onFabClick()
    fun onAddGroupChildClicked()
}
